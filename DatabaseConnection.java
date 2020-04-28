
package learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://192.168.66.249:8201/mega?zeroDateTimeBehavior=CONVERT_TO_NULL";
    final String USERNAME = "";
    final String PASSWORD = "";
    
    public DatabaseConnection () {
       
        try {
            Connection conn = DriverManager.getConnection(URL);
        } catch (SQLException ex) {
            System.out.println ("Nem sikeerült a kapcsolatot felépíteni! Hiba: " + ex);
        }
    }
}
