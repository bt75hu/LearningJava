package learning;

// import com.learning.learningjava.DatabaseConnect;

public class Learning {
    public static void main(String[] args) {
        Audio_RotID a, b;
//        DatabaseConnect db = new DatabaseConnect ();
        a = new Audio_RotID();
        b = new Audio_RotID("radio1", "N022");
        
        
        a.setDataBase("mega");
        a.getDataBase();
        a.getRotationID();
        a.setRotationID("123");
        a.getRotationID();
        b.getRotationID();
        System.out.println("Hello");
    }
}
