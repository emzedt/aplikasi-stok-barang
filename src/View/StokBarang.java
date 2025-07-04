package View;

import Model.Koneksi; //menghubungkan class Koneksi dari package Model ke StokBarang.java
import java.sql.Connection; //resource menggunakan Connection
import java.sql.ResultSet; //resource menggunakan ResultSet
import java.sql.Statement; //resource menggunakan Statement
import javax.swing.JOptionPane; //resource menggunakan JOptionPane
import javax.swing.table.DefaultTableModel; //resource menggunakan DefaultTableModel

public class StokBarang extends javax.swing.JFrame {

    String sql; //variabel tambahan untuk menampung syntax memanggil data dari database tbdatabarang
    Connection con; //membangun koneksi dengan sumber data
    Statement stm; //antarmuka yang mewakili pernyataan SQL
    ResultSet rs; //hasil Statement dari execute dan excuteQuery
    String diskon, totalhargajual, totalhargabeli; //variabel yang akan dimasukkan pada model.addRow
    int inihargabeli, iniquantity, inihargajual, inidiskon, thargajual, thargabeli; //int buat operasi
    String shargabeli, squantity; //variabel penerima value dari rs.getstring
    
    public StokBarang() {
        initComponents(); //method yang dihasilkan oleh NetBeans swing designer. Ini menjabarkan komponen dan menetapkan nilai defaultnya
        Koneksi DB = new Koneksi(); //objek dari package model class Koneksi
        DB.koneksi(); //method koneksi
        con = DB.con; //untuk membuat variabel con di JFrame StokBarang memiliki fungsi yang sama seperti di Koneksi.java
        stm = DB.stm; //untuk membuat variabel stm di JFrame StokBarang memiliki fungsi yang sama seperti di Koneksi.java
    }
    
    public void clearTable(){
        TabelStokBarang.setModel(new DefaultTableModel(null,new String[]{"Kode", "Nama Barang", "Kd_Satuan", "Satuan"
                , "Harga Beli", "Harga Jual", "Quantity", "Diskon", "Total Harga Jual", "Total Harga Beli"})); 
        //membersihkan Tabel berdasarkan title kolom
    }
    
    public void clear(){
        Kode1.setText(""); //membuat text field Kode1 kosong
        Kode2.setText(""); //membuat text field Kode2 kosong
        txtGrandTotalJual.setText(""); //membuat text field txtGrandTotalJual kosong
        txtGrandTotalBeli.setText(""); //membuat text field txtGrandTotalBeli kosong
    }
    
    private void showTable(){ //method menampilkan data-data dari beberapa variabel dan database tbdatabarang ke JTable TabelStokBarang
        DefaultTableModel model = (DefaultTableModel) TabelStokBarang.getModel(); //DefaultTableModel digunakan untuk memberikan header dan data pada kolom dan baris tabel 
        try
        {
            sql= "SELECT * FROM tbdatabarang GROUP BY 1"; //mengambil data dari tbdatabarang berdasarkan kode
            rs = stm.executeQuery(sql);/* Metode ini digunakan untuk mengeksekusi pernyataan yang mengembalikan data yang terstruktur ke dalam baris, 
                                yang masing-masing berisi informasi tentang sesuatu (contoh pilih). Ini mengembalikan objek dari kelas ResultSet. */
            while(rs.next()) //Mengembalikan nilai berikutnya jika cocok dengan pola yang dibuat dari string yang ditentukan.
            {   
                shargabeli = rs.getString("hargabeli"); //variabel shargabeli menampung value hargabeli dari tbdatabarang
                inihargabeli = Integer.parseInt(shargabeli); //variabel inihargabeli mengubah tipe data shargabeli menjadi int agar bisa dioperasikan
                inihargajual = (int) (inihargabeli+(0.2*inihargabeli)); //mencari value Harga Jual dengan cara inihargabeli+(0.2inihargabeli), kemudian ditampung di inihargajual
                inidiskon = (int) (0.1*inihargabeli); //mencari value Diskon dengan cara 10% dari inihargabeli, hasilnya ditampung di inidiskon
                squantity = rs.getString("quantity"); //variabel squantity menampung value quantity dari tbdatabarang
                iniquantity = Integer.parseInt(squantity); //mengubah squantity ke integer agar dapat dioperasikan, dan ditampung di variabel iniquantity
                thargajual = iniquantity*inihargajual; //mencari value Total Harga Jual dengan cara iniquantity*inihargajual, hasilnya ditampung di variabel thargajual
                thargabeli = iniquantity*(inihargabeli-inidiskon); //mencari value Total Harga beli dengan cara iniquantity*(inihargabeli-inidiskon), hasilnya ditampung di variabel thargabeli
                
                diskon = String.valueOf(inidiskon); //mengubah value inidiskon menjadi string, ditampung oleh variabel diskon
                totalhargajual = String.valueOf(thargajual); //mengubah value thargajual menjadi string, ditampung oleh variabel totalhargajual
                totalhargabeli = String.valueOf(thargabeli); //mengubah value thargabeli menjadi string, ditampung oleh variabel totalhargabeli
                
                model.addRow(new Object[]{rs.getString("kode"), rs.getString("namabarang"), rs.getString("kd_satuan")
                        , rs.getString("satuan"), rs.getString("hargabeli"), inihargajual, rs.getString("quantity")
                        , diskon, totalhargajual, totalhargabeli});
                //menambahkan data ke JTable TabelStokBarang secara berurut
                
            }
        } catch (Exception e) //akan aktif jika try gagal
          {
            JOptionPane.showMessageDialog(this, e.getMessage()); //Jika ada error di method showTable, maka akan menampilkan popup errornya dimana
          }
    }
    
    private void showTableTertentu(){ //method menampilkan data-data dari beberapa variabel dan database tbdatabarang berdasarkan inputan user ke JTable TabelStokBarang
        DefaultTableModel model = (DefaultTableModel) TabelStokBarang.getModel(); //DefaultTableModel digunakan untuk memberikan header dan data pada kolom dan baris tabel 
        try
        {
            sql= "SELECT * FROM tbdatabarang WHERE kode BETWEEN '"+Kode1.getText()+"' AND '"+Kode2.getText()+"'"; //mengambil value tbdatabarang berdasarkan kode tertentu sampai kode tertentu dari inputan user di text field Kode1, Kode2 ke JTable TabelStokBarang.
            rs = stm.executeQuery(sql);/* Metode ini digunakan untuk mengeksekusi pernyataan yang mengembalikan data yang terstruktur ke dalam baris, 
                                yang masing-masing berisi informasi tentang sesuatu (contoh pilih). Ini mengembalikan objek dari kelas ResultSet. */

            while(rs.next()) //Mengembalikan nilai berikutnya jika cocok dengan pola yang dibuat dari string yang ditentukan.
            {
                shargabeli = rs.getString("hargabeli"); //variabel shargabeli menampung value hargabeli dari tbdatabarang
                inihargabeli = Integer.parseInt(shargabeli); //variabel inihargabeli mengubah tipe data shargabeli menjadi int agar bisa dioperasikan
                inihargajual = (int) (inihargabeli+(0.2*inihargabeli)); //mencari value Harga Jual dengan cara inihargabeli+(0.2inihargabeli), kemudian ditampung di inihargajual
                inidiskon = (int) (0.1*inihargabeli); //mencari value Diskon dengan cara 10% dari inihargabeli, hasilnya ditampung di inidiskon
                squantity = rs.getString("quantity"); //variabel squantity menampung value quantity dari tbdatabarang
                iniquantity = Integer.parseInt(squantity); //mengubah squantity ke integer agar dapat dioperasikan, dan ditampung di variabel iniquantity
                thargajual = iniquantity*inihargajual; //mencari value Total Harga Jual dengan cara iniquantity*inihargajual, hasilnya ditampung di variabel thargajual
                thargabeli = iniquantity*(inihargabeli-inidiskon); //mencari value Total Harga beli dengan cara iniquantity*(inihargabeli-inidiskon), hasilnya ditampung di variabel thargabeli
                
                diskon = String.valueOf(inidiskon); //mengubah value inidiskon menjadi string, ditampung oleh variabel diskon
                totalhargajual = String.valueOf(thargajual); //mengubah value thargajual menjadi string, ditampung oleh variabel totalhargajual
                totalhargabeli = String.valueOf(thargabeli); //mengubah value thargabeli menjadi string, ditampung oleh variabel totalhargabeli
                
                model.addRow(new Object[]{rs.getString("kode"), rs.getString("namabarang"), rs.getString("kd_satuan")
                        , rs.getString("satuan"), rs.getString("hargabeli"), inihargajual, rs.getString("quantity")
                        , diskon, totalhargajual, totalhargabeli});
                //menambahkan data ke JTable TabelStokBarang secara berurut
            }
        } catch (Exception e) //akan aktif jika try gagal
          {
            JOptionPane.showMessageDialog(this, e.getMessage()); //Jika ada error di method showTableTertentu, maka akan menampilkan popup errornya dimana
          }
    }
    
    public void GTotalJual(){ //method pada txtGrandTotalJual untuk menjumlahkan kolom Total Harga Jual JTable TabelStokBarang
        int sum = 0; //variabel bantu sum yang telah diinisialisasi 0
        for (int i = 0; i<TabelStokBarang.getRowCount(); i++){ //for loop yang akan berhenti ketika jumlah baris dari JTable sudah tidak ada lagi
            sum = (sum+Integer.parseInt(TabelStokBarang.getValueAt((int)i, 8).toString())); //value sum + value kolom[i] Total Harga Jual, hasilnya ditampung oleh sum, loop terus hingga selesai
        }
            txtGrandTotalJual.setText(Integer.toString((int)sum).toString()); //txtGrandTotalJual menampilkan hasil akhir value sum
    }
    
    public void GTotalBeli(){ //method pada txtGrandTotalBeli untuk menjumlahkan kolom Total Harga Beli JTable TabelStokBarang
        int sum = 0; //variabel bantu sum yang telah diinisialisasi 0
        for (int i = 0; i<TabelStokBarang.getRowCount(); i++){ //for loop yang akan berhenti ketika jumlah baris dari JTable sudah tidak ada lagi
            sum = (sum+Integer.parseInt(TabelStokBarang.getValueAt((int)i, 9).toString())); //value sum + value kolom[i] Total Harga Beli, hasilnya ditampung oleh sum, loop terus hingga selesai
        }
            txtGrandTotalBeli.setText(Integer.toString((int)sum).toString()); //txtGrandTotalBeli menampilkan hasil akhir value sum
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        ButtonCetakSemua = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        Kode1 = new javax.swing.JTextField();
        Kode2 = new javax.swing.JTextField();
        ButtonCetakKode = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        ButtonClear = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelStokBarang = new javax.swing.JTable();
        txtGrandTotalJual = new javax.swing.JTextField();
        txtGrandTotalBeli = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        MenuHome = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        MenuItemDataPengguna = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        MenuItemLogOut = new javax.swing.JMenuItem();
        MenuIdentitasPerusahaan = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        MenuItemSatuanBarang = new javax.swing.JMenuItem();
        MenuItemDataBarang = new javax.swing.JMenuItem();
        MenuItemStokBarang = new javax.swing.JMenuItem();
        MenuLaporan = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stok Barang");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/Stok Barang Real.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(97, 109, 126));

        jPanel4.setBackground(new java.awt.Color(98, 111, 120));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true), "Navigasi : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16))); // NOI18N

        ButtonCetakSemua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-print-24.png"))); // NOI18N
        ButtonCetakSemua.setText("Cetak Semua");
        ButtonCetakSemua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCetakSemuaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("Tampilkan Data Dari Kode: ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Sampai Data Kode:");

        ButtonCetakKode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-print-24.png"))); // NOI18N
        ButtonCetakKode.setText("Cetak Kode");
        ButtonCetakKode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonCetakKodeActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setPreferredSize(new java.awt.Dimension(0, 1));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        ButtonClear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-broom-24.png"))); // NOI18N
        ButtonClear.setText("Clear");
        ButtonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ButtonClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Kode2, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(Kode1, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(63, 63, 63))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ButtonCetakSemua)
                        .addGap(144, 144, 144))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ButtonCetakKode)
                        .addGap(153, 153, 153))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(ButtonClear)
                        .addGap(173, 173, 173))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(ButtonCetakSemua)
                .addGap(10, 10, 10)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Kode1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Kode2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ButtonCetakKode)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ButtonClear)
                .addGap(15, 15, 15))
        );

        TabelStokBarang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Nama Barang", "Kd_Satuan", "Satuan", "Harga Beli", "Harga Jual", "Quantity", "Diskon", "Total Harga Jual", "Total Harga Beli"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(TabelStokBarang);

        jLabel4.setText("Total");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("© 2022 PT Jaya");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(663, 663, 663))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel4)
                            .addGap(36, 36, 36)
                            .addComponent(txtGrandTotalJual, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtGrandTotalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addGap(77, 77, 77)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1294, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(493, 493, 493)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtGrandTotalJual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGrandTotalBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MenuHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-home-48.png"))); // NOI18N
        MenuHome.setText("Home");
        MenuHome.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        MenuHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuHomeMouseClicked(evt);
            }
        });
        MenuHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuHomeActionPerformed(evt);
            }
        });
        jMenuBar1.add(MenuHome);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-user-48.png"))); // NOI18N
        jMenu5.setText("Pengguna");
        jMenu5.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        MenuItemDataPengguna.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-document-16.png"))); // NOI18N
        MenuItemDataPengguna.setText("Data Pengguna");
        MenuItemDataPengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuItemDataPenggunaMouseClicked(evt);
            }
        });
        MenuItemDataPengguna.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemDataPenggunaActionPerformed(evt);
            }
        });
        jMenu5.add(MenuItemDataPengguna);
        jMenu5.add(jSeparator3);

        MenuItemLogOut.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-log-out-16.png"))); // NOI18N
        MenuItemLogOut.setText("Log Out");
        MenuItemLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemLogOutActionPerformed(evt);
            }
        });
        jMenu5.add(MenuItemLogOut);

        jMenuBar1.add(jMenu5);

        MenuIdentitasPerusahaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-office-building-32.png"))); // NOI18N
        MenuIdentitasPerusahaan.setText("Identitas Perusahaan");
        MenuIdentitasPerusahaan.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        MenuIdentitasPerusahaan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuIdentitasPerusahaanMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuIdentitasPerusahaan);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-box-48.png"))); // NOI18N
        jMenu3.setText("Barang");
        jMenu3.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N

        MenuItemSatuanBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-box-16.png"))); // NOI18N
        MenuItemSatuanBarang.setText("Satuan Barang");
        MenuItemSatuanBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemSatuanBarangActionPerformed(evt);
            }
        });
        jMenu3.add(MenuItemSatuanBarang);

        MenuItemDataBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-inventory-16.png"))); // NOI18N
        MenuItemDataBarang.setText("Data Barang");
        MenuItemDataBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemDataBarangActionPerformed(evt);
            }
        });
        jMenu3.add(MenuItemDataBarang);

        MenuItemStokBarang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-goods-16.png"))); // NOI18N
        MenuItemStokBarang.setText("Stok Barang");
        MenuItemStokBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MenuItemStokBarangActionPerformed(evt);
            }
        });
        jMenu3.add(MenuItemStokBarang);

        jMenuBar1.add(jMenu3);

        MenuLaporan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/icons8-profit-report-48.png"))); // NOI18N
        MenuLaporan.setText("Laporan");
        MenuLaporan.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        MenuLaporan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MenuLaporanMouseClicked(evt);
            }
        });
        jMenuBar1.add(MenuLaporan);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ButtonCetakSemuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCetakSemuaActionPerformed
        clearTable(); //method membersihkan JTable TabelStokBarang
        showTable(); //method menampilkan semua data beberapa variabel dan tbdatabarang dan ke JTable TabelStokBarang
        GTotalJual(); //method menambahkan kolom Total Harga Jual dan akan ditampilkan di txtGrandTotalJual
        GTotalBeli(); //method menambahkan kolom Total Harga Beli dan akan ditampilkan di txtGrandTotalBeli
    }//GEN-LAST:event_ButtonCetakSemuaActionPerformed

    private void ButtonCetakKodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonCetakKodeActionPerformed
        clearTable(); //method membersihkan JTable TabelStokBarang
        showTableTertentu(); //method menampilkan data-data dari beberapa variabel dan database tbdatabarang berdasarkan inputan user ke JTable TabelStokBarang
        GTotalJual(); //method menambahkan kolom Total Harga Jual dan akan ditampilkan di txtGrandTotalJual
        GTotalBeli(); //method menambahkan kolom Total Harga Beli dan akan ditampilkan di txtGrandTotalBeli
    }//GEN-LAST:event_ButtonCetakKodeActionPerformed

    private void MenuHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuHomeMouseClicked
        new Dashboard().setVisible(true); //memunculkan JFrame Dashboard
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuHomeMouseClicked

    private void MenuHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuHomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuHomeActionPerformed

    private void MenuItemDataPenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuItemDataPenggunaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MenuItemDataPenggunaMouseClicked

    private void MenuItemDataPenggunaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemDataPenggunaActionPerformed
        new DataPengguna().setVisible(true);
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuItemDataPenggunaActionPerformed

    private void MenuItemLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemLogOutActionPerformed
        int response = JOptionPane.showConfirmDialog(this, "Apakah Anda ingin Log Out?", "Confirm", JOptionPane.YES_NO_OPTION
                , JOptionPane.QUESTION_MESSAGE);
        //`->menampilkan pop up pertanyaan Apakah Anda ingin Log Out?
        if(response==JOptionPane.YES_OPTION){ //Jika memilih yes
            new Login().setVisible(true); //method yang membuat JFrame Login muncul di layar
            this.dispose(); //menutup JFrame StokBarang
        } else if(response==JOptionPane.NO_OPTION){ //Jika memilih no
            //tidak terjadi apa-apa
        } else if(response==JOptionPane.CLOSED_OPTION){ //jika close
            //tidak terjadi apa-apa
        }
    }//GEN-LAST:event_MenuItemLogOutActionPerformed

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5ActionPerformed

    private void MenuIdentitasPerusahaanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuIdentitasPerusahaanMouseClicked
        new IdentitasPerusahaan().setVisible(true); //memunculkan JFrame IdentitasPerusahaan
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuIdentitasPerusahaanMouseClicked

    private void MenuItemSatuanBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemSatuanBarangActionPerformed
        new SatuanBarang().setVisible(true); //memunculkan JFrame SatuanBarang
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuItemSatuanBarangActionPerformed

    private void MenuItemDataBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemDataBarangActionPerformed
        new DataBarang().setVisible(true); //memunculkan JFrame DataBarang
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuItemDataBarangActionPerformed

    private void MenuItemStokBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MenuItemStokBarangActionPerformed
        new StokBarang().setVisible(true); //memunculkan JFrame StokBarang baru
        this.dispose(); //menutup JFrame StokBarang lama
    }//GEN-LAST:event_MenuItemStokBarangActionPerformed

    private void MenuLaporanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MenuLaporanMouseClicked
        new FrameReport().setVisible(true); //memunculkan JFrame FrameReport
        this.dispose(); //menutup JFrame StokBarang
    }//GEN-LAST:event_MenuLaporanMouseClicked

    private void ButtonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButtonClearActionPerformed
        clear(); //membersihkan text field
        clearTable(); //membersihkan JTable
    }//GEN-LAST:event_ButtonClearActionPerformed

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
            java.util.logging.Logger.getLogger(StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StokBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StokBarang().setVisible(true); //memunculkan JFrame StokBarang di layar
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ButtonCetakKode;
    private javax.swing.JButton ButtonCetakSemua;
    private javax.swing.JButton ButtonClear;
    private javax.swing.JTextField Kode1;
    private javax.swing.JTextField Kode2;
    private javax.swing.JMenu MenuHome;
    private javax.swing.JMenu MenuIdentitasPerusahaan;
    private javax.swing.JMenuItem MenuItemDataBarang;
    private javax.swing.JMenuItem MenuItemDataPengguna;
    private javax.swing.JMenuItem MenuItemLogOut;
    private javax.swing.JMenuItem MenuItemSatuanBarang;
    private javax.swing.JMenuItem MenuItemStokBarang;
    private javax.swing.JMenu MenuLaporan;
    private javax.swing.JTable TabelStokBarang;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTextField txtGrandTotalBeli;
    private javax.swing.JTextField txtGrandTotalJual;
    // End of variables declaration//GEN-END:variables
}
