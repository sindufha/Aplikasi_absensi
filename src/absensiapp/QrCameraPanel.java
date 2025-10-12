/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package absensiapp;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author MyBook Hype AMD
 */
public class QrCameraPanel extends javax.swing.JPanel {
public Webcam webcam;
private WebcamPanel webcamPanel;
private MainFrame mainFrame;
    /**
     * Creates new form QrCameraPanel
     */
    public QrCameraPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        loadCameras();
    }

private void loadCameras() {
    new Thread(() -> {
        try {
            java.util.List<Webcam> webcams = Webcam.getWebcams();

            SwingUtilities.invokeLater(() -> {
                jComboBoxCamera.removeAllItems();
                
                if (webcams.isEmpty()) {
                    jComboBoxCamera.addItem("Tidak ada kamera terdeteksi");
                    jComboBoxCamera.setEnabled(false);
                } else {
                    for (Webcam cam : webcams) {
                        jComboBoxCamera.addItem(cam.getName());
                    }
                    jComboBoxCamera.setEnabled(true);
                }
            });
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                jComboBoxCamera.removeAllItems();
                jComboBoxCamera.addItem("Error: " + e.getMessage());
                jComboBoxCamera.setEnabled(false);
            });
        }
    }).start();
}

// ===== Method 2: Connect Camera =====
private void connectCamera() {
    if (webcam != null && webcam.isOpen()) {
        disconnectCamera();
        jButtonConnect.setText("Connect");
        jLabelStatus.setText("Status: Terputus");
        jLabelStatus.setForeground(new java.awt.Color(231, 76, 60));
        return;
    }

    String selectedCamera = (String) jComboBoxCamera.getSelectedItem();

    if (selectedCamera == null || selectedCamera.contains("Tidak ada") 
        || selectedCamera.contains("Scanning")) {
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
                webcam.setViewSize(com.github.sarxos.webcam.WebcamResolution.VGA.getSize());
                webcam.open();

                SwingUtilities.invokeLater(() -> {
                    jPanelCamera.removeAll();
                    webcamPanel = new WebcamPanel(webcam);
                    webcamPanel.setPreferredSize(new java.awt.Dimension(640, 480));
                    webcamPanel.setSize(640, 480);
                    jPanelCamera.add(webcamPanel);
                    jPanelCamera.revalidate();
                    jPanelCamera.repaint();

                    jLabelStatus.setText("Status: Terhubung ✓ " + selectedCamera);
                    jLabelStatus.setForeground(new java.awt.Color(46, 204, 113));
                    jButtonConnect.setText("Disconnect");

                    JOptionPane.showMessageDialog(this, 
                        "Kamera berhasil terhubung!\n" + selectedCamera, 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    if (MainFrame.onlineStatus != null) {
            mainFrame.onlineStatus.setText("Online");
            mainFrame.onlineStatus.setForeground(new java.awt.Color(18, 231, 60));
        }
                    
                });
            }
        } catch (Exception e) {
            SwingUtilities.invokeLater(() -> {
                jLabelStatus.setText("Status: Error - " + e.getMessage());
                jLabelStatus.setForeground(new java.awt.Color(231, 76, 60));
                JOptionPane.showMessageDialog(this, 
                    "Gagal terhubung ke kamera:\n" + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            });
        }
    });
    thread.start();
}

// ===== Method 3: Disconnect Camera =====
private void disconnectCamera() {
    if (webcam != null) {
        webcam.close();
        jPanelCamera.removeAll();
        jPanelCamera.revalidate();
        jPanelCamera.repaint();
        if (MainFrame.onlineStatus != null) {
            mainFrame.onlineStatus.setText("Offline");
            mainFrame.onlineStatus.setForeground(new java.awt.Color(231, 76, 60));
        }
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

        test1 = new test.Test();
        jPanelCamera = new javax.swing.JPanel();
        jComboBoxCamera = new javax.swing.JComboBox<>();
        jButtonConnect = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();
        jPanelCustom2 = new test.JPanelCustom();
        jPanelCustom1 = new test.JPanelCustom();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelStatus1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanelCamera.setBackground(new java.awt.Color(51, 51, 51));
        jPanelCamera.setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout jPanelCameraLayout = new javax.swing.GroupLayout(jPanelCamera);
        jPanelCamera.setLayout(jPanelCameraLayout);
        jPanelCameraLayout.setHorizontalGroup(
            jPanelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );
        jPanelCameraLayout.setVerticalGroup(
            jPanelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 403, Short.MAX_VALUE)
        );

        jComboBoxCamera.setForeground(new java.awt.Color(0, 51, 255));
        jComboBoxCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBoxCamera.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 51, 255), 2, true));

        jButtonConnect.setBackground(new java.awt.Color(42, 149, 255));
        jButtonConnect.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButtonConnect.setForeground(new java.awt.Color(255, 255, 255));
        jButtonConnect.setText("Connect");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jLabelStatus.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelStatus.setForeground(new java.awt.Color(51, 51, 51));
        jLabelStatus.setText("Status Kamera : ");

        jPanelCustom2.setBackground(new java.awt.Color(37, 99, 235));
        jPanelCustom2.setRoundBottomLeft(10);
        jPanelCustom2.setRoundBottomRight(10);
        jPanelCustom2.setRoundTopLeft(10);
        jPanelCustom2.setRoundTopRight(10);

        jPanelCustom1.setBackground(new java.awt.Color(255, 255, 255));
        jPanelCustom1.setRoundBottomLeft(8);
        jPanelCustom1.setRoundBottomRight(8);
        jPanelCustom1.setRoundTopLeft(8);
        jPanelCustom1.setRoundTopRight(8);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 102, 102));
        jLabel7.setText("Jam Absen : 06:15 ");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Kelas : 5 - B");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 102, 102));
        jLabel5.setText("NIS : 100931");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 102, 102));
        jLabel4.setText("Nama Siswa : Ahmad Fauzi");

        javax.swing.GroupLayout jPanelCustom1Layout = new javax.swing.GroupLayout(jPanelCustom1);
        jPanelCustom1.setLayout(jPanelCustom1Layout);
        jPanelCustom1Layout.setHorizontalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addContainerGap(134, Short.MAX_VALUE))
        );
        jPanelCustom1Layout.setVerticalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Jam Masuk kelas : 07:00 ");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal : 20/10/2025");

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Histori Absensi ");

        javax.swing.GroupLayout jPanelCustom2Layout = new javax.swing.GroupLayout(jPanelCustom2);
        jPanelCustom2.setLayout(jPanelCustom2Layout);
        jPanelCustom2Layout.setHorizontalGroup(
            jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCustom2Layout.createSequentialGroup()
                        .addComponent(jPanelCustom1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(8, 8, 8))
                    .addGroup(jPanelCustom2Layout.createSequentialGroup()
                        .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanelCustom2Layout.setVerticalGroup(
            jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(269, Short.MAX_VALUE))
        );

        jLabelStatus1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelStatus1.setForeground(new java.awt.Color(0, 102, 255));
        jLabelStatus1.setText("Pilih perangkat Kamera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jComboBoxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(25, 25, 25)
                                        .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabelStatus1))))
                        .addGap(49, 49, 49)
                        .addComponent(jPanelCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelStatus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanelCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabelStatus1)
                        .addGap(12, 12, 12)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStatus)
                .addGap(0, 217, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConnectActionPerformed
        // TODO add your handling code here:
        connectCamera();
    }//GEN-LAST:event_jButtonConnectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonConnect;
    private javax.swing.JComboBox<String> jComboBoxCamera;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelStatus1;
    private javax.swing.JPanel jPanelCamera;
    private test.JPanelCustom jPanelCustom1;
    private test.JPanelCustom jPanelCustom2;
    private test.Test test1;
    // End of variables declaration//GEN-END:variables
}
