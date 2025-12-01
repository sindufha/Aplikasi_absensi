package ClassAbsensi;
import java.sql.*;

public class LoginDB {
    
    public User login(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        
        try {
            // Ambil koneksi database
            conn = Koneksi.getKoneksi();
            
            if (conn == null) {
                System.out.println("✗ Koneksi database gagal!");
                return null;
            }
            
            // Query dengan PreparedStatement (aman dari SQL Injection)
            String sql = "SELECT * FROM user WHERE username = ? AND password = MD5(?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            // Eksekusi query
            rs = ps.executeQuery();
            
            // Cek apakah data ditemukan
            if (rs.next()) {
                
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setNama(rs.getString("nama"));
                user.setRole(rs.getString("role"));
                
                System.out.println("✓ Login berhasil! User: " + user.getNama());
            } else {
                System.out.println("✗ Username atau password salah!");
            }
            
        } catch (SQLException e) {
            System.out.println("✗ Error saat login: " + e.getMessage());
            e.printStackTrace();
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return user;
    }
    
    public boolean isUsernameExist(String username) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String sql = "SELECT username FROM user WHERE username = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            
            return rs.next(); 
            
        } catch (SQLException e) {
            System.out.println("✗ Error cek username: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}