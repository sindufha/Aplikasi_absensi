
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
/**
 *
 * @author MyBook Hype AMD
 */
public class MainFrame extends javax.swing.JFrame {
    private SidebarButtonManager sidebar;
    private ClassTambahan.JPanelCustom Sidebar;

    public MainFrame() {
        initComponents();
        setupLogoutMenu();
        setupResponsiveLayout();
        handleResize();
        
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
        
        btnGenerate.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnGenerate.setHorizontalAlignment(SwingConstants.LEFT);
        btnGenerate.setMargin(new Insets(5, 20, 5, 5));
        
        btnPengaturan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 1));
        btnPengaturan.setHorizontalAlignment(SwingConstants.LEFT);
        btnPengaturan.setMargin(new Insets(5, 20, 5, 5));
        
        sidebar = new SidebarButtonManager(
            btnDashboard, btnSiswa,btnAbsensi,btnGenerate,btnLaporan,btnPengaturan
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
    
    sidebar.setButtonIcons(btnGenerate, 
        "src/ikon_white/qr-white.png", 
        "src/ikon_blue/qr-blue.png");
    
    sidebar.setButtonIcons(btnPengaturan, 
        "src/ikon_white/gear-white.png", 
        "src/ikon_blue/gear-blue-35.png");
    
    
 
    }
private void setupResponsiveLayout() {
        setLocationRelativeTo(null);
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                handleResize();
            }
        });
        
        handleResize();
    }
   
    private void handleResize() {
    int w = getWidth();
    int h = getHeight();
    
    panelSidebar.setBounds(0, 0, 250, h);
    panelTopbar.setBounds(0, 0, w, 50);
    content.setBounds(250, 50, w - 250, h - 50);
    
    // TAMBAHKAN INI - Force resize panel yang ada di dalam content
    Component[] components = content.getComponents();
    for (Component comp : components) {
        if (comp instanceof JPanel) {
            comp.setSize(content.getWidth(), content.getHeight());
            comp.setBounds(0, 0, content.getWidth(), content.getHeight());
            comp.revalidate();
        }
    }
    
    revalidate();
    repaint();
}

private void setupLogoutMenu() {
    JPopupMenu popupMenu = new JPopupMenu();
    popupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
    
    JMenuItem logoutItem = new JMenuItem("Log out");
    logoutItem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
    logoutItem.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    
    logoutItem.setPreferredSize(new Dimension(170, 45));
    
    logoutItem.setBackground(Color.WHITE);
    logoutItem.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            logoutItem.setBackground(new Color(240, 240, 240));
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            logoutItem.setBackground(Color.WHITE);
        }
    });
    
    logoutItem.addActionListener(e -> handleLogout());
    
    popupMenu.add(logoutItem);
    
    btnUser.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            popupMenu.show(btnUser, 20, btnUser.getHeight());
        }
    });
}

private void handleLogout() {
    int confirm = JOptionPane.showConfirmDialog(
        this,
        "Apakah Anda yakin ingin logout?",
        "Konfirmasi Logout",
        JOptionPane.YES_NO_OPTION
    );
    
    if (confirm == JOptionPane.YES_OPTION) {
        dispose();
        
        new FrameLogin().setVisible(true);
    }
}
private void setupPanelResize(JPanel panel) {
    panel.addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            panel.setBounds(0, 0, content.getWidth(), content.getHeight());
            
            // Resize komponen di dalam panel
            for (Component comp : panel.getComponents()) {
                if (comp instanceof javax.swing.JScrollPane) {
                    // Sesuaikan posisi tabel
                    int headerHeight = 320; // sesuaikan
                    comp.setBounds(20, headerHeight, 
                                  panel.getWidth() - 40, 
                                  panel.getHeight() - headerHeight - 20);
                }
            }
            
            panel.revalidate();
            panel.repaint();
        }
    });
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelSidebar = new ClassTambahan.JPanelCustom();
        jLabel4 = new javax.swing.JLabel();
        btnScan = new javax.swing.JButton();
        btnPengaturan = new javax.swing.JButton();
        btnGenerate = new javax.swing.JButton();
        btnAbsensi = new javax.swing.JButton();
        btnSiswa = new javax.swing.JButton();
        btnDashboard = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLaporan = new javax.swing.JButton();
        panelTopbar = new javax.swing.JPanel();
        btnUser = new javax.swing.JLabel();
        content = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 768));
        jPanel1.setLayout(null);

        panelSidebar.setBackground(new java.awt.Color(8, 86, 210));
        panelSidebar.setRoundBottomRight(70);
        panelSidebar.setRoundTopRight(70);

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Scan QR");

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

        btnGenerate.setBackground(new java.awt.Color(8, 86, 210));
        btnGenerate.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N
        btnGenerate.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/qr-white.png"))); // NOI18N
        btnGenerate.setText("Generate QR");
        btnGenerate.setBorder(null);
        btnGenerate.setIconTextGap(30);
        btnGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerateActionPerformed(evt);
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

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("SIAKHA");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Aplikasi Absensi SD Khadijah");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/siakha-80(1).png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Siakha v0.1");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("© 2025");

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

        javax.swing.GroupLayout panelSidebarLayout = new javax.swing.GroupLayout(panelSidebar);
        panelSidebar.setLayout(panelSidebarLayout);
        panelSidebarLayout.setHorizontalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSidebarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnScan)
                .addGap(71, 71, 71))
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGroup(panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(btnDashboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAbsensi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPengaturan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLaporan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelSidebarLayout.setVerticalGroup(
            panelSidebarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSidebarLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(btnDashboard, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnAbsensi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(btnLaporan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPengaturan, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnScan)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(32, 32, 32)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jPanel1.add(panelSidebar);
        panelSidebar.setBounds(0, 0, 250, 770);

        panelTopbar.setBackground(new java.awt.Color(164, 216, 239));
        panelTopbar.setForeground(new java.awt.Color(164, 216, 239));
        panelTopbar.setPreferredSize(new java.awt.Dimension(1300, 100));

        btnUser.setBackground(new java.awt.Color(164, 216, 239));
        btnUser.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        btnUser.setForeground(new java.awt.Color(255, 0, 0));
        btnUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ikon_white/icons8-user-male-30.png"))); // NOI18N
        btnUser.setText("USER : ADMIN");
        btnUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUser.setOpaque(true);
        btnUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnUserMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnUserMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                btnUserMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout panelTopbarLayout = new javax.swing.GroupLayout(panelTopbar);
        panelTopbar.setLayout(panelTopbarLayout);
        panelTopbarLayout.setHorizontalGroup(
            panelTopbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTopbarLayout.createSequentialGroup()
                .addContainerGap(956, Short.MAX_VALUE)
                .addComponent(btnUser, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelTopbarLayout.setVerticalGroup(
            panelTopbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnUser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        jPanel1.add(panelTopbar);
        panelTopbar.setBounds(200, 0, 1170, 40);

        content.setBackground(new java.awt.Color(255, 255, 255));
        content.setMaximumSize(new java.awt.Dimension(1120, 650));
        content.setLayout(new java.awt.CardLayout());
        jPanel1.add(content);
        content.setBounds(250, 40, 1120, 730);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        setSize(new java.awt.Dimension(1380, 776));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDashboardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDashboardActionPerformed
        sidebar.setActive(btnDashboard);
        panelDashboard pd = new panelDashboard();
        content.removeAll();
        content.add(pd);

        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnDashboardActionPerformed

    private void btnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswaActionPerformed
        sidebar.setActive(btnSiswa);
        panelSiswa ps = new panelSiswa();
        content.removeAll();
        content.add(ps);

        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnSiswaActionPerformed

    private void btnAbsensiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbsensiActionPerformed
        sidebar.setActive(btnAbsensi);
        panelAbsensi ab = new panelAbsensi();
        setupPanelResize(ab);
        content.removeAll();
        content.add(ab);
        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnAbsensiActionPerformed

    private void btnGenerateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerateActionPerformed
        
        sidebar.setActive(btnGenerate);
        panelGenerateQR pg = new panelGenerateQR();
        content.removeAll();
       content.add(pg);
        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnGenerateActionPerformed

    private void btnPengaturanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengaturanActionPerformed
        sidebar.setActive(btnPengaturan);
        panelPengaturan1 ad = new panelPengaturan1();
        content.removeAll();
        content.add(ad);
        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnPengaturanActionPerformed

    private void btnScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnScanActionPerformed
        sidebar.setActive(null);
        content.removeAll();
        content.add(new QRScanner());

        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnScanActionPerformed

    private void btnUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMouseEntered
       btnUser.setBackground(new Color(77,216,239));
    }//GEN-LAST:event_btnUserMouseEntered

    private void btnUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMouseExited
        btnUser.setBackground(new Color(164,216,239));
    }//GEN-LAST:event_btnUserMouseExited

    private void btnUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMousePressed
       btnUser.setBackground(new Color(30,216,239));
    }//GEN-LAST:event_btnUserMousePressed

    private void btnUserMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnUserMouseReleased
       btnUser.setBackground(new Color(164,216,239));
    }//GEN-LAST:event_btnUserMouseReleased

    private void btnLaporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLaporanActionPerformed
      sidebar.setActive(btnLaporan);
        panelLaporan pl = new panelLaporan();
        content.removeAll();
        content.add(pl);
        content.repaint();
        content.revalidate();
    }//GEN-LAST:event_btnLaporanActionPerformed

    
    
    
                             


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
            System.err.println("Failed to initialize flatLaF");
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
    private javax.swing.JButton btnGenerate;
    private javax.swing.JButton btnLaporan;
    private javax.swing.JButton btnPengaturan;
    private javax.swing.JButton btnScan;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JLabel btnUser;
    private javax.swing.JPanel content;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private ClassTambahan.JPanelCustom panelSidebar;
    private javax.swing.JPanel panelTopbar;
    // End of variables declaration//GEN-END:variables
}
