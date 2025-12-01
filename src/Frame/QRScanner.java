/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.*;
import java.util.List;
import ClassAbsensi.AbsensiDAO;
import java.awt.Dimension;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import ClassAbsensi.Absensi;
import ClassAbsensi.QRScannerPanel;
import ClassAbsensi.Siswa;
/**
 *
 * @author MyBook Hype AMD
 */
public class QRScanner extends JPanel {
    private Webcam webcam;
    private WebcamPanel webcamPanel;
    private Thread scanThread;
    private boolean isScanning = false;
    private AbsensiDAO absensiDAO;
    private DefaultTableModel tableModel;
    
  
   public QRScanner() {
        initComponents(); // Auto-generated NetBeans
        setupComponents(); // Custom method kita
    }
private void setupComponents() {
    // Inisialisasi DAO
    absensiDAO = new AbsensiDAO();
    
    // Setup table model
    tableModel = (DefaultTableModel) tableAbsensi.getModel();
    tableAbsensi.setModel(new DefaultTableModel(
        new Object[][] {},
        new String[] {"NIS", "Nama", "Kelas", "Waktu", "Status", "Metode"}
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });
    tableModel = (DefaultTableModel) tableAbsensi.getModel();
    tableAbsensi.getTableHeader().setReorderingAllowed(false);
    
    // Set button colors
    btnConnect.setBackground(new Color(52, 152, 219));
    btnConnect.setForeground(Color.WHITE);
    
    btnStartScan.setBackground(new Color(46, 204, 113));
    btnStartScan.setForeground(Color.WHITE);
    btnStartScan.setEnabled(false);
    
    btnStopScan.setBackground(new Color(231, 76, 60));
    btnStopScan.setForeground(Color.WHITE);
    btnStopScan.setEnabled(false);
    
    // Set camera panel
    cameraPanel.setBackground(Color.BLACK);
    
    // Load kamera dan data
    loadCameras();
    loadAbsensiHariIni();
}
    private void loadCameras() {
    Thread thread = new Thread(() -> {
        cameraComboBox.removeAllItems();
        cameraComboBox.addItem("-- Scanning Kamera --");

        try {
            java.util.List<Webcam> webcams = Webcam.getWebcams();

            if (webcams.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    cameraComboBox.removeAllItems();
                    cameraComboBox.addItem("Tidak ada kamera terdeteksi");
                    cameraComboBox.setEnabled(false);
                });
            } else {
                SwingUtilities.invokeLater(() -> {
                    cameraComboBox.removeAllItems();
                    for (Webcam cam : webcams) {
                        cameraComboBox.addItem(cam.getName());
                    }
                    cameraComboBox.setEnabled(true);
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                cameraComboBox.removeAllItems();
                cameraComboBox.addItem("Error: " + e.getMessage());
                cameraComboBox.setEnabled(false);
            });
        }
    });
    thread.start();
}

private void connectCamera() {
    if (webcam != null && webcam.isOpen()) {
        disconnectCamera();
        return;
    }

    String selectedCamera = (String) cameraComboBox.getSelectedItem();

    if (selectedCamera == null || selectedCamera.contains("Tidak ada") 
        || selectedCamera.contains("Scanning") || selectedCamera.contains("Error")) {
        JOptionPane.showMessageDialog(this, "Pilih kamera terlebih dahulu!", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    Thread thread = new Thread(() -> {
        try {
            java.util.List<Webcam> webcams = Webcam.getWebcams();
            for (Webcam cam : webcams) {
                if (cam.getName().equals(selectedCamera)) {
                    webcam = cam;
                    break;
                }
            }

            if (webcam != null) {
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.open();

                SwingUtilities.invokeLater(() -> {
                    // PERBAIKAN: Clear panel dengan benar
                    cameraPanel.removeAll();
                    
                    // PERBAIKAN: Set layout null atau FlowLayout
                    cameraPanel.setLayout(new java.awt.BorderLayout());
                    
                    webcamPanel = new WebcamPanel(webcam);
                    webcamPanel.setFPSDisplayed(true); // Show FPS untuk debug
                    webcamPanel.setDisplayDebugInfo(true); // Show debug info
                    
                    // PERBAIKAN: Add ke center
                    cameraPanel.add(webcamPanel, java.awt.BorderLayout.CENTER);
                    
                    // PENTING: Revalidate dan repaint
                    cameraPanel.revalidate();
                    cameraPanel.repaint();
                    cameraPanel.updateUI();

                    lblStatus.setText("Terhubung ✓");
                    lblStatus.setForeground(new Color(46, 204, 113));
                    btnConnect.setText("Disconnect");
                    btnStartScan.setEnabled(true);
                    
                    System.out.println("Camera connected: " + webcam.getName());
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> {
                lblStatus.setText("Error");
                lblStatus.setForeground(new Color(231, 76, 60));
                JOptionPane.showMessageDialog(QRScanner.this, 
                    "Gagal terhubung ke kamera:\n" + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    });
    thread.start();
}
public void cleanup() {
    stopScanning();
    if (webcam != null && webcam.isOpen()) {
        webcam.close();
    }
}
private void disconnectCamera() {
    // PERBAIKAN: Stop scanning dulu
    stopScanning();
    
    // PERBAIKAN: Close webcam dengan aman
    SwingUtilities.invokeLater(() -> {
        try {
            if (webcamPanel != null) {
                webcamPanel.stop(); // Stop panel dulu
                cameraPanel.remove(webcamPanel);
                webcamPanel = null;
            }
            
            if (webcam != null && webcam.isOpen()) {
                webcam.close();
                System.out.println("Webcam closed");
            }
            
            cameraPanel.removeAll();
            cameraPanel.revalidate();
            cameraPanel.repaint();
            
            lblStatus.setText("Terputus");
            lblStatus.setForeground(new Color(231, 76, 60));
            btnConnect.setText("Connect");
            btnStartScan.setEnabled(false);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    });
}

private void startScanning() {
    if (webcam == null || !webcam.isOpen()) {
        JOptionPane.showMessageDialog(this, 
            "Kamera belum terhubung!", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    isScanning = true;
    btnStartScan.setEnabled(false);
    btnStopScan.setEnabled(true);
    btnConnect.setEnabled(false);
    lblScanStatus.setText("🔍 Scanning... Tunjukkan QR Code");
    lblScanStatus.setForeground(new Color(52, 152, 219));

    scanThread = new Thread(() -> {
        System.out.println("Scan thread started");
        
        while (isScanning) {
            try {
                if (webcam != null && webcam.isOpen()) {
                    BufferedImage image = webcam.getImage();
                    
                    if (image != null) {
                        String qrCodeText = decodeQRCode(image);
                        
                        if (qrCodeText != null && !qrCodeText.isEmpty()) {
                            System.out.println("QR Code detected: " + qrCodeText);
                            
                            // PERBAIKAN: Pause scanning sementara
                            final String qrData = qrCodeText;
                            isScanning = false; // Stop scanning sementara
                            
                            SwingUtilities.invokeLater(() -> {
                                prosesAbsensi(qrData);
                            });
                            
                            // Delay sebelum scan lagi
                            Thread.sleep(3000);
                            isScanning = true; // Resume scanning
                        }
                    }
                }
                
                // PERBAIKAN: Delay lebih pendek untuk responsif
                Thread.sleep(300);
                
            } catch (InterruptedException e) {
                System.out.println("Scan thread interrupted");
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Scan thread stopped");
    });
    
    scanThread.setDaemon(true); // PENTING: Set daemon agar tidak blocking
    scanThread.start();
}

private void stopScanning() {
    System.out.println("Stopping scan...");
    isScanning = false;
    
    if (scanThread != null && scanThread.isAlive()) {
        scanThread.interrupt();
        try {
            scanThread.join(1000); // Tunggu max 1 detik
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    SwingUtilities.invokeLater(() -> {
        btnStartScan.setEnabled(true);
        btnStopScan.setEnabled(false);
        btnConnect.setEnabled(true);
        lblScanStatus.setText("Scan dihentikan");
        lblScanStatus.setForeground(new Color(149, 165, 166));
    });
}

private String decodeQRCode(BufferedImage image) {
    if (image == null) return null;
    
    try {
        // PERBAIKAN: Tambah hints untuk better detection
        java.util.Map<com.google.zxing.DecodeHintType, Object> hints = new java.util.HashMap<>();
        hints.put(com.google.zxing.DecodeHintType.TRY_HARDER, Boolean.TRUE);
        hints.put(com.google.zxing.DecodeHintType.POSSIBLE_FORMATS, 
                  java.util.Arrays.asList(com.google.zxing.BarcodeFormat.QR_CODE));
        
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
        
    } catch (NotFoundException e) {
        // QR tidak ditemukan - normal
        return null;
    } catch (Exception e) {
        // Error lain
        return null;
    }
}

private void prosesAbsensi(String qrCodeData) {
    System.out.println("Processing absensi for: " + qrCodeData);
    
    // PERBAIKAN: Buat variabel final untuk digunakan di lambda
    final String originalQrCode = qrCodeData;
    
    // Tambahkan prefix "QR" jika belum ada
    String qrCodeToSearch = qrCodeData;
    if (!qrCodeData.startsWith("QR")) {
        qrCodeToSearch = "QR" + qrCodeData;
    }
    
    final String finalQrCodeToSearch = qrCodeToSearch; // PENTING: Buat final
    
    System.out.println("Searching in database: " + finalQrCodeToSearch);
    
    Siswa siswa = absensiDAO.getSiswaByQRCode(finalQrCodeToSearch);
    
    if (siswa == null) {
        lblScanStatus.setText("❌ QR Code tidak terdaftar!");
        lblScanStatus.setForeground(new Color(231, 76, 60));
        Toolkit.getDefaultToolkit().beep();
        
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this,
                "QR Code tidak terdaftar!\n" + 
                "Data yang di-scan: " + originalQrCode + "\n" +
                "Dicari di database: " + finalQrCodeToSearch,
                "Error",
                JOptionPane.ERROR_MESSAGE);
        });
        return;
    }
    
    System.out.println("Siswa ditemukan: " + siswa.getNamaSiswa());
    
    Date today = new Date(System.currentTimeMillis());
    boolean sudahAbsen = absensiDAO.sudahAbsenHariIni(siswa.getIdSiswa(), today);
    
    // PERBAIKAN: Buat variabel final untuk siswa info
    final String nis = siswa.getNis();
    final String nama = siswa.getNamaSiswa();
    final String kelas = siswa.getNamaKelas() != null ? siswa.getNamaKelas() : "-";
    
    if (sudahAbsen) {
        boolean success = absensiDAO.updateAbsensiPulang(siswa.getIdSiswa(), today);
        
        if (success) {
            lblScanStatus.setText("✓ Absen Pulang: " + nama);
            lblScanStatus.setForeground(new Color(46, 204, 113));
            Toolkit.getDefaultToolkit().beep();
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Absen Pulang Berhasil!\n\n" +
                    "NIS: " + nis + "\n" +
                    "Nama: " + nama + "\n" +
                    "Kelas: " + kelas,
                    "Absen Pulang",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        }
    } else {
        boolean success = absensiDAO.insertAbsensiMasuk(siswa.getIdSiswa(), "QR");
        
        if (success) {
            lblScanStatus.setText("✓ Absen Masuk: " + nama);
            lblScanStatus.setForeground(new Color(46, 204, 113));
            Toolkit.getDefaultToolkit().beep();
            
            SwingUtilities.invokeLater(() -> {
                JOptionPane.showMessageDialog(this,
                    "Absen Masuk Berhasil!\n\n" +
                    "NIS: " + nis + "\n" +
                    "Nama: " + nama + "\n" +
                    "Kelas: " + kelas,
                    "Absen Masuk",
                    JOptionPane.INFORMATION_MESSAGE);
            });
        } else {
            lblScanStatus.setText("❌ Gagal menyimpan absensi!");
            lblScanStatus.setForeground(new Color(231, 76, 60));
        }
    }
    
    loadAbsensiHariIni();
}

private void loadAbsensiHariIni() {
    tableModel.setRowCount(0);
    
    List<Absensi> listAbsensi = absensiDAO.getAbsensiHariIni();
    
    for (Absensi absensi : listAbsensi) {
        Object[] row = {
            absensi.getNis(),
            absensi.getNamaSiswa(),
            absensi.getNamaKelas(),
            absensi.getWaktuMasuk(),
            absensi.getStatus(),
            absensi.getMetode()
        };
        tableModel.addRow(row);
    }
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        cameraPanel = new javax.swing.JPanel();
        cameraComboBox = new javax.swing.JComboBox<>();
        btnConnect = new javax.swing.JButton();
        lblStatus11 = new javax.swing.JLabel();
        lblCamera = new javax.swing.JLabel();
        btnStartScan = new javax.swing.JButton();
        btnStopScan = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        lblScanStatus = new javax.swing.JLabel();
        btnRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAbsensi = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        cameraPanel.setBackground(new java.awt.Color(51, 51, 51));
        cameraPanel.setMaximumSize(new java.awt.Dimension(700, 700));
        cameraPanel.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout cameraPanelLayout = new javax.swing.GroupLayout(cameraPanel);
        cameraPanel.setLayout(cameraPanelLayout);
        cameraPanelLayout.setHorizontalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        cameraPanelLayout.setVerticalGroup(
            cameraPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        cameraComboBox.setForeground(new java.awt.Color(0, 51, 255));
        cameraComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cameraComboBox.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 2, true));

        btnConnect.setBackground(new java.awt.Color(42, 149, 255));
        btnConnect.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnConnect.setForeground(new java.awt.Color(255, 255, 255));
        btnConnect.setText("Connect");
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        lblStatus11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblStatus11.setForeground(new java.awt.Color(51, 51, 51));
        lblStatus11.setText("Status Kamera : ");

        lblCamera.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblCamera.setForeground(new java.awt.Color(0, 102, 255));
        lblCamera.setText("Pilih perangkat Kamera");

        btnStartScan.setText("Start");
        btnStartScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartScanActionPerformed(evt);
            }
        });

        btnStopScan.setText("Stop");
        btnStopScan.setEnabled(false);
        btnStopScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopScanActionPerformed(evt);
            }
        });

        lblStatus.setText("Belum Terhubung");

        lblScanStatus.setText("Scan QR Untuk Absensi");

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        tableAbsensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "NIS", "Nama", "Kelas", "Waktu", "Status", "Metode"
            }
        ));
        jScrollPane1.setViewportView(tableAbsensi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCamera)
                    .addComponent(cameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblStatus11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblStatus))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnStartScan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnStopScan, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(cameraComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(lblScanStatus)
                                .addGap(46, 46, 46)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cameraPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCamera)
                            .addComponent(lblScanStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cameraComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatus11)
                    .addComponent(lblStatus))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnStartScan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnStopScan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed
        // TODO add your handling code here:
        connectCamera();
    }//GEN-LAST:event_btnConnectActionPerformed

    private void btnStartScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartScanActionPerformed
        startScanning();
    }//GEN-LAST:event_btnStartScanActionPerformed

    private void btnStopScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopScanActionPerformed
      stopScanning();
    }//GEN-LAST:event_btnStopScanActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        loadAbsensiHariIni();
    }//GEN-LAST:event_btnRefreshActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnStartScan;
    private javax.swing.JButton btnStopScan;
    private javax.swing.JComboBox<String> cameraComboBox;
    private javax.swing.JPanel cameraPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCamera;
    private javax.swing.JLabel lblScanStatus;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblStatus11;
    private javax.swing.JTable tableAbsensi;
    // End of variables declaration//GEN-END:variables
}