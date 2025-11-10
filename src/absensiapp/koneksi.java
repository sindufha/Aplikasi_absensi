/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package absensiapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author siti novi triana
 */
public class koneksi {
    
    private static Connection mysqlconfig;
    
    
    public static Connection konek(){
        //url koneksi ke database; jdbc:mysql://[host]:[port]/[nama_database]
        try {
            String url = "jdbc:mysql://localhost:3306/absensi_sdi";

            //username database
            String user = "root";

            //password database
            String pass = "";

//            //membuka koneksi ke database dan menyimpannya kevariabel mysqlconfig
            mysqlconfig = DriverManager.getConnection(url, user, pass);
        } catch (SQLException sQLException) {
            //menampilkan pesan error jika koneksi gagal
            System.err.println(sQLException.getMessage());
        }
        //mengembalikan objek koneksi (bisa null jika gagal)
        return mysqlconfig;
    }
    
}
