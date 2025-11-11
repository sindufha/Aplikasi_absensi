package ClassAbsensi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DATABASE = "nama_database";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE;
    
    public static Connection getKoneksi() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Koneksi berhasil!");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver MySQL tidak ditemukan!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Koneksi gagal!");
            e.printStackTrace();
        }
        return conn;
    }
}