package Model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Koneksi {
    public Connection con;
    public Statement stm;
    
    public void koneksi() {
       try{
            String url ="jdbc:mysql://localhost/uas_31210053";
            String user="root";
            String pass="";
            Class.forName("com.mysql.cj.jdbc.Driver"); 
            con = DriverManager.getConnection(url, user, pass);
            stm = con.createStatement();
            System.out.println("koneksi berhasil;");
       } catch (Exception e){
//          System.err.println("koneksi gagal " + e.getMessage());
            e.printStackTrace();
       }
    }
}
