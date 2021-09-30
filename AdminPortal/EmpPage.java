import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.sql.*;

public class EmpPage extends JFrame implements ActionListener {
    int count;
    Connection conn;
    JTextField fname, minit, lname, ssn, bdate, address, sex, salary, superssn, dno, email;
    JButton btn;
    JCheckBox box;

    public EmpPage(Connection con) throws Exception {
        conn=con;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,600);
        this.setLayout(new FlowLayout());

        box= new JCheckBox("Has Dependents");
        box.setFocusable(false);

        btn = new JButton("Add Employee");
        btn.setBounds(500,200,100,80);
        btn.setFocusable(false);
        btn.addActionListener(this);

        fname = new JTextField("First Name");
        fname.setPreferredSize(new Dimension(100,40));
        minit = new JTextField("Middle Initial");
        minit.setPreferredSize(new Dimension(100,40));
        lname = new JTextField("Last Name");
        lname.setPreferredSize(new Dimension(100,40));
        ssn = new JTextField("Social Security Number");
        ssn.setPreferredSize(new Dimension(100,40));
        bdate = new JTextField("Birth date (YYYY-MM-DD)");
        bdate.setPreferredSize(new Dimension(100,40));
        address = new JTextField("Address");
        address.setPreferredSize(new Dimension(100,40));
        sex = new JTextField("Sex (M/F)");
        sex.setPreferredSize(new Dimension(100,40));
        salary = new JTextField("Salary");
        salary.setPreferredSize(new Dimension(100,40));
        superssn = new JTextField("Supervisor SSN");
        superssn.setPreferredSize(new Dimension(100,40));
        dno = new JTextField("Department Number");
        dno.setPreferredSize(new Dimension(100,40));
        email = new JTextField("Email Address");
        email.setPreferredSize(new Dimension(100,40));
        
        this.add(fname);
        this.add(minit);
        this.add(lname);
        this.add(ssn);
        this.add(bdate);
        this.add(address);
        this.add(sex);
        this.add(salary);
        this.add(superssn);
        this.add(dno);
        this.add(email);
        this.add(box);
        this.add(btn);
        

        this.setTitle("Add New Employee Data");
        this.setVisible(true);
        this.setResizable(false);
    }

    public void insertEmp() throws Exception{
        Statement s=conn.createStatement();
        String query="INSERT INTO employee VALUES ('";
        query=query.concat(fname.getText()).concat("', '");
        query=query.concat(minit.getText()).concat("', '");
        query=query.concat(lname.getText()).concat("', '");
        query=query.concat(ssn.getText()).concat("', ");
        query=query.concat("TO_DATE('"+ bdate.getText()+"','YYYY-MM-DD'), '");
        query=query.concat(address.getText()).concat("', '");
        query=query.concat(sex.getText()).concat("', ");
        query=query.concat(salary.getText()).concat(", '");
        query=query.concat(superssn.getText()).concat("', ");
        query=query.concat(dno.getText()).concat(", '");
        query=query.concat(email.getText()).concat("')");

        s.executeUpdate(query);
    }
    
    public boolean checkEmp() throws Exception{
        Statement s=conn.createStatement();
        String query="select fname from employee where ssn='";
        query=query.concat(ssn.getText()).concat("'");
        ResultSet r= s.executeQuery(query);

        if (r.next()) return true;
        else return false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        boolean deps=false;
        if (e.getSource()==btn){
            if (box.isSelected()) deps=true;
            try {
                insertEmp();
            }
            catch(Exception excp) {}
            try{
            if (checkEmp()) {
                JOptionPane.showMessageDialog(null, "Employee "+ssn.getText() +" Added Successfully.", "SUCCESS" , JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new ProjPage(conn, ssn.getText(), deps);
            }
            else JOptionPane.showMessageDialog(null, "Employee Not Added. Please Insert Correct Values.", "FAILURE" , JOptionPane.ERROR_MESSAGE);
            }
            catch (Exception excp) {}
                
        }
    }

}
