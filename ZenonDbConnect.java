/**
    * Zenon Adatbázis - v1.0.1.0
    * 
    * JDBC Driver:      MySQL Connector/J 
    *                   Verzió: mysql-connector-java-8.0.20 
    *                   (Revision: afc0a13cd3c5a0bf57eaa809ee0ee6df1fd5ac9b)
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
    
    private String URL = "jdbc:mysql://192.168.66.249:8201/";
    private Connection conn = null; 
    private Statement createStatement = null;
    private DatabaseMetaData dbMetaData = null;
    private ResultSetMetaData resultMetaData;
    private ResultSet result;
    private String sql = null; // az osztály SQL kérésének érteke. Szabályos SQL lekérdezést kell beállítani a setSQL metódussal!
    private static String thisClassNameToMessages = "(ZenonDbConnect Osztály) ";
    
    public int resultNofRows;
    public int resultNofColumns;
    public String columnNameArray[];
    public String columnTypeArray[];
    
    public ZenonDbConnect () {
        System.out.println("Meg kell adni egy adatbázist! Például 'mega' vagy 'radio1'");
    }
   
    public ZenonDbConnect (String database) {
        URL += database + "?zeroDateTimeBehavior=CONVERT_TO_NULL";
        System.out.println("Kapcsolódás a kiszolgálóhoz: " + URL);
        System.out.println("Adatbázis: " + database);
        
        try {
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            // System.out.println(conn.getMetaData());
        } catch (SQLException ex) {
            System.out.println (thisClassNameToMessages + "Nem sikerült az "
                    + "Adatbázis kapcsolatot felépíteni! Hiba: " + ex);
        }
           
        if (conn != null) {
            try {
                createStatement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            } catch (SQLException ex) {
                System.out.println (thisClassNameToMessages + "Nem sikerült a "
                        + "Statement (szállítási réteg) kapcsolatot felépíteni! Hiba: " + ex);
            }

            try {
                dbMetaData = (DatabaseMetaData) conn.getMetaData();
                System.out.println(thisClassNameToMessages + "Driver Name: " + dbMetaData.getDriverName() + " - Verzió: " + dbMetaData.getDriverVersion());
            } catch (SQLException ex) {
                System.out.println (thisClassNameToMessages + "Nem sikerült a Meta Adatokat lekérdezni! Hiba: " + ex);
            }
        }
    }
    
    /**
     * 
     *  
     * A metódus beállítja az osztály SQL kérését. 
     * Paraméterként egy szabályos SQL lekérdezést kell megadni. 
     * 
     * Nincs visszatérési értéke.
     * 
     * @param sqlQuery (String)
     * 
     */
    
   public void setSQL (String sqlQuery) {
       this.sql = sqlQuery;
       System.out.println(thisClassNameToMessages + "Új SQL kérés: " + this.sql);
   }
   
   /**
    * 
    * A metódus az osztály SQL kérésnek megfelelően visszadja JDBC Result 
    * struktúrában a bejegyzett hangfájlok, NUM_ROTATION, NAME, TITLE értékeit.
    * 
    * Nem vár paramétert, viszont meghívásának feltétele, hogy 
    * szabályos SQL lekérdezést kell beállítani a setSQL metódussal!
    *
    * @return result (ResultSet);
    * 
    */
   
   public ResultSet getIdNameTitleResult () {
       if (sql == null) {
           exNoSqlQuery ();
           return null;
       }
       
       if (createStatement != null) {
            try {
                
                result = createStatement.executeQuery(sql);
                resultMetaData = (ResultSetMetaData) result.getMetaData();
                
                int columnCount = resultMetaData.getColumnCount();
                String columnName[] = new String [columnCount];
                String columnType[] = new String [columnCount];
                
                System.out.println (thisClassNameToMessages + "A lekérdezésben "
                        + "ezek a mezők jöttek eredményül.");
                for (int i = 1; i <= columnCount; i++) {
                    columnNameArray [i-1] = resultMetaData.getColumnLabel (i);
                    columnTypeArray [i-1] = resultMetaData.getColumnTypeName (i);
                    
                    System.out.println(columnName [i-1] + " [" 
                            + columnType [i-1] + "]");
                    
                }
                
                System.out.println(thisClassNameToMessages + "A lekérdezés " 
                        + columnCount + " oszlopot adott eredményül.");
                return result;
                
            } catch (SQLException ex) {
                System.out.println (thisClassNameToMessages + "Nem sikerült "
                        + "az SQL kérést lefuttatni! Hiba: " + ex);
            }
            
        }
        return null;
   }

   /**
    * 
    * A metódus az osztály SQL kérésnek megfelelően visszadja JDBC Result 
    * struktúrában a bejegyzett hangfájlok összes értékeit.
    * 
    * Nem vár paramétert (a ZenonDbConnect objektum sql változójában beállított
    * lekérdezést futtatja.
    *
     * @return result (ResultSet);
    */
   
   public ResultSet getFullRecord () {
        if (sql == null) {
           exNoSqlQuery ();
           return null;
        }
        if (createStatement != null) {
            try {
                result = createStatement.executeQuery(sql);
                resultMetaData = (ResultSetMetaData) result.getMetaData();
                resultNofRows = getNofResultRows (result);
                setColNamesArray();
                System.out.println("A lekérdezés " + resultNofRows + " sort adott eredményül.");
                return result;
            } catch (SQLException ex) {
                System.out.println (thisClassNameToMessages + 
                        "Nem sikerült az SQL kérést lefuttatni! Hiba: " + ex);
            }
        }
        return null;
   }
   
   public void lsColProporties () {
        for (int i = 0; i < resultNofColumns; i++) {
            System.out.println(i + ": " + columnNameArray [i] + " [" + columnTypeArray [i] + "]");
        }
   }
   
   private void setColNamesArray () {
        try {
            resultNofColumns = resultMetaData.getColumnCount();
        } catch (SQLException ex) {
            System.out.println("Nem sikerült megállapítani, hány mezőt adott vissza a lekérdezés. " + ex);
        }
        columnNameArray = new String [resultNofColumns];
        columnTypeArray = new String [resultNofColumns];
        System.out.println (thisClassNameToMessages 
              + "A lekérdezésben ezek a mezők jöttek eredményül.");
        for (int i = 1; i <= resultNofColumns; i++) {
            try {
                columnNameArray [i-1] = resultMetaData.getColumnLabel (i);
            } catch (SQLException ex) {
                System.out.println(thisClassNameToMessages + "Nem sikerült a(z)" + i + ". mező nevét megállapítani! Hiba: " + ex);
            }
            try {
                columnTypeArray [i-1] = resultMetaData.getColumnTypeName (i);
            } catch (SQLException ex) {
                System.out.println(thisClassNameToMessages + "Nem sikerült a(z)" + i + ". mező tipusát megállapítani! Hiba: " + ex);
            }
        }
        System.out.println(thisClassNameToMessages + 
                "A lekérdezés " + resultNofColumns + " oszlopot adott eredményül.");
   }
   
   private int getNofResultRows (ResultSet result) {
       int numLastRow;
       if (result != null) {
           try {
               result.last();
           } catch (SQLException ex) {
               System.out.println("Nem sikerült a cursort a találat utolsó rekordjára mozgatni. " + ex);
               return 0;
           }
           try {
               numLastRow = result.getRow();
           } catch (SQLException ex) {
               System.out.println("Nem sikerült a megállapítani a cursor pozícióját. " + ex);
               return 0;
           }
           try {
               result.beforeFirst();
           } catch (SQLException ex) {
               System.out.println("Nem sikerült cursort az utolsóű sor elé mozgatni. " + ex);
               return 0;
           }

           return numLastRow;
       }
       
        return 0;
   }
   
   private static void exNoSqlQuery () {
        System.out.println("Nincs beállítva SQL lekérdezés!");
        System.out.println("Szabályos SQL lekérdezést kell beállítani a setSQL metódussal!"); 
   }
}
