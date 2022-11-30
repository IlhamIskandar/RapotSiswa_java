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
 * @author UH
 */
public class DataPengguna extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tm;
    
    /**
     * Creates new form DataPengguna
     */
    public DataPengguna() {
        initComponents();
        connectDB();
        refreshTableSiswa();
        
        getId();
        inputId.setSelectedIndex(-1);
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
    
    private void refreshTablePetugas(){
        tm = new DefaultTableModel(null, new Object[] { "ID user", "Username", "Password", "Level" });
        tableUser.setModel(tm);
        tm.getDataVector().removeAllElements();
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM user WHERE level = 'petugas'");
            ResultSet r = s.executeQuery();
            
            String level , password;
            
            while(r.next()) {
                if ("petugas".equals(r.getString(4))) {
                    password = "*******";
                    Object[] data = {
                        r.getString(1),
                        r.getString(2),
                        "*******",
                        r.getString(4)
                    };
                    tm.addRow(data);
                }else {
                    password = r.getString(3);
                    Object[] data = {
                        r.getString(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4)
                    };
                    tm.addRow(data);
                }
            };
            System.out.println("BERHASIL mengambil data petugas");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data petugas : "+e);
        }
    }
    
    private void refreshTableGuru(){
        tm = new DefaultTableModel(null, new Object[] { "ID user", "Username", "Password", "Level" });
        tableUser.setModel(tm);
        tm.getDataVector().removeAllElements();
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM user WHERE level = 'guru'");
            ResultSet r = s.executeQuery();
            
            String level , password;
            
            while(r.next()) {
                if ("petugas".equals(r.getString(4))) {
                    password = "*******";
                    Object[] data = {
                        r.getString(1),
                        r.getString(2),
                        "*******",
                        r.getString(4)
                    };
                    tm.addRow(data);
                }else {
                    password = r.getString(3);
                    Object[] data = {
                        r.getString(1),
                        r.getString(2),
                        r.getString(3),
                        r.getString(4)
                    };
                    tm.addRow(data);
                }
            };
            System.out.println("BERHASIL mengambil data guru");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data guru : "+e);
        }
    }
    
    private void refreshTableSiswa(){
        tm = new DefaultTableModel(null, new Object[] { "ID user", "Username", "Password", "Level" });
        tableUser.setModel(tm);
        tm.getDataVector().removeAllElements();
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM user WHERE level = 'siswa'");
            ResultSet r = s.executeQuery();
                        
            while(r.next()) {
                Object[] data = {
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4)
                };
                tm.addRow(data);
            };
            System.out.println("BERHASIL mengambil data siswa");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil data siswa: "+e);
        }
    }
    

    private void getId(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM user WHERE level != 'petugas'");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                inputId.addItem(r.getString("id_user"));
            }
            System.out.println( "BERHASIL mengambil data Jurusan");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data Jurusan: "+ e);
        }
    }

    private void tambahData(){
        String username, password,level;
        username = Inputusername.getText();
        password = InputPassword.getText();
        level = (String) inputLevel.getSelectedItem();
        
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO user VALUES(?, ?, ?, ?)");
            ps.setString(1, null);
            ps.setString(2, username);
            ps.setString(3, password);
            ps.setString(4, level.toLowerCase());
            ps.executeUpdate();
            
            switch(level){
                case "Siswa":
                    refreshTableSiswa();
                    break;
                case "Guru":
                    refreshTableGuru();
                    break;
                case "Petugas":
                    refreshTablePetugas();
                    break;
            }
            
            Inputusername.setText("");
            InputPassword.setText("");
            System.out.println("BERHASIL menambah data user");
        } catch (Exception e) {
            System.out.println("GAGAL menambah data user: "+e);
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data");
        }
    }
    
    private void hapusData(){
        String idUser, level;
        idUser = (String) inputId.getSelectedItem();
        level = "";
        try {
            PreparedStatement getLevel = conn.prepareStatement( "SELECT level FROM user WHERE id_user = ?");
            getLevel.setString(1, idUser);
            ResultSet gl = getLevel.executeQuery();
            while (gl.next()) {
                level = gl.getString(1);
            }
            
            try {
                PreparedStatement ps = conn.prepareStatement("DELETE FROM user WHERE id_user = ?");
                ps.setString(1, idUser);
                ps.executeUpdate();

                switch(level){
                    case "siswa":
                        refreshTableSiswa();
                        break;
                    case "guru":
                        refreshTableGuru();
                        break;
                    case "petugas":
                        refreshTablePetugas();
                        break;
                }
                System.out.println("BERHASIL menghapus data");
            } catch (Exception e) {
                System.out.println("GAGAL menghapus data : "+e);
                JOptionPane.showMessageDialog(null, "Gagal Menghapus Data");
            }
            System.out.println("BERHASIL mengambil level user");
        } catch (Exception e) {
            System.out.println("GAGAL mengambil level user : "+e);
        }
    }
    
    private void ubahData(){
        String username, password,level, idUser;
        username = Inputusername.getText();
        password = InputPassword.getText();
        idUser = (String) inputId.getSelectedItem();
        level = "";
        
        try {
            PreparedStatement getLevel = conn.prepareStatement( "SELECT level FROM user WHERE id_user = ?");
            getLevel.setString(1, idUser);
            ResultSet gl = getLevel.executeQuery();
            while (gl.next()) {
                level = gl.getString(1);
            }
            
            try {
                PreparedStatement ps = conn.prepareStatement("UPDATE user SET username = ?, password = ? WHERE id_user = ?");
                ps.setString(1, username);
                ps.setString(2, password);
                ps.setString(3, idUser);
                ps.executeUpdate();

                switch(level){
                    case "siswa":
                        refreshTableSiswa();
                        break;
                    case "guru":
                        refreshTableGuru();
                        break;
                    case "petugas":
                        refreshTablePetugas();
                        break;
                }
                System.out.println("BERHASIL mengubah data user");
            } catch (Exception e) {
                System.out.println("GAGAL mengubah data user: "+e);
                JOptionPane.showMessageDialog(null, "Gagal Mengubah Data");

            }
            
        } catch (Exception e) {
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

        jPanel2 = new javax.swing.JPanel();
        sideBar4 = new javax.swing.JPanel();
        btnSiswa4 = new javax.swing.JButton();
        btnMapel4 = new javax.swing.JButton();
        btnGuru4 = new javax.swing.JButton();
        btnKelas4 = new javax.swing.JButton();
        btnJurusan4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        menuAwal4 = new javax.swing.JButton();
        btnuser4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Inputusername = new javax.swing.JTextField();
        InputPassword = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        inputLevel = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        inputId = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        btnAcak = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        sideBar4.setBackground(new java.awt.Color(51, 153, 255));

        btnSiswa4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnSiswa4.setText("Data Siswa");
        btnSiswa4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiswa4ActionPerformed(evt);
            }
        });

        btnMapel4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnMapel4.setText("Data Mata Pelajaran");
        btnMapel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnMapel4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMapel4ActionPerformed(evt);
            }
        });

        btnGuru4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnGuru4.setText("Data Guru");
        btnGuru4.setActionCommand("");
        btnGuru4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuru4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuru4ActionPerformed(evt);
            }
        });

        btnKelas4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnKelas4.setText("Data Kelas");
        btnKelas4.setActionCommand("Data Guru");
        btnKelas4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnKelas4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelas4ActionPerformed(evt);
            }
        });

        btnJurusan4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnJurusan4.setText("Data Jurusan");
        btnJurusan4.setActionCommand("Data Guru");
        btnJurusan4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnJurusan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJurusan4ActionPerformed(evt);
            }
        });

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/R.png"))); // NOI18N

        menuAwal4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        menuAwal4.setText("Menu Awal");
        menuAwal4.setActionCommand("Data Guru");
        menuAwal4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        menuAwal4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuAwal4ActionPerformed(evt);
            }
        });

        btnuser4.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnuser4.setText("Data Pengguna");
        btnuser4.setActionCommand("Data Guru");
        btnuser4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnuser4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnuser4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sideBar4Layout = new javax.swing.GroupLayout(sideBar4);
        sideBar4.setLayout(sideBar4Layout);
        sideBar4Layout.setHorizontalGroup(
            sideBar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBar4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(sideBar4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sideBar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnMapel4, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(btnSiswa4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnGuru4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKelas4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnJurusan4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(menuAwal4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnuser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        sideBar4Layout.setVerticalGroup(
            sideBar4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideBar4Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(btnSiswa4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnMapel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuru4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKelas4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnJurusan4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnuser4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(menuAwal4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Data Pengguna");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setText("Username");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setText("Level");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Password");

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
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
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableUser);

        jButton1.setBackground(new java.awt.Color(0, 255, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add-user.png"))); // NOI18N
        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnUbah.setBackground(new java.awt.Color(255, 204, 0));
        btnUbah.setForeground(new java.awt.Color(255, 255, 255));
        btnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        btnUbah.setText("Ubah");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setBackground(new java.awt.Color(255, 0, 0));
        btnHapus.setForeground(new java.awt.Color(255, 255, 255));
        btnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bin (3).png"))); // NOI18N
        btnHapus.setText("Hapus");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        inputLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Siswa", "Guru", "Petugas" }));
        inputLevel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputLevelActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("ID User");

        inputId.setEditable(true);
        inputId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inputIdActionPerformed(evt);
            }
        });

        btnAcak.setBackground(new java.awt.Color(0, 153, 204));
        btnAcak.setForeground(new java.awt.Color(255, 255, 255));
        btnAcak.setText("Acak Sandi");
        btnAcak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcakActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(sideBar4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(inputLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(186, 186, 186))
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(InputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(Inputusername, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnAcak, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(inputId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jSeparator1))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2)))
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Inputusername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(InputPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAcak))
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(inputLevel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(inputId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus)
                    .addComponent(btnUbah))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void inputLevelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputLevelActionPerformed
        // TODO add your handling code here:
        String pilihan;
        pilihan = (String) inputLevel.getSelectedItem();
        switch(pilihan){
            case "Siswa":
            refreshTableSiswa();
            break;
            case "Guru":
            refreshTableGuru();
            break;
            case "Petugas":
            refreshTablePetugas();
            break;
        }
    }//GEN-LAST:event_inputLevelActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnuser4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuser4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnuser4ActionPerformed

    private void menuAwal4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuAwal4ActionPerformed
        // TODO add your handling code here:
        MainMenu a = new MainMenu();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_menuAwal4ActionPerformed

    private void btnJurusan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurusan4ActionPerformed
        // TODO add your handling code here:
        DataJurusan a = new DataJurusan();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnJurusan4ActionPerformed

    private void btnKelas4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelas4ActionPerformed
        // TODO add your handling code here:
        DataKelas a = new DataKelas();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnKelas4ActionPerformed

    private void btnGuru4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuru4ActionPerformed
        // TODO add your handling code here:
        DataGuru a = new DataGuru();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnGuru4ActionPerformed

    private void btnMapel4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapel4ActionPerformed
        // TODO add your handling code here:
        DataMapel a = new DataMapel();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnMapel4ActionPerformed

    private void btnSiswa4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswa4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSiswa4ActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_btnHapusActionPerformed

    private void inputIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inputIdActionPerformed
        // TODO add your handling code here:
        String id;
        id = (String) inputId.getSelectedItem();
        if (id != null) {
            try {
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE id_user = ? AND level != 'petugas'");
                ps.setString(1, id);
                ResultSet r = ps.executeQuery();
                while (r.next()) {
                    Inputusername.setText(r.getString("username"));
                    InputPassword.setText(r.getString("password"));
                }
                System.out.println( "BERHASIL mengambil data siswa");
            } catch (Exception e) {
                System.out.println( "GAGAL mengambil data siswa : "+ e);
            }
        }else{
            Inputusername.setText("");
            InputPassword.setText("");
        }
    }//GEN-LAST:event_inputIdActionPerformed

    private void btnAcakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcakActionPerformed
        // TODO add your handling code here:
        String acak;
        acak = FunctionLib.generateRandomPassword(10);
        InputPassword.setText(acak);
    }//GEN-LAST:event_btnAcakActionPerformed

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
        ubahData();
    }//GEN-LAST:event_btnUbahActionPerformed

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked
        // TODO add your handling code here:
        int data = tableUser.getSelectedRow();
        
        String kode;
        kode = (String) tm.getValueAt(data, 0);
        inputId.setSelectedItem(kode);
    }//GEN-LAST:event_tableUserMouseClicked

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
            java.util.logging.Logger.getLogger(DataPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InputPassword;
    private javax.swing.JTextField Inputusername;
    private javax.swing.JButton btnAcak;
    private javax.swing.JButton btnGuru4;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnJurusan4;
    private javax.swing.JButton btnKelas4;
    private javax.swing.JButton btnMapel4;
    private javax.swing.JButton btnSiswa4;
    private javax.swing.JButton btnUbah;
    private javax.swing.JButton btnuser4;
    private javax.swing.JComboBox<String> inputId;
    private javax.swing.JComboBox<String> inputLevel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton menuAwal4;
    private javax.swing.JPanel sideBar4;
    private javax.swing.JTable tableUser;
    // End of variables declaration//GEN-END:variables
}
