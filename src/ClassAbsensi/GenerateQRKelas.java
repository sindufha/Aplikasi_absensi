/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassAbsensi;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerateQRKelas {
    private KelasDAO kelasDAO;
    private SiswaDAO siswaDAO;
    
    public GenerateQRKelas() {
        this.kelasDAO = new KelasDAO();
        this.siswaDAO = new SiswaDAO();
    }
    
    /**
     * Get total siswa aktif
     * @return Jumlah siswa aktif
     */
    public int getTotalSiswaAktif() {
        String sql = "SELECT COUNT(*) as total FROM siswa WHERE status = 'Aktif'";
        
        try (Connection conn = Koneksi.getKoneksi();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Get list semua kelas
     * @return List kelas
     */
    public List<Kelas> getListKelas() {
        return kelasDAO.loadAllKelas();
    }
    
    /**
     * Get jumlah siswa aktif per kelas
     * @param idKelas ID kelas
     * @return Jumlah siswa aktif di kelas tersebut
     */
    public int getJumlahSiswaByKelas(int idKelas) {
        String sql = "SELECT COUNT(*) as total FROM siswa WHERE id_kelas = ? AND status = 'Aktif'";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idKelas);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt("total");
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    /**
     * Generate QR untuk satu kelas
     * @param idKelas ID kelas
     * @param outputFolder Folder output
     * @return true jika berhasil
     */
    public boolean generateAllKelas(String outputFolder) {
    List<Kelas> listKelas = kelasDAO.loadAllKelas();
    
    if (listKelas.isEmpty()) {
        return false;
    }
    
    String zipFile = outputFolder + File.separator + "QR_Semua_Kelas.zip";
    
    try (FileOutputStream fos = new FileOutputStream(zipFile);
         ZipOutputStream zos = new ZipOutputStream(fos)) {
        
        for (Kelas kelas : listKelas) {
            List<Siswa> listSiswa = getSiswaByKelas(kelas.getIdKelas());
            
            if (listSiswa.isEmpty()) continue;
            
            String folderName = "Kelas_" + kelas.getTingkat() + "_" + kelas.getTahunAjaran().replace("/", "-") + "/";
            
            for (Siswa siswa : listSiswa) {
                // ✅ Ambil QR code dari database
                String qrData = siswa.getQrCode(); // Sudah ada "QR" + NIS dari database
                
                if (qrData == null || qrData.isEmpty()) {
                    qrData = "QR" + siswa.getNis(); // Fallback
                }
                
                String fileName = folderName + siswa.getNis() + "" + siswa.getNamaSiswa().replaceAll("[^a-zA-Z0-9]", "") + ".png";
                
                byte[] qrBytes = createQRImage(qrData);
                
                if (qrBytes != null) {
                    ZipEntry entry = new ZipEntry(fileName);
                    zos.putNextEntry(entry);
                    zos.write(qrBytes);
                    zos.closeEntry();
                }
            }
        }
        
        return true;
        
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Generate QR per kelas
 */
public boolean generateByKelas(int idKelas, String outputFolder) {
    List<Siswa> listSiswa = getSiswaByKelas(idKelas);
    
    if (listSiswa.isEmpty()) {
        return false;
    }
    
    String zipFile = outputFolder + File.separator + "QR_Kelas_" + idKelas + ".zip";
    
    try (FileOutputStream fos = new FileOutputStream(zipFile);
         ZipOutputStream zos = new ZipOutputStream(fos)) {
        
        for (Siswa siswa : listSiswa) {
            // ✅ Ambil QR code dari database
            String qrData = siswa.getQrCode();
            
            if (qrData == null || qrData.isEmpty()) {
                qrData = "QR" + siswa.getNis();
            }
            
            String fileName = siswa.getNis() + "" + siswa.getNamaSiswa().replaceAll("[^a-zA-Z0-9]", "") + ".png";
            
            byte[] qrBytes = createQRImage(qrData);
            
            if (qrBytes != null) {
                ZipEntry entry = new ZipEntry(fileName);
                zos.putNextEntry(entry);
                zos.write(qrBytes);
                zos.closeEntry();
            }
        }
        
        return true;
        
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}

/**
 * Get siswa by kelas - PASTIKAN INCLUDE qr_code column
 */
private List<Siswa> getSiswaByKelas(int idKelas) {
    List<Siswa> list = new ArrayList<>();
    String sql = "SELECT nis, nama_siswa, id_kelas, qr_code FROM siswa WHERE id_kelas = ? AND status = 'Aktif'";
    
    try (Connection conn = Koneksi.getKoneksi();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        
        ps.setInt(1, idKelas);
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Siswa siswa = new Siswa();
            siswa.setNis(rs.getInt("nis"));
            siswa.setNamaSiswa(rs.getString("nama_siswa"));
            siswa.setIdKelas(rs.getInt("id_kelas"));
            siswa.setQrCode(rs.getString("qr_code")); // ✅ Ambil QR code dari DB
            list.add(siswa);
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return list;
}
    
    /**
     * Create QR Code image
     * @param data Data untuk QR Code
     * @return Byte array gambar PNG
     */
    private byte[] createQRImage(String data) {
        try {
            QRCodeWriter writer = new QRCodeWriter();
            BitMatrix matrix = writer.encode(data, BarcodeFormat.QR_CODE, 300, 300);
            BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", baos);
            
            return baos.toByteArray();
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

    

