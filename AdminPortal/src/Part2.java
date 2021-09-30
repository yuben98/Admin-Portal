import java.sql.*;

public class Part2 {
    public static void main(String[] args) throws Exception{
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
        }
        catch(ClassNotFoundException e){
            System.out.println("Could not load driver");
        }
        Connection conn= DriverManager.getConnection("jdbc:oracle:thin:@artemis.vsnet.gmu.edu:1521/vse18c.vsnet.gmu.edu", "abenlafq", "owoodseb");
        new MainPage(conn);
    }
}
