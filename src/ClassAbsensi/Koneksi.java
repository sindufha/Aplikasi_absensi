package ClassAbsensi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection conn;
    
    private static final String URL = "jdbc:mysql://localhost:3306/absensiapp";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getKoneksi() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Koneksi database berhasil!");
                String url = "jdbc:mysql://localhost:3306/absensi_sdi";
                String user = "root";
                String password = "";
                
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(url, user, password);
                System.out.println("Koneksi berhasil!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Koneksi database gagal: " + e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔒 Koneksi database ditutup");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
