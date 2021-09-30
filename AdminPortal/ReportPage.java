import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.sql.*;


public class ReportPage extends JFrame{

    Connection conn;
    String ssn;

    public ReportPage(Connection con, String num) throws Exception {
        conn=con;
        ssn=num;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setTitle("Employee Report");
        this.setVisible(true);
        this.setResizable(false);

        JPanel panel=new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextArea emp=new JTextArea(empRep());
        emp.setFont(emp.getFont().deriveFont(14f));
        emp.setEditable(false);
        emp.setWrapStyleWord(true);

        JTextArea proj=new JTextArea(projRep());
        proj.setFont(proj.getFont().deriveFont(14f));
        proj.setEditable(false);
        proj.setWrapStyleWord(true);

        JTextArea dep=new JTextArea(depRep());
        dep.setFont(dep.getFont().deriveFont(14f));
        dep.setEditable(false);
        dep.setWrapStyleWord(true);

        panel.add(emp);
        panel.add(proj);
        panel.add(dep);

        this.getContentPane().add(panel);
        this.validate();
        conn.close();
    }

    public String empRep() throws Exception {
        String result="";
        Statement s=conn.createStatement();
        String query="select * from employee where ssn='";
        query=query.concat(ssn).concat("'");
        ResultSet r= s.executeQuery(query);
        r.next();
        result="-----------Employee Data-----------\n\n";
        result=result.concat("First Name: ").concat(r.getString(1));
        result=result.concat("\nMiddle Initial: ").concat(r.getString(2));
        result=result.concat("\nLast Name: ").concat(r.getString(3));
        result=result.concat("\nSSN: ").concat(r.getString(4));
        result=result.concat("\nBirthdate: ").concat(r.getString(5));
        result=result.concat("\nAddress: ").concat(r.getString(6));
        result=result.concat("\nSex: ").concat(r.getString(7));
        result=result.concat("\nSalary: ").concat(r.getString(8));
        result=result.concat("\nSuper SSN: ").concat(r.getString(9));
        result=result.concat("\nDepartment: ").concat(r.getString(10));
        result=result.concat("\nEmail: ");
        if(r.getString(11)!=null) result=result.concat(r.getString(11));
        else result=result.concat("null");

        return result;
    }

    public String projRep() throws Exception {
        String result="";
        Statement s=conn.createStatement();
        String query="select * from works_on where essn='";
        query=query.concat(ssn).concat("'");
        ResultSet r= s.executeQuery(query);
        result="-----------Project Data-----------\n\n";
        while (r.next()){
            result=result.concat("+++++++++++\n");
            result=result.concat("Pno: ").concat(r.getString(2));
            result=result.concat("\nHours").concat(r.getString(3)).concat("\n");
        }
        return result;
    }

    public String depRep() throws Exception {
        String result="";
        Statement s=conn.createStatement();
        String query="select * from dependent where essn='";
        query=query.concat(ssn).concat("'");
        ResultSet r= s.executeQuery(query);
        result="-----------Dependent Data-----------\n\n";
        while (r.next()){
            result=result.concat("+++++++++++\n");
            result=result.concat("Dep. Name: ").concat(r.getString(2));
            result=result.concat("\nSex:").concat(r.getString(3));
            result=result.concat("\nBirthdate:").concat(r.getString(4));
            result=result.concat("\nRelationship:").concat(r.getString(5)).concat("\n");
        }
        return result;
    }
}
