/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package rapotsiswa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PC-RPL 10
 */
public class DataGuru extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tm;
    /**
     * Creates new form DataGuru
     */
    public DataGuru() {
        initComponents();
        connectDB();
        getMapel();
        getKodeGuru();
        refreshTable();
        namaMapel.setText("");
        cbKdMapel.setSelectedIndex(-1);
        InputKodeGuru.setSelectedIndex(-1);
    }
    
    private void connectDB() {
        conn = null;
        DBConnection db = new DBConnection();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db.server(), db.username(), db.password());
            System.out.println("BERHASIL tersambung ke database");
        } catch (Exception e) {
            System.out.println("GAGAL tersambung ke database: " + e);
        }
    }
    
    private void refreshTable(){
        tm = new DefaultTableModel(null, new Object[] { "Kode Guru", "Nama Guru", "Mapel" });
        Tabelguru.setModel(tm);
        tm.getDataVector().removeAllElements();

        try {
            PreparedStatement s = conn.prepareStatement("SELECT guru.kode_guru, guru.nama_guru, mapel.nama_mapel FROM guru, mapel WHERE guru.kode_mapel=mapel.kode_mapel ");
            ResultSet r = s.executeQuery();
            while(r.next()) {
                Object[] data = {
                r.getString(1),
                r.getString(2),
                r.getString(3)
                };
           
            tm.addRow(data);
            
            }
            System.out.println("BERHASIL mengambil data guru ");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data guru : "+e);
        }
    }
    
    private void getMapel(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT kode_mapel FROM mapel");
            ResultSet r = s.executeQuery();
//            comboMapel.addItem(Arrays.toString);
            while (r.next()) {    
                
                cbKdMapel.addItem(r.getString("kode_mapel"));
            }
            
            System.out.println("BERHASIL mengambil data mapel");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data mapel : " + e);
        }
    }
    
    private void getKodeGuru(){
        
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT kode_guru FROM guru");
            ResultSet r = ps.executeQuery();
            
            while (r.next()) {                
                InputKodeGuru.addItem(r.getString("kode_guru"));
            }
            
            System.out.println("BERHASIL mengambil data kode guru");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data kode guru : " + e);
        }
    }
    
    private void tambahData(){
        String namaGuru, kodeMapel;
        namaGuru = InputNamaGuru.getText();
        kodeMapel = (String) cbKdMapel.getSelectedItem();
        
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO guru VALUES(? ,?, ?)");
            ps.setString(1, null);
            ps.setString(2, namaGuru);
            ps.setString(3, kodeMapel);
            ps.executeUpdate();
            
            refreshTable();
            InputNamaGuru.setText("");
            cbKdMapel.setSelectedIndex(-1);
            namaMapel.setText("");
            
            System.out.println("");
        } catch (Exception e) {
            System.out.println("GAGAL EKSEKUSI QUERY" +e);
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data");
        }
    }
    
    private void hapusData(){
        String kodeGuru;
        kodeGuru = (String) InputKodeGuru.getSelectedItem();
        
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM guru WHERE kode_guru = ?");
            ps.setString(1, kodeGuru);
            ps.executeUpdate();
            
            refreshTable();
            InputKodeGuru.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("GAGAL EKSEKUSI QUERY"+e);
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data");
            
        }
    }
    
    private void ubahData(){
        String namaGuru, kodeMapel, kodeGuru;
        kodeMapel = (String) cbKdMapel.getSelectedItem();
        namaGuru = InputNamaGuru.getText();
        kodeGuru = (String) InputKodeGuru.getSelectedItem();
        if (kodeMapel != "") {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE guru SET nama_guru = ?, kode_mapel = ? WHERE kode_guru = ?");
                ps.setString(1, namaGuru);
                ps.setString(2, kodeMapel);
                ps.setString(3, kodeGuru);
                ps.executeUpdate();                
            } catch (Exception e) {
                System.out.println("GAGAL EKSEKUSI QUERY"+e);
                JOptionPane.showMessageDialog(null, "Gagal Mengubah Data");
            
            }
        }else {
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE guru SET nama_guru = ?  WHERE kode_guru = ?");
                ps.setString(1, namaGuru);
                ps.setString(2, kodeGuru);
                ps.executeUpdate();
            } catch (Exception e) {
                System.out.println("GAGAL EKSEKUSI QUERY"+e);
                JOptionPane.showMessageDialog(null, "Gagal Mengubah Data");
            
            }
        }
        refreshTable();
        InputNamaGuru.setText("");
        InputKodeGuru.setSelectedIndex(0);
        cbKdMapel.setSelectedIndex(-1);
        namaMapel.setText("");
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
        jPanel2 = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        btnSiswa = new javax.swing.JButton();
        btnMapel = new javax.swing.JButton();
        btnGuru = new javax.swing.JButton();
        btnKelas = new javax.swing.JButton();
        btnJurusan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        menuAwal = new javax.swing.JButton();
        btnuser = new javax.swing.JButton();
        Labelkodekelas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabelguru = new javax.swing.JTable();
        InputNamaGuru = new javax.swing.JTextField();
        btntambah = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        LabelKodeGuru = new javax.swing.JLabel();
        LabelKodeMaoel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        cbKdMapel = new javax.swing.JComboBox<>();
        namaMapel = new javax.swing.JLabel();
        LabelNamaGuru = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        InputKodeGuru = new javax.swing.JComboBox<>();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        sideBar.setBackground(new java.awt.Color(51, 153, 255));

        btnSiswa.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnSiswa.setText("Data Siswa");
        btnSiswa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiswaActionPerformed(evt);
            }
        });

        btnMapel.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnMapel.setText("Data Mata Pelajaran");
        btnMapel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapelActionPerformed(evt);
            }
        });

        btnGuru.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnGuru.setText("Data Guru");
        btnGuru.setActionCommand("");
        btnGuru.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuruActionPerformed(evt);
            }
        });

        btnKelas.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnKelas.setText("Data Kelas");
        btnKelas.setActionCommand("Data Guru");
        btnKelas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelasActionPerformed(evt);
            }
        });

        btnJurusan.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnJurusan.setText("Data Jurusan");
        btnJurusan.setActionCommand("Data Guru");
        btnJurusan.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJurusanActionPerformed(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/R.png"))); // NOI18N

        menuAwal.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        menuAwal.setText("Menu Awal");
        menuAwal.setActionCommand("Data Guru");
        menuAwal.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAwalActionPerformed(evt);
            }
        });

        btnuser.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnuser.setText("Data Pengguna");
        btnuser.setActionCommand("Data Guru");
        btnuser.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnuser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sideBarLayout = new javax.swing.GroupLayout(sideBar);
        sideBar.setLayout(sideBarLayout);
        sideBarLayout.setHorizontalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sideBarLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMapel, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnSiswa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuru, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnJurusan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuAwal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnuser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sideBarLayout.setVerticalGroup(
            sideBarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBarLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(btnSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMapel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(menuAwal, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        Labelkodekelas.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Labelkodekelas.setText("Data Guru");

        Tabelguru.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        Tabelguru.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabelguru.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelguruMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabelguru);

        InputNamaGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNamaGuruActionPerformed(evt);
            }
        });

        btntambah.setBackground(new java.awt.Color(0, 255, 0));
        btntambah.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btntambah.setForeground(new java.awt.Color(255, 255, 255));
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add-user.png"))); // NOI18N
        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });

        btnubah.setBackground(new java.awt.Color(255, 204, 0));
        btnubah.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnubah.setForeground(new java.awt.Color(255, 255, 255));
        btnubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        btnubah.setText("Ubah");
        btnubah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnubahActionPerformed(evt);
            }
        });

        btnhapus.setBackground(new java.awt.Color(255, 0, 0));
        btnhapus.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnhapus.setForeground(new java.awt.Color(255, 255, 255));
        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bin (3).png"))); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        LabelKodeGuru.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        LabelKodeGuru.setText("Kode Guru");

        LabelKodeMaoel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        LabelKodeMaoel.setText("Kode Mapel");

        cbKdMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbKdMapelActionPerformed(evt);
            }
        });

        LabelNamaGuru.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        LabelNamaGuru.setText("Nama Guru");

        InputKodeGuru.setEditable(true);
        InputKodeGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputKodeGuruActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addComponent(LabelKodeMaoel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(LabelNamaGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(LabelKodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(InputNamaGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(InputKodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(cbKdMapel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(namaMapel, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(49, 49, 49)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnubah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Labelkodekelas)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Labelkodekelas)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(InputNamaGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btntambah)
                            .addComponent(LabelNamaGuru))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnubah)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(LabelKodeMaoel)
                                    .addComponent(cbKdMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(namaMapel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnhapus)
                    .addComponent(LabelKodeGuru)
                    .addComponent(InputKodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswaActionPerformed
        // TODO add your handling code here:
        DataSiswa a = new DataSiswa();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnSiswaActionPerformed

    private void btnMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapelActionPerformed
        // TODO add your handling code here:
        DataMapel a = new DataMapel();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnMapelActionPerformed

    private void btnGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuruActionPerformed

    private void btnKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelasActionPerformed
        // TODO add your handling code here:
        DataKelas a = new DataKelas();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnKelasActionPerformed

    private void btnJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurusanActionPerformed
        // TODO add your handling code here:
        DataJurusan a = new DataJurusan();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnJurusanActionPerformed

    private void menuAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAwalActionPerformed
        // TODO add your handling code here:
        MainMenu a = new MainMenu();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_menuAwalActionPerformed

    private void InputNamaGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNamaGuruActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputNamaGuruActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        // TODO add your handling code here:
        ubahData();
    }//GEN-LAST:event_btnubahActionPerformed

    private void cbKdMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbKdMapelActionPerformed
        // TODO add your handling code here:
        String mapel;
        mapel = (String) cbKdMapel.getSelectedItem();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT nama_mapel FROM mapel where kode_mapel = ?");
            ps.setString(1, mapel);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                namaMapel.setText(r.getString("nama_mapel"));
            }
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_cbKdMapelActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btnuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuserActionPerformed
        // TODO add your handling code here:
        DataPengguna a = new DataPengguna();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnuserActionPerformed

    private void InputKodeGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputKodeGuruActionPerformed
        // TODO add your handling code here:
        String kodeGuru;
        kodeGuru = (String) InputKodeGuru.getSelectedItem();
        if (kodeGuru != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT kode_guru, nama_guru, kode_mapel FROM guru WHERE kode_guru = ?");
                ps.setString(1, kodeGuru);
                ResultSet r = ps.executeQuery();
                while (r.next()) {
                    InputNamaGuru.setText(r.getString("nama_guru"));
                    cbKdMapel.setSelectedItem(r.getString("kode_mapel"));                    
                }
            } catch (Exception e) {
            }
        }else{
            InputNamaGuru.setText("");
            cbKdMapel.setSelectedIndex(0);  
        }
    }//GEN-LAST:event_InputKodeGuruActionPerformed

    private void TabelguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelguruMouseClicked
        // TODO add your handling code here:
        int data = Tabelguru.getSelectedRow();
        
        String kode;
        kode = (String) tm.getValueAt(data, 0);
        InputKodeGuru.setSelectedItem(kode);
    }//GEN-LAST:event_TabelguruMouseClicked

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
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DataGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataGuru.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataGuru().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> InputKodeGuru;
    private javax.swing.JTextField InputNamaGuru;
    private javax.swing.JLabel LabelKodeGuru;
    private javax.swing.JLabel LabelKodeMaoel;
    private javax.swing.JLabel LabelNamaGuru;
    private javax.swing.JLabel Labelkodekelas;
    private javax.swing.JTable Tabelguru;
    private javax.swing.JButton btnGuru;
    private javax.swing.JButton btnJurusan;
    private javax.swing.JButton btnKelas;
    private javax.swing.JButton btnMapel;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnubah;
    private javax.swing.JButton btnuser;
    private javax.swing.JComboBox<String> cbKdMapel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton menuAwal;
    private javax.swing.JLabel namaMapel;
    private javax.swing.JPanel sideBar;
    // End of variables declaration//GEN-END:variables
}
