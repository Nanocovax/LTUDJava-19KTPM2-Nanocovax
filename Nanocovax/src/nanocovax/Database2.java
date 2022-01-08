package nanocovax;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Database2 {
    private static String url = "jdbc:mysql://localhost/nanocovax_thanhtoan";
    private static String username = "root";
    private static String password = "Baokhuyen2001@";

    public static Connection DBConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            //System.out.println("Database is connected!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot connect to Database - Error:" + e);
        }
        return conn;
    }

    public static boolean createAdmin(String id) {
        Connection conn = DBConnection();
        boolean res = false;
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into TAIKHOAN values('" + id + "', 0);";
            int x = statement.executeUpdate(sql);
            if (x != 0) {
                res = true;
                //JOptionPane.showMessageDialog(null, "The customer account has been created!");
                System.out.println("The admin account has been created!");
            }
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public static boolean createCustomer(String id) {
        Connection conn = DBConnection();
        boolean res = false;
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into TAIKHOAN values('" + id + "', 314000000);";
            int x = statement.executeUpdate(sql);
            if (x != 0) {
                res = true;
                //JOptionPane.showMessageDialog(null, "The customer account has been created!");
                System.out.println("The customer account has been created!");
            }
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public static int getAccountBalance(String id) {
        Connection conn = DBConnection();
        int result = 0;
        try {
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select sodu from TAIKHOAN where id = '" + id + "';");
            if (rs.next())
                result = rs.getInt("sodu");
            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public static int doATransaction(String id, int money) {
        Connection conn = DBConnection();
        int rs = 2;
        try {
            Statement statement = conn.createStatement();
            int accountBalance = getAccountBalance(id);
            if (money > accountBalance) {
                rs = 0;
                //JOptionPane.showMessageDialog(null, "The account balance is not adequate!");
                System.out.println("The account balance is not adequate!");
            } else {
                accountBalance = accountBalance - money;
                String sql = "update TAIKHOAN set sodu = " + accountBalance + " where id = '" + id + "';";

                int x = statement.executeUpdate(sql);
                if (x != 0) {
                    rs = 1;
                    //JOptionPane.showMessageDialog(null, "The transaction has been done successfully!");
                    System.out.println("The transaction has been done successfully!");
                } else {
                    //JOptionPane.showMessageDialog(null, "The transaction has been canceled!");
                    System.out.println("The transaction has been canceled!");
                }
            }

            conn.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    public static boolean receiveATransaction(int money) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            int accountBalance = getAccountBalance("admin");

            accountBalance = accountBalance + money;
            String sql = "update TAIKHOAN set sodu = " + accountBalance + " where id = 'admin';";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Update purchase fail!");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Update successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Customer> getCustomerList() {
        ArrayList<Customer> list = new ArrayList<>();
        String sql = "select * from taikhoan;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Customer s = new Customer();
                s.setId(rs.getString("id"));
                s.setAccountBalance(rs.getInt("sodu"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<TransactionHistory> getTransactionList(String id) {
        ArrayList<TransactionHistory> list = new ArrayList<>();
        String sql = "select * from lichsu where id = '" + id + "';";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TransactionHistory s = new TransactionHistory();
                s.setId(rs.getString("id"));
                s.setDate(rs.getTimestamp("thoigian"));
                s.setExpense(rs.getInt("tienra"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean updateTransactionHistory(String id, String date, int expense) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String sql = "insert into lichsu values('" + id + "', '" + dtf.format(now) + "', '" + expense + "');";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Updated History successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) {

    }
}
