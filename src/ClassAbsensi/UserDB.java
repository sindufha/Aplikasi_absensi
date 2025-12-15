package ClassAbsensi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {
    
    /**
     * Method untuk mengambil SEMUA data user dari database
     * Ini yang dipake buat nampilin ke tabel
     */
    public List<User> getAllUsers() {
        List<User> listUser = new ArrayList<>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Koneksi.getKoneksi();
            
            if (conn == null) {
                System.out.println("✗ Koneksi database gagal!");
                return listUser;
            }
            
            String sql = "SELECT * FROM user ORDER BY user_id ASC";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            
            // Loop semua data user
            while (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("nama"),
                    rs.getString("role")
                );
                listUser.add(user);
            }
            
            System.out.println("✓ Berhasil load " + listUser.size() + " data user");
            
        } catch (SQLException e) {
            System.out.println("✗ Error load data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return listUser;
    }
    
    /**
     * Method untuk TAMBAH user baru
     */
    public boolean addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String sql = "INSERT INTO user (username, password, nama, role) VALUES (?, MD5(?), ?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNama());
            ps.setString(4, user.getRole());
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("✗ Error tambah user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Method untuk UPDATE user
     */
    public boolean updateUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Koneksi.getKoneksi();
            
            // Kalau password diisi, update password juga
            String sql;
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                sql = "UPDATE user SET username=?, password=MD5(?), nama=?, role=? WHERE user_id=?";
            } else {
                sql = "UPDATE user SET username=?, nama=?, role=? WHERE user_id=?";
            }
            
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getNama());
                ps.setString(4, user.getRole());
                ps.setInt(5, user.getUserId());
            } else {
                ps.setString(2, user.getNama());
                ps.setString(3, user.getRole());
                ps.setInt(4, user.getUserId());
            }
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("✗ Error update user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Method untuk HAPUS user
     */
    public boolean deleteUser(int userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        
        try {
            conn = Koneksi.getKoneksi();
            String sql = "DELETE FROM user WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            System.out.println("✗ Error hapus user: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Method untuk cek username sudah ada atau belum
     * (Bisa juga pakai dari LoginDAO, tapi lebih rapi kalau di UserDAO juga ada)
     */
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
    public User getUserById(int userId) {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    
    try {
        conn = Koneksi.getKoneksi();
        String sql = "SELECT * FROM user WHERE user_id = ?";
        ps = conn.prepareStatement(sql);
        ps.setInt(1, userId);
        rs = ps.executeQuery();
        
        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password")); // MD5 hash dari database
            user.setNama(rs.getString("nama"));
            user.setRole(rs.getString("role"));
            return user;
        }
        
    } catch (SQLException e) {
        System.out.println("✗ Error get user by ID: " + e.getMessage());
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return null; // Kalau user tidak ditemukan
}
}