import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.sql.*;


public class DepPage extends JFrame implements ActionListener{

    Connection conn;
    String ssn;
    JTextField[] dnames;
    JTextField[] sx;
    JTextField[] bdates;
    JTextField[] rships;
    JPanel depPanel;
    JPanel btnPanel;
    JButton moreDep, endBtn, rmBtn;
    int count;

    public DepPage(Connection con, String num) throws Exception {
        conn=con;
        ssn=num;
        count=0;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setTitle("Add Employee Dependents");
        this.setVisible(true);
        this.setResizable(false);

        depPanel=new JPanel();
        depPanel.setSize(500,600);
        depPanel.setPreferredSize(new Dimension(500,600));
        depPanel.setLayout(new FlowLayout(FlowLayout.CENTER,25,15));
        depPanel.setBackground(Color.LIGHT_GRAY);

        btnPanel=new JPanel();
        btnPanel.setSize(300,600);
        btnPanel.setPreferredSize(new Dimension(250,600));
        btnPanel.setLayout(new GridLayout(3,1));
        btnPanel.setBackground(Color.BLACK);
        btnPanel.setVisible(true);

        this.add(depPanel);
        this.add(btnPanel);

        dnames=new JTextField[10];
        sx=new JTextField[10];
        bdates=new JTextField[10];
        rships=new JTextField[10];


        moreDep = new JButton("More Dependents");
        moreDep.setSize(200, 80);
        moreDep.setFocusable(false);
        moreDep.addActionListener(this);
        btnPanel.add(moreDep);

        addDepFields();

        endBtn = new JButton("Confirm Changes and Proceed");
        endBtn.setSize(200,80);
        endBtn.setFocusable(false);
        endBtn.addActionListener(this);
        btnPanel.add(endBtn);

        rmBtn = new JButton("Remove Last Dependent Fields");
        rmBtn.setSize(200,80);
        rmBtn.setFocusable(false);
        rmBtn.addActionListener(this);
        btnPanel.add(rmBtn);

        this.validate();
    }

    public void addDepFields(){
        dnames[this.count]=new JTextField("Dep. First Name");
        dnames[this.count].setPreferredSize(new Dimension(100,40));

        sx[this.count]=new JTextField("Sex (1 char)");
        sx[this.count].setPreferredSize(new Dimension(100,40));

        bdates[this.count]=new JTextField("BD: YYYY-MM-DD");
        bdates[this.count].setPreferredSize(new Dimension(100,40));

        rships[this.count]=new JTextField("Relationship");
        rships[this.count].setPreferredSize(new Dimension(100,40));

        depPanel.add(dnames[this.count]);
        depPanel.add(sx[this.count]);
        depPanel.add(bdates[this.count]);
        depPanel.add(rships[this.count]);
        
        depPanel.revalidate();
        depPanel.repaint();

        this.count++;
    }

    public void remDepFields(){
        this.count--;
        depPanel.remove(dnames[this.count]);
        depPanel.remove(sx[this.count]);
        depPanel.remove(bdates[this.count]);
        depPanel.remove(rships[this.count]);

        depPanel.revalidate();
        depPanel.repaint();
    }

    public void insertDeps() throws Exception{
        Statement s=conn.createStatement();
        String query="insert into dependent values ('";
        query=query.concat(this.ssn);
        query=query.concat("', '");
        String sql;
        for (int i=0;i<this.count;i++) {
            sql=query;
            sql=sql.concat(dnames[i].getText()).concat("', '").concat(sx[i].getText()).concat("', ");
            sql=sql.concat("TO_DATE('"+ bdates[i].getText()+"','YYYY-MM-DD'), '");
            sql=sql.concat(rships[i].getText()).concat("')");
            s.executeUpdate(sql);
        }
    }

    public boolean checkDeps() throws Exception {
        Statement s=conn.createStatement();
        String query="select count(*) from dependent where essn='";
        query=query.concat(this.ssn).concat("'");
        ResultSet r= s.executeQuery(query);
        r.next();
        return (r.getInt(1) == this.count);
    } 

    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==moreDep) {
            if (count<10) addDepFields();
        }
        if (e.getSource()==rmBtn) {
            if (count >1) remDepFields();
        }
        if (e.getSource()==endBtn) {
            try{
                insertDeps();
                if (checkDeps()) {
                    JOptionPane.showMessageDialog(null, "Employee Dependents Updated Successfully.", "SUCCESS" , JOptionPane.INFORMATION_MESSAGE);
                    this.dispose();
                    new ReportPage(conn, ssn);
                }
                else JOptionPane.showMessageDialog(null, "Employee Dependents not Updated Successfully. Please Recheck input", "FAILURE" , JOptionPane.ERROR_MESSAGE);
                }
                
                catch(Exception excp) {}
        }

    }
}
