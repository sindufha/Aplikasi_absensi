package ClassAbsensi;

import ClassAbsensi.Koneksi;
import ClassAbsensi.Siswa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaDB {
    private Connection conn;

    public SiswaDB() {
        conn = Koneksi.getKoneksi();
    }

    public boolean tambahSiswa(Siswa siswa) {
        String sql = "INSERT INTO siswa (nis, nama_siswa, id_kelas, jenis_kelamin, qr_code) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, siswa.getNis());
            ps.setString(2, siswa.getNamaSiswa());
            ps.setInt(3, siswa.getIdKelas());
            ps.setString(4, siswa.getJenisKelamin());
            ps.setString(5, siswa.getQrCode());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Siswa> getAllSiswa() {
        List<Siswa> list = new ArrayList<>();
        String sql = "SELECT * FROM siswa ORDER BY nama_siswa";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Siswa siswa = new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
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
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Siswa getSiswaByNis(String nis) {
        String sql = "SELECT * FROM siswa WHERE nis = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nis);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
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
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Siswa> getSiswaByKelas(int idKelas) {
        List<Siswa> list = new ArrayList<>();
        String sql = "SELECT * FROM siswa WHERE id_kelas = ? ORDER BY nama_siswa";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idKelas);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Siswa siswa = new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
                );
                list.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updateSiswa(Siswa siswa) {
        String sql = "UPDATE siswa SET nis=?, nama_siswa=?, id_kelas=?, jenis_kelamin=?, qr_code=? WHERE id_siswa=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, siswa.getNis());
            ps.setString(2, siswa.getNamaSiswa());
            ps.setInt(3, siswa.getIdKelas());
            ps.setString(4, siswa.getJenisKelamin());
            ps.setString(5, siswa.getQrCode());
            ps.setInt(6, siswa.getIdSiswa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hapusSiswa(int idSiswa) {
        String sql = "DELETE FROM siswa WHERE id_siswa = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isNisExist(String nis) {
        String sql = "SELECT COUNT(*) FROM siswa WHERE nis = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nis);
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
        String sql = "SELECT * FROM siswa WHERE nis LIKE ? OR nama_siswa LIKE ? ORDER BY nama_siswa";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            String searchPattern = "%" + keyword + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Siswa siswa = new Siswa(
                    rs.getInt("id_siswa"),
                    rs.getString("nis"),
                    rs.getString("nama_siswa"),
                    rs.getInt("id_kelas"),
                    rs.getString("jenis_kelamin"),
                    rs.getString("qr_code")
                );
                list.add(siswa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}