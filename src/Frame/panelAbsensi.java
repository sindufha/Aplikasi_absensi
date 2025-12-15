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
import java.awt.Component;
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
import javax.swing.JTable;
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
    model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 4;
        }
    };
    
    model.addColumn("No");
    model.addColumn("NIS");
    model.addColumn("Nama Siswa");
    model.addColumn("Kelas");
    model.addColumn("Status Absensi");
    
    tblAbsensi.setModel(model);
    
    List<String> statusList = absensiDAO.getAllStatus();
    String[] statusArray = statusList.toArray(new String[0]);
    
    JComboBox<String> comboBox = new JComboBox<>(statusArray);
    comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    
    TableColumn statusColumn = tblAbsensi.getColumnModel().getColumn(4);
    statusColumn.setCellEditor(new DefaultCellEditor(comboBox));
    statusColumn.setCellRenderer(new StatusCellRenderer());
    
    tblAbsensi.getColumnModel().getColumn(0).setPreferredWidth(60);
    tblAbsensi.getColumnModel().getColumn(1).setPreferredWidth(120);
    tblAbsensi.getColumnModel().getColumn(2).setPreferredWidth(280);
    tblAbsensi.getColumnModel().getColumn(3).setPreferredWidth(90);
    tblAbsensi.getColumnModel().getColumn(4).setPreferredWidth(180);
    
    tblAbsensi.setRowHeight(45);
    tblAbsensi.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    tblAbsensi.setIntercellSpacing(new Dimension(0, 0));
    
    JTableHeader header = tblAbsensi.getTableHeader();
    header.setBackground(new Color(249, 250, 251));
    header.setForeground(new Color(50, 0, 128));
    header.setFont(new Font("Segoe UI", Font.BOLD, 12));
    header.setPreferredSize(new Dimension(header.getWidth(), 48));
    
    header.setDefaultRenderer(new DefaultTableCellRenderer() {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel(value.toString());
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(50, 0, 128));
        label.setBackground(new Color(249, 250, 251));
        label.setOpaque(true);
        
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        
        label.setHorizontalAlignment(column == 0 || column == 3 ? JLabel.CENTER : JLabel.LEFT);
        return label;
    }
});
    
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(JLabel.CENTER);
    tblAbsensi.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    tblAbsensi.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
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
        List<Siswa> listSiswa = siswaDAO.getSiswaByKelas(idKelas,"Aktif");
        
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
        
    }
private void simpanAbsensi() {
    int rowCount = tblAbsensi.getRowCount();
    
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
            // ✅ Ambil NIS dari tabel dan convert ke int
            Object nisObj = tblAbsensi.getValueAt(i, 1);
            int nis = 0;
            
            // Handle jika NIS berupa String atau Integer
            if (nisObj instanceof Integer) {
                nis = (Integer) nisObj;
            } else if (nisObj instanceof String) {
                nis = Integer.parseInt(nisObj.toString());
            }
            
            String status = tblAbsensi.getValueAt(i, 4).toString();
            
            // ✅ Ambil siswa berdasarkan NIS (int)
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
            
        } catch (NumberFormatException e) {
            System.err.println("❌ Format NIS tidak valid pada baris " + (i + 1));
            e.printStackTrace();
            gagal++;
        } catch (Exception e) {
            System.err.println("❌ Error pada baris " + (i + 1));
            e.printStackTrace();
            gagal++;
        }
    }
    
    // Tampilkan hasil
    String message;
    if (diupdate > 0) {
        message = String.format(
            "✓ Absensi berhasil disimpan!\n\n" +
            "Berhasil disimpan: %d siswa\n" +
            "Diupdate (sudah ada): %d siswa\n" +
            "Gagal: %d siswa",
            berhasil - diupdate, diupdate, gagal
        );
    } else {
        message = String.format(
            "✓ Absensi berhasil disimpan!\n\n" +
            "Berhasil: %d siswa\n" +
            "Gagal: %d siswa",
            berhasil, gagal
        );
    }
    
    int messageType = (gagal > 0) ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE;
    
    JOptionPane.showMessageDialog(this,
        message,
        "Info Simpan Absensi",
        messageType);
    
    // Clear tabel setelah simpan jika tidak ada error
    if (gagal == 0) {
        model.setRowCount(0);
        JOptionPane.showMessageDialog(this,
            "Tabel berhasil dikosongkan.\nSilakan muat data siswa baru jika diperlukan.",
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this,
            "Beberapa data gagal disimpan.\nPeriksa log error dan coba lagi untuk data yang gagal.",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
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
        cbKelas = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnSimpanAbsen = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAbsensi = new javax.swing.JTable();

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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));

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

        cbKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKelasActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setText("Pilih Kelas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 19, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(68, 68, 68)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTanggal)
                                    .addComponent(waktuRealTime)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(33, 33, 33)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        btnSimpanAbsen.setBackground(new java.awt.Color(0, 204, 0));
        btnSimpanAbsen.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSimpanAbsen.setForeground(new java.awt.Color(255, 255, 255));
        btnSimpanAbsen.setText("Simpan Absensi");
        btnSimpanAbsen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanAbsenActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Absen Siswa");

        jLabel5.setForeground(new java.awt.Color(51, 51, 57));
        jLabel5.setText("Daftar Siswa Muncul Disini");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 10, 20));

        tblAbsensi.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblAbsensi.setModel(new javax.swing.table.DefaultTableModel(
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
        tblAbsensi.setGridColor(new java.awt.Color(204, 204, 204));
        tblAbsensi.setInheritsPopupMenu(true);
        tblAbsensi.setShowGrid(true);
        tblAbsensi.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tblAbsensi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addGap(774, 774, 774)
                        .addComponent(btnSimpanAbsen)
                        .addGap(18, 18, 18))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5))
                    .addComponent(btnSimpanAbsen, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(62, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSimpanAbsenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanAbsenActionPerformed
      simpanAbsensi();
    }//GEN-LAST:event_btnSimpanAbsenActionPerformed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        tblAbsensi.clearSelection();
    }//GEN-LAST:event_formMouseClicked

    private void cbKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKelasActionPerformed
     // ✅ Null check dulu
    if (cbKelas.getSelectedItem() == null) {
        return;
    }
    
    String selected = cbKelas.getSelectedItem().toString();
    
    if (!selected.equals("-- Pilih Kelas --")) {
        loadDataSiswa();
    }
    }//GEN-LAST:event_cbKelasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSimpanAbsen;
    private javax.swing.JComboBox<String> cbKelas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTable tblAbsensi;
    private javax.swing.JLabel waktuRealTime;
    // End of variables declaration//GEN-END:variables
}
