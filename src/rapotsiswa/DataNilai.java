/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author acer
 */
public class DataNilai extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tm;
    String id_penilaian;
            
    /**
     * Creates new form DataNilai
     */
    public DataNilai() {
        initComponents();
        connectDB();
        refreshTable();
        getNIS();
        getKodeGuru();
        getJenisPenilaian();
        getID();
        InputNis.setSelectedIndex(-1);
        inputKodeGuru.setSelectedIndex(-1);
        inputIDnilai.setSelectedIndex(-1);
        InputNilai.setText("");
//        System.out.println("aasd"+inputKodeGuru.getSelectedItem());
        
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
    
    private void refreshTable() {
        tm = new DefaultTableModel(null, new Object[] {"ID","NIS", "Nama", "Kelas","Mapel", "Jenis Penilaian", "Nilai"});
        Tabelnilai.setModel(tm);
        tm.getDataVector().removeAllElements();
        
        try {
            PreparedStatement p = conn.prepareStatement("SELECT nilai.id_nilai, nilai.nis, siswa.nama, CONCAT(siswa.kode_kelas, siswa.kode_jurusan), mapel.nama_mapel, jenis_penilaian.jenis, nilai.nilai FROM siswa, nilai, mapel, guru, jenis_penilaian WHERE siswa.nis = nilai.nis AND nilai.id_penilaian = jenis_penilaian.id_penilaian AND nilai.kode_guru = guru.kode_guru AND guru.kode_mapel = mapel.kode_mapel  Group BY nilai.id_nilai");
            ResultSet result = p.executeQuery();
            
            while(result.next()) {
                Object[] data = {
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    result.getString(7)
                };
                tm.addRow(data);
            }
            System.out.println("BERHASIL mengambil data nilai");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data nilai : "+ e);
        }
    }
    
//    public class getSiswa {
//        String nis, nama;
//        
//        public String getNIS(){
//            return nis;
//        }
//        public void setNIS(String nis){
//            this.nis = nis;
//        }
//        public String getNama(){
//            return nama;
//        }
//        public void setNama(String nama){
//            this.nama = nama;
//        }
//        
//        @Override
//        public String toString(){
//            return nama;
//        }
//    }
    
    private void getID(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT id_nilai FROM nilai");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
//                 + r.getString("nama")
                inputIDnilai.addItem(r.getString("id_nilai"));
            }
            System.out.println( "BERHASIL mengambil data id nilai");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data id nilai : "+ e);
        }
    }
    
    private void getNIS(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT nis, nama FROM siswa");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
//                 + r.getString("nama")
                InputNis.addItem(r.getString("nis"));
            }
            System.out.println( "BERHASIL mengambil data nis");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data nis : "+ e);
        }
    }
    
    private void getKodeGuru(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT guru.kode_guru, guru.nama_guru, mapel.kode_mapel FROM guru, mapel WHERE guru.kode_mapel = mapel.kode_mapel ORDER BY nama_guru");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                inputKodeGuru.addItem(r.getString("kode_guru") +" "+r.getString("nama_guru")+" "+r.getString("kode_mapel"));
            }
            System.out.println( "BERHASIL mengambil data kode guru");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data kode guru : "+ e);
        }
    }

    private void getJenisPenilaian(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT id_penilaian, jenis FROM jenis_penilaian");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                InputJenisPenilaian.addItem(r.getString("jenis"));
                
            }
            System.out.println( "BERHASIL mengambil data jenis penilaian");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data jenis penilaian : "+ e);
        }
    }
    
    private void tambahData(){
        String nis, kodeGuru, kodeNilai, nilai;
        nis = (String) InputNis.getSelectedItem();
        kodeGuru = FunctionLib.toNumberOnly((String) inputKodeGuru.getSelectedItem());
        kodeNilai = (String) InputJenisPenilaian.getSelectedItem();
        nilai = (String) InputNilai.getText();
        
//        System.out.println(nis);
//        System.out.println(kodeGuru);
//        System.out.println(kodeNilai);
//        System.out.println(id_penilaian);
//        System.out.println(nilai);
        
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO nilai VALUES(?, ?, ?, ?, ?)");
            ps.setString(1, null);
            ps.setString(2, id_penilaian);
            ps.setString(3, nis);
            ps.setString(4, kodeGuru);
            ps.setString(5, nilai);
            ps.executeUpdate();
            
            refreshTable();
//            InputNis.setSelectedIndex(-1);
//            inputKodeGuru.setSelectedIndex(-1);
//            InputJenisPenilaian.setSelectedIndex(-1);
            InputNilai.setText("");
            System.out.println("BERHASIL menambah data nilai");
        } catch (Exception e) {
            System.out.println("GAGAL menambah data nilai " +e);
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data");
        }
    }
    
    private void setIdPenilaian(String jenis){
        this.id_penilaian = jenis;
    }
    private String getIdPenilaian(){
        return id_penilaian;
    }
    
    private void hapusData(){
        String idNilai;
        idNilai = (String) inputIDnilai.getSelectedItem();
        
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM nilai WHERE id_nilai = ?");
            ps.setString(1, idNilai);
            ps.executeUpdate();
            
            refreshTable();
            InputNilai.setText("");
            
            System.out.println("BERHASIL menghapus data nilai");
        } catch (Exception e) {
            System.out.println("GAGAL menghapus data "+e);
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data");
            
        }
    }
    
    private void ubahData(){
        String idNilai, nilai;
        idNilai = (String) inputIDnilai.getSelectedItem();
        nilai = InputNilai.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE nilai SET nilai = ? WHERE id_nilai = ?");
            ps.setString(1, nilai);
            ps.setString(2, idNilai);
            ps.executeUpdate();
            
            refreshTable();
            InputNilai.setText("");
//            inputIDnilai.set("");
            System.out.println("BERHASIL mengubah data nilai");
        } catch (Exception e) {
            System.out.println("GAGAL mengubah data nilai"+e);
            JOptionPane.showMessageDialog(null, "Gagal Mengubah Data");
            
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

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Labelpts = new javax.swing.JLabel();
        Labeluh = new javax.swing.JLabel();
        LabelJnsPenilaian = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabelnilai = new javax.swing.JTable();
        btntambah = new javax.swing.JButton();
        btnubah = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        sideBar2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        menuAwal2 = new javax.swing.JButton();
        nilaiSiswa3 = new javax.swing.JButton();
        Labelnis = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        InputJenisPenilaian = new javax.swing.JComboBox<>();
        InputNis = new javax.swing.JComboBox<>();
        inputKodeGuru = new javax.swing.JComboBox<>();
        Labeluh1 = new javax.swing.JLabel();
        lblNamaGuru = new javax.swing.JLabel();
        lblMapel = new javax.swing.JLabel();
        LabelJnsPenilaian1 = new javax.swing.JLabel();
        InputNilai = new javax.swing.JTextField();
        lblKelas = new javax.swing.JLabel();
        lblNamaSiswa = new javax.swing.JLabel();
        Labeluh6 = new javax.swing.JLabel();
        Labeluh7 = new javax.swing.JLabel();
        lblJenisNilai = new javax.swing.JLabel();
        Labelidn = new javax.swing.JLabel();
        inputIDnilai = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mainPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Data Nilai");

        Labelpts.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Labelpts.setText("Kode Guru-Mapel");

        Labeluh.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        Labeluh.setText("Nama Guru");

        LabelJnsPenilaian.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        LabelJnsPenilaian.setText(" Jenis Penilaian        :");

        Tabelnilai.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        Tabelnilai.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabelnilai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelnilaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Tabelnilai);

        btntambah.setBackground(new java.awt.Color(0, 255, 0));
        btntambah.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btntambah.setForeground(new java.awt.Color(255, 255, 255));
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add-user (1).png"))); // NOI18N
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

        sideBar2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/R.png"))); // NOI18N

        menuAwal2.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        menuAwal2.setText("Menu Awal");
        menuAwal2.setActionCommand("Data Guru");
        menuAwal2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuAwal2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAwal2ActionPerformed(evt);
            }
        });

        nilaiSiswa3.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        nilaiSiswa3.setText("Data Nilai");
        nilaiSiswa3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nilaiSiswa3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sideBar2Layout = new javax.swing.GroupLayout(sideBar2);
        sideBar2.setLayout(sideBar2Layout);
        sideBar2Layout.setHorizontalGroup(
            sideBar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBar2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel7)
                .addContainerGap(40, Short.MAX_VALUE))
            .addGroup(sideBar2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sideBar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nilaiSiswa3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuAwal2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sideBar2Layout.setVerticalGroup(
            sideBar2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBar2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(nilaiSiswa3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuAwal2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Labelnis.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Labelnis.setText("NIS");

        InputJenisPenilaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputJenisPenilaianActionPerformed(evt);
            }
        });

        InputNis.setEditable(true);
        InputNis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNisActionPerformed(evt);
            }
        });

        inputKodeGuru.setEditable(true);
        inputKodeGuru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputKodeGuruActionPerformed(evt);
            }
        });

        Labeluh1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        Labeluh1.setText("Mapel");

        lblNamaGuru.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNamaGuru.setText(":");

        lblMapel.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblMapel.setText(":");

        LabelJnsPenilaian1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        LabelJnsPenilaian1.setText("Nilai    :");

        InputNilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNilaiActionPerformed(evt);
            }
        });

        lblKelas.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblKelas.setText(":");

        lblNamaSiswa.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        lblNamaSiswa.setText(":");

        Labeluh6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        Labeluh6.setText("Nama Siswa");

        Labeluh7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        Labeluh7.setText("Kelas");

        lblJenisNilai.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N

        Labelidn.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Labelidn.setText("ID Nilai");

        inputIDnilai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputIDnilaiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addComponent(sideBar2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(LabelJnsPenilaian1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(InputNilai, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE))
                                            .addComponent(Labelnis)
                                            .addComponent(InputNis, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Labelpts, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(inputKodeGuru, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(LabelJnsPenilaian, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(Labeluh7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(Labeluh6, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(lblNamaSiswa, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                                                    .addComponent(lblKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(InputJenisPenilaian, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(Labeluh1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(Labeluh, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(mainPanelLayout.createSequentialGroup()
                                                        .addGap(10, 10, 10)
                                                        .addComponent(lblJenisNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(lblMapel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(lblNamaGuru, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 88, Short.MAX_VALUE)
                                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(btnhapus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btnubah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(btntambah, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(Labelidn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(inputIDnilai, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap())
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(73, 530, Short.MAX_VALUE))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jSeparator1)
                        .addContainerGap())))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Labelnis)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(InputNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNamaSiswa, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Labeluh6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Labeluh7)
                                    .addComponent(lblKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(Labelpts)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(inputKodeGuru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(mainPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNamaGuru, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Labeluh))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Labeluh1)
                                    .addComponent(lblMapel, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblJenisNilai, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(LabelJnsPenilaian)
                                .addComponent(InputJenisPenilaian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(btntambah)
                        .addGap(18, 18, 18)
                        .addComponent(Labelidn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(inputIDnilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnubah)))
                .addGap(6, 6, 6)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelJnsPenilaian1)
                    .addComponent(InputNilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnhapus))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuAwal2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAwal2ActionPerformed
        // TODO add your handling code here:
        MenuGuru a = new MenuGuru();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_menuAwal2ActionPerformed

    private void nilaiSiswa3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nilaiSiswa3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nilaiSiswa3ActionPerformed

    private void btnubahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnubahActionPerformed
        // TODO add your handling code here:
        ubahData();
    }//GEN-LAST:event_btnubahActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_btntambahActionPerformed

    private void InputNilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNilaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputNilaiActionPerformed

    private void InputNisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNisActionPerformed
        // TODO add your handling code here:
        String nis;
        nis = (String) InputNis.getSelectedItem();
        if (nis != null) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT siswa.nama, siswa.kode_kelas, jurusan.nama_jurusan FROM siswa, kelas, jurusan WHERE siswa.kode_kelas = kelas.kode_kelas AND siswa.kode_jurusan = jurusan.kode_jurusan AND nis = ?");
            ps.setString(1, nis);
            ResultSet r = ps.executeQuery();
            while (r.next()) {
                lblNamaSiswa.setText(": " + r.getString("nama"));
                lblKelas.setText(": " + r.getString("kode_kelas") + " " + r.getString("nama_jurusan"));
            }
            System.out.println( "BERHASIL mengambil data siswa");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data siswa : "+ e);
        }
        }else{
            lblNamaSiswa.setText(": ");
            lblKelas.setText(": ");
        }
        
    }//GEN-LAST:event_InputNisActionPerformed

    private void inputKodeGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputKodeGuruActionPerformed
        // TODO add your handling code here:
        String kodeGuru;
        kodeGuru = (String) inputKodeGuru.getSelectedItem();

        if (kodeGuru != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT guru.nama_guru, mapel.nama_mapel FROM guru, mapel WHERE guru.kode_mapel = mapel.kode_mapel AND kode_guru = ?");
                ps.setString(1, kodeGuru);
                ResultSet r = ps.executeQuery();
                while (r.next()) {
                    lblNamaGuru.setText(": " + r.getString("nama_guru"));
                    lblMapel.setText(": " + r.getString("nama_mapel"));
                }
                System.out.println( "BERHASIL mengambil data guru");
            } catch (Exception e) {
                System.out.println( "GAGAL mengambil data guru : "+ e);
            }
        }else{
            lblNamaGuru.setText(": ");
            lblMapel.setText(": ");
        }
    }//GEN-LAST:event_inputKodeGuruActionPerformed

    private void InputJenisPenilaianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputJenisPenilaianActionPerformed
        // TODO add your handling code here:
        String idpenilaian;
        idpenilaian = (String) InputJenisPenilaian.getSelectedItem();
        try {
            PreparedStatement s = conn.prepareStatement("SELECT id_penilaian FROM jenis_penilaian WHERE jenis = ?");
            s.setString(1, idpenilaian);
            ResultSet r = s.executeQuery();
            while (r.next()) {                
                setIdPenilaian(r.getString("id_penilaian"));
                System.out.println("di while : " + r.getString("id_penilaian"));
            }
            
            System.out.println( "BERHASIL mengambil id penilaian");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil id penilaian : " + e);
        }
        
//        String idPenilaian;
//        idPenilaian = (String) InputJenisPenilaian.getSelectedItem();
          //set label penilaian
//        if (idPenilaian != null) {
//            try {
//                PreparedStatement ps = conn.prepareStatement("SELECT jenis FROM jenis_penilaian WHERE id_penilaian = ?");
//                ps.setString(1, idPenilaian);
//                ResultSet r = ps.executeQuery();
//                while (r.next()) {
//                    lblJenisNilai.setText(": " + r.getString("jenis"));
//                }
//                System.out.println( "BERHASIL mengambil data penilaian");
//            } catch (Exception e) {
//                System.out.println( "GAGAL mengambil data penilaian : "+ e);
//            }
//        }else{
//            lblJenisNilai.setText(": ");
//        }
    }//GEN-LAST:event_InputJenisPenilaianActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnhapusActionPerformed

    private void TabelnilaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelnilaiMouseClicked
        // TODO add your handling code here:
        int data = Tabelnilai.getSelectedRow();
        
        String kode;
        kode = (String) tm.getValueAt(data, 0);
        inputIDnilai.setSelectedItem(kode);
        
    }//GEN-LAST:event_TabelnilaiMouseClicked

    private void inputIDnilaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputIDnilaiActionPerformed
        // TODO add your handling code here:
        String id = (String)inputIDnilai.getSelectedItem();
        try {
            PreparedStatement p = conn.prepareStatement("SELECT nilai.id_penilaian, nilai.kode_guru, nilai.id_nilai, nilai.nis, siswa.nama, CONCAT(siswa.kode_kelas, siswa.kode_jurusan), mapel.nama_mapel, jenis_penilaian.jenis, nilai.nilai FROM siswa, nilai, mapel, guru, jenis_penilaian WHERE siswa.nis = nilai.nis AND nilai.id_penilaian = jenis_penilaian.id_penilaian AND nilai.kode_guru = guru.kode_guru AND guru.kode_mapel = mapel.kode_mapel AND nilai.id_nilai = ?  Group BY nilai.id_nilai");
            p.setString(1, id);
            ResultSet result = p.executeQuery();
            
            while(result.next()) {
                InputNis.setSelectedItem(result.getString("nis"));
                inputKodeGuru.setSelectedItem(result.getString("kode_guru"));
                InputJenisPenilaian.setSelectedItem(result.getString("id_penilaian"));
                InputNilai.setText(result.getString("nilai"));
//                Object[] data = {
//                    result.getString(1),
//                    result.getString(2),
//                    result.getString(3),
//                    result.getString(4),
//                    result.getString(5),
//                    result.getString(6),
//                    result.getString(7)
//                };
//                tm.addRow(data);
            }
            System.out.println("BERHASIL mengambil data nilai");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data nilai : "+ e);
        }
    }//GEN-LAST:event_inputIDnilaiActionPerformed

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
            java.util.logging.Logger.getLogger(DataNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataNilai.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataNilai().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> InputJenisPenilaian;
    private javax.swing.JTextField InputNilai;
    private javax.swing.JComboBox<String> InputNis;
    private javax.swing.JLabel LabelJnsPenilaian;
    private javax.swing.JLabel LabelJnsPenilaian1;
    private javax.swing.JLabel Labelidn;
    private javax.swing.JLabel Labelnis;
    private javax.swing.JLabel Labelpts;
    private javax.swing.JLabel Labeluh;
    private javax.swing.JLabel Labeluh1;
    private javax.swing.JLabel Labeluh6;
    private javax.swing.JLabel Labeluh7;
    private javax.swing.JTable Tabelnilai;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnubah;
    private javax.swing.JComboBox<String> inputIDnilai;
    private javax.swing.JComboBox<String> inputKodeGuru;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblJenisNilai;
    private javax.swing.JLabel lblKelas;
    private javax.swing.JLabel lblMapel;
    private javax.swing.JLabel lblNamaGuru;
    private javax.swing.JLabel lblNamaSiswa;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JButton menuAwal2;
    private javax.swing.JButton nilaiSiswa3;
    private javax.swing.JPanel sideBar2;
    // End of variables declaration//GEN-END:variables
}
