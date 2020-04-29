/**
    * Zenon Adatbázis - v1.0.0.0
    * 
*/

package learning;

import com.mysql.cj.jdbc.DatabaseMetaData;
import com.mysql.cj.jdbc.result.ResultSetMetaData;
import java.sql.Connection;                         //Adatbáziskapcsolat osztály
import java.sql.DriverManager;                      //Adatbázis Driver kezelő osztály
import java.sql.SQLException;                       //Adatbázis kivételkezelő osztály
import java.sql.Statement;                          //Adatbázis Statement osztály
import java.sql.ResultSet;
   
public class ZenonDbConnect {
    final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    final String USERNAME = "root";
    final String PASSWORD = "cart32";    
    public String URL = "jdbc:mysql://192.168.66.249:8201/";
    public Connection conn = null; 
    public Statement createStatement = null;
    public DatabaseMetaData dbMetaData = null;
    public ResultSetMetaData resultMetaData;
    public ResultSet result;
    public String sql = null; 
    
    public ZenonDbConnect () {
        System.out.println("Meg kell adni egy adatbázist! Például 'mega' vagy 'radio1'");
    }
   
    public ZenonDbConnect (String database) {
        URL += database + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
        
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println(conn.getMetaData());
        } catch (SQLException ex) {
            System.out.println ("Nem sikerült az Adatbázis kapcsolatot felépíteni! Hiba: " + ex);
        }
           
        if (conn != null) {
            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült a Statement kapcsolatot felépíteni! Hiba: " + ex);
            }

            try {
                dbMetaData = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver Name: " + dbMetaData.getDriverName() + " - Verzió: " + dbMetaData.getDriverVersion());
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült a MetaData kapcsolatot felépíteni! Hiba: " + ex);
            }
        }
    }
    
    /**
     * 
     *  
     * A metódus beállítja az osztály SQL kérését.Paraméterként egy szabályos SQL lekérdezést kell átadni.Nincs visszatérési értéke.
     *
     * 
     * @param sqlQuery (String)
     * 
     */
    
   public void setSQL (String sqlQuery) {
       this.sql = sqlQuery;
       System.out.println("Új SQL kérés: " + this.sql);
   }
   
   /**
    * 
    * A metódus az osztály SQL kérésnek megfelelően visszadja JDBC Result struktúrában 
    * a bejegyzett hangfájlokat. Nem vár paramétert.
    *
     * @return result (ResultSet);
    */
   
   public ResultSet getIdNameTitleResult () {
        if (createStatement != null) {
            try {
                result = createStatement.executeQuery(sql);
                resultMetaData = (ResultSetMetaData) result.getMetaData();
                int columnCount = resultMetaData.getColumnCount();
                System.out.println("A lekérdezés " + columnCount + " oszlopot adott eredményül.");
                return result;
            } catch (SQLException ex) {
                System.out.println ("Nem sikerült az SQL kérést lefuttatni! Hiba: " + ex);
            }
            
        }
        return null;
   }
}
