package ClassAbsensi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static Connection conn;
    
    private static final String URL = "jdbc:mysql://localhost:3306/absensiapp2";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getKoneksi() {
        try {
            if (conn == null || conn.isClosed()) {
                conn = DriverManager.getConnection(URL, USER, PASSWORD);

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
