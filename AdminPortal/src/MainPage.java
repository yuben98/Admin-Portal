import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.sql.*;


public class MainPage extends JFrame implements ActionListener{
    JButton btn;
    JTextField txt;
    Connection conn;

    MainPage(Connection con) throws Exception{
        
        conn=con;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLayout(new FlowLayout());

        btn = new JButton("Login");
        btn.setBounds(500,200,100,80);
        btn.setFocusable(false);
        btn.addActionListener(this);

        txt = new JTextField("Input Manager SSN");
        txt.setPreferredSize(new Dimension(250,40));

        
        this.add(txt);
        this.add(btn);

        this.setTitle("Manager Login");
        this.setVisible(true);
        this.setResizable(false);
    }

    public int checkManager(String ssn) throws Exception{
        Statement s=conn.createStatement();
        String query="select dnumber from department where mgrssn='";
        query=query.concat(ssn).concat("'");
        ResultSet r= s.executeQuery(query);

        if (r.next()) return (r.getInt(1));
        else return 0;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        int dno=0;
        if(e.getSource()==btn) {
            try{
            dno=checkManager(txt.getText());
            }
            catch (Exception excp) {}
            if (dno != 0){
                try{
                new EmpPage(conn);
                }
                catch(Exception excp) {}
            }
            else JOptionPane.showMessageDialog(null, "No manager exists with that SSN.", "NICE TRY" , JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }

    }

}
