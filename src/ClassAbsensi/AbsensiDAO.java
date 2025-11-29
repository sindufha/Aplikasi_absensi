package ClassAbsensi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsensiDAO {
    
    /**
     * Cek apakah siswa sudah absen hari ini
     */
    public boolean sudahAbsenHariIni(int idSiswa, Date tanggal) {
        String sql = "SELECT COUNT(*) FROM absensi WHERE id_siswa = ? AND tanggal = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSiswa);
            ps.setDate(2, tanggal);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Ambil data siswa berdasarkan QR Code
     */
    public Siswa getSiswaByQRCode(String qrCode) {
        String sql = "SELECT s.*, k.nama_kelas FROM siswa s " +
                     "JOIN kelas k ON s.id_kelas = k.id_kelas " +
                     "WHERE s.qr_code = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, qrCode);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Siswa siswa = new Siswa();
                siswa.setIdSiswa(rs.getInt("id_siswa"));
                siswa.setNis(rs.getString("nis"));
                siswa.setNamaSiswa(rs.getString("nama_siswa"));
                siswa.setIdKelas(rs.getInt("id_kelas"));
                siswa.setJenisKelamin(rs.getString("jenis_kelamin"));
                siswa.setQrCode(rs.getString("qr_code"));
                siswa.setIdKelas(rs.getInt("id_kelas"));
                return siswa;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Insert absensi masuk
     */
    public boolean insertAbsensiMasuk(int idSiswa, String metode) {
    Connection conn = null;
    PreparedStatement psJam = null;
    PreparedStatement psInsert = null;
    ResultSet rs = null;
    
    try {
        conn = Koneksi.getKoneksi();
        
        if (conn == null) {
            System.err.println("Koneksi database null!");
            return false;
        }
        
        // Step 1: Tentukan status berdasarkan jam masuk
        String status = "Hadir"; // Default
        
        String sqlJam = "SELECT jam_masuk, toleransi FROM jam_absensi WHERE id = 1";
        psJam = conn.prepareStatement(sqlJam);
        rs = psJam.executeQuery();
        
        if (rs.next()) {
            Time jamMasuk = rs.getTime("jam_masuk");
            int toleransi = rs.getInt("toleransi");
            
            // Hitung batas waktu telat
            long batasTelat = jamMasuk.getTime() + (toleransi * 60 * 1000);
            Time sekarang = new Time(System.currentTimeMillis());
            
            if (sekarang.getTime() > batasTelat) {
                status = "Telat";
            }
        }
        
        // Tutup ResultSet dan PreparedStatement untuk jam_absensi
        if (rs != null) rs.close();
        if (psJam != null) psJam.close();
        
        // Step 2: Insert absensi dengan status yang sudah ditentukan
        String sqlInsert = "INSERT INTO absensi (id_siswa, tanggal, waktu_masuk, status, metode) " +
                          "VALUES (?, CURDATE(), CURTIME(), ?, ?)";
        
        psInsert = conn.prepareStatement(sqlInsert);
        psInsert.setInt(1, idSiswa);
        psInsert.setString(2, status);
        psInsert.setString(3, metode);
        
        int result = psInsert.executeUpdate();
        
        System.out.println("Absensi berhasil disimpan - Status: " + status);
        return result > 0;
        
    } catch (SQLException e) {
        System.err.println("Error insertAbsensiMasuk: " + e.getMessage());
        e.printStackTrace();
        return false;
        
    } finally {
        // Tutup semua resource dengan urutan yang benar
        try {
            if (rs != null) rs.close();
            if (psJam != null) psJam.close();
            if (psInsert != null) psInsert.close();
            // JANGAN close connection jika pakai connection pool
            // if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    /**
     * Update absensi pulang
     */
    public boolean updateAbsensiPulang(int idSiswa, Date tanggal) {
        String sql = "UPDATE absensi SET waktu_pulang = CURTIME() " +
                     "WHERE id_siswa = ? AND tanggal = ?";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idSiswa);
            ps.setDate(2, tanggal);
            
            int result = ps.executeUpdate();
            return result > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Tentukan status berdasarkan jam masuk
     */
    private String tentukanStatus() {
        String sql = "SELECT jam_masuk, toleransi FROM jam_absensi WHERE id = 1";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                Time jamMasuk = rs.getTime("jam_masuk");
                int toleransi = rs.getInt("toleransi");
                
                // Hitung batas waktu telat
                long batasTelat = jamMasuk.getTime() + (toleransi * 60 * 1000);
                long waktuSekarang = System.currentTimeMillis();
                
                // Ambil waktu sekarang (hanya time, tanpa date)
                Time sekarang = new Time(waktuSekarang);
                
                if (sekarang.getTime() <= batasTelat) {
                    return "Hadir";
                } else {
                    return "Telat";
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return "Hadir"; // Default
    }
    
    /**
     * Get absensi hari ini
     */
    public List<Absensi> getAbsensiHariIni() {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT a.*, s.nis, s.nama_siswa, k.nama_kelas " +
                     "FROM absensi a " +
                     "JOIN siswa s ON a.id_siswa = s.id_siswa " +
                     "JOIN kelas k ON s.id_kelas = k.id_kelas " +
                     "WHERE a.tanggal = CURDATE() " +
                     "ORDER BY a.waktu_masuk DESC";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Absensi absensi = new Absensi();
                absensi.setIdAbsensi(rs.getInt("id_absensi"));
                absensi.setIdSiswa(rs.getInt("id_siswa"));
                absensi.setTanggal(rs.getDate("tanggal"));
                absensi.setWaktuMasuk(rs.getTime("waktu_masuk"));
                absensi.setWaktuPulang(rs.getTime("waktu_pulang"));
                absensi.setStatus(rs.getString("status"));
                absensi.setMetode(rs.getString("metode"));
                absensi.setKeterangan(rs.getString("keterangan"));
                
                // Data dari join
                absensi.setNis(rs.getString("nis"));
                absensi.setNamaSiswa(rs.getString("nama_siswa"));
                absensi.setNamaKelas(rs.getString("nama_kelas"));
                
                list.add(absensi);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Get absensi by tanggal range
     */
    public List<Absensi> getAbsensiByTanggal(Date tanggalMulai, Date tanggalAkhir) {
        List<Absensi> list = new ArrayList<>();
        String sql = "SELECT a.*, s.nis, s.nama_siswa, k.nama_kelas " +
                     "FROM absensi a " +
                     "JOIN siswa s ON a.id_siswa = s.id_siswa " +
                     "JOIN kelas k ON s.id_kelas = k.id_kelas " +
                     "WHERE a.tanggal BETWEEN ? AND ? " +
                     "ORDER BY a.tanggal DESC, a.waktu_masuk DESC";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDate(1, tanggalMulai);
            ps.setDate(2, tanggalAkhir);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Absensi absensi = new Absensi();
                absensi.setIdAbsensi(rs.getInt("id_absensi"));
                absensi.setIdSiswa(rs.getInt("id_siswa"));
                absensi.setTanggal(rs.getDate("tanggal"));
                absensi.setWaktuMasuk(rs.getTime("waktu_masuk"));
                absensi.setWaktuPulang(rs.getTime("waktu_pulang"));
                absensi.setStatus(rs.getString("status"));
                absensi.setMetode(rs.getString("metode"));
                absensi.setKeterangan(rs.getString("keterangan"));
                
                absensi.setNis(rs.getString("nis"));
                absensi.setNamaSiswa(rs.getString("nama_siswa"));
                absensi.setNamaKelas(rs.getString("nama_kelas"));
                
                list.add(absensi);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    

}