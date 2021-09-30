import java.sql.*;

public class Part2 {
    public static void main(String[] args) throws Exception{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e){
            System.out.println("Could not load driver");
        }
        // add your own parameters
        Connection conn= DriverManager.getConnection("");
        new MainPage(conn);
    }
}
