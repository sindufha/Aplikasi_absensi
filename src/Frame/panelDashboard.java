/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;

import ClassAbsensi.Absensi;
import ClassAbsensi.AbsensiDAO;
import ClassAbsensi.KelasDAO;
import ClassAbsensi.SiswaDAO;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author MyBook Hype AMD
 */
public class panelDashboard extends javax.swing.JPanel{
private SiswaDAO siswaDAO;
private KelasDAO kelasDAO;
private AbsensiDAO absensiDAO;

    public panelDashboard() {
        initComponents();
        tampilkanChart();
         siswaDAO = new SiswaDAO();
        kelasDAO = new KelasDAO();
        absensiDAO = new AbsensiDAO();
        loadDashboardData();
    }
    private void loadDashboardData() {
    loadTotalSiswa();
    loadTotalKelas();
    loadAbsensiHariIni();
}
public void refreshDashboard() {
    loadDashboardData();
}
private void loadTotalSiswa() {
    try {
        int totalSiswa = siswaDAO.getTotalSiswa();
        lblSiswa.setText(String.valueOf(totalSiswa));
    } catch (Exception e) {
        System.err.println("Error load total siswa: " + e.getMessage());
        lblSiswa.setText("0");
    }
}

private void loadTotalKelas() {
    try {
        int totalKelas = kelasDAO.getTotalKelas();
        lblKelas.setText(String.valueOf(totalKelas));
    } catch (Exception e) {
        System.err.println("Error load total kelas: " + e.getMessage());
        lblKelas.setText("0");
    }
}

private void loadAbsensiHariIni() {
    try {
        // Ambil semua data absensi hari ini
            List<Absensi> listAbsensi = absensiDAO.getAbsensiHariIni();
        
        // Hitung manual berdasarkan status
        int hadir = 0;
        int izin = 0;
        int sakit = 0;
        int alfa = 0;
        int terlambat = 0;
        
        for (Absensi absensi : listAbsensi) {
            String status = absensi.getStatus().toLowerCase();
            
            if (status.contains("hadir")) {
                hadir++;
            } else if (status.contains("izin")) {
                izin++;
            } else if (status.contains("sakit")) {
                sakit++;
            } else if (status.contains("alfa") || status.contains("alpa")) {
                alfa++;
            } else if (status.contains("telat") || status.contains("terlambat")) {
                terlambat++;
            }
        }
        
        // Set ke label
        lblHadir.setText(String.valueOf(hadir));
        lblIzin.setText(String.valueOf(izin));
        lblSakit.setText(String.valueOf(sakit));
        lblAlfa.setText(String.valueOf(alfa));
        lblTerlambat.setText(String.valueOf(terlambat));
        
    } catch (Exception e) {
        System.err.println("Error load absensi hari ini: " + e.getMessage());
        e.printStackTrace();
        
        // Set default value jika error
        lblHadir.setText("0");
        lblIzin.setText("0");
        lblSakit.setText("0");
        lblAlfa.setText("0");
        lblTerlambat.setText("0");
    }
}

    private void tampilkanChart() {
    try {
        // Null check
        if (absensiDAO == null || siswaDAO == null) {
            System.err.println("⚠ DAO belum diinisialisasi!");
            return;
        }
        
        // Buat dataset untuk line chart
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        // Ambil data 7 hari terakhir
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
        Calendar cal = Calendar.getInstance();
        
        for (int i = 6; i >= 0; i--) {
            cal.setTime(new Date());
            cal.add(Calendar.DAY_OF_MONTH, -i);
            
            java.sql.Date tanggal = new java.sql.Date(cal.getTimeInMillis());
            String labelTanggal = i == 0 ? "Hari ini" : sdf.format(tanggal);
            
            // Hitung total hadir untuk hari ini
            List<Absensi> listAbsensi = absensiDAO.getAbsensiByTanggal(tanggal, tanggal);
            int totalHadir = 0;
            
            for (Absensi absensi : listAbsensi) {
                String status = absensi.getStatus().toLowerCase();
                if (status.contains("hadir") || status.contains("telat") || status.contains("terlambat")) {
                    totalHadir++;
                }
            }
            
            // Tambahkan ke dataset
            dataset.addValue(totalHadir, "Siswa Hadir", labelTanggal);
        }
        
        // Buat LINE CHART
        JFreeChart chart = ChartFactory.createLineChart(
                "Grafik Kehadiran 7 Hari Terakhir",
                "Tanggal",
                "Jumlah Siswa",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );
        
        // Kustomisasi tampilan chart
        chart.setBackgroundPaint(new java.awt.Color(30, 192, 213)); // Purple background
        chart.getTitle().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 16));
        chart.getTitle().setPaint(java.awt.Color.WHITE);
        
        // Kustomisasi plot
        org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new java.awt.Color(30, 192, 213)); // Purple
        plot.setRangeGridlinePaint(new java.awt.Color(200, 150, 210)); // Light purple gridlines
        plot.setOutlineVisible(false);
        plot.setDomainGridlinesVisible(false);
        
        // Kustomisasi line renderer
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer = 
            new org.jfree.chart.renderer.category.LineAndShapeRenderer();
        
        // Set line properties
        renderer.setSeriesPaint(0, java.awt.Color.BLUE); // White line
        renderer.setSeriesStroke(0, new java.awt.BasicStroke(
            3.0f, // Line width
            java.awt.BasicStroke.CAP_ROUND,
            java.awt.BasicStroke.JOIN_ROUND
        ));
        
        // Set shape properties (titik)
        renderer.setSeriesShape(0, new java.awt.geom.Ellipse2D.Double(-5, -5, 10, 10)); // Circle dengan diameter 10
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
        
        // Set renderer ke plot
        plot.setRenderer(renderer);
        
        // Kustomisasi X-Axis (tanggal)
        org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        domainAxis.setTickLabelPaint(java.awt.Color.WHITE);
        domainAxis.setAxisLinePaint(java.awt.Color.WHITE);
        domainAxis.setTickMarksVisible(false);
        domainAxis.setLabelPaint(java.awt.Color.WHITE);
        domainAxis.setCategoryMargin(0.0);
        domainAxis.setLowerMargin(0.05);
        domainAxis.setUpperMargin(0.05);
        
        // Kustomisasi Y-Axis (jumlah siswa)
        org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
        rangeAxis.setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
        rangeAxis.setTickLabelPaint(java.awt.Color.WHITE);
        rangeAxis.setAxisLinePaint(java.awt.Color.WHITE);
        rangeAxis.setTickMarksVisible(false);
        rangeAxis.setLabelPaint(java.awt.Color.WHITE);
        rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createIntegerTickUnits());
        
        // Auto range untuk Y-axis
        rangeAxis.setAutoRange(true);
        rangeAxis.setAutoRangeIncludesZero(true);
        
        // Buat ChartPanell
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(
            panelChart.getWidth() > 0 ? panelChart.getWidth() : 650, 
            340 // Tinggi chart seperti di gambar
        ));
        chartPanel.setMinimumSize(new java.awt.Dimension(400, 180));
        chartPanel.setMaximumSize(new java.awt.Dimension(1000, 250));
        chartPanel.setBackground(new java.awt.Color(255, 255, 255));
        
        // Disable zoom
        chartPanel.setDomainZoomable(false);
        chartPanel.setRangeZoomable(false);
        
        // Tambahkan ke panelChart
        panelChart.removeAll();
        panelChart.setLayout(new java.awt.BorderLayout());
        panelChart.add(chartPanel, java.awt.BorderLayout.CENTER);
        panelChart.revalidate();
        panelChart.repaint();
        
        System.out.println("✅ Line chart berhasil ditampilkan");
        
    } catch (Exception e) {
        System.err.println("Error membuat chart: " + e.getMessage());
        e.printStackTrace();
    }
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelCustom4 = new ClassTambahan.JPanelCustom();
        jLabel2 = new javax.swing.JLabel();
        lblSiswa = new javax.swing.JLabel();
        jPanelCustom5 = new ClassTambahan.JPanelCustom();
        jLabel5 = new javax.swing.JLabel();
        lblKelas = new javax.swing.JLabel();
        panelChart = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblHadir = new javax.swing.JLabel();
        lblIzin = new javax.swing.JLabel();
        lblSakit = new javax.swing.JLabel();
        lblAlfa = new javax.swing.JLabel();
        lblTerlambat = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Dashboard");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(873, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        jPanelCustom4.setBackground(new java.awt.Color(0, 255, 255));
        jPanelCustom4.setRoundBottomLeft(16);
        jPanelCustom4.setRoundBottomRight(16);
        jPanelCustom4.setRoundTopLeft(16);
        jPanelCustom4.setRoundTopRight(16);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Siswa");

        lblSiswa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSiswa.setForeground(new java.awt.Color(255, 255, 255));
        lblSiswa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSiswa.setText("90");

        javax.swing.GroupLayout jPanelCustom4Layout = new javax.swing.GroupLayout(jPanelCustom4);
        jPanelCustom4.setLayout(jPanelCustom4Layout);
        jPanelCustom4Layout.setHorizontalGroup(
            jPanelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom4Layout.setVerticalGroup(
            jPanelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSiswa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelCustom5.setBackground(new java.awt.Color(0, 153, 255));
        jPanelCustom5.setRoundBottomLeft(16);
        jPanelCustom5.setRoundBottomRight(16);
        jPanelCustom5.setRoundTopLeft(16);
        jPanelCustom5.setRoundTopRight(16);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Kelas");

        lblKelas.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblKelas.setForeground(new java.awt.Color(255, 255, 255));
        lblKelas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKelas.setText("90");

        javax.swing.GroupLayout jPanelCustom5Layout = new javax.swing.GroupLayout(jPanelCustom5);
        jPanelCustom5.setLayout(jPanelCustom5Layout);
        jPanelCustom5Layout.setHorizontalGroup(
            jPanelCustom5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblKelas, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom5Layout.setVerticalGroup(
            jPanelCustom5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblKelas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelChartLayout = new javax.swing.GroupLayout(panelChart);
        panelChart.setLayout(panelChartLayout);
        panelChartLayout.setHorizontalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelChartLayout.setVerticalGroup(
            panelChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 408, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(0, 0, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Absensi Harian");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(219, 219, 219)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Hadir");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Izin");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel9.setText("Alfa");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel10.setText("Sakit");

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Terlambat");

        lblHadir.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblHadir.setText("0");

        lblIzin.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblIzin.setText("0");

        lblSakit.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblSakit.setText("0");

        lblAlfa.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblAlfa.setText("0");

        lblTerlambat.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblTerlambat.setText("0");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(lblHadir)))
                .addGap(59, 59, 59)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(78, 78, 78)
                        .addComponent(jLabel10))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblIzin)
                        .addGap(103, 103, 103)
                        .addComponent(lblSakit)))
                .addGap(82, 82, 82)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(jLabel11)
                        .addGap(43, 43, 43))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblAlfa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblTerlambat)
                        .addGap(78, 78, 78))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHadir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblIzin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSakit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblAlfa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblTerlambat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelCustom4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelCustom5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustom4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private ClassTambahan.JPanelCustom jPanelCustom4;
    private ClassTambahan.JPanelCustom jPanelCustom5;
    private javax.swing.JLabel lblAlfa;
    private javax.swing.JLabel lblHadir;
    private javax.swing.JLabel lblIzin;
    private javax.swing.JLabel lblKelas;
    private javax.swing.JLabel lblSakit;
    private javax.swing.JLabel lblSiswa;
    private javax.swing.JLabel lblTerlambat;
    private javax.swing.JPanel panelChart;
    // End of variables declaration//GEN-END:variables
}
