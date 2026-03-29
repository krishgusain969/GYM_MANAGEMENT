package src;
public class TestDB {
    public static void main(String[] args) throws Exception {
        System.out.println("Testing DB...");
        DatabaseConnection.getConnection();
        System.out.println("Done.");
    }
}
