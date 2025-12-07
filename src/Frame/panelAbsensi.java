/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;

import ClassAbsensi.AbsensiDAO;
import ClassAbsensi.ComboBoxRenderer;
import ClassAbsensi.Siswa;
import ClassAbsensi.SiswaDAO;
import ClassAbsensi.StatusCellRenderer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author MyBook Hype AMD
 */
public class panelAbsensi extends javax.swing.JPanel {
    private AbsensiDAO absensiDAO;
    private SiswaDAO siswaDAO;
    private DefaultTableModel model;
    /**
     * Creates new form panelAbsensi
     */
    public panelAbsensi() {
        initComponents();
        
        absensiDAO = new AbsensiDAO();
        siswaDAO = new SiswaDAO();
        setupTable();
        loadComboBoxKelas();
        updateTanggalInfo();
        
        Timer t = new Timer(1000,(e)->{
        java.sql.Time waktu = new java.sql.Time(System.currentTimeMillis());
        waktuRealTime.setText(String.valueOf(waktu));
       
        });
         t.start();
                }
    private void loadComboBoxKelas() {
        
        cbKelas.removeAllItems();
        cbKelas.addItem("-- Pilih Kelas --");
        
        List<Integer> listKelas = siswaDAO.getDistinctKelas();
        
        for (Integer kelas : listKelas) {
            cbKelas.addItem(kelas.toString());
        }
    }
    private void setupTable() {
        // Buat model tabel
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hanya kolom Status Absensi (index 4) yang bisa diedit
                return column == 4;
            }
        };
        
        model.addColumn("No");
        model.addColumn("NIS");
        model.addColumn("Nama Siswa");
        model.addColumn("Kelas");
        model.addColumn("Status Absensi");
        
        tblAbsensiMnl.setModel(model);
        
        // Load status dari database
        List<String> statusList = absensiDAO.getAllStatus();
        String[] statusArray = statusList.toArray(new String[0]);
        
        // Buat ComboBox untuk kolom Status
        JComboBox<String> comboBox = new JComboBox<>(statusArray);
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Set ke kolom tabel
        TableColumn statusColumn = tblAbsensiMnl.getColumnModel().getColumn(4);
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));
        statusColumn.setCellRenderer(new StatusCellRenderer());
        
        // Set lebar kolom
        tblAbsensiMnl.getColumnModel().getColumn(0).setPreferredWidth(60);   // No
        tblAbsensiMnl.getColumnModel().getColumn(1).setPreferredWidth(120);  // NIS
        tblAbsensiMnl.getColumnModel().getColumn(2).setPreferredWidth(280);  // Nama
        tblAbsensiMnl.getColumnModel().getColumn(3).setPreferredWidth(90);   // Kelas
        tblAbsensiMnl.getColumnModel().getColumn(4).setPreferredWidth(180);  // Status
        
        // Set tinggi row
        tblAbsensiMnl.setRowHeight(50);
        
        // Style header tabel
        JTableHeader header = tblAbsensiMnl.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(241, 245, 249)); // #F1F5F9
        header.setForeground(new Color(51, 65, 85)); // #334155
        header.setPreferredSize(new Dimension(header.getWidth(), 48));
       
    header.setBackground(new Color(249, 250, 251)); // abu-abu muda
    header.setForeground(new Color(50, 0, 128)); // text abu gelap
    header.setFont(new Font("Segoe UI", Font.BOLD, 12));
    header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
    // Tinggi baris
    tblAbsensiMnl.setRowHeight(45);
    
    // Font isi tabel
    tblAbsensiMnl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    
    // Hilangkan grid
    
    tblAbsensiMnl.setIntercellSpacing(new Dimension(0, 0));
        // Center align untuk kolom No dan Kelas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tblAbsensiMnl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblAbsensiMnl.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
    }
private void updateTanggalInfo() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd MMMM yyyy");
        String tanggal = sdf.format(new java.util.Date());
        lblTanggal.setText(tanggal);
        // Ganti jLabel1 dengan label yang sesuai di design kamu
        // Contoh: jLabel1.setText(tanggal);
        // atau: lblTanggal.setText(tanggal);
    }
private void loadDataSiswa() {
        model.setRowCount(0); // Clear tabel
        
        String selectedKelas = cbKelas.getSelectedItem().toString();
        if (selectedKelas.equals("-- Pilih Kelas --")) {
            JOptionPane.showMessageDialog(this,
                "Pilih kelas terlebih dahulu!",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int idKelas = Integer.parseInt(selectedKelas);
        List<Siswa> listSiswa = siswaDAO.getSiswaByKelas(idKelas);
        
        if (listSiswa.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada siswa di kelas ini!",
                "Info",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int no = 1;
        for (Siswa siswa : listSiswa) {
            model.addRow(new Object[]{
                no++,
                siswa.getNis(),
                siswa.getNamaSiswa(),
                siswa.getIdKelas(),
                "Alfa" // Default value
            });
        }
        
        JOptionPane.showMessageDialog(this,
            "Data siswa berhasil dimuat: " + listSiswa.size() + " siswa",
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
    }
private void simpanAbsensi() {
        int rowCount = tblAbsensiMnl.getRowCount();
        
        if (rowCount == 0) {
            JOptionPane.showMessageDialog(this,
                "Tidak ada data untuk disimpan!\nMuat data siswa terlebih dahulu.",
                "Peringatan",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Konfirmasi
        int confirm = JOptionPane.showConfirmDialog(this,
            "Simpan absensi untuk " + rowCount + " siswa?",
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        // Ambil tanggal hari ini
        Date tanggal = new java.sql.Date(System.currentTimeMillis());
        
        int berhasil = 0;
        int gagal = 0;
        int diupdate = 0;
        
        for (int i = 0; i < rowCount; i++) {
            try {
                String nis = tblAbsensiMnl.getValueAt(i, 1).toString();
                String status = tblAbsensiMnl.getValueAt(i, 4).toString();
                
                // Ambil id_siswa dari NIS
                Siswa siswa = siswaDAO.getSiswaByNis(nis);
                
                if (siswa != null) {
                    // Cek apakah sudah absen hari ini
                    if (absensiDAO.sudahAbsenHariIni(siswa.getIdSiswa(), (java.sql.Date) tanggal)) {
                        // Update jika sudah ada
                        if (absensiDAO.updateAbsensiManual(siswa.getIdSiswa(), (java.sql.Date) tanggal, status, "")) {
                            berhasil++;
                            diupdate++;
                        } else {
                            gagal++;
                        }
                    } else {
                        // Insert baru jika belum ada
                        if (absensiDAO.insertAbsensiManual(siswa.getIdSiswa(), (java.sql.Date) tanggal, status, "")) {
                            berhasil++;
                        } else {
                            gagal++;
                        }
                    }
                } else {
                    gagal++;
                    System.err.println("❌ Siswa dengan NIS " + nis + " tidak ditemukan!");
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                gagal++;
            }
        }
        
        // Tampilkan hasil
        String message;
        if (diupdate > 0) {
            message = String.format(
                "Absensi berhasil disimpan!\n\n" +
                "Berhasil disimpan: %d siswa\n" +
                "Diupdate (sudah ada): %d siswa\n" +
                "Gagal: %d siswa",
                berhasil - diupdate, diupdate, gagal
            );
        } else {
            message = String.format(
                "Absensi berhasil disimpan!\n\n" +
                "Berhasil: %d siswa\n" +
                "Gagal: %d siswa",
                berhasil, gagal
            );
        }
        
        JOptionPane.showMessageDialog(this,
            message,
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
        
        // Clear tabel setelah simpan jika tidak ada error
        if (gagal == 0) {
            model.setRowCount(0);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        waktuRealTime = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnMuat = new javax.swing.JPanel();
        cbKelas = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnSimpanAbsen = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAbsensiMnl = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Absensi Manual");

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

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("Tanggal : ");

        lblTanggal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTanggal.setText("10-05-2025");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Waktu : ");

        waktuRealTime.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        waktuRealTime.setText("06:00");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Waktu Masuk : ");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setText("07:00");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(33, 33, 33)
                        .addComponent(jLabel7))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(68, 68, 68)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTanggal)
                            .addComponent(waktuRealTime))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lblTanggal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(waktuRealTime))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setText("Pilih Kelas");

        jButton1.setText("muat");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnMuatLayout = new javax.swing.GroupLayout(btnMuat);
        btnMuat.setLayout(btnMuatLayout);
        btnMuatLayout.setHorizontalGroup(
            btnMuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnMuatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(btnMuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnMuatLayout.createSequentialGroup()
                        .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1))
                    .addComponent(jLabel8))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        btnMuatLayout.setVerticalGroup(
            btnMuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnMuatLayout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnMuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        btnSimpanAbsen.setText("Simpan Absensi");
        btnSimpanAbsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanAbsenActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tblAbsensiMnl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "No", "NIS", "Nama", "Kelas", "Status", "Absensi"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAbsensiMnl.setGridColor(new java.awt.Color(204, 204, 204));
        tblAbsensiMnl.setInheritsPopupMenu(true);
        tblAbsensiMnl.setShowGrid(true);
        jScrollPane1.setViewportView(tblAbsensiMnl);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1015, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnMuat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpanAbsen)
                        .addGap(50, 50, 50))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnMuat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpanAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        loadDataSiswa();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnSimpanAbsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanAbsenActionPerformed
      simpanAbsensi();
    }//GEN-LAST:event_btnSimpanAbsenActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        tblAbsensiMnl.clearSelection();
    }//GEN-LAST:event_formMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnMuat;
    private javax.swing.JButton btnSimpanAbsen;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTable tblAbsensiMnl;
    private javax.swing.JLabel waktuRealTime;
    // End of variables declaration//GEN-END:variables
}
