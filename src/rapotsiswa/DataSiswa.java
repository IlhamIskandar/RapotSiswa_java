/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rapotsiswa;

/**
 *
 * @author acer
 */
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DataSiswa extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tm; 
    /**
     * Creates new form DataSiswa
     */
    public DataSiswa() {
        initComponents();
        connectDB();
        refreshTable();
        getSiswa();
        getJurusan();
        getKelas();
        InputPilih.setSelectedIndex(-1);
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
        tm = new DefaultTableModel(null, new Object[] {"nis", "Nama","Kode Kelas","Kode Jurusan", "No Telepon", "Alamat"});
        tabelSiswa.setModel(tm);
        tm.getDataVector().removeAllElements();
        
        try {
            PreparedStatement p = conn.prepareStatement("SELECT * FROM siswa");
            ResultSet result = p.executeQuery();
            
            while(result.next()) {
                Object[] data = {
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                };
                tm.addRow(data);
            }
            
        } catch (Exception e) {
            System.out.println("ERROR QUERY KE DATABASE:\n"+ e);
        }
    }
    
    private void getSiswa(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT nis FROM siswa");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                InputPilih.addItem(r.getString("nis"));
            }
            System.out.println( "BERHASIL mengambil data siswa");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data siswa : "+ e);
        }
    }
    
    private void getJurusan(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT kode_jurusan FROM jurusan");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                InputJurusan.addItem(r.getString("kode_jurusan"));
            }
            System.out.println( "BERHASIL mengambil data Jurusan");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data Jurusan: "+ e);
        }
    }
    
    private void getKelas(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT kode_kelas FROM kelas");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                InputKdKelas.addItem(r.getString("kode_kelas"));
            }
            System.out.println( "BERHASIL mengambil data kelas");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data kelas: "+ e);
        }
    }
    
    private String cekAkun(){
        String nis, hasil;
        nis = InputNis.getText();
        hasil = "0";
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT id_user FROM user WHERE username = ?");            
            ps.setString(1, nis);
            ResultSet r = ps.executeQuery();
            while(r.next()){
                hasil = r.getString("id_user");
            }
            System.out.println("BERHASIL cek akun. ID = "+hasil);

        } catch (Exception e) {
            System.out.println("GAGAL cek akun" +e);
        }
        return hasil;
    }
    
    private void tambahData(){
        String nis, namaSiswa, kodeKelas, kodeJurusan, nomorTelepon, alamat, idUser;
        nis = InputNis.getText();
        namaSiswa = InputNama.getText();
        kodeKelas = (String) InputKdKelas.getSelectedItem();
        kodeJurusan = (String) InputJurusan.getSelectedItem();
        nomorTelepon = InputHP.getText();
        alamat = InputAlamat.getText();
        
        idUser = cekAkun();
//        System.out.println("Default Id"+idUser);
        if (idUser != "0") {
            try {
                PreparedStatement ps = conn.prepareStatement("INSERT INTO siswa VALUES(?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, nis);
                ps.setString(2, namaSiswa);
                ps.setString(3, kodeKelas);
                ps.setString(4, kodeJurusan);
                ps.setString(5, nomorTelepon);
                ps.setString(6, alamat);
                ps.setString(7, idUser);
                ps.executeUpdate();
                
                InputPilih.addItem(nis);
                
                System.out.println("BERHASIL input data siswa");
            } catch (Exception e) {
                System.out.println("GAGAL menambah ada siswa : " +e);
                JOptionPane.showMessageDialog(null, "Gagal Menambah Data");
            }
        } else if (idUser == "0"){
            try {
                String accPassword;
                accPassword = FunctionLib.generateRandomPassword(10);
                
                PreparedStatement ps = conn.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?)");
                ps.setString(1, null);
                ps.setString(2, nis);
                ps.setString(3, accPassword);
                ps.setString(4, "siswa");
                ps.executeUpdate();
                System.out.println("BERHASIL membuat akun siswa");
                try {
                    PreparedStatement getUserId = conn.prepareStatement("SELECT id_user FROM user WHERE username = ?");
                    getUserId.setString(1, nis);
                    ResultSet r = getUserId.executeQuery();
                    while(r.next()){
                        idUser = r.getString("id_user");
                    }
                    System.out.println("BERHASIL mengambil ID User");
                    try {
                        PreparedStatement inputSiswa = conn.prepareStatement("INSERT INTO siswa VALUES(?, ?, ?, ?, ?, ?, ?)");
                        inputSiswa.setString(1, nis);
                        inputSiswa.setString(2, namaSiswa);
                        inputSiswa.setString(3, kodeKelas);
                        inputSiswa.setString(4, kodeJurusan);
                        inputSiswa.setString(5, nomorTelepon);
                        inputSiswa.setString(6, alamat);
                        inputSiswa.setString(7, idUser);
                        inputSiswa.executeUpdate();
                        
                        InputPilih.addItem(nis);
                        
                        System.out.println("BERHASIL input data siswa");
                    } catch (Exception e) {
                        System.out.println("GAGAL input data siswa : "+e);
                    }
                    
                } catch (Exception e) {
                    System.out.println("GAGAL mengambil ID User :"+e);
                }
            } catch (Exception e) {
                System.out.println("GAGAL membuat akun siswa : " +e);
            }
        }
        refreshTable();
        InputNis.setText("");
        InputNama.setText("");
        InputKdKelas.setSelectedIndex(1);
        InputJurusan. setSelectedIndex(1);
        InputHP.setText("");
        InputAlamat.setText("");
    }
    
    private void hapusData(){
        String nis;
        nis = InputNis.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM siswa WHERE nis = ?");
            ps.setString(1, nis);
            ps.executeUpdate();
            
            refreshTable();
            InputNis.setText("");
            InputNama.setText("");
            InputKdKelas.setSelectedIndex(1);
            InputJurusan. setSelectedIndex(1);
            InputHP.setText("");
            InputAlamat.setText("");
            
            System.out.println("BERHASIL menghapus data");
        } catch (Exception e) {
            System.out.println("GAGAL menghapus data : "+e);
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data");
            
        }
    }
    
    private void ubahData(){
        String nis, namaSiswa, kodeKelas, kodeJurusan, nomorTelepon, alamat;
        nis = InputNis.getText();
        namaSiswa = InputNama.getText();
        kodeKelas = (String) InputKdKelas.getSelectedItem();
        kodeJurusan = (String) InputJurusan.getSelectedItem();
        nomorTelepon = InputHP.getText();
        alamat = InputAlamat.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE siswa SET nama = ?, kode_kelas = ?, kode_jurusan = ?, nomor_telepon = ?, alamat = ? WHERE nis = ?");
            ps.setString(1, namaSiswa);
            ps.setString(2, kodeKelas);
            ps.setString(3, kodeJurusan);
            ps.setString(4, nomorTelepon);
            ps.setString(5, alamat);
            ps.setString(6, nis);
            ps.executeUpdate();
            
            refreshTable();
            InputNis.setText("");
            InputNama.setText("");
            InputKdKelas.setSelectedIndex(0);
            InputJurusan. setSelectedIndex(0);
            InputHP.setText("");
            InputAlamat.setText("");
            
            System.out.println("BERHASIL mengubah data siswa");
        } catch (Exception e) {
            System.out.println("GAGAL mengubah data siswa : " +e);
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

        jPasswordField1 = new javax.swing.JPasswordField();
        jPanel1 = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        btnSiswa = new javax.swing.JButton();
        btnMapel = new javax.swing.JButton();
        btnGuru = new javax.swing.JButton();
        btnKelas = new javax.swing.JButton();
        btnJurusan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        menuAwal = new javax.swing.JButton();
        btnuser = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        InputNis = new javax.swing.JTextField();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        InputNama = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        InputJurusan = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        InputAlamat = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelSiswa = new javax.swing.JTable();
        InputHP = new javax.swing.JTextField();
        InputKdKelas = new javax.swing.JComboBox<>();
        InputPilih = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

        jPasswordField1.setText("jPasswordField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

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
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Data Siswa");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel6.setText("NIS");

        InputNis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNisActionPerformed(evt);
            }
        });

        btnTambah.setBackground(new java.awt.Color(0, 255, 0));
        btnTambah.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnTambah.setForeground(new java.awt.Color(255, 255, 255));
        btnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add-user (1).png"))); // NOI18N
        btnTambah.setText("Tambah");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(255, 204, 0));
        btnUbah.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));
        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bin (3).png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel7.setText("Nama");

        InputNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputNamaActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setText("Kode Kelas");

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel9.setText("Kode Jurusan");

        jLabel10.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel10.setText("Nomor Telepon");

        jLabel11.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel11.setText("Alamat");

        InputJurusan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputJurusanActionPerformed(evt);
            }
        });

        InputAlamat.setColumns(20);
        InputAlamat.setRows(5);
        jScrollPane1.setViewportView(InputAlamat);

        tabelSiswa.setModel(new javax.swing.table.DefaultTableModel(
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
        tabelSiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelSiswaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelSiswa);

        InputKdKelas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputKdKelasActionPerformed(evt);
            }
        });

        InputPilih.setEditable(true);
        InputPilih.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputPilihActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setText("Pilih Data");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                            .addComponent(InputKdKelas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(InputNis)
                            .addComponent(InputNama)
                            .addComponent(InputHP)
                            .addComponent(InputJurusan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnTambah)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(4, 4, 4)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(InputPilih, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 584, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel12)
                    .addComponent(InputPilih, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(InputNis, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTambah)
                    .addComponent(btnUbah)
                    .addComponent(btnHapus))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addGap(4, 4, 4)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(InputNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(InputKdKelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(InputHP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 321, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswaActionPerformed
        // TODO add your handling code here:
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
        DataGuru a = new DataGuru();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
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

    private void InputNisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNisActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputNisActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahData();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void InputNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_InputNamaActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_btnTambahActionPerformed

    private void InputJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputJurusanActionPerformed
        // TODO add your handling code here:
        String jurusan;
        jurusan = (String) InputJurusan.getSelectedItem();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT nama_jurusan FROM jurusan where kode_jurusan = ?");
            ps.setString(1, jurusan);
            ResultSet r = ps.executeQuery();
           
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_InputJurusanActionPerformed

    private void InputKdKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputKdKelasActionPerformed
        // TODO add your handling code here:
         String kelas;
        kelas = (String) InputKdKelas.getSelectedItem();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT nama_jurusan FROM jurusan where kode_jurusan = ?");
            ps.setString(1,kelas);
            ResultSet r = ps.executeQuery();
           
            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_InputKdKelasActionPerformed

    private void btnuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuserActionPerformed
        // TODO add your handling code here:
        DataPengguna a = new DataPengguna();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnuserActionPerformed

    private void InputPilihActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputPilihActionPerformed
        // TODO add your handling code here:
        String nis;
        nis = (String) InputPilih.getSelectedItem();
        if (nis != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT nis, nama, kode_kelas, kode_jurusan, nomor_telepon, alamat FROM siswa WHERE nis = ?");
                ps.setString(1, nis);
                ResultSet r = ps.executeQuery();
                while (r.next()) {
                    InputNis.setText(r.getString(1));
                    InputNama.setText(r.getString(2));
                    InputKdKelas.setSelectedItem(r.getString(3));
                    InputJurusan.setSelectedItem(r.getString(4));
                    InputHP.setText(r.getString(5));
                    InputAlamat.setText(r.getString(6));
                }
            } catch (Exception e) {
            }
        }else{
            InputNis.setText("");
            InputNama.setText("");
            InputKdKelas.setSelectedIndex(0);
            InputJurusan.setSelectedIndex(0);
            InputHP.setText("");
            InputAlamat.setText("");
        }
        
    }//GEN-LAST:event_InputPilihActionPerformed

    private void tabelSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSiswaMouseClicked
        // TODO add your handling code here:
        int data = tabelSiswa.getSelectedRow();
        
        String kode;
        kode = (String) tm.getValueAt(data, 0);
        InputPilih.setSelectedItem(kode);
    }//GEN-LAST:event_tabelSiswaMouseClicked

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
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea InputAlamat;
    private javax.swing.JTextField InputHP;
    private javax.swing.JComboBox<String> InputJurusan;
    private javax.swing.JComboBox<String> InputKdKelas;
    private javax.swing.JTextField InputNama;
    private javax.swing.JTextField InputNis;
    private javax.swing.JComboBox<String> InputPilih;
    private javax.swing.JButton btnGuru;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnJurusan;
    private javax.swing.JButton btnKelas;
    private javax.swing.JButton btnMapel;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btnuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton menuAwal;
    private javax.swing.JPanel sideBar;
    private javax.swing.JTable tabelSiswa;
    // End of variables declaration//GEN-END:variables
}
