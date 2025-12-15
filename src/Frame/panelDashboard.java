/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author MyBook Hype AMD
 */
public class panelDashboard extends javax.swing.JPanel {

    public panelDashboard() {
        initComponents();
        tampilkanChart();
    }

     private void tampilkanChart() {
        // Buat dataset
         DefaultCategoryDataset dataset = new DefaultCategoryDataset();
         dataset.setValue(90.90, "Hadir", "Hadir");
         dataset.setValue(70.90, "Izin", "Izin");
         dataset.setValue(50.90, "Sakit", "Sakit");
         dataset.setValue(30.90, "Terlambat", "Terlambat");
         dataset.setValue(10.90, "Alfa", "Alfa");

         // Buat chart HORIZONTAL
         JFreeChart chart = ChartFactory.createBarChart(
                 "Data Absensi SDI Khadijah",
                 "Keterangan",
                 "Presentase (%)",
                 dataset,
                 PlotOrientation.VERTICAL,
                 false,
                 true,
                 false
         );

         // Kustomisasi tampilan chart
         chart.setBackgroundPaint(java.awt.Color.WHITE);
         chart.getTitle().setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 18));

         // Kustomisasi plot
         org.jfree.chart.plot.CategoryPlot plot = chart.getCategoryPlot();
         plot.setBackgroundPaint(java.awt.Color.WHITE);
         plot.setRangeGridlinePaint(new java.awt.Color(200, 200, 200));
         plot.setOutlineVisible(false);

         // Kustomisasi bar renderer dengan warna berbeda untuk setiap kategori
         org.jfree.chart.renderer.category.BarRenderer renderer = new org.jfree.chart.renderer.category.BarRenderer() {
             @Override
             public java.awt.Paint getItemPaint(int row, int column) {
                 // row = series, column = kategori
                 switch (row) {
                     case 0:
                         return new java.awt.Color(0, 255, 255);      // Hadir - Cyan
                     case 1:
                         return new java.awt.Color(0, 153, 255);      // Izin - Blue  
                     case 2:
                         return new java.awt.Color(0, 102, 255);      // Sakit - Dark Blue
                     case 3:
                         return new java.awt.Color(51, 0, 255);       // Terlambat - Purple
                     case 4:
                         return new java.awt.Color(255, 51, 0);       // Alfa - Red
                     default:
                         return java.awt.Color.GRAY;
                 }
             }
         };

         plot.setRenderer(renderer);
         renderer.setBarPainter(new org.jfree.chart.renderer.category.StandardBarPainter());
         renderer.setShadowVisible(false);
         renderer.setDrawBarOutline(false);
         
         renderer.setItemMargin(0.0);  
         renderer.setMinimumBarLength(0.9);
//        renderer.setItem
         // Kustomisasi axis
         
         // Atur lebar kategori agar bar lebih besar
org.jfree.chart.axis.CategoryAxis domainAxis = plot.getDomainAxis();
domainAxis.setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
domainAxis.setCategoryMargin(0.1);  // Atur margin antar kategori (0.0 - 0.5)
domainAxis.setLowerMargin(0.01);    // Margin bawah
domainAxis.setUpperMargin(0.01);    // Margin atas
         domainAxis.setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

         org.jfree.chart.axis.NumberAxis rangeAxis = (org.jfree.chart.axis.NumberAxis) plot.getRangeAxis();
         rangeAxis.setTickLabelFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
         rangeAxis.setRange(0, 100);
         
         // Buat ChartPanel
         ChartPanel chartPanel = new ChartPanel(chart);
         chartPanel.setPreferredSize(new java.awt.Dimension(600, 470));
         chartPanel.setBackground(java.awt.Color.WHITE);

         // Tambahkan ke panelChart
         panelChart.removeAll();
         panelChart.setLayout(new java.awt.BorderLayout());
         panelChart.add(chartPanel, java.awt.BorderLayout.CENTER);
         panelChart.revalidate();
         panelChart.repaint();
     }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanelCustom4 = new ClassTambahan.JPanelCustom();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanelCustom5 = new ClassTambahan.JPanelCustom();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanelCustom6 = new ClassTambahan.JPanelCustom();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanelCustom7 = new ClassTambahan.JPanelCustom();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanelCustom8 = new ClassTambahan.JPanelCustom();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        panelChart = new javax.swing.JPanel();

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
        jLabel2.setText("Hadir");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("90");

        javax.swing.GroupLayout jPanelCustom4Layout = new javax.swing.GroupLayout(jPanelCustom4);
        jPanelCustom4.setLayout(jPanelCustom4Layout);
        jPanelCustom4Layout.setHorizontalGroup(
            jPanelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom4Layout.setVerticalGroup(
            jPanelCustom4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom4Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
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
        jLabel5.setText("Izin");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("90");

        javax.swing.GroupLayout jPanelCustom5Layout = new javax.swing.GroupLayout(jPanelCustom5);
        jPanelCustom5.setLayout(jPanelCustom5Layout);
        jPanelCustom5Layout.setHorizontalGroup(
            jPanelCustom5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom5Layout.setVerticalGroup(
            jPanelCustom5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanelCustom6.setBackground(new java.awt.Color(0, 102, 255));
        jPanelCustom6.setRoundBottomLeft(16);
        jPanelCustom6.setRoundBottomRight(16);
        jPanelCustom6.setRoundTopLeft(16);
        jPanelCustom6.setRoundTopRight(16);

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Sakit");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("90");

        javax.swing.GroupLayout jPanelCustom6Layout = new javax.swing.GroupLayout(jPanelCustom6);
        jPanelCustom6.setLayout(jPanelCustom6Layout);
        jPanelCustom6Layout.setHorizontalGroup(
            jPanelCustom6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom6Layout.setVerticalGroup(
            jPanelCustom6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanelCustom7.setBackground(new java.awt.Color(51, 0, 255));
        jPanelCustom7.setRoundBottomLeft(16);
        jPanelCustom7.setRoundBottomRight(16);
        jPanelCustom7.setRoundTopLeft(16);
        jPanelCustom7.setRoundTopRight(16);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Terlambat");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("90");

        javax.swing.GroupLayout jPanelCustom7Layout = new javax.swing.GroupLayout(jPanelCustom7);
        jPanelCustom7.setLayout(jPanelCustom7Layout);
        jPanelCustom7Layout.setHorizontalGroup(
            jPanelCustom7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom7Layout.setVerticalGroup(
            jPanelCustom7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom7Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addContainerGap(30, Short.MAX_VALUE))
        );

        jPanelCustom8.setBackground(new java.awt.Color(255, 51, 0));
        jPanelCustom8.setRoundBottomLeft(16);
        jPanelCustom8.setRoundBottomRight(16);
        jPanelCustom8.setRoundTopLeft(16);
        jPanelCustom8.setRoundTopRight(16);

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Alfa");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("90");

        javax.swing.GroupLayout jPanelCustom8Layout = new javax.swing.GroupLayout(jPanelCustom8);
        jPanelCustom8.setLayout(jPanelCustom8Layout);
        jPanelCustom8Layout.setHorizontalGroup(
            jPanelCustom8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
        );
        jPanelCustom8Layout.setVerticalGroup(
            jPanelCustom8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom8Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addContainerGap(30, Short.MAX_VALUE))
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
                        .addGap(30, 30, 30)
                        .addComponent(jPanelCustom5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(jPanelCustom6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelCustom7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jPanelCustom8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelCustom5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustom6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustom8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustom7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelCustom4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(49, 49, 49)
                .addComponent(panelChart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(110, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private ClassTambahan.JPanelCustom jPanelCustom4;
    private ClassTambahan.JPanelCustom jPanelCustom5;
    private ClassTambahan.JPanelCustom jPanelCustom6;
    private ClassTambahan.JPanelCustom jPanelCustom7;
    private ClassTambahan.JPanelCustom jPanelCustom8;
    private javax.swing.JPanel panelChart;
    // End of variables declaration//GEN-END:variables
}
