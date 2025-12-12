/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Frame;
import ClassAbsensi.Absensi;
import ClassAbsensi.AbsensiDAO;
import ClassAbsensi.Koneksi;
import ClassAbsensi.Laporan;
import ClassAbsensi.Kelas;
import ClassAbsensi.KelasDAO;
import java.io.File;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
public class panelLaporan extends javax.swing.JPanel {
    private Laporan laporanPDF;
    private KelasDAO kelasDAO;
    private AbsensiDAO absensiDAO;

    @Override
    public void print(Graphics g) {
        super.print(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    /**
     * Creates new form panelLaporan
     */
    public panelLaporan() {
        initComponents();
        
        laporanPDF = new Laporan();
        kelasDAO = new KelasDAO();
        absensiDAO = new AbsensiDAO();
        loadComboBoxBulan();
        loadComboBoxKelas();
        loadRingkasan();
        loadTableLaporan();
        
    }
    private void loadTableLaporan() {
    try {
        // Ambil bulan yang dipilih di ComboBox
        int bulan = cmbBulan.getSelectedIndex() + 1; // Index dimulai dari 0
        
        // Ambil tahun saat ini
        int tahun = Calendar.getInstance().get(Calendar.YEAR);
        
        List<Absensi> listAbsensi;
        
        // Cek apakah kelas sudah dipilih
        if (cKelas.getSelectedIndex() == 0) {
            // Jika belum pilih kelas, tampilkan semua kelas
            listAbsensi = absensiDAO.getAbsensiByBulan(bulan, tahun);
        } else {
            // Jika sudah pilih kelas, tampilkan data kelas tersebut
            String selectedKelas = cKelas.getSelectedItem().toString();
            int idKelas = Integer.parseInt(selectedKelas.split(" - ")[0]);
            listAbsensi = kelasDAO.getAbsensiByKelasAndBulan(idKelas, bulan, tahun);
        }
        
        // Tampilkan ke tabel
        tampilkanDataKeTable(listAbsensi);
        
        // Update ringkasan
        updateRingkasan(listAbsensi);
        
    } catch (Exception e) {
        System.err.println("Error saat load tabel laporan: " + e.getMessage());
        e.printStackTrace();
    }
}

/**
 * Method untuk menampilkan data absensi ke tabel
 */
private void tampilkanDataKeTable(List<Absensi> listAbsensi) {
    // Buat model tabel
    DefaultTableModel model = new DefaultTableModel() {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // Semua cell tidak bisa diedit
    }
    };
    
    // Tambahkan kolom
    model.addColumn("No");
    model.addColumn("NIS");
    model.addColumn("Nama Siswa");
    model.addColumn("Tanggal");
    model.addColumn("Status");
    model.addColumn("Keterangan");
    
    // Tambahkan data
    int no = 1;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Format tanggal Indonesia
    
    for (Absensi absensi : listAbsensi) {
        Object[] row = new Object[6];
        row[0] = no++;
        row[1] = absensi.getNis();
        row[2] = absensi.getNamaSiswa();
        row[3] = sdf.format(absensi.getTanggal()); // Format tanggal
        row[4] = absensi.getStatus(); // Hadir, Izin, Sakit, Alfa
        row[5] = absensi.getKeterangan() != null ? absensi.getKeterangan() : "-";
        
        model.addRow(row);
    }
    
    // Set model ke tabel
    tblAbsensi.setModel(model); // Sesuaikan dengan nama tabel Anda
    
    // Atur lebar kolom (opsional)
    tblAbsensi.getColumnModel().getColumn(0).setPreferredWidth(50);  // No
    tblAbsensi.getColumnModel().getColumn(1).setPreferredWidth(100); // NIS
    tblAbsensi.getColumnModel().getColumn(2).setPreferredWidth(200); // Nama
    tblAbsensi.getColumnModel().getColumn(3).setPreferredWidth(100); // Tanggal
    tblAbsensi.getColumnModel().getColumn(4).setPreferredWidth(80);  // Status
    tblAbsensi.getColumnModel().getColumn(5).setPreferredWidth(150); // Keterangan
}
     private void loadComboBoxKelas() {
        List<Kelas> listKelas = kelasDAO.loadAllKelas();
        
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("-- Pilih Kelas --");
        
        for (Kelas kelas : listKelas) {
            String item = kelas.getIdKelas() + " - Kelas " + kelas.getTingkat();
            model.addElement(item);
        }
        
        cKelas.setModel(model);
    }
    
    /**
     * Load ringkasan (dari data hari ini atau bulan ini)
     */
    private void loadRingkasan() {
        // Implementasi sesuai kebutuhan
        // Bisa ambil data dari database untuk hari ini
        lblTanggal.setText("Hari ini");
        lblSiswaHadir.setText("19");
        lblSiswaIzin.setText("1");
        lblSiswaSakit.setText("4");
        lblSiswaAlfa.setText("2");
  }
    private void loadComboBoxBulan() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        
        String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
                         "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
        
        for (int i = 0; i < bulan.length; i++) {
            model.addElement((i + 1) + " - " + bulan[i]);
        }
        
        cmbBulan.setModel(model);
        
        // Set default ke bulan sekarang
        Calendar now = Calendar.getInstance();
        cmbBulan.setSelectedIndex(now.get(Calendar.MONTH));}

     

   
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
        jPanelCustom1 = new ClassTambahan.JPanelCustom();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btn_tampilkan = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        cKelas = new javax.swing.JComboBox<>();
        cmbBulan = new javax.swing.JComboBox<>();
        jPanelCustom2 = new ClassTambahan.JPanelCustom();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTanggal = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblSiswaHadir = new javax.swing.JLabel();
        lblSiswaIzin = new javax.swing.JLabel();
        lblSiswaSakit = new javax.swing.JLabel();
        lblSiswaAlfa = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblAbsensi = new javax.swing.JTable();

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Laporan");

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

        jPanelCustom1.setBackground(new java.awt.Color(220, 235, 255));
        jPanelCustom1.setRoundBottomLeft(22);
        jPanelCustom1.setRoundBottomRight(22);
        jPanelCustom1.setRoundTopLeft(22);
        jPanelCustom1.setRoundTopRight(22);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(44, 62, 80));
        jLabel2.setText("Filter Laporan");

        jLabel3.setForeground(new java.awt.Color(44, 62, 80));
        jLabel3.setText("Pilih Bulan");

        jButton1.setBackground(new java.awt.Color(34, 139, 34));
        jButton1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Export PDF");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btn_tampilkan.setBackground(new java.awt.Color(138, 43, 226));
        btn_tampilkan.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btn_tampilkan.setForeground(new java.awt.Color(255, 255, 255));
        btn_tampilkan.setText("Tampilkan");
        btn_tampilkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tampilkanActionPerformed(evt);
            }
        });

        jLabel16.setText("Pilih Kelas");

        cKelas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", " " }));
        cKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cKelasActionPerformed(evt);
            }
        });

        cmbBulan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbBulanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCustom1Layout = new javax.swing.GroupLayout(jPanelCustom1);
        jPanelCustom1.setLayout(jPanelCustom1Layout);
        jPanelCustom1Layout.setHorizontalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(cKelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCustom1Layout.createSequentialGroup()
                        .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(btn_tampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))
                    .addGroup(jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelCustom1Layout.createSequentialGroup()
                        .addComponent(cmbBulan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanelCustom1Layout.setVerticalGroup(
            jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cmbBulan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                .addGroup(jPanelCustom1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_tampilkan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        jPanelCustom2.setBackground(new java.awt.Color(153, 153, 255));
        jPanelCustom2.setRoundBottomLeft(22);
        jPanelCustom2.setRoundBottomRight(22);
        jPanelCustom2.setRoundTopLeft(22);
        jPanelCustom2.setRoundTopRight(22);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Ringkasan");

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Tanggal dan Waktu : ");

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Siswa Hadir : ");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Siswa Izin :");

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Siswa Sakit : ");

        lblTanggal.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblTanggal.setForeground(new java.awt.Color(255, 255, 255));
        lblTanggal.setText("Hari ini");

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Siswa Alpa :");

        lblSiswaHadir.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSiswaHadir.setForeground(new java.awt.Color(255, 255, 255));
        lblSiswaHadir.setText("19");

        lblSiswaIzin.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSiswaIzin.setForeground(new java.awt.Color(255, 255, 255));
        lblSiswaIzin.setText("1");

        lblSiswaSakit.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSiswaSakit.setForeground(new java.awt.Color(255, 255, 255));
        lblSiswaSakit.setText("4");

        lblSiswaAlfa.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblSiswaAlfa.setForeground(new java.awt.Color(255, 255, 255));
        lblSiswaAlfa.setText("8");

        javax.swing.GroupLayout jPanelCustom2Layout = new javax.swing.GroupLayout(jPanelCustom2);
        jPanelCustom2.setLayout(jPanelCustom2Layout);
        jPanelCustom2Layout.setHorizontalGroup(
            jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanelCustom2Layout.createSequentialGroup()
                        .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSiswaAlfa)
                            .addComponent(lblSiswaSakit)
                            .addComponent(lblSiswaIzin)
                            .addComponent(lblSiswaHadir)
                            .addComponent(lblTanggal))))
                .addContainerGap(279, Short.MAX_VALUE))
        );
        jPanelCustom2Layout.setVerticalGroup(
            jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCustom2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lblTanggal))
                .addGap(18, 18, 18)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblSiswaHadir))
                .addGap(18, 18, 18)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(lblSiswaIzin))
                .addGap(18, 18, 18)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(lblSiswaSakit))
                .addGap(18, 18, 18)
                .addGroup(jPanelCustom2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblSiswaAlfa))
                .addGap(40, 40, 40))
        );

        tblAbsensi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblAbsensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAbsensiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblAbsensi);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanelCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanelCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1062, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelCustom2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tampilkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tampilkanActionPerformed
        // TODO add your handling code here:
       if (cKelas.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this,
            "Silakan pilih kelas terlebih dahulu!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try {
        // Ambil ID Kelas dari ComboBox
        String selectedKelas = cKelas.getSelectedItem().toString();
        int idKelas = Integer.parseInt(selectedKelas.split(" - ")[0]); // Ambil ID sebelum " - " dan convert ke int
        
        // Ambil bulan yang dipilih
        int bulanIndex = cmbBulan.getSelectedIndex() + 1; // Index dimulai dari 0
        
        // Ambil tahun saat ini (atau bisa ditambahkan ComboBox tahun)
        int tahun = Calendar.getInstance().get(Calendar.YEAR);
        
        // Ambil data absensi dari database
        List<Absensi> listAbsensi = kelasDAO.getAbsensiByKelasAndBulan(idKelas, bulanIndex, tahun);
        
        // Tampilkan ke tabel
        tampilkanDataKeTable(listAbsensi);
        
        // Update ringkasan
        updateRingkasan(listAbsensi);
        
        JOptionPane.showMessageDialog(this,
            "Data berhasil ditampilkan!",
            "Informasi",
            JOptionPane.INFORMATION_MESSAGE);
            
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
            "Terjadi kesalahan saat mengambil data: " + e.getMessage(),
            "Error",
            JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}



/**
 * Method untuk update ringkasan absensi
 */
private void updateRingkasan(List<Absensi> listAbsensi) {
    int hadir = 0, izin = 0, sakit = 0, alfa = 0;
    
    for (Absensi absensi : listAbsensi) {
        String status = absensi.getStatus().toLowerCase();
        
        if (status.contains("hadir")) {
            hadir++;
        } else if (status.contains("izin")) {
            izin++;
        } else if (status.contains("sakit")) {
            sakit++;
        } else if (status.contains("alfa") || status.contains("alpa")) {
            alfa++;
        }
    }
    
    // Update label ringkasan
    String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
                     "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    lblTanggal.setText(bulan[cmbBulan.getSelectedIndex()]);
    lblSiswaHadir.setText(String.valueOf(hadir));
    lblSiswaIzin.setText(String.valueOf(izin));
    lblSiswaSakit.setText(String.valueOf(sakit));
    lblSiswaAlfa.setText(String.valueOf(alfa));

    }//GEN-LAST:event_btn_tampilkanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       if (cKelas.getSelectedItem() == null || cKelas.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(this,
            "Silakan pilih kelas terlebih dahulu!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Validasi ComboBox Bulan
    if (cmbBulan.getSelectedItem() == null || cmbBulan.getSelectedIndex() < 0) {
        JOptionPane.showMessageDialog(this,
            "Silakan pilih bulan terlebih dahulu!",
            "Peringatan",
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    // Get bulan dan tahun
    int bulan = cmbBulan.getSelectedIndex() + 1; // Index 0 = Januari
    int tahun = Calendar.getInstance().get(Calendar.YEAR); // Tahun sekarang
    
    // Get ID kelas
    String selectedKelas = cKelas.getSelectedItem().toString();
    int idKelas;
    
    try {
        if (selectedKelas.contains(" - ")) {
            String[] parts = selectedKelas.split(" - ");
            idKelas = Integer.parseInt(parts[0].trim());
        } else {
            idKelas = Integer.parseInt(selectedKelas.trim());
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this,
            "Format kelas tidak valid!",
            "Error",
            JOptionPane.ERROR_MESSAGE);
        return;
    }
    
    // Nama bulan
    String[] namaBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni",
                          "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    
    // File chooser
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Simpan Laporan PDF");
    
    String defaultName = "Laporan_Kelas_" + idKelas + "" + namaBulan[bulan - 1] + "" + tahun + ".pdf";
    fileChooser.setSelectedFile(new File(defaultName));
    
    FileNameExtensionFilter filter = new FileNameExtensionFilter("PDF Files", "pdf");
    fileChooser.setFileFilter(filter);
    
    int result = fileChooser.showSaveDialog(this);
    
    if (result == JFileChooser.APPROVE_OPTION) {
        String outputPath = fileChooser.getSelectedFile().getAbsolutePath();
        
        if (!outputPath.toLowerCase().endsWith(".pdf")) {
            outputPath += ".pdf";
        }
        
        // Generate PDF
        Laporan laporan = new Laporan();
        boolean success = laporan.generateLaporanPDF(outputPath, bulan, tahun, idKelas);
        
        if (success) {
            int open = JOptionPane.showConfirmDialog(this,
                "✓ Laporan PDF berhasil dibuat!\n\n" +
                "File: " + new File(outputPath).getName() + "\n\n" +
                "Buka file sekarang?",
                "Sukses",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
            
            if (open == JOptionPane.YES_OPTION) {
                try {
                    Desktop.getDesktop().open(new File(outputPath));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this,
                        "Tidak dapat membuka file.\n" +
                        "Lokasi: " + outputPath,
                        "Info",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this,
                "✗ Gagal membuat laporan PDF!\n\n" +
                "Kemungkinan penyebab:\n" +
                "• Tidak ada data siswa/absensi\n" +
                "• File sedang dibuka\n" +
                "• Koneksi database error",
                "Error",
                JOptionPane.ERROR_MESSAGE);}
    }

        

    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblAbsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAbsensiMouseClicked
        // TODO add your handling code here:
    
    
    }//GEN-LAST:event_tblAbsensiMouseClicked

    private void cmbBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbBulanActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cmbBulanActionPerformed

    private void cKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cKelasActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_cKelasActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_tampilkan;
    private javax.swing.JComboBox<String> cKelas;
    private javax.swing.JComboBox<String> cmbBulan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private ClassTambahan.JPanelCustom jPanelCustom1;
    private ClassTambahan.JPanelCustom jPanelCustom2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblSiswaAlfa;
    private javax.swing.JLabel lblSiswaHadir;
    private javax.swing.JLabel lblSiswaIzin;
    private javax.swing.JLabel lblSiswaSakit;
    private javax.swing.JLabel lblTanggal;
    private javax.swing.JTable tblAbsensi;
    // End of variables declaration//GEN-END:variables

    
}
