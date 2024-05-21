package ConnectionHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CreateConnection {
    public  static  Connection createConnection(){
        Connection con;
        try {
             con= DriverManager.getConnection("jdbc:mysql://localhost:3306/jha-jfrcjd-m3","root","sql123");
            return  con;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
