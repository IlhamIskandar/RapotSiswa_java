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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author acer
 */
public class LaporanNilaiSiswa extends javax.swing.JFrame {

    private String nis, inptjns;
    Connection conn;
    DefaultTableModel tm;
//    LoginForm lf = new LoginForm();
    /**
     * Creates new form LaporanNilaiSiswa
     */
    public LaporanNilaiSiswa() {
        initComponents();
        connectDB();
        refreshTable();
        dataSiswa();
        getJenisPenilaian();
        InputJenisPenilaian.setSelectedIndex(-1);
    }
    
    public void setNIS(String n){
        this.nis = n;
    }
    
    public final String getNIS(){
        return nis;
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
    
    private void dataSiswa(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT siswa.nis, siswa.nama, kelas.nama_kelas, jurusan.nama_jurusan FROM siswa, kelas, jurusan WHERE siswa.kode_kelas = kelas.kode_kelas AND siswa.kode_jurusan = jurusan.kode_jurusan AND nis = ?");
            s.setString(1, getNIS());
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                inputNama.setText(r.getString("nama"));
                InputNIS.setText(r.getString("nis"));
                InputKelas.setText(r.getString("nama_kelas"));
                InputJurusan.setText(r.getString("nama_jurusan"));
            }
            System.out.println( "BERHASIL mengambil data siswa");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data siswa : "+ e);
        }
    }
    
    private void getJenisPenilaian(){
        try {
            PreparedStatement s = conn.prepareStatement("SELECT jenis, id_penilaian FROM jenis_penilaian");
            ResultSet r = s.executeQuery();
            while (r.next()) {    
                
                InputJenisPenilaian.addItem(r.getString("jenis"));
            }
            System.out.println( "BERHASIL mengambil data jenis penilaian");
        } catch (Exception e) {
            System.out.println( "GAGAL mengambil data jenis penilaian : "+ e);
        }
    }
    
    private void refreshTable() {
        inptjns = (String) InputJenisPenilaian.getSelectedItem();
        tm = new DefaultTableModel(null, new Object[] {"ID","NIS", "Nama", "Kelas","Mapel", "Jenis Penilaian", "Nilai"});
        TabelNilai.setModel(tm);
        tm.getDataVector().removeAllElements();
        System.out.println(inptjns);
        if (inptjns != null) {
            try {
                PreparedStatement p = conn.prepareStatement("SELECT nilai.id_nilai, nilai.nis, siswa.nama, CONCAT(siswa.kode_kelas, siswa.kode_jurusan), mapel.nama_mapel, jenis_penilaian.jenis, nilai.nilai FROM siswa, nilai, mapel, guru, jenis_penilaian WHERE siswa.nis = nilai.nis AND nilai.id_penilaian = jenis_penilaian.id_penilaian AND nilai.kode_guru = guru.kode_guru AND guru.kode_mapel = mapel.kode_mapel AND nilai.nis = ? AND jenis_penilaian.jenis = ? Group BY nilai.id_nilai");
                p.setString(1, getNIS());
                p.setString(2, inptjns);
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
                System.out.println("BERHASIL mengambil data nilai siswa");
            } catch (Exception e) {
                System.out.println("GAGAL mengambil data nilai siswa : "+ e);
            }
        }else{
            try {
                PreparedStatement p = conn.prepareStatement("SELECT nilai.id_nilai, nilai.nis, siswa.nama, CONCAT(siswa.kode_kelas, siswa.kode_jurusan), mapel.nama_mapel, jenis_penilaian.jenis, nilai.nilai FROM siswa, nilai, mapel, guru, jenis_penilaian WHERE siswa.nis = nilai.nis AND nilai.id_penilaian = jenis_penilaian.id_penilaian AND nilai.kode_guru = guru.kode_guru AND guru.kode_mapel = mapel.kode_mapel AND nilai.nis = ?  Group BY nilai.id_nilai ORDER BY nilai.id_penilaian");
                p.setString(1, getNIS());
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
                System.out.println("BERHASIL mengambil data nilai siswa");
            } catch (Exception e) {
                System.out.println("GAGAL mengambil data nilai siswa : "+ e);
            }
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        inputNama = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        InputNIS = new javax.swing.JLabel();
        InputKelas = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelNilai = new javax.swing.JTable();
        btnShow = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        InputJurusan = new javax.swing.JLabel();
        InputJenisPenilaian = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        lblJenisNilai = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/R1.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SMKN 1 KATAPANG");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Rekap Nilai Siswa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel2.setText("Nama");

        inputNama.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel4.setText("NIS");

        InputNIS.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        InputKelas.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel7.setText("Kelas");

        TabelNilai.setModel(new javax.swing.table.DefaultTableModel(
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
        TabelNilai.setSelectionBackground(new java.awt.Color(0, 102, 255));
        jScrollPane1.setViewportView(TabelNilai);

        btnShow.setBackground(new java.awt.Color(51, 153, 255));
        btnShow.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        btnShow.setForeground(new java.awt.Color(255, 255, 255));
        btnShow.setText("Tampilkan Data");
        btnShow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel8.setText("Jurusan");

        InputJurusan.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N

        InputJenisPenilaian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InputJenisPenilaianActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        jLabel9.setText("Jenis Penilaian");

        lblJenisNilai.setFont(new java.awt.Font("SansSerif", 1, 11)); // NOI18N
        lblJenisNilai.setEnabled(false);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(InputJenisPenilaian, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblJenisNilai, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                                .addGap(205, 205, 205))
                            .addComponent(inputNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(InputNIS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(InputKelas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(InputJurusan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnShow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnShow)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(inputNama, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(InputNIS, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(InputKelas, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(InputJurusan, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblJenisNilai, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(InputJenisPenilaian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel9)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowActionPerformed
        // TODO add your handling code here:
//        System.out.println(getNIS());
        InputJenisPenilaian.setSelectedIndex(-1);
        refreshTable();
        dataSiswa();
    }//GEN-LAST:event_btnShowActionPerformed

    private void InputJenisPenilaianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InputJenisPenilaianActionPerformed
        // TODO add your handling code here:
        String idPenilaian;
        idPenilaian = (String) InputJenisPenilaian.getSelectedItem();
        
        refreshTable();

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
            java.util.logging.Logger.getLogger(LaporanNilaiSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LaporanNilaiSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LaporanNilaiSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LaporanNilaiSiswa.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LaporanNilaiSiswa().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> InputJenisPenilaian;
    private javax.swing.JLabel InputJurusan;
    private javax.swing.JLabel InputKelas;
    private javax.swing.JLabel InputNIS;
    private javax.swing.JTable TabelNilai;
    private javax.swing.JButton btnShow;
    private javax.swing.JLabel inputNama;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblJenisNilai;
    // End of variables declaration//GEN-END:variables
}
