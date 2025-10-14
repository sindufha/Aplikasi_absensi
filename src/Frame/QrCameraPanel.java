/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;
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
public MainFrame mainFrame;
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

        jPanelCamera = new javax.swing.JPanel();
        jComboBoxCamera = new javax.swing.JComboBox<>();
        jButtonConnect = new javax.swing.JButton();
        jLabelStatus = new javax.swing.JLabel();
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

        jLabelStatus1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabelStatus1.setForeground(new java.awt.Color(0, 102, 255));
        jLabelStatus1.setText("Pilih perangkat Kamera");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelStatus)))
                .addContainerGap(367, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelStatus1)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
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
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelStatus1;
    private javax.swing.JPanel jPanelCamera;
    // End of variables declaration//GEN-END:variables
}
