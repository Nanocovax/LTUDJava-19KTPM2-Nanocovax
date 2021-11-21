package nanocovax;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

public class Database {
    private static String password = ""; // mysql password
    public static Connection DBConnection()
    {
        Connection conn = null;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");

            conn = DriverManager.getConnection("jdbc:mysql://localhost/Nanocovax","root", password);
            System.out.println("Database is connected!");
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Cannot connect to DB - Error:" + e);
        }
        return conn;
    }

    public static boolean varifyLogin(String username,String pass)
    {
        boolean login=false;
        Connection conn=DBConnection();
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from TAIKHOAN where username = '"+ username +"' and password = '"+pass+"';");
            if (!rs.next())
                login=false;
            else {
                login=true;
                if (username.equals("admin")) {
                    statement.executeUpdate("update TAIKHOAN set tinhTrang = true where username = 'admin';");
                    System.out.println("Admin has been initialized!");
                }
            }

            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return login;
    }

    public static void main(String args[]) { }
}
