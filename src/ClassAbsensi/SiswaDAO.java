package ClassAbsensi;

import ClassAbsensi.Koneksi;
import ClassAbsensi.Siswa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaDAO {
    private Connection conn;

    public SiswaDAO() {
        conn = Koneksi.getKoneksi();
    }

    public boolean tambahSiswa(Siswa siswa) {
        String sql = "INSERT INTO siswa (nis, nama_siswa, id_kelas, jenis_kelamin, qr_code, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, siswa.getNis()); // ✅ Ubah ke setInt
            ps.setString(2, siswa.getNamaSiswa());
            ps.setInt(3, siswa.getIdKelas());
            ps.setString(4, siswa.getJenisKelamin());
            ps.setString(5, siswa.getQrCode());
            ps.setString(6, siswa.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Siswa> getAllSiswa() {
        List<Siswa> list = new ArrayList<>();
        String sql = "SELECT * FROM siswa ORDER BY nama_siswa";
        try (Connection conn = Koneksi.getKoneksi(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Siswa siswa = new Siswa(
                        rs.getInt("id_siswa"),
                        rs.getInt("nis"), // ✅ Ubah ke getInt
                        rs.getString("nama_siswa"),
                        rs.getInt("id_kelas"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("qr_code"),
                        rs.getString("status")
                );
                list.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Siswa getSiswaById(int idSiswa) {
        String sql = "SELECT * FROM siswa WHERE id_siswa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getInt("nis"), // ✅ Ubah ke getInt
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Siswa getSiswaByNis(int nis) { // ✅ Parameter ubah ke int
        String sql = "SELECT * FROM siswa WHERE nis = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nis); // ✅ Ubah ke setInt
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getInt("nis"), // ✅ Ubah ke getInt
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code"),
                    rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Siswa getSiswaByQrCode(String qrCode) {
        String sql = "SELECT * FROM siswa WHERE qr_code = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, qrCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Siswa(
                        rs.getInt("id_siswa"),
                        rs.getInt("nis"), // ✅ Ubah ke getInt
                        rs.getString("nama_siswa"),
                        rs.getInt("id_kelas"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("qr_code"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
 * Get siswa by kelas (semua status)
 */
public List<Siswa> getSiswaByKelas(int idKelas) {
    return getSiswaByKelas(idKelas, null);
}

/**
 * Get siswa by kelas dengan filter status
 * @param idKelas ID kelas
 * @param status Status siswa (Aktif/Tidak Aktif), null untuk semua
 * @return List siswa
 */
public List<Siswa> getSiswaByKelas(int idKelas, String status) {
    List<Siswa> list = new ArrayList<>();
    
    String sql = "SELECT * FROM siswa WHERE id_kelas = ?";
    if (status != null && !status.isEmpty()) {
        sql += " AND status = ?";
    }
    sql += " ORDER BY nama_siswa ASC";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idKelas);
        if (status != null && !status.isEmpty()) {
            ps.setString(2, status);
        }
        
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Siswa siswa = new Siswa();
            siswa.setNis(rs.getInt("nis"));
            siswa.setNamaSiswa(rs.getString("nama_siswa"));
            siswa.setIdKelas(rs.getInt("id_kelas"));
            siswa.setJenisKelamin(rs.getString("jenis_kelamin"));
            siswa.setQrCode(rs.getString("qr_code"));
            siswa.setStatus(rs.getString("status"));
            list.add(siswa);
        }
        
    } catch (SQLException e) {
        System.err.println("Error getSiswaByKelas: " + e.getMessage());
        e.printStackTrace();
    }
    
    return list;
}

/**
 * Get siswa aktif by kelas
 */
public List<Siswa> getSiswaAktifByKelas(int idKelas) {
    return getSiswaByKelas(idKelas, "Aktif");
}

    public boolean updateSiswa(Siswa siswa) {
        String sql = "UPDATE siswa SET nama_siswa=?, id_kelas=?, jenis_kelamin=?, status=? WHERE nis=?"; // ✅ WHERE menggunakan nis
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, siswa.getNamaSiswa());
            ps.setInt(2, siswa.getIdKelas());
            ps.setString(3, siswa.getJenisKelamin());
            ps.setString(4, siswa.getStatus());
            ps.setInt(5, siswa.getNis()); // ✅ Ubah ke setInt
            
            int rowsAffected = ps.executeUpdate();
            System.out.println("Rows updated: " + rowsAffected); // Debug
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;   
        }
    }

    public boolean nonaktifkanSiswa(int nis) {
        String sql = "UPDATE siswa SET status = 'Nonaktif' WHERE nis = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nis);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean aktivkanSiswa(int nis) {
        String sql = "UPDATE siswa SET status = 'Aktif' WHERE nis = ?";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nis);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isNisExist(int nis) { // ✅ Parameter ubah ke int
        String sql = "SELECT COUNT(*) FROM siswa WHERE nis = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, nis); // ✅ Ubah ke setInt
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getTotalSiswa() {
        String sql = "SELECT COUNT(*) FROM siswa";
        try (Connection conn = Koneksi.getKoneksi();
                Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int getTotalSiswaAktif() {
        String sql = "SELECT COUNT(*) FROM siswa WHERE status = 'Aktif'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Siswa> cariSiswa(String keyword) {
        List<Siswa> list = new ArrayList<>();
        // ✅ Cari berdasarkan nama saja, atau tambah CAST untuk NIS
        String sql = "SELECT * FROM siswa WHERE CAST(nis AS CHAR) LIKE ? OR nama_siswa LIKE ? ORDER BY nama_siswa";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Siswa siswa = new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getInt("nis"), // ✅ Ubah ke getInt
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code"),
                    rs.getString("status")
                );
                list.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<Integer> getDistinctKelas() {
        List<Integer> listKelas = new ArrayList<>();
        String sql = "SELECT DISTINCT id_kelas FROM siswa ORDER BY id_kelas";
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listKelas.add(rs.getInt("id_kelas"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listKelas;
    }
    
    // ✅ Method tambahan untuk filter siswa aktif/nonaktif
    public List<Siswa> getSiswaByStatus(String status) {
        List<Siswa> list = new ArrayList<>();
        String sql = "SELECT * FROM siswa WHERE status = ? ORDER BY nama_siswa";
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Siswa siswa = new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getInt("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code"),
                    rs.getString("status")
                );
                list.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
