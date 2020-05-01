package learning;

import java.sql.SQLException;

public class Learning {
    public static void main(String[] args) throws SQLException {
//        Audio_RotID a, b;
        // DatabaseConnect db = new DatabaseConnect ();
//        a = new Audio_RotID();
//        b = new Audio_RotID("radio1", "N022");
       Audio audio = new Audio ("470094", "radio1");
        System.out.println("(" + audio.rotationID + ") " + audio.name + " - " + audio.title);     
//        
//        zdb.setSQL ("SELECT * FROM `t_rotation` WHERE `NAME` LIKE 'Depec%' AND `s6` LIKE 'Z' AND `s3` LIKE 'K'");
//        ResultSet queryResult = zdb.getIdNameTitleResult();
//        
//        if (queryResult != null) {
//            System.out.println("Sikeres visszatérés az eredménnyel.");
//            
//                String name;
//                String title;
//                String rotationID;
//                
//                while (queryResult.next()) {
//                    name = queryResult.getString("NAME");
//                    title = queryResult.getString("TITLE");
//                    rotationID = queryResult.getString("NUM_ROTATION");
//                    System.out.println("(" + rotationID + ") " + name + " - " + title);
//                }
//
//        }
//        
//        a.setDataBase("mega");
//        a.getDataBase();
//        a.getRotationID();
//        a.setRotationID("123");
//        a.getRotationID();
//        b.getRotationID();
//        System.out.println("Hello");
    }
}
