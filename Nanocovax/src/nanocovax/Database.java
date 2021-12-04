package nanocovax;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Database {
    private static String url = "jdbc:mysql://localhost/Nanocovax";
    private static String username = "root";
    private static String password = "Baokhuyen2001@";

    public static Connection DBConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("Database is connected!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot connect to Database - Error:" + e);
        }
        return conn;
    }

    public static void createAdmin(String id, String password) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            password = Encryption.encryptMD5(password);
            String sql = "insert into TAIKHOAN values('" + id + "', '" + password + "', 'admin', 'bt');";
            statement.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "The admin account has been created!");
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean varifyAdminInit() {
        Connection conn = DBConnection();
        boolean result = false;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from TAIKHOAN;");
            if (!rs.next())
                result = true;
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static int varifyLogin(String id, String password) {
        int result = -1;
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            password = Encryption.encryptMD5(password);
            ResultSet rs = statement.executeQuery("select tinhtrang from TAIKHOAN where id = '" + id + "' and phanquyen = 'nql';");
            if (rs.next() && rs.getString(1).equals("ckh")) {
                result = 3;
            } else {
                rs = statement.executeQuery("select phanquyen from TAIKHOAN where id = '" + id + "' and password = '" + password + "';");
                if (rs.next()) {
                    String role = rs.getString(1);
                    if (role.equals("admin"))
                        result = 0;
                    else if (role.equals("nql"))
                        result = 1;
                    else if (role.equals("nguoidung"))
                        result = 2;
                }
            }
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /*---------------NOI DIEU TRI---------------------*/
    public static int countNDT() {
        Connection conn = DBConnection();
        int id = -1;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select count(*) from noidieutri");

            if (rs.next()) {
                id = rs.getInt(1) + 1;
            }

            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return id;
    }

    public static boolean createNDT(String id, String ten, int sucChua, int dangChua) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into noidieutri values(\"" + id + "\", \"" + ten + "\", " + sucChua + ", " + dangChua + ");";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Đã tồn tại");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Thêm thành công!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNDT(String id, String ten, int sucChua, int dangChua) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE noidieutri\n" +
                    "SET ten = '" + ten + "', sucChua = " + sucChua + ", dangChua =" + dangChua + "\n" +
                    "WHERE id_ndt = " + id + ";";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Chỉnh sửa thất bại!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Chỉnh sửa thành công!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<NoiDieuTri> getListNDT() {
        ArrayList<NoiDieuTri> list = new ArrayList<>();
        String sql = "select * from noidieutri";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NoiDieuTri s = new NoiDieuTri();
                s.setId(rs.getInt("id_ndt"));
                s.setTen(rs.getString("ten"));
                s.setSucChua(rs.getInt("sucChua"));
                s.setDangChua(rs.getInt("dangChua"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<NoiDieuTri> searchNDT(String ten) {
        ArrayList<NoiDieuTri> list = new ArrayList<>();
        String sql = "select * from noidieutri where ten LIKE '%" + ten + "%'";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NoiDieuTri s = new NoiDieuTri();
                s.setId(rs.getInt("id_ndt"));
                s.setTen(rs.getString("ten"));
                s.setSucChua(rs.getInt("sucChua"));
                s.setDangChua(rs.getInt("dangChua"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]) {
    }
}
