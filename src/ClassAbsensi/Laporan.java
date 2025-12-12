package ClassAbsensi;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class Laporan {
    
    // Font definitions
    private static Font fontTitle = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static Font fontSubtitle = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
    private static Font fontHeader = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
    private static Font fontNormal = new Font(Font.FontFamily.HELVETICA, 9, Font.NORMAL);
    private static Font fontSmall = new Font(Font.FontFamily.HELVETICA, 8, Font.NORMAL);
    
    /**
     * Generate laporan PDF absensi
     */
    public boolean generateLaporanPDF(String outputPath, int bulan, int tahun, int idKelas) {
        Document document = new Document(PageSize.A4.rotate()); // Landscape
        
        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();
            
            // Get data kelas
            Kelas kelas = getKelasInfo(idKelas);
            if (kelas == null) {
                System.err.println("❌ Kelas tidak ditemukan!");
                return false;
            }
            
            
            
            document.add(new Paragraph(" ")); // Spacing
            
            // Add Title
            Paragraph title = new Paragraph("DAFTAR HADIR SISWA", fontTitle);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            
            // Add Subtitle - Nama Sekolah
            Paragraph subtitle1 = new Paragraph("SD KHADIJAH", fontSubtitle);
            subtitle1.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle1);
            
            // Add Tahun Ajaran
            Paragraph subtitle2 = new Paragraph("TAHUN PELAJARAN " + kelas.getTahunAjaran(), fontSubtitle);
            subtitle2.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle2);
            
            document.add(new Paragraph(" ")); // Spacing
            
            // Add Bulan dan Kelas info
            String namaBulan = new SimpleDateFormat("MMMM", new Locale("id", "ID")).format(
                new GregorianCalendar(tahun, bulan - 1, 1).getTime()
            );
            
            Paragraph info = new Paragraph(
                "Bulan : " + namaBulan + "   Kelas : " + kelas.getTingkat() + " (" + kelas.getTahunAjaran() + ")",
                fontNormal
            );
            document.add(info);
            
            document.add(new Paragraph(" ")); // Spacing
            
            // Create attendance table
            PdfPTable table = createAttendanceTable(idKelas, bulan, tahun);
            if (table != null) {
                document.add(table);
            } else {
                document.add(new Paragraph("Tidak ada data absensi untuk bulan ini.", fontNormal));
            }
            
            document.add(new Paragraph(" ")); // Spacing
            
            // Add summary
            addSummary(document, idKelas, bulan, tahun);
            
            // Add footer
            addFooter(document);
            
            document.close();
            System.out.println("✓ Laporan PDF berhasil dibuat: " + outputPath);
            return true;
            
        } catch (Exception e) {
            System.err.println("❌ Error generate PDF: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * Create attendance table dengan kolom tanggal
     */
    private PdfPTable createAttendanceTable(int idKelas, int bulan, int tahun) {
        try {
            // Get jumlah hari dalam bulan
            Calendar calendar = Calendar.getInstance();
            calendar.set(tahun, bulan - 1, 1);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            
            // Kolom: No, Nama, Date1, Date2, ..., DateN, H, T, S, I, A
            int totalColumns = 2 + daysInMonth + 5; // No, Nama, Tanggal(N), H, T, S, I, A
            
            PdfPTable table = new PdfPTable(totalColumns);
            table.setWidthPercentage(100);
            
            // Set column widths
            float[] widths = new float[totalColumns];
            widths[0] = 0.5f; // No
            widths[1] = 3f;   // Nama
            for (int i = 2; i < totalColumns - 5; i++) {
                widths[i] = 0.6f; // Tanggal
            }
            widths[totalColumns - 5] = 0.7f; // H (Hadir)
            widths[totalColumns - 4] = 0.7f; // T (Telat)
            widths[totalColumns - 3] = 0.7f; // S (Sakit)
            widths[totalColumns - 2] = 0.7f; // I (Izin)
            widths[totalColumns - 1] = 0.7f; // A (Alfa)
            table.setWidths(widths);
            
            // Header Row 1 - Hari/Tanggal
            addHeaderCell(table, "No", 1, 2);         // No (rowspan 2)
            addHeaderCell(table, "Nama", 1, 2);       // Nama (rowspan 2)
            addHeaderCell(table, "Hari/Tanggal", daysInMonth, 1); // Tanggal header
            addHeaderCell(table, "H", 1, 2);          // Hadir (rowspan 2)
            addHeaderCell(table, "T", 1, 2);          // Telat (rowspan 2)
            addHeaderCell(table, "S", 1, 2);          // Sakit (rowspan 2)
            addHeaderCell(table, "I", 1, 2);          // Izin (rowspan 2)
            addHeaderCell(table, "A", 1, 2);          // Alfa (rowspan 2)
            
            // Header Row 2 - Tanggal dan hari
            SimpleDateFormat sdfDay = new SimpleDateFormat("EEE", new Locale("id", "ID"));
            for (int day = 1; day <= daysInMonth; day++) {
                calendar.set(tahun, bulan - 1, day);
                String dayName = sdfDay.format(calendar.getTime()).substring(0, 3); // Sen, Sel, Rab
                
                PdfPCell cell = new PdfPCell(new Phrase(dayName + "\n" + String.format("%02d", day), fontSmall));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPadding(3);
                table.addCell(cell);
            }
            
            // Get list siswa
            java.util.List<Siswa> listSiswa = getSiswaByKelas(idKelas);
            
            if (listSiswa.isEmpty()) {
                System.out.println("⚠ Tidak ada siswa di kelas ini");
                return null;
            }
            
            // Data rows
            int no = 1;
            for (Siswa siswa : listSiswa) {
                // No
                PdfPCell cellNo = new PdfPCell(new Phrase(String.valueOf(no++), fontNormal));
                cellNo.setHorizontalAlignment(Element.ALIGN_CENTER);
                cellNo.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellNo.setPadding(5);
                table.addCell(cellNo);
                
                // Nama
                PdfPCell cellNama = new PdfPCell(new Phrase(siswa.getNamaSiswa(), fontNormal));
                cellNama.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cellNama.setPadding(5);
                table.addCell(cellNama);
                
                // Get absensi data untuk siswa ini
                Map<Integer, String> absensiMap = getAbsensiSiswa(siswa.getNis(), bulan, tahun);
                
                int countH = 0, countT = 0, countS = 0, countI = 0, countA = 0;
                
                // Tanggal columns
                for (int day = 1; day <= daysInMonth; day++) {
                    String status = absensiMap.getOrDefault(day, "");
                    
                    PdfPCell cellStatus = new PdfPCell(new Phrase(status, fontSmall));
                    cellStatus.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cellStatus.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cellStatus.setPadding(3);
                    
                    // Color coding
                    if (status.equals("H")) {
                        cellStatus.setBackgroundColor(new BaseColor(144, 238, 144)); // Light Green
                        countH++;
                    } else if (status.equals("T")) {
                        cellStatus.setBackgroundColor(new BaseColor(255, 200, 124)); // Orange
                        countT++;
                    } else if (status.equals("S")) {
                        cellStatus.setBackgroundColor(new BaseColor(255, 255, 153)); // Yellow
                        countS++;
                    } else if (status.equals("I")) {
                        cellStatus.setBackgroundColor(new BaseColor(173, 216, 230)); // Light Blue
                        countI++;
                    } else if (status.equals("A")) {
                        cellStatus.setBackgroundColor(new BaseColor(255, 153, 153)); // Light Red
                        countA++;
                    }
                    
                    table.addCell(cellStatus);
                }
                
                // Summary columns
                addDataCell(table, String.valueOf(countH));
                addDataCell(table, String.valueOf(countT));
                addDataCell(table, String.valueOf(countS));
                addDataCell(table, String.valueOf(countI));
                addDataCell(table, String.valueOf(countA));
            }
            
            return table;
            
        } catch (Exception e) {
            System.err.println("❌ Error create table: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Add header cell with colspan and rowspan
     */
    private void addHeaderCell(PdfPTable table, String text, int colspan, int rowspan) {
        PdfPCell cell = new PdfPCell(new Phrase(text, fontHeader));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    /**
     * Add data cell
     */
    private void addDataCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, fontNormal));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPadding(5);
        table.addCell(cell);
    }
    
    /**
     * Add summary information
     */
    private void addSummary(Document document, int idKelas, int bulan, int tahun) throws DocumentException {
        java.util.List<Siswa> listSiswa = getSiswaByKelas(idKelas);
        
        int totalSiswa = listSiswa.size();
        int totalLaki = 0;
        int totalPerempuan = 0;
        
        for (Siswa siswa : listSiswa) {
            if (siswa.getJenisKelamin().equals("L")) {
                totalLaki++;
            } else {
                totalPerempuan++;
            }
        }
        
        Paragraph summary = new Paragraph();
        summary.add(new Chunk("Jumlah siswa : " + totalSiswa + "\n", fontNormal));
        summary.add(new Chunk("Laki-laki    : " + totalLaki + "\n", fontNormal));
        summary.add(new Chunk("Perempuan    : " + totalPerempuan + "\n", fontNormal));
        
        document.add(summary);
    }
    
    /**
     * Add footer dengan keterangan
     */
    private void addFooter(Document document) throws DocumentException {
        document.add(new Paragraph(" "));
        
        Paragraph keterangan = new Paragraph("Keterangan:", fontNormal);
        keterangan.add("\nH = Hadir   T = Telat   S = Sakit   I = Izin   A = Alfa");
        
        document.add(keterangan);
        
        // Tanggal cetak
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy HH:mm", new Locale("id", "ID"));
        Paragraph tanggalCetak = new Paragraph("\nDicetak pada: " + sdf.format(new Date()), fontSmall);
        tanggalCetak.setAlignment(Element.ALIGN_RIGHT);
        document.add(tanggalCetak);
    }
    
    /**
     * Get kelas info dari database
     */
    private Kelas getKelasInfo(int idKelas) {
        KelasDAO kelasDAO = new KelasDAO();
        return kelasDAO.loadKelasById(idKelas);
    }
    
    /**
     * Get siswa by kelas dari database
     */
    private java.util.List<Siswa> getSiswaByKelas(int idKelas) {
        SiswaDAO siswaDAO = new SiswaDAO();
        return siswaDAO.getSiswaByKelas(idKelas);
    }
    
    /**
     * Get absensi data untuk satu siswa dalam satu bulan
     * Query langsung ke tabel absensi
     * @return Map<day, status> misal: {1=H, 2=H, 3=S, 4=H}
     */
    private Map<Integer, String> getAbsensiSiswa(int nis, int bulan, int tahun) {
        Map<Integer, String> map = new HashMap<>();
        
        String sql = "SELECT DAY(a.tanggal) as day, a.status " +
                     "FROM absensi a " +
                     "JOIN siswa s ON a.id_siswa = s.id_siswa " +
                     "WHERE s.nis = ? " +
                     "AND MONTH(a.tanggal) = ? " +
                     "AND YEAR(a.tanggal) = ? " +
                     "ORDER BY a.tanggal";
        
        try (Connection conn = Koneksi.getKoneksi();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, nis);
            ps.setInt(2, bulan);
            ps.setInt(3, tahun);
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                int day = rs.getInt("day");
                String status = rs.getString("status");
                
                // Convert status to single letter untuk PDF
                if (status != null && !status.isEmpty()) {
                    String statusCode = "";
                    
                    switch (status.toLowerCase()) {
                        case "hadir":
                            statusCode = "H";
                            break;
                        case "telat":
                            statusCode = "T";
                            break;
                        case "sakit":
                            statusCode = "S";
                            break;
                        case "izin":
                            statusCode = "I";
                            break;
                        case "alfa":
                            statusCode = "A";
                            break;
                        default:
                            // Jika sudah format singkat
                            statusCode = status.substring(0, 1).toUpperCase();
                    }
                    
                    map.put(day, statusCode);
                }
            }
            
            System.out.println("✓ NIS " + nis + " - Loaded " + map.size() + " hari absensi");
            
        } catch (SQLException e) {
            System.err.println("❌ Error getAbsensiSiswa NIS " + nis + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return map;
    }
}