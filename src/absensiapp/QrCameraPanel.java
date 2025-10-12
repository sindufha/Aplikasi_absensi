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
private Webcam webcam;
private WebcamPanel webcamPanel;
private javax.swing.JLabel onlineStatus;
    /**
     * Creates new form QrCameraPanel
     */
    public QrCameraPanel() {
        loadCameras();
        initComponents();
       
        
    }
public void setStatusLabel(javax.swing.JLabel label) {
    this.onlineStatus = label;
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
                    if (onlineStatus != null) {
                    onlineStatus.setText("Terhubung: " + selectedCamera);
                    onlineStatus.setForeground(new java.awt.Color(46, 204, 113));
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
        if (onlineStatus != null) {
                    onlineStatus.setText("Terputus: " );
                    onlineStatus.setForeground(new java.awt.Color(231, 76, 60));
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

        jPanelCamera.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout jPanelCameraLayout = new javax.swing.GroupLayout(jPanelCamera);
        jPanelCamera.setLayout(jPanelCameraLayout);
        jPanelCameraLayout.setHorizontalGroup(
            jPanelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 634, Short.MAX_VALUE)
        );
        jPanelCameraLayout.setVerticalGroup(
            jPanelCameraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 445, Short.MAX_VALUE)
        );

        jComboBoxCamera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonConnect.setText("Connect");
        jButtonConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConnectActionPerformed(evt);
            }
        });

        jLabelStatus.setText("Status");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBoxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jLabelStatus))
                    .addComponent(jPanelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 496, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCamera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCamera, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonConnect, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelStatus))
                .addGap(0, 169, Short.MAX_VALUE))
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
    private javax.swing.JPanel jPanelCamera;
    // End of variables declaration//GEN-END:variables
}
