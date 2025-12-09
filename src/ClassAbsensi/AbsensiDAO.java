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
    String sql = "SELECT s.*, k.tingkat FROM siswa s " +  // ✅ Pakai tingkat
                 "JOIN kelas k ON s.id_kelas = k.id_kelas " +
                 "WHERE s.qr_code = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, qrCode);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            Siswa siswa = new Siswa();
            siswa.setIdSiswa(rs.getInt("id_siswa"));
            siswa.setNis(rs.getInt("nis"));
            siswa.setNamaSiswa(rs.getString("nama_siswa"));
            siswa.setIdKelas(rs.getInt("id_kelas"));
            siswa.setJenisKelamin(rs.getString("jenis_kelamin"));
            siswa.setQrCode(rs.getString("qr_code"));
            
            // ✅ Set nama kelas dari tingkat (misal: "Kelas 1", "Kelas 2")
            siswa.setNamaKelas("Kelas " + rs.getInt("tingkat"));
            
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
    PreparedStatement psCheck = null;
    PreparedStatement psJam = null;
    PreparedStatement psInsert = null;
    ResultSet rs = null;
    
    try {
        conn = Koneksi.getKoneksi();
        
        if (conn == null) {
            System.err.println("Koneksi database null!");
            return false;
        }
        
        // ✅ CEK DUPLIKAT DALAM METHOD INI (pakai connection yang sama)
        String sqlCheck = "SELECT COUNT(*) FROM absensi WHERE id_siswa = ? AND tanggal = CURDATE()";
        psCheck = conn.prepareStatement(sqlCheck);
        psCheck.setInt(1, idSiswa);
        ResultSet rsCheck = psCheck.executeQuery();
        
        if (rsCheck.next() && rsCheck.getInt(1) > 0) {
            System.out.println("⚠️ Siswa sudah absen hari ini!");
            rsCheck.close();
            psCheck.close();
            return false;
        }
        
        rsCheck.close();
        psCheck.close();
        
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
        
        System.out.println("✅ Absensi berhasil disimpan - Status: " + status);
        return result > 0;
        
    } catch (SQLException e) {
        System.err.println("❌ Error insertAbsensiMasuk: " + e.getMessage());
        e.printStackTrace();
        return false;
        
    } finally {
        try {
            if (rs != null) rs.close();
            if (psCheck != null) psCheck.close();
            if (psJam != null) psJam.close();
            if (psInsert != null) psInsert.close();
            // Connection TIDAK di-close karena dikelola oleh Koneksi class
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
    
    /**
     * Update absensi pulang
     */

    
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
        String sql = "SELECT a.*, s.nis, s.nama_siswa, k.tingkat " +
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
                absensi.setNamaKelas("Kelas " + rs.getInt("tingkat"));
                
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
        String sql = "SELECT a.*, s.nis, s.nama_siswa, k.tingkat " +
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
                absensi.setNamaKelas("Kelas " + rs.getInt("tingkat"));
                
                list.add(absensi);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    /**
 * Get semua status absensi dari database
 */
/**
 * Get semua status absensi dari database
 */
public List<String> getAllStatus() {
    List<String> listStatus = new ArrayList<>();
    
    // Hardcode semua status yang boleh dipilih guru
    listStatus.add("Hadir");
    listStatus.add("Sakit");
    listStatus.add("Izin");
    listStatus.add("Alfa");
    listStatus.add("Telat");
    
    return listStatus;
}

/**
 * Simpan absensi manual (untuk absensi guru input manual)
 */
public boolean insertAbsensiManual(int idSiswa, Date tanggal, String status, String keterangan) {
    // ✅ CEK DULU: Apakah siswa ini sudah absen hari ini?
    if (sudahAbsenHariIni(idSiswa, tanggal)) {
        // Kalau sudah ada, UPDATE saja
        System.out.println("⚠️ Siswa sudah absen hari ini, melakukan UPDATE...");
        return updateAbsensiManual(idSiswa, tanggal, status, keterangan);
    }
    
    // Kalau belum ada, baru INSERT
    String sql = "INSERT INTO absensi (id_siswa, tanggal, waktu_masuk, status, metode, keterangan) " +
                 "VALUES (?, ?, CURTIME(), ?, 'Manual', ?)";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idSiswa);
        ps.setDate(2, tanggal);
        ps.setString(3, status);
        ps.setString(4, keterangan);
        
        int result = ps.executeUpdate();
        System.out.println("✅ Absensi manual berhasil disimpan - ID Siswa: " + idSiswa + ", Status: " + status);
        return result > 0;
        
    } catch (SQLException e) {
        System.err.println("❌ Error insertAbsensiManual: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}

/**
 * Cek apakah siswa sudah absen pada tanggal dan jam tertentu
 */


/**
 * Update absensi manual (jika sudah ada data sebelumnya)
 */

public boolean updateAbsensiManual(int idSiswa, Date tanggal, String status, String keterangan) {
    String sql = "UPDATE absensi SET status = ?, keterangan = ?, waktu_masuk = CURTIME(), metode = 'Manual' " +
                 "WHERE id_siswa = ? AND tanggal = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setString(1, status);
        ps.setString(2, keterangan);
        ps.setInt(3, idSiswa);
        ps.setDate(4, tanggal);
        
        int result = ps.executeUpdate();
        System.out.println("✅ Absensi manual berhasil diupdate - ID Siswa: " + idSiswa + ", Status: " + status);
        return result > 0;
        
    } catch (SQLException e) {
        System.err.println("❌ Error updateAbsensiManual: " + e.getMessage());
        e.printStackTrace();
        return false;
    }
}
public boolean updateWaktuAbsensi(int idSiswa, Date tanggal) {
    // ✅ Update waktu_masuk terakhir (untuk koreksi)
    String sql = "UPDATE absensi SET waktu_masuk = CURTIME() " +
                 "WHERE id_siswa = ? AND tanggal = ?";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idSiswa);
        ps.setDate(2, tanggal);
        
        int result = ps.executeUpdate();
        System.out.println("✅ Absensi diupdate - ID Siswa: " + idSiswa);
        return result > 0;
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return false;
}

public List<Absensi> getAbsensiByTanggalKelas(Date tanggal, int idKelas) {
    List<Absensi> list = new ArrayList<>();
    String sql = "SELECT a.*, s.nis, s.nama_siswa, k.tingkat " +  // ✅ tingkat
             "FROM absensi a " +
             "JOIN siswa s ON a.id_siswa = s.id_siswa " +
             "JOIN kelas k ON s.id_kelas = k.id_kelas " +
             "WHERE a.tanggal = ? AND s.id_kelas = ? " +
             "ORDER BY s.nama_siswa";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setDate(1, tanggal);
        ps.setInt(2, idKelas);
        
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
            absensi.setNamaKelas("Kelas " + rs.getInt("tingkat"));
            
            list.add(absensi);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return list;
}
}
