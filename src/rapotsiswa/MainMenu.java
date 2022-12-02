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
public class MainMenu extends javax.swing.JFrame {
    String username, level;
    Connection conn;
    DefaultTableModel tm;
    /**
     * Creates new form MainMenu
     */
    public MainMenu() {
        initComponents();
        connectDB();
        refreshTableSiswa();
        refreshTableMapel();
        refreshTableGuru();
        refreshTableJurusan();
        refreshTableUser();
        refreshTableKelas();
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
    
    private void refreshTableSiswa() {
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
    
    private void refreshTableMapel(){
        tm = new DefaultTableModel(null, new Object[] { "Kode Mapel", "Nama Mapel" });
        tableMapel.setModel(tm);
        tm.getDataVector().removeAllElements();

        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM mapel");
            ResultSet r = s.executeQuery();
            while(r.next()) {
                Object[] data = {
                r.getString(1),
                r.getString(2)
                };
           
            tm.addRow(data);
            
            };
        } catch (Exception e) {
            System.out.println("ERROR QUERY KE DATABASE : \n"+e+"\n\n");
        }
    }
    
    private void refreshTableGuru(){
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
    
    private void refreshTableKelas(){
        tm = new DefaultTableModel(null, new Object[] { "Kode Kelas", "Nama Kelas" });
        TableKelas.setModel(tm);
        tm.getDataVector().removeAllElements();

        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM kelas");
            ResultSet r = s.executeQuery();
            while(r.next()) {
                Object[] data = {
                r.getString(1),
                r.getString(2)
                };
           
            tm.addRow(data);
            
            };
        } catch (Exception e) {
            System.out.println("ERROR QUERY KE DATABASE : \n"+e+"\n\n");
        }
    }
    
    private void refreshTableJurusan(){
        tm = new DefaultTableModel(null, new Object[] { "Kode Jurusan", "Nama Jurusan" });
        Tabeljurusan.setModel(tm);
        tm.getDataVector().removeAllElements();

        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM jurusan");
            ResultSet r = s.executeQuery();
            while(r.next()) {
                Object[] data = {
                r.getString(1),
                r.getString(2)
                };
           
            tm.addRow(data);
            
            };
        } catch (Exception e) {
            System.out.println("ERROR QUERY KE DATABASE : \n"+e+"\n\n");
        }
    }
    
    private void refreshTableUser(){
        tm = new DefaultTableModel(null, new Object[] { "ID user", "Username", "Password", "Level" });
        tableUser.setModel(tm);
        tm.getDataVector().removeAllElements();
        try {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM user");
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
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        sideBar = new javax.swing.JPanel();
        btnSiswa = new javax.swing.JButton();
        btnMapel = new javax.swing.JButton();
        btnGuru = new javax.swing.JButton();
        btnKelas = new javax.swing.JButton();
        btnJurusan = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        btnuser = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelSiswa = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMapel = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabelguru = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        TableKelas = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        Tabeljurusan = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

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

        btnLogout.setFont(new java.awt.Font("SansSerif", 0, 11)); // NOI18N
        btnLogout.setText("Logout");
        btnLogout.setActionCommand("Data Guru");
        btnLogout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
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
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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

        tableMapel.setModel(new javax.swing.table.DefaultTableModel(
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
        tableMapel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMapelMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tableMapel);

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
        jScrollPane3.setViewportView(Tabelguru);

        TableKelas.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        TableKelas.setModel(new javax.swing.table.DefaultTableModel(
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
        TableKelas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableKelasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(TableKelas);

        Tabeljurusan.setModel(new javax.swing.table.DefaultTableModel(
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
        Tabeljurusan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabeljurusanMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(Tabeljurusan);

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
        jScrollPane6.setViewportView(tableUser);

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel1.setText("Data Siswa");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setText("Data Mapel");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setText("Data Guru");

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel4.setText("Data Kelas");

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel6.setText("Data Jurusan");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel7.setText("Data User");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(274, 274, 274))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(251, 251, 251)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(385, 385, 385))
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiswaActionPerformed
        // TODO add your handling code here:
        DataSiswa a = new DataSiswa();
        a.setVisible(true);
        a.setEnabled(true);
        this.hide();
    }//GEN-LAST:event_btnSiswaActionPerformed

    private void btnMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapelActionPerformed
        // TODO add your handling code here:
        DataMapel a = new DataMapel();
        a.setVisible(true);
        a.setEnabled(true);
        this.hide();
    }//GEN-LAST:event_btnMapelActionPerformed

    private void btnGuruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuruActionPerformed
        // TODO add your handling code here:
        DataGuru a = new DataGuru();
        a.setVisible(true);
        a.setEnabled(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnGuruActionPerformed

    private void btnKelasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelasActionPerformed
        // TODO add your handling code here:
        DataKelas a = new DataKelas();
        a.setVisible(true);
        a.setEnabled(true);
        this.hide();
    }//GEN-LAST:event_btnKelasActionPerformed

    private void btnJurusanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJurusanActionPerformed
        // TODO add your handling code here:
        DataJurusan a = new DataJurusan();
        a.setVisible(true);
        a.setEnabled(true);
        this.hide();
    }//GEN-LAST:event_btnJurusanActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        int result = JOptionPane.showConfirmDialog(null, "Konfirmasi Logout", "Logout" ,JOptionPane.YES_NO_OPTION );
        if (result == JOptionPane.YES_OPTION) {
            LoginForm l = new LoginForm();
        l.setVisible(true);
        this.setVisible(false);
        }
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuserActionPerformed
        // TODO add your handling code here:
        DataPengguna a = new DataPengguna();
        a.setVisible(true);
        a.setEnabled(true);
        this.hide();
    }//GEN-LAST:event_btnuserActionPerformed

    private void tabelSiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelSiswaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelSiswaMouseClicked

    private void tableMapelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMapelMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tableMapelMouseClicked

    private void TabelguruMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelguruMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabelguruMouseClicked

    private void TableKelasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TableKelasMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TableKelasMouseClicked

    private void TabeljurusanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabeljurusanMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_TabeljurusanMouseClicked

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Tabelguru;
    private javax.swing.JTable Tabeljurusan;
    private javax.swing.JTable TableKelas;
    private javax.swing.JButton btnGuru;
    private javax.swing.JButton btnJurusan;
    private javax.swing.JButton btnKelas;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnMapel;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JButton btnuser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel sideBar;
    private javax.swing.JTable tabelSiswa;
    private javax.swing.JTable tableMapel;
    private javax.swing.JTable tableUser;
    // End of variables declaration//GEN-END:variables
}
