/**
 *
 * POJO Audio
 * 
 * Ez az osztály megkap egy adatbázis lekérdezést, majd ebből egy HashMap 
 * objektumot hoz létre, melyben eltárolja az audio fájl adatbázisban szereplő
 * értékeit. 
 * 
 * A HasMap osztály képes arra, hogy egy asszociatív tömbhöz hasonló objektumot 
 * hozzunk létre.
 * Bővebb leírás: https://www.webotlet.hu/?p=1434
 * 
 *  
 * @param String RotationNumber, String Database
 * 
 * @author  Baranyai Tibor
 * @e-mail  bt75hu@gmail.com
 * @GitHub  https://github.com/bt75hu
 *  
 */

package learning;

import java.util.HashMap;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Audio {
    private static String thisClassNameToMessages = "(Audio Osztály) ";
    private int queryResultRow = 0; 
    HashMap<String, String> audioDbResult = new HashMap<>();

    private ZenonDbConnect zdb;
    public String name;
    public String title;
    public String rotationID;
    

    public Audio () {
        Audio.audioHelp();
        System.out.println(thisClassNameToMessages + "Az objektum nem jött létre!");
    }
    
    public Audio (String itsNotEnough) {
        Audio.audioHelp();
        System.out.println(thisClassNameToMessages + "Az objektum nem jött létre mert nem elég egy paraméter az objektum létrehozásához!");       
    }
 
    
    public Audio (String audioRotationNumber, String audioDatabaseName) throws SQLException {
              
            // létrehozz a ZenonDbConnet objektumot, a paraméterrel beállítja az adatbázis nevét
        zdb = new ZenonDbConnect (audioDatabaseName);    

            // beállítja a ZenonDbConnect tipusú objektum (zdb) adatbázis SQL lekérdezését.
        zdb.setSQL ("SELECT * FROM `t_rotation` WHERE `NUM_ROTATION` "  
                + "= '" + audioRotationNumber + "'");
        
            // meghívja getFullRecord metódust, mely * lekérdezést küld. 
        ResultSet queryResult = zdb.getFullRecord();
        

                /*
                    A lekérdezés abban az esetben lehet eredményes ha:
                    ---------------------------------------------------
                        1.  A lekérdezés végrehajtása sikeres volt. A ZenonDbConnect osztály
                            már biztos, hogy küldött hibaüzenetet, ha a lekérdezés végrehajtása
                            sikertelen volt. De itt is megnézzük, hogy nem NULL értékű-e a 
                            lekérdezés eredménye (queryResult)
                        2.  A Zenon adatbázisában a NUM_ROTATION elméletileg az egyedi azonosítókat
                            tartalmazza, de nincs deffiniálva PRIMARY KEY -ként a t_rotation táblában.
                            Épp ezért, akár program hiba miatt is, előfordulhat egyezés.
                            Mivel ez az osztály is, és a program teljes egésze erre a NUM_ROTATION 
                            értékre épít, mint elsődleges azonosító, így fontos, hogy leellenőrizzük, hogy
                            a lekérdezés csak egyetlen egy eredményt adott vissza!
                */

        if (queryResult != null) {
                queryResultRow = zdb.resultNofRows;

                if (queryResultRow == 1) {
                
                System.out.println(thisClassNameToMessages + "Sikeres visszatérés az következő eredménnyel: ");

                queryResult.next();
                    name = queryResult.getString("NAME");
                    title = queryResult.getString("TITLE");
                    rotationID = queryResult.getString("NUM_ROTATION");
                    System.out.println("(" + rotationID + ") " + name + " - " + title);
                
                this.setAudioValues(queryResult);
                
            } else if (queryResultRow > 1) {
                System.out.println(thisClassNameToMessages + "Az adatbázisban több (" + queryResultRow + ") bejegyzés is van ezzel a NUM_ROTATION értékkel");
            } else {
                System.out.println(thisClassNameToMessages + "A lekérdezés üres eredményhalmazt adott ezzel az azonosítóval: "  + audioRotationNumber);
                System.out.println(thisClassNameToMessages + "Sorok száma: "  + queryResultRow);
            }
        }
    }

    private void setAudioValues (ResultSet queryResult) {
        String[] resultColumnNames = zdb.columnNameArray; 
        String[] resultColumnTypes = zdb.columnTypeArray;
        int resultNofColumns = zdb.resultNofColumns;
    }
   

    
    private static void audioHelp () {
        System.out.println(thisClassNameToMessages + "Az Audio osztály paramétereként egy String "
                + "tipusú azonosítót, valamint egy adatbázist kell megadni.");
        System.out.println(thisClassNameToMessages + "Ez az azonosító a t_rotation tábla NUM_ROTATION "
                + "mezője alapján hoz létre lekérdezést, mely a rekord összes "
                + "értékét visszadja.");
        
        System.out.println(thisClassNameToMessages + "Például: Audio hangfajl = new Audio (\"470097\", \"radio1\");");
    }
    

}
