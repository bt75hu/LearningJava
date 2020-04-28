package learning;

/**
 *
 * @author  Baranyai Tibor
 * @e-mail  bt75hu@gmail.com
 * @GitHub  https://github.com/bt75hu
 *  
 */
public class Audio_RotID {
    // Variables
    String  dbDatabase;
    String  rotID;
    String  rotName;
    String  rotTitle;
    int     rotLenght;
    
    
    // Methods
    public void setRotationID (String rotationID) {
        rotID = rotationID;
        System.out.println("RotationID beállításra került. Értéke: " + rotID);
    }

    public void setDataBase (String nameOfDatabase) {
        dbDatabase = nameOfDatabase;
        System.out.println("Adatbázis beállításra került. Értéke: " + dbDatabase);
    }
    
    public void getDataBase () {
        if (dbDatabase != null) {
            System.out.println(dbDatabase);
        } else {
            System.out.println("Még nincs beállítva adatbázis!");
        }
    }

    public void getRotationID () {
        if (rotID != null) {
            System.out.println(rotID);
        } else {
            System.out.println("Még nincs beállítva a rotationID!");
        }
    }

    // Constructors
    
    public Audio_RotID () {
        System.out.println ("Be kell állítani az adatbázis nevét és a fájl azonosítóját!");
    }
    
    public Audio_RotID (String nameOfDatabase, String rotationID) {
        this.setDataBase(nameOfDatabase);
        this.setRotationID(rotationID);
    }
}
