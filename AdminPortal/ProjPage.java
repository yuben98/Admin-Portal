import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;

public class ProjPage extends JFrame implements ActionListener{

    boolean hasDeps;
    Connection conn;
    JTextField[] pnos;
    JTextField[] hrs;
    JPanel projPanel;
    JPanel btns;
    JButton moreProj, btn, removeBtn;
    String ssn;
    int count;

    public ProjPage(Connection con, String num, boolean deps) throws Exception {
        conn=con;
        ssn=num;
        hasDeps=deps;

        count=0;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLayout(new FlowLayout());
        this.setTitle("Assign Employee to Projects");
        this.setVisible(true);
        this.setResizable(false);

        projPanel=new JPanel();
        projPanel.setSize(300,600);
        projPanel.setPreferredSize(new Dimension(300,600));
        projPanel.setLayout(new FlowLayout());
        projPanel.setBackground(Color.LIGHT_GRAY);


        btns=new JPanel();
        btns.setPreferredSize(new Dimension(300,600));
        btns.setSize(300,600);

        this.add(projPanel);
        this.add(btns);

        pnos=new JTextField[10];
        hrs=new JTextField[10];

        moreProj = new JButton("More Projects");
        moreProj.setBounds(340, 200, 200, 80);
        moreProj.setFocusable(false);
        moreProj.addActionListener(this);
        btns.add(moreProj);
        addProjFields();

        btn = new JButton("Confirm changes and leave screen");
        btn.setBounds(550,200,200,80);
        btn.setFocusable(false);
        btn.addActionListener(this);
        btns.add(btn);

        removeBtn = new JButton("Remove Last Project");
        removeBtn.setBounds(700,200,200,80);
        removeBtn.setFocusable(false);
        removeBtn.addActionListener(this);
        btns.add(removeBtn);

        this.validate();
    }

    public void addProjFields(){
        pnos[this.count]=new JTextField("Project Number");
        pnos[this.count].setPreferredSize(new Dimension(100,40));
        pnos[this.count].setBounds(47, 10+(count*50), 100, 40);

        hrs[this.count]=new JTextField("Hours");
        hrs[this.count].setPreferredSize(new Dimension(100,40));
        hrs[this.count].setBounds(152, 10+(count*50), 100, 40);


        projPanel.add(pnos[this.count]);
        projPanel.add(hrs[this.count]);
        projPanel.revalidate();
        projPanel.repaint();
        this.count++;
    }

    public void remProjFields(){
        this.count--;
        projPanel.remove(pnos[this.count]);
        projPanel.remove(hrs[this.count]);
        projPanel.revalidate();
        projPanel.repaint();
    }

    public float checkTotal() {
        float total=0;
        for (int i=0;i<this.count;i++) {
            total =total + Float.parseFloat(hrs[i].getText());
        }
        return total;
    }

    public void insertWorks() throws Exception{
        Statement s=conn.createStatement();
        String query="insert into works_on values ('";
        query=query.concat(this.ssn);
        query=query.concat("', ");
        String sql=query;
        for (int i=0;i<this.count;i++) {
            sql=query;
            sql=sql.concat(pnos[i].getText()).concat(", ").concat(hrs[i].getText()).concat(")");
            s.executeUpdate(sql);
        }
    }

    public boolean checkWorks() throws Exception{
        Statement s=conn.createStatement();
        String query="select count(*) from works_on where essn='";
        query=query.concat(this.ssn).concat("'");
        ResultSet r= s.executeQuery(query);
        r.next();
        return (r.getInt(1) == this.count);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==moreProj) {
            if (count<10) addProjFields();
        }
        if (e.getSource()==removeBtn) {
            if (count >1) remProjFields();
        }
        if (e.getSource()==btn){
            try{
            if (checkTotal()<=40) {
                insertWorks();
                if (checkWorks()) {
                    JOptionPane.showMessageDialog(null, "Employee Projects Updated Successfully.", "SUCCESS" , JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    if (this.hasDeps) new DepPage(conn, ssn);
                    else new ReportPage(conn, ssn);
                }
                else JOptionPane.showMessageDialog(null, "Employee Projects not Updated Successfully. Please Recheck input", "FAILURE" , JOptionPane.ERROR_MESSAGE);
            }
            else JOptionPane.showMessageDialog(null, "Employees are not Slaves. 40 hours max.", "SLAVE OWNER ALERT" , JOptionPane.ERROR_MESSAGE);
            }
            
            catch(Exception excp) {}
        }

    }
}
