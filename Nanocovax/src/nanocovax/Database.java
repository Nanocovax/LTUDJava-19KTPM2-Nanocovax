package nanocovax;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Database {
    private static String url = "jdbc:mysql://localhost/Nanocovax";
    private static String username = "root";
    private static String password = "Dra2gon3storm5&";

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
            ResultSet rs = statement.executeQuery("select tinhtrang from TAIKHOAN where id = '" + id + "' and password = '" + password + "' and phanquyen = 'nql';");
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

    public static boolean createNDT( String ten, int sucChua, int dangChua) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into noidieutri(ten, sucChua, dangChua) values(\"" + ten + "\", " + sucChua + ", " + dangChua + ");";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Add successfully!");
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
                JOptionPane.showMessageDialog(null, "Update fail!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Update successfully!");
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

    public static boolean deleteNDT(String id) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "delete from noidieutri where id_ndt = " + id + ";";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Delete fail!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Delete successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*---------------NOI DIEU TRI---------------------*/
    public static boolean createNQL(String id, String password) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            password = Encryption.encryptMD5(password);
            String sql = "insert into taikhoan values('" + id + "', '" + password + "', 'nql', 'ckh');";

            int result = statement.executeUpdate(sql);
            conn.close();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Already existed");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Add successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNQL(String id, String password, String status) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            password = Encryption.encryptMD5(password);
            String sql = "UPDATE taikhoan\n" +
                    "SET password = '" + password + "', tinhtrang ='" + status + "'\n" +
                    "WHERE id = '" + id + "' and phanquyen = 'nql';";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Update fail!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Update successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<NguoiQuanLy> getListNQL() {
        ArrayList<NguoiQuanLy> list = new ArrayList<>();
        String sql = "select * from taikhoan where phanquyen ='nql'";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiQuanLy s = new NguoiQuanLy();
                s.setId(rs.getString("id"));
                String strTemp = rs.getString("phanquyen");
                switch (strTemp) {
                    case "admin":
                        s.setPhanquyen("Administrator");
                        break;
                    case "nql":
                        s.setPhanquyen("Moderator");
                        break;
                    case "nguoidung":
                        s.setTinhtrang("User");
                        break;
                }

                strTemp = rs.getString("tinhtrang");
                switch (strTemp) {
                    case "bt":
                        s.setTinhtrang("Normal");
                        break;
                    case "ckh":
                        s.setTinhtrang("Non-activated");
                        break;
                    case "khoa":
                        s.setTinhtrang("Blocked");
                        break;
                }

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<NguoiQuanLy> searchNQL(String id) {
        ArrayList<NguoiQuanLy> list = new ArrayList<>();
        String sql = "select * from taikhoan where id = '" + id + "' and phanquyen = 'nql'";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NguoiQuanLy s = new NguoiQuanLy();
                s.setId(rs.getString("id"));
                String strTemp = rs.getString("phanquyen");
                switch (strTemp) {
                    case "admin":
                        s.setPhanquyen("Administrator");
                        break;
                    case "nql":
                        s.setPhanquyen("Moderator");
                        break;
                    case "nguoidung":
                        s.setTinhtrang("User");
                        break;
                }

                strTemp = rs.getString("tinhtrang");
                switch (strTemp) {
                    case "bt":
                        s.setTinhtrang("Normal");
                        break;
                    case "ckh":
                        s.setTinhtrang("Non-activated");
                        break;
                    case "khoa":
                        s.setTinhtrang("Blocked");
                        break;
                }

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean deleteNQL(String id) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "delete from taikhoan where id = '" + id + "' and phanquyen = 'nql';";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Deleting fails!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String args[]) {
    }
}
