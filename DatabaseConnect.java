
package learning;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;     //Adatbáziskapcsolat osztály
import java.sql.DriverManager;  //Adatbázis Driver kezelő osztály
import java.sql.SQLException;   //Adatbázis kivételkezelő osztály
import java.sql.Statement;      //Adatbázis Statement osztály
import java.sql.ResultSet;
   
public class DatabaseConnect {
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    final String URL = "jdbc:mysql://192.168.66.249:8201/mega?zeroDateTimeBehavior=CONVERT_TO_NULL";
    final String USERNAME = "root";
    final String PASSWORD = "cart32";
    Connection conn = null; 
    Statement createStatement = null;
    DatabaseMetaData dbmeta = null;
    
    
   
    public DatabaseConnect () {
    /* 
        * Létrehozza az adatbázis-kapcsolatot. DriverManager osztály - getConnection metódus
    */
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println(conn.getMetaData());
        } catch (SQLException ex) {
            System.out.println ("Nem sikerült az Adatbázis kapcsolatot felépíteni! Hiba: " + ex);
        }
 
    /*
        * Létrehozza az lekérdezés-kapcsolatot (Statement). Küldi a kérést, szállítja az eredményt.
        * A Statement objektum küldi el az SQL utasításunkat az adatbázis-kezelőnek, a már meglévő
        * DriverManager objektum (conn) kapcsolatán keresztül.
    */
    
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült a Statement kapcsolatot felépíteni! Hiba: " + ex);
            }
            
            try {
                dbmeta = (DatabaseMetaData) conn.getMetaData();
                System.out.println("MySQL Driver: " + dbmeta.getDriverName());
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült a MetaData kapcsolatot felépíteni! Hiba: " + ex);
            }
        }
        
        if (createStatement != null) {
            String sql = "SELECT `NAME`, `TITLE` FROM `t_rotation` WHERE `s6` LIKE 'Z' AND `s3` LIKE 'K'";

            ResultSetMetaData rsmd;
         
            
            try {
                ResultSet rs = createStatement.executeQuery(sql);
                
                rsmd = (ResultSetMetaData) rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                System.out.println(columnCount);
                String name;
                String title;
                
                while (rs.next()) {
                    name = rs.getString("NAME");
                    title = rs.getString("TITLE");
                    System.out.println(name + " - " + title);
                }
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült a Lekérdezés kapcsolatot felépíteni! Hiba: " + ex);
            }
        }
    }
    
   
}
