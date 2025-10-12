package absensiapp;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class QRScannerPanel extends JPanel {
    private JComboBox<String> cameraComboBox;
    private JButton btnConnect;
    private JLabel lblStatus;
    private JPanel cameraPanel;
    private Webcam webcam;
    private WebcamPanel webcamPanel;

    public QRScannerPanel() {
        initComponents();
        loadCameras();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new TitledBorder("QR Scanner - Camera Detection"));

        // Panel atas: kontrol
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        controlPanel.setBackground(new Color(240, 240, 240));

        // Label
        JLabel lblCamera = new JLabel("Pilih Kamera:");
        controlPanel.add(lblCamera);

        // Combo Box kamera
        cameraComboBox = new JComboBox<>();
        cameraComboBox.setPreferredSize(new Dimension(250, 30));
        controlPanel.add(cameraComboBox);

        // Tombol Connect
        btnConnect = new JButton("Connect");
        btnConnect.setPreferredSize(new Dimension(100, 30));
        btnConnect.setBackground(new Color(52, 152, 219));
        btnConnect.setForeground(Color.WHITE);
        btnConnect.addActionListener(e -> connectCamera());
        controlPanel.add(btnConnect);

        // Label Status
        lblStatus = new JLabel("Status: Belum terhubung");
        lblStatus.setForeground(new Color(231, 76, 60));
        controlPanel.add(lblStatus);

        add(controlPanel, BorderLayout.NORTH);

        // Panel tengah: preview kamera
        cameraPanel = new JPanel();
        cameraPanel.setBackground(Color.BLACK);
        cameraPanel.setPreferredSize(new Dimension(640, 480));
        cameraPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        add(cameraPanel, BorderLayout.CENTER);
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
            btnConnect.setText("Connect");
            lblStatus.setText("Status: Terputus");
            lblStatus.setForeground(new Color(231, 76, 60));
            return;
        }

        String selectedCamera = (String) cameraComboBox.getSelectedItem();

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
                    webcam.setViewSize(WebcamResolution.VGA.getSize());
                    webcam.open();

                    SwingUtilities.invokeLater(() -> {
                        cameraPanel.removeAll();
                        webcamPanel = new WebcamPanel(webcam);
                        webcamPanel.setPreferredSize(new Dimension(640, 480));
                        cameraPanel.add(webcamPanel);
                        cameraPanel.revalidate();
                        cameraPanel.repaint();

                        lblStatus.setText("Status: Terhubung ✓ " + selectedCamera);
                        lblStatus.setForeground(new Color(46, 204, 113));
                        btnConnect.setText("Disconnect");

                        JOptionPane.showMessageDialog(QRScannerPanel.this, 
                            "Kamera berhasil terhubung!\n" + selectedCamera, 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                    });
                }
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    lblStatus.setText("Status: Error - " + e.getMessage());
                    lblStatus.setForeground(new Color(231, 76, 60));
                    JOptionPane.showMessageDialog(QRScannerPanel.this, 
                        "Gagal terhubung ke kamera:\n" + e.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        });
        thread.start();
    }

    private void disconnectCamera() {
        if (webcam != null) {
            webcam.close();
            cameraPanel.removeAll();
            cameraPanel.revalidate();
            cameraPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("QR Scanner - Camera Detection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new QRScannerPanel());
            frame.setSize(700, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}