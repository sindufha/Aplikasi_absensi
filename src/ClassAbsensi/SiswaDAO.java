package ClassAbsensi;

import ClassAbsensi.Koneksi;
import ClassAbsensi.Siswa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiswaDAO {
    //Menyimpan objek koneksi database.
    private Connection conn;

    public SiswaDAO() {
        //Saat objek SiswaDAO dibuat, langsung mengambil koneksi dari class Koneksi.
        conn = Koneksi.getKoneksi();
    }

    public boolean tambahSiswa(Siswa siswa) {
        String sql = "INSERT INTO siswa (nis, nama_siswa, id_kelas, jenis_kelamin, qr_code) VALUES (?, ?, ?, ?, ?)";
        
        //Membuat PreparedStatement untuk query.
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            
            //Mengisi nilai parameter query sesuai data dari objek Siswa.
            ps.setString(1, siswa.getNis());
            ps.setString(2, siswa.getNamaSiswa());
            ps.setInt(3, siswa.getIdKelas());
            ps.setString(4, siswa.getJenisKelamin());
            ps.setString(5, siswa.getQrCode());
            //Mengembalikan true jika berhasil ditambahkan.
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Siswa> getAllSiswa() {
        List<Siswa> list = new ArrayList<>(); //buat objek bernama list untuk menampung objek Siswa dari database.
        //kenapa memakai order by
        //Karena database TIDAK menjamin urutan data jika hanya menulis select from urutan data bisa berubah ubah
        String sql = "SELECT * FROM siswa ORDER BY nama_siswa";
        
        //Membuat statement dan menjalankan query.
        try (Connection conn = Koneksi.getKoneksi(); 
                Statement stmt = conn.createStatement(); 
                ResultSet rs = stmt.executeQuery(sql)) {
            
            //Looping setiap baris hasil query.
            while (rs.next()) { 
                
                //Membuat objek Siswa dari row database.
                Siswa siswa = new Siswa(
                        rs.getInt("id_siswa"),
                        rs.getString("nis"),
                        rs.getString("nama_siswa"),
                        rs.getInt("id_kelas"),
                        rs.getString("jenis_kelamin"),
                        rs.getString("qr_code")
                );
                siswa.setStatus(rs.getString("status"));  //Set nilai kolom status.
                list.add(siswa); //tambahkan data siswa ke list siswa
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Siswa getSiswaById(int idSiswa) {
        String sql = "SELECT * FROM siswa WHERE id_siswa = ?"; //Query untuk mencari data berdasarkan id.
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSiswa);
            ResultSet rs = ps.executeQuery();
            //Jika data ditemukan, buat objek siswa dan return.
            if (rs.next()) {
                return new Siswa(
                        //buat objek siswa dari row database
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
        String sql = "SELECT * FROM siswa WHERE nis = ?"; //Query untuk mencari data berdasarkan nis
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
        List<Siswa> list = new ArrayList<>(); //Buat list kosong yang nanti akan diisi dengan objek Siswa dari database.
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
        String sql = "UPDATE siswa SET nama_siswa=?, id_kelas=?, jenis_kelamin=? WHERE id_siswa=?";
         //Membuat PreparedStatement untuk query
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            //Mengisi nilai parameter query sesuai data dari objek Siswa.
            ps.setString(1, siswa.getNamaSiswa());
            ps.setInt(2, siswa.getIdKelas());
            ps.setString(3, siswa.getJenisKelamin());
            ps.setInt(4, siswa.getIdSiswa());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;   
        }
    }

   public boolean nonaktifkanSiswa(int nis) {
       //kenapa kok pakai update?
       //karena biar Data siswa tetap ada dan hanya statusnya saja yang berubah.
    String sql = "UPDATE siswa SET status = 'Nonaktif' WHERE nis = ?";
    try (Connection conn = Koneksi.getKoneksi();
            PreparedStatement ps = conn.prepareStatement(sql)) {
        //Mengisi nilai parameter query sesuai data
        ps.setInt(1, nis);
        return ps.executeUpdate() > 0;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
    }
}
    public boolean isNisExist(String nis) { //Method ini digunakan untuk mengecek apakah NIS sudah ada di database atau belum
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
        //Membuat statement dan menjalankan query.
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
        List<Siswa> list = new ArrayList<>(); //Buat list kosong yang nanti akan diisi dengan objek Siswa dari database.
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
    
    public List<Integer> getDistinctKelas() {
        List<Integer> listKelas = new ArrayList<>(); //Buat list kelas kosong dgn tipe data int yang nanti akan diisi dengan objek id kelas dari database.
        String sql = "SELECT DISTINCT id_kelas FROM siswa ORDER BY id_kelas"; //“Query untuk mengambil semua id_kelas yang unik dari tabel siswa lalu urutkan dari terkecil ke terbesar.”
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                listKelas.add(rs.getInt("id_kelas")); //ambil nilai integer dari kolom id_kelas lalu tambah ke variable listKelas
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return listKelas;
    }
    
    
}
