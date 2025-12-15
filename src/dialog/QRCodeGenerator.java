/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensiapp;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
/**
 *
 * @author siti novi triana
 */
public class QRCodeGenerator {  
    /**
     * Generate QR Code dan return BufferedImage
     * @param data Data yang mau di-encode (NIS, nama, dll)
     * @param width Lebar QR Code (pixel)
     * @param height Tinggi QR Code (pixel)
     * @return BufferedImage QR Code
     */
    public static BufferedImage generateQRImage(String data, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            
            return MatrixToImageWriter.toBufferedImage(bitMatrix);
            
        } catch (WriterException e) {
            System.err.println("Error generating QR Code: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Generate QR Code dan save ke file
     * @param data Data yang mau di-encode
     * @param filePath Path file output (misal: "qrcodes/siswa_001.png")
     * @param width Lebar QR Code
     * @param height Tinggi QR Code
     * @return true jika berhasil, false jika gagal
     */
    public static boolean generateQRFile(String data, String filePath, int width, int height) {
        try {
            // Generate QR Code image
            BufferedImage qrImage = generateQRImage(data, width, height);
            
            if (qrImage == null) {
                return false;
            }
            
            // Buat folder jika belum ada
            File file = new File(filePath);
            File parentDir = file.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }
            
            // Save ke file
            ImageIO.write(qrImage, "PNG", file);
            System.out.println("QR Code saved to: " + filePath);
            return true;
            
        } catch (IOException e) {
            System.err.println("Error saving QR Code: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Generate QR Code untuk siswa berdasarkan NIS
     * @param nis NIS siswa
     * @return BufferedImage QR Code
     */
    public static BufferedImage generateSiswaQR(String nis) {
        return generateQRImage(nis, 300, 300);
    }
    
    /**
     * Generate dan save QR Code siswa ke folder qrcodes/
     * @param nis NIS siswa
     * @return path file yang di-generate
     */
    public static String generateAndSaveSiswaQR(String nis) {
        String folderPath = "qrcodes";
        String fileName = "siswa_" + nis + ".png";
        String fullPath = folderPath + "/" + fileName;
        
        boolean success = generateQRFile(nis, fullPath, 300, 300);
        
        return success ? fullPath : null;
    }
}

