/**
 *
 * Audio Class
 * 
 * Ez az osztály egy ZenonDBConnect objektumon keresztül, egy adatbázis 
 * lekérdezésből származó eredményből tölti be az audio file, adatbázisban
 * tárolt adatait. Majd ebből egy HashMap objektumot hoz létre, melyben 
 * eltárolja az audio fájl adatbázisban szereplő értékeit. 
 * 
 * Ezen kívül tárolja az adatbázis táblában lévő mezők nevét és típusát.
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
    HashMap<String, String> audioDbColsNameResult = new HashMap<>();
    HashMap<String, String> audioDbColsTypeResult = new HashMap<>();
    private ZenonDbConnect zdb;
    
    public String name;
    public String title;
    public String rotationID;
    public String broadcastDate;
    public String released;
    public String kind;

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
                
                System.out.println(thisClassNameToMessages + "Sikeres visszatérés összesen: 1 eredménnyel.");
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
        /** 
         * A metódus HashMap típusú objektumba tölti be a táblából lekérdezett 
         * rekord mezőinek értékeit, valamint egy másik HashMap osztályba 
         * letárolja a mezők típusát is, későbbi használatra. Az objektum 
         * kulcsa mindkét esetben egyenlő az adattábla mezőinek nevével, 
         * melyhez hozzárendeli a megfelelő értékeket.
         * 
         * A mezők NEVÉT a ZenonDbConnect objektum columnNameArray tömbjéből másolja,  
         * a mezők TÍPUSÁT a ZenonDbConnect objektum columnTypeArray tömbjéből másolja.
         * 
         * A MAP típusokról bővebben: https://www.webotlet.hu/?p=1434
         * 
         * @param ResultSet Result
         * 
         * Nincs visszatérési érték.
         */        
        String[] resultColumnNamesArray = zdb.columnNameArray; 
        String[] resultColumnTypesArray = zdb.columnTypeArray;
        int resultNofColumns = zdb.resultNofColumns;
        String columnValue;
        try {
            queryResult.first();            //Beállítjuk a cursort az első sorra, hogy biztosan abból olvassa az értékeket.
            for (int i = 0; i < resultNofColumns; i++) {
                audioDbColsTypeResult.put(resultColumnNamesArray [i], resultColumnTypesArray[i]);
                try {
                    columnValue = queryResult.getString(resultColumnNamesArray [i]);
                    audioDbColsNameResult.put(resultColumnNamesArray [i], columnValue);
                } catch (SQLException ex) {
                    System.out.println(thisClassNameToMessages + "Nem sikerült kiolvasni a mező értékét. "
                            + "(Mező: " + resultColumnNamesArray [i] +" Hiba: " + ex);
                }
            }
            
            name = audioDbColsNameResult.get("NAME");
            title = audioDbColsNameResult.get("TITLE");
            rotationID = audioDbColsNameResult.get("NUM_ROTATION");
            released = audioDbColsNameResult.get("s1");
            broadcastDate = audioDbColsNameResult.get("BROADCAST_DATE");
            kind = audioDbColsNameResult.get("s10");

        } catch (SQLException ex) {
            System.out.println(thisClassNameToMessages + "Nem sikerült lekérdezés " +
                    "eredményének tömbjében a cursort a következő rekordra állítani."
            + "(Találatok száma: " + resultNofColumns + " Hiba: " + ex);
        }
        

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
