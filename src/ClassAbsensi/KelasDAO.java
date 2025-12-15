package ClassAbsensi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class KelasDAO {
    
    public List<Kelas> loadAllKelas() {
        List<Kelas> kelasList = new ArrayList<>();
        String query = "SELECT id_kelas, tingkat, tahun_ajaran FROM kelas ORDER BY tingkat ASC";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Kelas kelas = new Kelas();
                kelas.setIdKelas(rs.getInt("id_kelas"));
                kelas.setTingkat(rs.getInt("tingkat"));
                kelas.setTahunAjaran(rs.getString("tahun_ajaran"));
                
                kelasList.add(kelas);
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading kelas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return kelasList;
    }
    
    /**
     * Load kelas berdasarkan ID
     * @param idKelas ID kelas yang dicari
     * @return Objek Kelas atau null jika tidak ditemukan
     */
    public Kelas loadKelasById(int idKelas) {
        String query = "SELECT id_kelas, tingkat, tahun_ajaran FROM kelas WHERE id_kelas = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, idKelas);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Kelas kelas = new Kelas();
                    kelas.setIdKelas(rs.getInt("id_kelas"));
                    kelas.setTingkat(rs.getInt("tingkat"));
                    kelas.setTahunAjaran(rs.getString("tahun_ajaran"));
                    return kelas;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading kelas by ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Load kelas berdasarkan tingkat
     * @param tingkat Tingkat kelas (1-6)
     * @return List of Kelas dengan tingkat tersebut
     */
    public List<Kelas> loadKelasByTingkat(int tingkat) {
        List<Kelas> kelasList = new ArrayList<>();
        String query = "SELECT id_kelas, tingkat, tahun_ajaran FROM kelas WHERE tingkat = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, tingkat);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Kelas kelas = new Kelas();
                    kelas.setIdKelas(rs.getInt("id_kelas"));
                    kelas.setTingkat(rs.getInt("tingkat"));
                    kelas.setTahunAjaran(rs.getString("tahun_ajaran"));
                    kelasList.add(kelas);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading kelas by tingkat: " + e.getMessage());
            e.printStackTrace();
        }
        
        return kelasList;
    }
    
    public List<Kelas> loadKelasByTahunAjaran(String tahunAjaran) {
        List<Kelas> kelasList = new ArrayList<>();
        String query = "SELECT id_kelas, tingkat, tahun_ajaran FROM kelas WHERE tahun_ajaran = ? ORDER BY tingkat ASC";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, tahunAjaran);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Kelas kelas = new Kelas();
                    kelas.setIdKelas(rs.getInt("id_kelas"));
                    kelas.setTingkat(rs.getInt("tingkat"));
                    kelas.setTahunAjaran(rs.getString("tahun_ajaran"));
                    kelasList.add(kelas);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error loading kelas by tahun ajaran: " + e.getMessage());
            e.printStackTrace();
        }
        
        return kelasList;
    }
    
    /**
     * Get total jumlah kelas
     * @return Jumlah total kelas
     */
    public int getTotalKelas() {
        String query = "SELECT COUNT(*) as total FROM kelas";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            System.err.println("Error getting total kelas: " + e.getMessage());
            e.printStackTrace();
        }
        
        return 0;
    }
    public List<Absensi> getAbsensiByKelasAndBulan(int idKelas, int bulan, int tahun) {
    List<Absensi> listAbsensi = new ArrayList<>();
    // Sesuaikan query dengan struktur tabel yang benar
    // Kemungkinan relasi: a.id_siswa = s.id_siswa atau a.nis_siswa = s.nis
    String query = "SELECT a.*, s.nis, s.nama_siswa " +
                   "FROM absensi a " +
                   "INNER JOIN siswa s ON a.id_siswa = s.id_siswa " +
                   "WHERE s.id_kelas = ? " +
                   "AND MONTH(a.tanggal) = ? " +
                   "AND YEAR(a.tanggal) = ? " +
                   "ORDER BY a.tanggal, s.nama_siswa";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement pstmt = conn.prepareStatement(query)) {
        
        pstmt.setInt(1, idKelas);
        pstmt.setInt(2, bulan);
        pstmt.setInt(3, tahun);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Absensi absensi = new Absensi();
                absensi.setIdAbsensi(rs.getInt("id_absensi"));
                absensi.setIdSiswa(rs.getInt("id_siswa")); // Gunakan id_siswa dari tabel absensi
                absensi.setNis(rs.getString("nis")); // Ambil nis dari tabel siswa (hasil JOIN)
                absensi.setNamaSiswa(rs.getString("nama_siswa"));
                absensi.setTanggal(rs.getDate("tanggal"));
                absensi.setStatus(rs.getString("status"));
                absensi.setKeterangan(rs.getString("keterangan"));
                
                listAbsensi.add(absensi);
            }
        }
        
    } catch (SQLException e) {
        System.err.println("Error mengambil data absensi: " + e.getMessage());
        e.printStackTrace();
    }
    
    return listAbsensi;
}
}