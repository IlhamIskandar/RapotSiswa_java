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
public class DataMapel extends javax.swing.JFrame {
    Connection conn;
    DefaultTableModel tm;
    /**
     * Creates new form DataMapel
     */
    public DataMapel() {
        initComponents();
        connectDB();
        refreshTable();
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
    
    private void tambahData(){
        String namaMapel, kodeMapel;
        namaMapel = InputNmMapel.getText();
        kodeMapel = InputKdMapel.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO mapel VALUES(?, ?)");
            ps.setString(1, kodeMapel);
            ps.setString(2, namaMapel);
            ps.executeUpdate();
            
            refreshTable();
            InputKdMapel.setText("");
            InputNmMapel.setText("");
        } catch (Exception e) {
            System.out.println("GAGAL EKSEKUSI QUERY" +e);
            JOptionPane.showMessageDialog(null, "Gagal Menambah Data");
        }
    }
    
    private void hapusData(){
        String kodeMapel;
        kodeMapel = InputKdMapel.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM mapel WHERE kode_mapel = ?");
            ps.setString(1, kodeMapel);
            ps.executeUpdate();
            
            refreshTable();
            InputKdMapel.setText("");
            InputNmMapel.setText("");
        } catch (Exception e) {
            System.out.println("GAGAL EKSEKUSI QUERY"+e);
            JOptionPane.showMessageDialog(null, "Gagal Menghapus Data");
            
        }
    }
    
    private void ubahData(){
        String namaMapel, kodeMapel;
        kodeMapel = InputKdMapel.getText();
        namaMapel = InputNmMapel.getText();
        
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE mapel SET nama_mapel = ? WHERE kode_mapel = ?");
            ps.setString(1, namaMapel);
            ps.setString(2, kodeMapel);
            ps.executeUpdate();
            
            refreshTable();
            InputKdMapel.setText("");
            InputNmMapel.setText("");
        } catch (Exception e) {
            System.out.println("GAGAL EKSEKUSI QUERY"+e);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        tableMapel = new javax.swing.JTable();
        KdMapel = new javax.swing.JLabel();
        NmMapel = new javax.swing.JLabel();
        InputKdMapel = new javax.swing.JTextField();
        InputNmMapel = new javax.swing.JTextField();
        TambahBtn = new javax.swing.JButton();
        ubahBtn = new javax.swing.JButton();
        hapusBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

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
                    .addComponent(btnuser, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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
        jScrollPane1.setViewportView(tableMapel);

        KdMapel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        KdMapel.setText("Kode Mata Pelajaran");

        NmMapel.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        NmMapel.setText("Nama Mata Pelajaran");

        InputKdMapel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputKdMapelActionPerformed(evt);
            }
        });

        TambahBtn.setBackground(new java.awt.Color(0, 255, 0));
        TambahBtn.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        TambahBtn.setForeground(new java.awt.Color(255, 255, 255));
        TambahBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/add-user (1).png"))); // NOI18N
        TambahBtn.setText("Tambah");
        TambahBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahBtnActionPerformed(evt);
            }
        });

        ubahBtn.setBackground(new java.awt.Color(255, 204, 0));
        ubahBtn.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        ubahBtn.setForeground(new java.awt.Color(255, 255, 255));
        ubahBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/pencil.png"))); // NOI18N
        ubahBtn.setText("Ubah");
        ubahBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ubahBtnActionPerformed(evt);
            }
        });

        hapusBtn.setBackground(new java.awt.Color(255, 0, 0));
        hapusBtn.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        hapusBtn.setForeground(new java.awt.Color(255, 255, 255));
        hapusBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/bin (3).png"))); // NOI18N
        hapusBtn.setText("Hapus");
        hapusBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusBtnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setText("Data Mata Pelajaran");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(sideBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 424, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(KdMapel)
                                            .addComponent(NmMapel))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(InputKdMapel, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
                                            .addComponent(InputNmMapel))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TambahBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(ubahBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(hapusBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 595, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sideBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(KdMapel)
                    .addComponent(InputKdMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TambahBtn))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NmMapel)
                    .addComponent(InputNmMapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ubahBtn))
                .addGap(18, 18, 18)
                .addComponent(hapusBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
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
        DataSiswa a = new DataSiswa();
        a.setVisible(true);
        a.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnSiswaActionPerformed

    private void btnMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMapelActionPerformed
        // TODO add your handling code here:
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

    private void TambahBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahBtnActionPerformed
        // TODO add your handling code here:
        tambahData();
    }//GEN-LAST:event_TambahBtnActionPerformed

    private void ubahBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ubahBtnActionPerformed
        // TODO add your handling code here:
        ubahData();
    }//GEN-LAST:event_ubahBtnActionPerformed

    private void hapusBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusBtnActionPerformed
        // TODO add your handling code here:
        hapusData();
    }//GEN-LAST:event_hapusBtnActionPerformed

    private void InputKdMapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputKdMapelActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_InputKdMapelActionPerformed

    private void btnuserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnuserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnuserActionPerformed

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
            java.util.logging.Logger.getLogger(DataMapel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DataMapel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DataMapel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DataMapel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DataMapel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField InputKdMapel;
    private javax.swing.JTextField InputNmMapel;
    private javax.swing.JLabel KdMapel;
    private javax.swing.JLabel NmMapel;
    private javax.swing.JButton TambahBtn;
    private javax.swing.JButton btnGuru;
    private javax.swing.JButton btnJurusan;
    private javax.swing.JButton btnKelas;
    private javax.swing.JButton btnMapel;
    private javax.swing.JButton btnSiswa;
    private javax.swing.JButton btnuser;
    private javax.swing.JButton hapusBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton menuAwal;
    private javax.swing.JPanel sideBar;
    private javax.swing.JTable tableMapel;
    private javax.swing.JButton ubahBtn;
    // End of variables declaration//GEN-END:variables
}
