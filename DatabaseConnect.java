
package learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnect {
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://192.168.66.249:8201/mega?zeroDateTimeBehavior=CONVERT_TO_NULL";
    final String USERNAME = "root";
    final String PASSWORD = "cart32";
    Connection conn = null;
    
    public DatabaseConnect () {
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException ex) {
            System.out.println ("Nem sikerült a kapcsolatot felépíteni! Hiba: " + ex);
        }
    }
}
