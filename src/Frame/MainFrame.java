
package Frame;
import ClassAbsensi.SidebarButtonManager;
import ClassAbsensi.QRScannerPanel;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.UIManager;
import ClassTambahan.JPanelCustom;
/**
 *
 * @author MyBook Hype AMD
 */
public class MainFrame extends javax.swing.JFrame {
    private SidebarButtonManager sidebar;
    private ClassTambahan.JPanelCustom Sidebar;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        
        btnDashboard.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnDashboard.setHorizontalAlignment(SwingConstants.LEFT);
        btnDashboard.setMargin(new Insets(5, 20, 5, 5));

        btnSiswa.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnSiswa.setHorizontalAlignment(SwingConstants.LEFT);
        btnSiswa.setMargin(new Insets(5, 20, 5, 5));
        
        btnAbsensi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnAbsensi.setHorizontalAlignment(SwingConstants.LEFT);
        btnAbsensi.setMargin(new Insets(5, 20, 5, 5));
        
        btnLaporan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnLaporan.setHorizontalAlignment(SwingConstants.LEFT);
        btnLaporan.setMargin(new Insets(5, 20, 5, 5));
        
        btnPengaturan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnPengaturan.setHorizontalAlignment(SwingConstants.LEFT);
        btnPengaturan.setMargin(new Insets(5, 20, 5, 5));
        
        sidebar = new SidebarButtonManager(
            btnDashboard, btnSiswa,btnAbsensi,btnLaporan,btnPengaturan
        );
    sidebar.setButtonIcons(btnDashboard, 
        "src/ikon_white/home-white.png", 
        "src/ikon_blue/home-blue-35.png");
    
    sidebar.setButtonIcons(btnSiswa, 
        "src/ikon_white/student-white.png", 
        "src/ikon_blue/student-blue-35.png");
    
    sidebar.setButtonIcons(btnAbsensi, 
        "src/ikon_white/checklist-white.png", 
        "src/ikon_blue/checklist-blue-35.png");
    
    sidebar.setButtonIcons(btnLaporan, 
        "src/ikon_white/notes-white.png", 
        "src/ikon_blue/notes-blue-35.png");
    
    sidebar.setButtonIcons(btnPengaturan, 
        "src/ikon_white/gear-white.png", 
        "src/ikon_blue/gear-blue-35.png");
    
    
 
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelCustom1 = new ClassTambahan.JPanelCustom();
        onlineStatus = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnScan = new javax.swing.JButton();
        btnPengaturan = new javax.swing.JButton();
        btnLaporan = new javax.swing.JButton();
        btnAbsensi = new javax.swing.JButton();
        btnSiswa = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        topBar = new javax.swing.JPanel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.setLayout(null);

        jPanelCustom1.setBackground(new java.awt.Color(8, 86, 210));
        jPanelCustom1.setPreferredSize(new java.awt.Dimension(250, 942));

        onlineStatus.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        onlineStatus.setForeground(new java.awt.Color(231, 76, 60));
        onlineStatus.setText("Offline");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Scanner Status : ");

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Untuk Memulai Scanning");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Tekan Tombol Diatas");

        btnScan.setBackground(new java.awt.Color(8, 86, 210));
        btnScan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/icons8-qr-96.png"))); // NOI18N
        btnScan.setBorder(null);
        btnScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnScanActionPerformed(evt);
            }
        });

        btnPengaturan.setBackground(new java.awt.Color(8, 86, 210));
        btnPengaturan.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnPengaturan.setForeground(new java.awt.Color(255, 255, 255));
        btnPengaturan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/gear-white.png"))); // NOI18N
        btnPengaturan.setText("Pengaturan");
        btnPengaturan.setBorder(null);
        btnPengaturan.setIconTextGap(30);
        btnPengaturan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengaturanActionPerformed(evt);
            }
        });

        btnLaporan.setBackground(new java.awt.Color(8, 86, 210));
        btnLaporan.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnLaporan.setForeground(new java.awt.Color(255, 255, 255));
        btnLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/notes-white.png"))); // NOI18N
        btnLaporan.setText("Laporan");
        btnLaporan.setBorder(null);
        btnLaporan.setIconTextGap(30);
        btnLaporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLaporanActionPerformed(evt);
            }
        });

        btnAbsensi.setBackground(new java.awt.Color(8, 86, 210));
        btnAbsensi.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnAbsensi.setForeground(new java.awt.Color(255, 255, 255));
        btnAbsensi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/checklist-white.png"))); // NOI18N
        btnAbsensi.setText("Absensi");
        btnAbsensi.setBorder(null);
        btnAbsensi.setIconTextGap(30);
        btnAbsensi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbsensiActionPerformed(evt);
            }
        });

        btnSiswa.setBackground(new java.awt.Color(8, 86, 210));
        btnSiswa.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnSiswa.setForeground(new java.awt.Color(255, 255, 255));
        btnSiswa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/student-white.png"))); // NOI18N
        btnSiswa.setText("Siswa");
        btnSiswa.setBorder(null);
        btnSiswa.setIconTextGap(30);
        btnSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiswaActionPerformed(evt);
            }
        });

        btnDashboard.setBackground(new java.awt.Color(8, 86, 210));
        btnDashboard.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnDashboard.setForeground(new java.awt.Color(255, 255, 255));
        btnDashboard.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/home-white.png"))); // NOI18N
        btnDashboard.setText("Dashboard");
        btnDashboard.setBorder(null);
        btnDashboard.setIconTextGap(30);
        btnDashboard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDashboardActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("SIAKHA");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Sistem Absensi Khadijah");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/icons8-school-49.png"))); // NOI18N

        javax.swing.GroupLayout jPanelCustom1Layout = new javax.swing.GroupLayout(jPanelCustom1);
        jPanelCustom1.setLayout(jPanelCustom1Layout);
        jPanelCustom1Layout.setHorizontalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnPengaturan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnAbsensi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnSiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(onlineStatus)
                        .addGap(87, 87, 87))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(42, 42, 42)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jLabel1))
                            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(btnScan)
                        .addGap(75, 75, 75))))
        );
        jPanelCustom1Layout.setVerticalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3))
                .addGap(51, 51, 51)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAbsensi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(btnScan)
                .addGap(32, 32, 32)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(onlineStatus)
                .addContainerGap(180, Short.MAX_VALUE))
        );

        jPanel1.add(jPanelCustom1);
        jPanelCustom1.setBounds(0, 0, 250, 942);

        topBar.setBackground(new java.awt.Color(164, 216, 239));
        topBar.setForeground(new java.awt.Color(164, 216, 239));
        topBar.setPreferredSize(new java.awt.Dimension(1300, 100));

        javax.swing.GroupLayout topBarLayout = new javax.swing.GroupLayout(topBar);
        topBar.setLayout(topBarLayout);
        topBarLayout.setHorizontalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1130, Short.MAX_VALUE)
        );
        topBarLayout.setVerticalGroup(
            topBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 50, Short.MAX_VALUE)
        );

        jPanel1.add(topBar);
        topBar.setBounds(240, 0, 1130, 50);

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setLayout(new java.awt.CardLayout());
        jPanel1.add(content);
        content.setBounds(250, 50, 1120, 720);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanActionPerformed
    sidebar.setActive(null);
        content.removeAll();
        content.add(new QrCameraPanel(this));

        content.repaint();
        content.revalidate();

    }//GEN-LAST:event_btnScanActionPerformed

    private void btnPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengaturanActionPerformed
    sidebar.setActive(btnPengaturan);
        panelPengaturan pp = new panelPengaturan();
        content.removeAll();
        content.add(pp);
        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnPengaturanActionPerformed

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
      sidebar.setActive(btnLaporan);

    }//GEN-LAST:event_btnLaporanActionPerformed

    private void btnAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsensiActionPerformed
     sidebar.setActive(btnAbsensi);

    }//GEN-LAST:event_btnAbsensiActionPerformed

    private void btnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswaActionPerformed
sidebar.setActive(btnSiswa);
        addSiswa as = new addSiswa();
        content.removeAll();
        content.add(as);

        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnSiswaActionPerformed

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
       sidebar.setActive(btnDashboard);
    }//GEN-LAST:event_btnDashboardActionPerformed
                         


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbsensi;
    private javax.swing.JButton btnDashboard;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengaturan;
    private javax.swing.JButton btnScan;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JPanel content;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private ClassTambahan.JPanelCustom jPanelCustom1;
    public static javax.swing.JLabel onlineStatus;
    private javax.swing.JPanel topBar;
    // End of variables declaration//GEN-END:variables
}
