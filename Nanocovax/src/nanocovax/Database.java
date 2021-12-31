package nanocovax;

import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

public class Database {
    private static String url = "jdbc:mysql://localhost/Nanocovax";
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
                rs = statement.executeQuery("select phanquyen from TAIKHOAN where id = '" + id + "' and password = '" + password + "' and tinhtrang = 'bt';");
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

    public static boolean updateNQLPassword(String username, String password) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            password = Encryption.encryptMD5(password);
            String sql = "UPDATE taikhoan\n" +
                    "SET password = '" + password + "', tinhtrang = 'bt'\n" +
                    "WHERE id = '" + username + "';";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Updating fails!");
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Updated successfully!");
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
    static String getIdNDT(String ten) {
        String sql = "select * from noidieutri where ten =\"" + ten + "\"";
        Connection conn = DBConnection();
        NoiDieuTri s = new NoiDieuTri();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s.setId(rs.getString("id_ndt"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.getId();
    }


    public static boolean createNDT(String ten, int sucChua, int dangChua) {
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
                //id_ndt
                String id_ndt = getIdNDT(ten);
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                //historyMod(id_nql, thoigian, "add", id_ndt, "id_ndt");
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
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                //historyMod(id_nql, thoigian, "update", id, "id_ndt");

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
                if (rs.getBoolean("active")) {
                    NoiDieuTri s = new NoiDieuTri();
                    s.setId(rs.getString("id_ndt"));
                    s.setTen(rs.getString("ten"));
                    s.setSucChua(rs.getInt("sucChua"));
                    s.setDangChua(rs.getInt("dangChua"));

                    list.add(s);
                }
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
                if (rs.getBoolean("active")) {
                    NoiDieuTri s = new NoiDieuTri();
                    s.setId(rs.getString("id_ndt"));
                    s.setTen(rs.getString("ten"));
                    s.setSucChua(rs.getInt("sucChua"));
                    s.setDangChua(rs.getInt("dangChua"));

                    list.add(s);
                }
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
            String sql = "UPDATE noidieutri set active=0 where id_ndt = " + id + ";";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Delete fail!");
                return false;
            } else {
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                //historyMod(id_nql, thoigian, "delete", id, "id_ndt");

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

    /*---------------NGUOI QUAN LY---------------------*/
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
            String sql;
            if (password.isEmpty()) {
                sql = "UPDATE taikhoan\n" +
                        "SET tinhtrang ='" + status + "'\n" +
                        "WHERE id = '" + id + "' and phanquyen = 'nql';";
            } else {
                password = Encryption.encryptMD5(password);
                sql = "UPDATE taikhoan\n" +
                        "SET password = '" + password + "', tinhtrang ='" + status + "'\n" +
                        "WHERE id = '" + id + "' and phanquyen = 'nql';";
            }

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
            String sql = "UPDATE taikhoan SET tinhtrang = 'khoa' WHERE id = '" + id + "' and phanquyen = 'nql';";

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

    public static ArrayList<UserBranchActivity> getUserBranchActivity(String idNQL) {
        ArrayList<UserBranchActivity> list = new ArrayList<>();
        String sql = "select nd.id, nd.hoten, ls.hoatdong, ls.thoigian from lichsunql as ls join ttnguoidung nd on ls.id=nd.id\n" +
                "where ls.id_nql='" + idNQL + "' and ls.id!=''\n" +
                "order by ls.thoigian desc;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                UserBranchActivity s = new UserBranchActivity();
                s.setUserId(rs.getString("id"));
                s.setUserName(rs.getString("hoten"));
                s.setActivity(rs.getString("hoatdong"));
                s.setDate(rs.getTimestamp("thoigian"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<FoodPackageBranchActivity> getFPBranchActivity(String idNQL) {
        ArrayList<FoodPackageBranchActivity> list = new ArrayList<>();
        String sql = "select nyp.id_nyp, nyp.tengoi, ls.hoatdong, ls.thoigian from lichsunql as ls\n" +
                "join nhuyeupham nyp on ls.id_nyp=nyp.id_nyp\n" +
                "where ls.id_nql='" + idNQL + "'\n" +
                "order by ls.thoigian desc;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FoodPackageBranchActivity s = new FoodPackageBranchActivity();
                s.setFPId(rs.getString("id_nyp"));
                s.setFPName(rs.getString("tengoi"));
                s.setActivity(rs.getString("hoatdong"));
                s.setDate(rs.getTimestamp("thoigian"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<HospitalBranchActivity> getHospitalBranchActivity(String idNQL) {
        ArrayList<HospitalBranchActivity> list = new ArrayList<>();
        String sql = "select ndt.id_ndt, ndt.ten, ls.hoatdong, ls.thoigian from lichsunql as ls\n" +
                "join noidieutri ndt on ls.id_ndt=ndt.id_ndt\n" +
                "where ls.id_nql='" + idNQL + "'\n" +
                "order by ls.thoigian desc;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HospitalBranchActivity s = new HospitalBranchActivity();
                s.setHospitalId(rs.getString("id_ndt"));
                s.setHospitalName(rs.getString("ten"));
                s.setActivity(rs.getString("hoatdong"));
                s.setDate(rs.getTimestamp("thoigian"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /*---------------NGUOI QUAN Ly---------------------*/

    public static ArrayList<CityProvince> getCityProvinceList() {
        ArrayList<CityProvince> list = new ArrayList<>();
        String sql = "select matp, ten\n" +
                "from tinhthanhpho;\n";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CityProvince s = new CityProvince();
                s.setId(rs.getString("matp"));
                s.setName(rs.getString("ten"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<District> getDistrictList(String matp) {
        ArrayList<District> list = new ArrayList<>();
        String sql = "select maqh, ten\n" +
                "from quanhuyen\n" +
                "where matp = '" + matp + "';\n";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                District s = new District();
                s.setId(rs.getString("maqh"));
                s.setName(rs.getString("ten"));
                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Ward> getWardList(String maqh) {
        ArrayList<Ward> list = new ArrayList<>();
        String sql = "select maxp, ten\n" +
                "from xaphuong\n" +
                "where maqh = '" + maqh + "';\n";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ward s = new Ward();
                s.setId(rs.getString("maxp"));
                s.setName(rs.getString("ten"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static boolean isAvailable(String id) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "select * from noidieutri\n" +
                    "where id_ndt = '" + id + "';";

            ResultSet rs = statement.executeQuery(sql);

            if (!rs.next()) {
                conn.close();
                return false;
            } else {
                if (rs.getInt("dangchua") < rs.getInt("succhua")) {
                    conn.close();
                    return true;
                } else {
                    conn.close();
                    return false;
                }
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean createUser(String id, String name, String doB, String matp, String maqh, String maxp, String status, String hospital, String idNLQ) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String password = Encryption.encryptMD5(id);

            if (!isAvailable(hospital)) {
                JOptionPane.showMessageDialog(null, "Hospital is not available!");
                conn.close();
                return false;
            }

            String sql = "insert into taikhoan values('" + id + "', '" + password + "', 'nguoidung', 'bt');";

            int result = statement.executeUpdate(sql);
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "Already existed");
                conn.close();
                return false;
            } else {
                sql = "insert into ttnguoidung(id, hoten, ngaysinh, tinhtp, quanhuyen, xaphuong, trangthai, ndt, sodu, sono) values('" + id + "', '" + name + "', '" + doB + "', '" + matp + "', '" + maqh + "', '" + maxp + "', '" + status + "', '" + hospital + "', 100000000, 0);";
                result = statement.executeUpdate(sql);
                JOptionPane.showMessageDialog(null, "Added new user successfully!");

                if (!idNLQ.isEmpty()) {
                    sql = "insert into lienquan values('" + id + "', '" + idNLQ + "');";
                    result = statement.executeUpdate(sql);
                }

                conn.close();
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /*public static ArrayList<User> getListUser() {
        ArrayList<User> list = new ArrayList<>();
        // String sql = "select id, hoten, ngaysinh, trangthai, ten from ttnguoidung join noidieutri on ndt = id_ndt;";
        String sql = "select * from ttnguoidung\n"+
                "join noidieutri ndt on ndt = ndt.id_ndt\n"+
                "join tinhthanhpho ttp on tinhtp = ttp.matp\n"+
                "join quanhuyen qh on quanhuyen = qh.maqh\n"+
                "join xaphuong xp on xaphuong = xp.maxp;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User s = new User();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("hoten"));

                String d = rs.getString("ngaysinh");
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = oldFormat.parse(d);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String output = newFormat.format(date);
                s.setDoB(output);

                CityProvince cp = new CityProvince();
                cp.setId(rs.getString("ttp.matp"));
                cp.setName(rs.getString("ttp.ten"));

                District district = new District();
                district.setId(rs.getString("qh.maqh"));
                district.setName(rs.getString("qh.ten"));

                Ward w = new Ward();
                w.setId(rs.getString("xp.maxp"));
                w.setName(rs.getString("xp.ten"));

                Address address = new Address();
                address.setCityProvince(cp);
                address.setDistrict(district);
                address.setWard(w);
                s.setAddress(address);

                s.setStatus(rs.getString("trangthai"));

                Hospital hospital = new Hospital();
                hospital.setId(rs.getString("ndt.id_ndt"));
                hospital.setName(rs.getString("ndt.ten"));
                hospital.setCapacity(rs.getInt("ndt.succhua"));
                hospital.setOccupancy(rs.getInt("ndt.dangchua"));
                s.setHospital(hospital);
                //s.setHospital(rs.getString("ten"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }*/

    public static ArrayList<User> getListUser(String order) {
        ArrayList<User> list = new ArrayList<>();
        // String sql = "select id, hoten, ngaysinh, trangthai, ten from ttnguoidung join noidieutri on ndt = id_ndt;";
        String sql = "select * from ttnguoidung ttnd\n" +
                "join noidieutri ndt on ttnd.ndt = ndt.id_ndt\n" +
                "join tinhthanhpho ttp on ttnd.tinhtp = ttp.matp\n" +
                "join quanhuyen qh on ttnd.quanhuyen = qh.maqh\n" +
                "join xaphuong xp on ttnd.xaphuong = xp.maxp\n" +
                "join taikhoan tk on ttnd.id = tk.id\n" +
                "where tk.tinhtrang = 'bt'\n" +
                "order by ttnd." + order + ";";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User s = new User();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("hoten"));

                String d = rs.getString("ngaysinh");
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = oldFormat.parse(d);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String output = newFormat.format(date);
                s.setDoB(output);

                CityProvince cp = new CityProvince();
                cp.setId(rs.getString("ttp.matp"));
                cp.setName(rs.getString("ttp.ten"));

                District district = new District();
                district.setId(rs.getString("qh.maqh"));
                district.setName(rs.getString("qh.ten"));

                Ward w = new Ward();
                w.setId(rs.getString("xp.maxp"));
                w.setName(rs.getString("xp.ten"));

                Address address = new Address();
                address.setCityProvince(cp);
                address.setDistrict(district);
                address.setWard(w);
                s.setAddress(address);

                String status = rs.getString("trangthai");
                if (status.equals("D"))
                    status = "Dead";
                else if (status.equals("R"))
                    status = "Recovered";
                s.setStatus(status);

                Hospital hospital = new Hospital();
                hospital.setId(rs.getString("ndt.id_ndt"));
                hospital.setName(rs.getString("ndt.ten"));
                hospital.setCapacity(rs.getInt("ndt.succhua"));
                hospital.setOccupancy(rs.getInt("ndt.dangchua"));
                s.setHospital(hospital);
                //s.setHospital(rs.getString("ten"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static User searchAUser(String id) {
        User s = new User();
        String sql = "select * from ttnguoidung ttnd\n" +
                "join noidieutri ndt on ttnd.ndt = ndt.id_ndt\n" +
                "join tinhthanhpho ttp on ttnd.tinhtp = ttp.matp\n" +
                "join quanhuyen qh on ttnd.quanhuyen = qh.maqh\n" +
                "join xaphuong xp on ttnd.xaphuong = xp.maxp\n" +
                "join taikhoan tk on ttnd.id = tk.id\n" +
                "where ttnd.id = '" + id + "';";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s.setId(rs.getString("id"));
                s.setName(rs.getString("hoten"));

                String d = rs.getString("ngaysinh");
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = oldFormat.parse(d);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String output = newFormat.format(date);
                s.setDoB(output);

                CityProvince cp = new CityProvince();
                cp.setId(rs.getString("ttp.matp"));
                cp.setName(rs.getString("ttp.ten"));

                District district = new District();
                district.setId(rs.getString("qh.maqh"));
                district.setName(rs.getString("qh.ten"));

                Ward w = new Ward();
                w.setId(rs.getString("xp.maxp"));
                w.setName(rs.getString("xp.ten"));

                Address address = new Address();
                address.setCityProvince(cp);
                address.setDistrict(district);
                address.setWard(w);
                s.setAddress(address);

                String status = rs.getString("trangthai");
                if (status.equals("D"))
                    status = "Dead";
                else if (status.equals("R"))
                    status = "Recovered";
                s.setStatus(status);

                //s.setHospital(rs.getString("ten"));
                Hospital hospital = new Hospital();
                hospital.setId(rs.getString("ndt.id_ndt"));
                hospital.setName(rs.getString("ndt.ten"));
                hospital.setCapacity(rs.getInt("ndt.succhua"));
                hospital.setOccupancy(rs.getInt("ndt.dangchua"));
                s.setHospital(hospital);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static ArrayList<User> searchUser(String id) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "select * from ttnguoidung ttnd\n" +
                "join noidieutri ndt on ttnd.ndt = ndt.id_ndt\n" +
                "join tinhthanhpho ttp on ttnd.tinhtp = ttp.matp\n" +
                "join quanhuyen qh on ttnd.quanhuyen = qh.maqh\n" +
                "join xaphuong xp on ttnd.xaphuong = xp.maxp\n" +
                "join taikhoan tk on ttnd.id = tk.id\n" +
                "where ttnd.id = '" + id + "' and tk.tinhtrang = 'bt';";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User s = new User();
                s.setId(rs.getString("id"));
                s.setName(rs.getString("hoten"));

                String d = rs.getString("ngaysinh");
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = oldFormat.parse(d);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String output = newFormat.format(date);
                s.setDoB(output);

                CityProvince cp = new CityProvince();
                cp.setId(rs.getString("ttp.matp"));
                cp.setName(rs.getString("ttp.ten"));

                District district = new District();
                district.setId(rs.getString("qh.maqh"));
                district.setName(rs.getString("qh.ten"));

                Ward w = new Ward();
                w.setId(rs.getString("xp.maxp"));
                w.setName(rs.getString("xp.ten"));

                Address address = new Address();
                address.setCityProvince(cp);
                address.setDistrict(district);
                address.setWard(w);
                s.setAddress(address);

                String status = rs.getString("trangthai");
                if (status.equals("D"))
                    status = "Dead";
                else if (status.equals("R"))
                    status = "Recovered";
                s.setStatus(status);

                //s.setHospital(rs.getString("ten"));
                Hospital hospital = new Hospital();
                hospital.setId(rs.getString("ndt.id_ndt"));
                hospital.setName(rs.getString("ndt.ten"));
                hospital.setCapacity(rs.getInt("ndt.succhua"));
                hospital.setOccupancy(rs.getInt("ndt.dangchua"));
                s.setHospital(hospital);

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean deleteUser(String id, String idNQL) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            /*String sql = "delete from lienquan\n" +
                    "where id = '" + id + "' or id_lienquan = '" + id + "';";
            int x = statement.executeUpdate(sql);

            sql = "delete from lichsundt\n" +
                    "where id = '" + id + "';";
            x = statement.executeUpdate(sql);

            sql = "delete from lichsunql\n" +
                    "where id = '" + id + "' and id_nql = '" + idNQL + "';";
            x = statement.executeUpdate(sql);

            sql = "delete from lichsutrangthai\n" +
                    "where id = '" + id + "';";
            x = statement.executeUpdate(sql);

            sql = "delete from ttnguoidung\n" +
                    "where id = '" + id + "';";
            x = statement.executeUpdate(sql);*/

            String sql = "UPDATE taikhoan\n" +
                    "SET tinhtrang = 'khoa'\n" +
                    "WHERE id = '" + id + "';";

            int x = statement.executeUpdate(sql);

            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Deleting fails!");
                conn.close();
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Deleted successfully!");
                conn.close();
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(String id, String name, String doB, String matp, String maqh, String maxp, String hospital, String idNLQ) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE ttnguoidung\n" +
                    "SET hoten = '" + name + "', ngaysinh = '" + doB + "', tinhtp = '" + matp + "', quanhuyen = '" + maqh + "', xaphuong = '" + maxp + "', ndt = '" + hospital + "'\n" +
                    "WHERE id = '" + id + "';";

            int x = statement.executeUpdate(sql);
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Updating fails!");
                conn.close();
                return false;
            } else {
                JOptionPane.showMessageDialog(null, "Updated successfully!");

                if (!idNLQ.equals(getNLQId(id))) {
                    sql = "delete from lienquan\n" +
                            "where id = '" + id + "';";
                    x = statement.executeUpdate(sql);

                    if (!idNLQ.isEmpty()) {
                        sql = "insert into lienquan values('" + id + "', '" + idNLQ + "');";
                        x = statement.executeUpdate(sql);
                    }
                }
                conn.close();
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUserStatus(String id, String status) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE ttnguoidung\n" +
                    "SET trangthai = '" + status + "'\n" +
                    "WHERE id = '" + id + "';";

            int x = statement.executeUpdate(sql);
            if (x == 0) {
                conn.close();
                return false;
            } else {
                conn.close();
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static String getUserStatus(String id) {
        String sql = "select trangthai from ttnguoidung\n" +
                "where id = '" + id + "';";
        String status = "";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                status = rs.getString("trangthai");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

    public static void updateAllStatuses(String id, int option, int n, boolean asc, boolean flag) {
        if (id == null)
            return;

        String status = getUserStatus(id);
        if (flag) {
            int num = (asc) ? (Integer.parseInt(String.valueOf(status.charAt(1))) + n) : (Integer.parseInt(String.valueOf(status.charAt(1))) - n);
            status = "F" + num;

            updateUserStatus(id, status);
        }

        String sql = "";
        if (option == 0)
            sql = "select * from lienquan lq join ttnguoidung ttnd on ttnd.id = lq.id join taikhoan tk on tk.id = lq.id  where lq.id_lienquan = '" + id + "' and tk.tinhtrang != 'khoa';";
        else
            sql = "select * from lienquan lq join ttnguoidung ttnd on ttnd.id = lq.id_lienquan join taikhoan tk on tk.id = lq.id_lienquan  where lq.id = '" + id + "' and tk.tinhtrang != 'khoa';";

        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String s = rs.getString("ttnd.id");
                updateAllStatuses(s, option, n, asc, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getNLQId(String id) {
        String sql = "select id_lienquan from lienquan\n" +
                "where id = '" + id + "';";
        String idNLQ = "";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                idNLQ = rs.getString("id_lienquan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idNLQ;
    }


    public static boolean updateLSNQL(int option, String idNQL, String date, String activity, String id) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "";

            switch (option) {
                case 0:
                    sql = "insert into lichsunql(id_nql, thoigian, hoatdong, id) values('" + idNQL + "', '" + date + "', '" + activity + "', '" + id + "');";
                    break;
                case 1:
                    sql = "insert into lichsunql(id_nql, thoigian, hoatdong, id_nyp) values('" + idNQL + "', '" + date + "', '" + activity + "', '" + id + "');";
                    break;
                case 2:
                    sql = "insert into lichsunql(id_nql, thoigian, hoatdong, id_ndt) values('" + idNQL + "', '" + date + "', '" + activity + "', '" + id + "');";
                    break;
            }

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Updated Moderator History successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateLSNDT(String id, String date, String idNDT) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into lichsundt values('" + id + "', '" + date + "', '" + idNDT + "');";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Updated Hospital History successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateLSTT(String id, String date, String time, String status) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into lichsutrangthai values('" + id + "', '" + date + "', '" + time + "', '" + status + "');";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Updated Hospital History successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateOccupancyNDT(String id_ndt, int option) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql;
            if (option == 0) {
                sql = "UPDATE noidieutri\n" +
                        "SET dangchua = dangchua + 1\n" +
                        "WHERE id_ndt = '" + id_ndt + "';";
            } else {
                sql = "UPDATE noidieutri\n" +
                        "SET dangchua = dangchua - 1\n" +
                        "WHERE id_ndt = '" + id_ndt + "';";
            }

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Updated Hospital Occupancy successfully!");
                return true;
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<HospitalHistory> getLSNDT(String id) {
        ArrayList<HospitalHistory> list = new ArrayList<>();
        String sql = "select * from lichsundt lsndt join noidieutri ndt on lsndt.id_ndt = ndt.id_ndt where id = '" + id + "';";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                HospitalHistory s = new HospitalHistory();
                s.setId(rs.getString("id"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = format.format(rs.getTimestamp("thoigian"));
                s.setTime(date);

                Hospital t = new Hospital();
                t.setId(rs.getString("id_ndt"));
                t.setName(rs.getString("ten"));
                t.setCapacity(rs.getInt("succhua"));
                t.setOccupancy(rs.getInt("dangchua"));
                s.setNDT(t);

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<User> getListNLQ(String id) {
        ArrayList<User> list = new ArrayList<>();
        String sql = "select * from ttnguoidung ttnd join noidieutri ndt on ttnd.ndt = ndt.id_ndt join tinhthanhpho ttp on tinhtp = ttp.matp join quanhuyen qh on quanhuyen = qh.maqh join xaphuong xp on xaphuong = xp.maxp join lienquan lq on ttnd.id = lq.id join taikhoan tk on ttnd.id = tk.id where lq.id_lienquan = '" + id + "' and tk.tinhtrang != 'khoa';";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User s = new User();
                s.setId(rs.getString("ttnd.id"));
                s.setName(rs.getString("ttnd.hoten"));

                String d = rs.getString("ttnd.ngaysinh");
                SimpleDateFormat oldFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = oldFormat.parse(d);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
                String output = newFormat.format(date);
                s.setDoB(output);

                CityProvince cp = new CityProvince();
                cp.setId(rs.getString("ttp.matp"));
                cp.setName(rs.getString("ttp.ten"));

                District district = new District();
                district.setId(rs.getString("qh.maqh"));
                district.setName(rs.getString("qh.ten"));

                Ward w = new Ward();
                w.setId(rs.getString("xp.maxp"));
                w.setName(rs.getString("xp.ten"));

                Address address = new Address();
                address.setCityProvince(cp);
                address.setDistrict(district);
                address.setWard(w);
                s.setAddress(address);

                s.setStatus(rs.getString("ttnd.trangthai"));

                Hospital hospital = new Hospital();
                hospital.setId(rs.getString("ndt.id_ndt"));
                hospital.setName(rs.getString("ndt.ten"));
                hospital.setCapacity(rs.getInt("ndt.succhua"));
                hospital.setOccupancy(rs.getInt("ndt.dangchua"));
                s.setHospital(hospital);
                //s.setHospital(rs.getString("ten"));

                list.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ----- STATISTICS ----- //
    public static DefaultCategoryDataset getSumStatus() {
        Connection conn = DBConnection();
        String sql = "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT count(id) as soca, trangthai, ngay\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1 and trangthai LIKE '%F%'\n" +
                "GROUP BY trangthai, ngay\n" +
                "ORDER BY ngay asc, trangthai asc;\n";
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset2.setValue(rs.getInt("soca"), rs.getString("trangthai"), rs.getString("ngay"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset2;
    }

    public static DefaultCategoryDataset getStatusChange() {
        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();

        Connection conn = DBConnection();
        String sql = "WITH group_final AS (\n" +
                "WITH group_4 AS (\n" +
                "WITH group_3 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_3\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_4\n" +
                "WHERE NOT EXISTS (\n" +

                "WITH group_2 AS (\n" +
                "WITH group_1 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_1\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_2\n" +
                "WHERE group_4.ngay = group_2.ngay\n" +
                ")\n" +
                ")\n" +
                "SELECT count(id) as soluongchuyen, ngay\n" +
                "FROM group_final\n" +
                "where trangthai like '%F%'\n" +
                "group by ngay\n" +
                "order by ngay asc;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset3.setValue(rs.getInt("soluongchuyen"), "Being treated", rs.getString("ngay"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn = DBConnection();
        sql = "WITH group_final AS (\n" +
                "WITH group_4 AS (\n" +
                "WITH group_3 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_3\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_4\n" +
                "WHERE NOT EXISTS (\n" +

                "WITH group_2 AS (\n" +
                "WITH group_1 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_1\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_2\n" +
                "WHERE group_4.ngay = group_2.ngay\n" +
                ")\n" +
                ")\n" +
                "SELECT count(id) as soluongchuyen, ngay\n" +
                "FROM group_final\n" +
                "where trangthai like '%R%'\n" +
                "group by ngay\n" +
                "order by ngay asc;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset3.setValue(rs.getInt("soluongchuyen"), "Recovered", rs.getString("ngay"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        conn = DBConnection();
        sql = "WITH group_final AS (\n" +
                "WITH group_4 AS (\n" +
                "WITH group_3 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_3\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_4\n" +
                "WHERE NOT EXISTS (\n" +

                "WITH group_2 AS (\n" +
                "WITH group_1 AS (\n" +
                "WITH group_data_2 AS (\n" +
                "WITH group_data_1 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay ASC, thoigian ASC) as stt\n" +
                "FROM group_data_1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_2\n" +
                "WHERE NOT EXISTS (\n" +
                "WITH group_data_5 AS (\n" +
                "WITH group_data_4 AS (\n" +
                "WITH group_data_3 AS (\n" +
                "WITH added_row_number AS (\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id, ngay ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM lichsutrangthai\n" +
                ")\n" +
                "SELECT id, ngay, thoigian, trangthai\n" +
                "FROM added_row_number\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *, ROW_NUMBER() OVER(PARTITION BY id ORDER BY ngay DESC, thoigian DESC) as stt\n" +
                "FROM group_data_3\n" +
                ")\n" +
                "SELECT id\n" +
                "FROM group_data_4\n" +
                "group by id\n" +
                "having count(ngay) = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_data_5\n" +
                "WHERE group_data_2.id = group_data_5.id\n" +
                ")\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_1\n" +
                "WHERE stt = 1\n" +
                ")\n" +
                "SELECT *\n" +
                "FROM group_2\n" +
                "WHERE group_4.ngay = group_2.ngay\n" +
                ")\n" +
                ")\n" +
                "SELECT count(id) as soluongchuyen, ngay\n" +
                "FROM group_final\n" +
                "where trangthai like '%D%'\n" +
                "group by ngay\n" +
                "order by ngay asc;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset3.setValue(rs.getInt("soluongchuyen"), "Dead", rs.getString("ngay"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset3;
    }

    public static DefaultCategoryDataset getSumNec() {
        Connection conn = DBConnection();
        String sql = "select ct.id_nyp, nyp.tengoi, sum(ct.id_nyp) as soluong from cthd ct left join nhuyeupham nyp on ct.id_nyp=nyp.id_nyp\n" +
                "group by ct.id_nyp, nyp.tengoi\n" +
                "order by ct.id_nyp asc";
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dataset2.setValue(rs.getInt("soluong"), rs.getString("tengoi"), rs.getString("tengoi"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset2;
    }

    //--------QUẢN LÝ NHU YẾU PHẨM-------------//
    static int getIdNYP(String ten) {
        String sql = "select * from nhuyeupham where tengoi =\"" + ten + "\"";
        Connection conn = DBConnection();
        NhuYeuPham s = new NhuYeuPham();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                s.setId_nyp(rs.getInt("id_nyp"));
                s.setTengoi(rs.getString("tengoi"));
                s.setThoihan(rs.getInt("thoihan"));
                s.setDongia(rs.getInt("dongia"));
                s.setGioihan(rs.getInt("gioihan"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.getId_nyp();
    }


    public static boolean createNYP(String id_nql, String tengoi, int thoihan, int dongia, int gioihan) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into nhuyeupham(tengoi, thoihan, dongia, gioihan) values(\"" + tengoi + "\", " + thoihan + ", " + dongia + ", " + gioihan + ");";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //id_nyp
                int id = getIdNYP(tengoi);
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                //historyMod(id_nql, thoigian, "add", String.valueOf(id), "id_nyp");

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

    public static boolean updateNYP(String id_nql, int id, String tengoi, int thoihan, int dongia, int gioihan) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE nhuyeupham\n" +
                    "SET tengoi = '" + tengoi + "', thoihan = " + thoihan + ", dongia =" + dongia + ", gioihan =" + gioihan + "\n" +
                    "WHERE id_nyp = " + id + ";";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Update fail!");
                return false;
            } else {
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                historyMod(id_nql, thoigian, "update", String.valueOf(id), "id_nyp");

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

    public static ArrayList<NhuYeuPham> getListNYP(String column, String ascDesc, String filterBy, String filterValue) {
        ArrayList<NhuYeuPham> list = new ArrayList<>();
        String sql = "select * from nhuyeupham order by " + column + " " + ascDesc + ";";
        if (!filterValue.equals("")) {
            sql = "select * from nhuyeupham where " + filterBy + " <= " + filterValue + " order by " + column + " " + ascDesc + ";";
        }
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("active")) {
                    NhuYeuPham s = new NhuYeuPham();
                    s.setId_nyp(rs.getInt("id_nyp"));
                    s.setTengoi(rs.getString("tengoi"));
                    s.setThoihan(rs.getInt("thoihan"));
                    s.setDongia(rs.getInt("dongia"));
                    s.setGioihan(rs.getInt("gioihan"));

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void sortListNYP(ArrayList<NhuYeuPham> list, String column) {
        if (column.equals("ID: Ascending")) {
            Collections.sort(list, NhuYeuPham.IDComparatorAsc);
        } else if (column.equals("ID: Descending")) {
            Collections.sort(list, NhuYeuPham.IDComparatorDesc);
        } else if (column.equals("Price: Ascending")) {
            Collections.sort(list, NhuYeuPham.PriceComparatorAsc);
        } else if (column.equals("Price: Descending")) {
            Collections.sort(list, NhuYeuPham.PriceComparatorDesc);
        } else if (column.equals("Duration: Ascending")) {
            Collections.sort(list, NhuYeuPham.DurationComparatorAsc);
        } else if (column.equals("Duration: Descending")) {
            Collections.sort(list, NhuYeuPham.DurationComparatorDesc);
        }
    }

    public static ArrayList<NhuYeuPham> searchNYP(String ten) {
        ArrayList<NhuYeuPham> list = new ArrayList<>();
        String sql = "select * from nhuyeupham where tengoi LIKE '%" + ten + "%'";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("active")) {
                    NhuYeuPham s = new NhuYeuPham();
                    s.setId_nyp(rs.getInt("id_nyp"));
                    s.setTengoi(rs.getString("tengoi"));
                    s.setThoihan(rs.getInt("thoihan"));
                    s.setDongia(rs.getInt("dongia"));
                    s.setGioihan(rs.getInt("gioihan"));

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean deleteNYP(String id_nql, String id) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE nhuyeupham SET active = 0 WHERE id_nyp = \"" + id + "\";";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Delete fail!");
                return false;
            } else {
                //time
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                String thoigian = dtf.format(now);

                historyMod(id_nql, thoigian, "delete", id, "id_nyp");

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

    //----------------LICH SU NQL-------------------------------//
    public static boolean historyMod(String id_nql, String thoigian, String hoatdong, String id_khac, String loai) {
        //loai "id", "id_nyp", "id_ndt"
        Connection conn = DBConnection();

        try {
            Statement statement = conn.createStatement();
            String sql = "";
            if (loai.equals("id_nyp")) {
                sql = "insert into lichsunql(id_nql, thoigian, hoatdong, id_nyp) values(\"" + id_nql + "\", \"" + thoigian + "\", \"" + hoatdong + "\", \"" + id_khac + "\");";
            } else if (loai.equals("id_ndt")) {
                sql = "insert into lichsunql(id_nql, thoigian, hoatdong, id_ndt) values(\"" + id_nql + "\", \"" + thoigian + "\", \"" + hoatdong + "\", \"" + id_khac + "\");";
            }

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                //JOptionPane.showMessageDialog(null, "Already exists");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Add successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public static ArrayList<LichSuNQL> getListHistoryModNes(String id_nql) {
        ArrayList<LichSuNQL> list = new ArrayList<>();
        String sql = "select * from lichsunql ls join nhuyeupham nyp on ls.id_nyp=nyp.id_nyp where id_nql=" + "\"" + id_nql + "\"";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!rs.getString("id_nyp").equals("")) {
                    LichSuNQL s = new LichSuNQL();
                    s.setId_nyp(rs.getString("tengoi"));
                    s.setThoigian(rs.getString("thoigian"));
                    s.setHoatdong(rs.getString("hoatdong"));

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<LichSuNQL> getListHistoryModHos(String id_nql) {
        ArrayList<LichSuNQL> list = new ArrayList<>();
        String sql = "select * from lichsunql ls join noidieutri ndt on ls.id_ndt=ndt.id_ndt where id_nql=" + "\"" + id_nql + "\"";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!rs.getString("id_ndt").equals("")) {
                    LichSuNQL s = new LichSuNQL();
                    s.setId_ndt(rs.getString("ten"));
                    s.setThoigian(rs.getString("thoigian"));
                    s.setHoatdong(rs.getString("hoatdong"));

                    list.add(s);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //--------------------HOA DON-------------------------------//
    static int getSoHd(String id, String thoigian) {
        String sql = "select * from hoadon where nguoimua =\"" + id + "\" and thoigian = \"" + thoigian + "\"";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return (rs.getInt("sohd"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public static boolean saveHoaDon(String id, String tongtien, String tratruoc, ArrayList<NhuYeuPham> dataList) {
        Connection conn = DBConnection();
        try {
            //time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String thoigian = dtf.format(now);

            Statement statement = conn.createStatement();
            String sql = "insert into hoadon(nguoimua, thoigian, tongtien, tratruoc) values(\"" + id + "\", \"" + thoigian + "\", " + tongtien + ", " + tratruoc + ");";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Purchase fail!");
                return false;
            } else {
                int soHd = getSoHd(id, thoigian);
                if (saveCTHD(soHd, dataList) && saveLichSuThanhToan(soHd, thoigian, tratruoc) && updateDuNo(id, Long.parseLong(tongtien), Long.parseLong(tratruoc))) {
                    JOptionPane.showMessageDialog(null, "Purchase successfully!");
                }
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveCTHD(int sohd, ArrayList<NhuYeuPham> dataList) {
        Connection conn = DBConnection();
        try {
            //time
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            Statement statement = conn.createStatement();
            String sql = "";
            for (NhuYeuPham item : dataList) {
                sql = "insert into cthd(sohd, soluong, id_nyp) values(\"" + sohd + "\", " + item.getSoluong() + ", " + item.getId_nyp() + ");";

                int x = statement.executeUpdate(sql);

                if (x == 0) {
                    JOptionPane.showMessageDialog(null, "Save in CTHD fail!");
                    return false;
                } else {
                    //     JOptionPane.showMessageDialog(null, "successfully!");

                }
            }
            conn.close();
            return true;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Long> getDuNo(String idUser) {
        ArrayList<Long> duNo = new ArrayList<Long>();
        String sql = "select * from ttnguoidung where id =\"" + idUser + "\"";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                duNo.add(rs.getLong("sodu"));
                duNo.add(rs.getLong("sono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return duNo;
    }

    public static boolean updateDuNo(String id, long tongtien, long tratruoc) {
        Connection conn = DBConnection();
        ArrayList<Long> cur_duno = getDuNo(id);

        try {
            Statement statement = conn.createStatement();
            String sql = "UPDATE ttnguoidung\n" +
                    "SET sodu = '" + (cur_duno.get(0) - tratruoc) + "', sono = " + (cur_duno.get(1) + tongtien - tratruoc) + "\n" +
                    "WHERE id = \"" + id + "\";";

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


    public static boolean saveLichSuThanhToan(int soHd, String thoigian, String tongtien) {
        Connection conn = DBConnection();
        try {
            Statement statement = conn.createStatement();
            String sql = "insert into lichsuthanhtoan(sohd, thoigian, sotien) values(\"" + soHd + "\", \"" + thoigian + "\", " + tongtien + ");";

            int x = statement.executeUpdate(sql);
            conn.close();
            if (x == 0) {
                JOptionPane.showMessageDialog(null, "Save lichsuthanhtoan fail!");
                return false;
            } else {
                //JOptionPane.showMessageDialog(null, "Save lichsuthanhtoan successfully!");
                return true;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //--------------------NGUOI DUNG-------------------------------//
    public static ArrayList<StatusHistory> getStatusHistoryList(String id) {
        ArrayList<StatusHistory> list = new ArrayList<>();
        String sql = "select * from lichsutrangthai where id = '" + id + "' order by ngay asc;";
        Connection conn = DBConnection();
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StatusHistory s = new StatusHistory();
                s.setId(rs.getString("id"));
                s.setStatus(rs.getString("trangthai"));
                s.setDate(rs.getString("ngay"));
                s.setTime(rs.getString("thoigian"));

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
