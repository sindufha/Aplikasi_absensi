package ClassAbsensi;

import java.sql.*;

public class PengaturanDAO {
    
    /**
     * Get pengaturan dari database (selalu id = 1)
     */
    public Pengaturan getPengaturan() {
        String sql = "SELECT * FROM pengaturan WHERE id = 1";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                Pengaturan p = new Pengaturan();
                p.setId(rs.getInt("id"));
                p.setNamaSekolah(rs.getString("nama_sekolah"));
                p.setAlamatSekolah(rs.getString("alamat_sekolah"));
                p.setTahunAjaranAktif(rs.getString("tahun_ajaran_aktif"));
                p.setKepalaSekolah(rs.getString("kepala_sekolah"));
                
                System.out.println("✓ Pengaturan loaded: " + p.getNamaSekolah());
                return p;
            } else {
                System.err.println("⚠ Data pengaturan tidak ditemukan!");
                return getDefaultPengaturan();
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error getPengaturan: " + e.getMessage());
            e.printStackTrace();
            return getDefaultPengaturan();
        }
    }
    
    /**
     * Update pengaturan ke database
     */
    public boolean updatePengaturan(Pengaturan pengaturan) {
        String sql = "UPDATE pengaturan SET " +
                     "nama_sekolah = ?, " +
                     "alamat_sekolah = ?, " +
                     "tahun_ajaran_aktif = ?, " +
                     "kepala_sekolah = ? " +
                     "WHERE id = 1";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pengaturan.getNamaSekolah());
            ps.setString(2, pengaturan.getAlamatSekolah());
            ps.setString(3, pengaturan.getTahunAjaranAktif());
            ps.setString(4, pengaturan.getKepalaSekolah());
            
            int rows = ps.executeUpdate();
            
            if (rows > 0) {
                System.out.println("✓ Pengaturan berhasil diupdate");
                return true;
            } else {
                System.err.println("⚠ Tidak ada data yang diupdate");
                return false;
            }
            
        } catch (SQLException e) {
            System.err.println("❌ Error updatePengaturan: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Get default pengaturan jika data tidak ada
     */
    private Pengaturan getDefaultPengaturan() {
        Pengaturan p = new Pengaturan();
        p.setId(1);
        p.setNamaSekolah("SD KHADIJAH");
        p.setAlamatSekolah("Jl. Pendidikan No. 123");
        p.setTahunAjaranAktif("2024/2025");
        p.setKepalaSekolah("Kepala Sekolah");
        return p;
    }
    
    /**
     * Reset ke default value
     */
    public boolean resetToDefault() {
        Pengaturan defaultPengaturan = getDefaultPengaturan();
        return updatePengaturan(defaultPengaturan);
    }
}