package nanocovax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalTime;

public class NQLMenu extends JFrame implements Runnable {
    private JPanel menuPanel;
    private JLabel lbNYP;
    private JLabel lbLogout;
    private JLabel lbUser;
    private JLabel lbStatistic;
    private JButton searchButton;
    private JButton removeButton;
    private JButton editButton;
    private JButton detailButton;
    private JButton addButton;
    private JLabel header;
    private JComboBox sortOption;
    private JTextField searchInput;
    private JTable userTable;
    private JLabel lbSort;
    private JButton refreshButton;
    private JPanel rootPanel;
    private JPanel managPanel;
    int indexRow;
    Object id = null;
    String order;
    String username;
    Thread thread;
    static Logger logger = LogManager.getLogger(NQLMenu.class);
    public void run() {
        sortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexCityPro = sortOption.getSelectedIndex();

                switch (indexCityPro) {
                    case 0:
                        order = "id asc";
                        break;
                    case 1:
                        order = "id desc";
                        break;
                    case 2:
                        order = "hoten asc";
                        break;
                    case 3:
                        order = "hoten desc";
                        break;
                    case 4:
                        order = "ngaysinh asc";
                        break;
                    case 5:
                        order = "ngaysinh desc";
                        break;
                    case 6:
                        order = "trangthai asc";
                        break;
                    case 7:
                        order = "trangthai desc";
                        break;
                    default:
                        order = "id asc";
                        break;
                }
                createTable(Database.getListUser(order));
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser a = new addUser(username);
                logger.info("Mod - Add user");
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.searchUser(searchInput.getText()));
                logger.info("Mod - Search for"+ searchInput.getText());
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.getListUser(order));
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //userDetail u = new userDetail();
                if (indexRow != -1) {
                    retriveUser();
                    //userDetail u = new userDetail(Database.getListUser(order).get(indexRow), username);
                    userDetail u = new userDetail(Database.searchAUser(id.toString()), username);
                } else {
                    userDetail u = new userDetail();
                }
                logger.info("Mod - Take a look at a user detail");
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //editUser editUser = new editUser();
                    if (indexRow != -1) {
                        retriveUser();
                        //editUser editUser = new editUser(Database.getListUser(order).get(indexRow), username);
                        editUser editUser = new editUser(Database.searchAUser(id.toString()), username);
                    } else {
                        editUser editNQL = new editUser(username);
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                logger.info("Mod - edit user's info");
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retriveUser();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete " + id.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    ArrayList<User> t = Database.searchUser(id.toString());

                    Database.updateOccupancyNDT(t.get(0).getHospital().getId(), 1);

                    Database.deleteUser(id.toString(), username);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Database.updateLSNQL(0, username, dtf.format(now), "removed", id.toString());

                    String hospital = Database.searchAUser(id.toString()).getHospital().getId();
                    Database.updateLSNQL(2, username, dtf.format(now), "removed " + id.toString(), hospital);

                    Database.updateLSNDT(id.toString(), dtf.format(now), hospital);

                    Database.updateOccupancyNDT(hospital, 1);
                    logger.info("Mod - remove user");
                }

            }
        });
        sortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sắp xếp bảng theo option được select
            }
        });
        lbNYP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NecManagement n = new NecManagement(username);
                setVisible(false);
                dispose();
                logger.info("Mod - User management -> NecManagement");
            }
        });

        lbStatistic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Statistic statistic = new Statistic(username);
                setVisible(false);
                dispose();
                logger.info("Mod - User management -> Statistic");
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();

                    Login frame = new Login();
                    frame.setVisible(true);
                    logger.info("Mod - Log out");
                }
            }
        });
    }

    NQLMenu(String username){
        add(this.rootPanel);
        order = "id asc";
        createTable(Database.getListUser(order));
        setSize(1900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        sortOption.setSelectedIndex(0);
        this.username = username;
        thread = new Thread(this);
        thread.start();

        /*sortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int indexCityPro = sortOption.getSelectedIndex();

                switch (indexCityPro) {
                    case 0:
                        order = "id asc";
                        break;
                    case 1:
                        order = "id desc";
                        break;
                    case 2:
                        order = "hoten asc";
                        break;
                    case 3:
                        order = "hoten desc";
                        break;
                    case 4:
                        order = "ngaysinh asc";
                        break;
                    case 5:
                        order = "ngaysinh desc";
                        break;
                    case 6:
                        order = "trangthai asc";
                        break;
                    case 7:
                        order = "trangthai desc";
                        break;
                    default:
                        order = "id asc";
                        break;
                }
                createTable(Database.getListUser(order));
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser a = new addUser(username);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.searchUser(searchInput.getText()));
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.getListUser(order));
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //userDetail u = new userDetail();
                if (indexRow != -1) {
                    retriveUser();
                    //userDetail u = new userDetail(Database.getListUser(order).get(indexRow), username);
                    userDetail u = new userDetail(Database.searchAUser(id.toString()), username);
                } else {
                    userDetail u = new userDetail();
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //editUser editUser = new editUser();
                    if (indexRow != -1) {
                        retriveUser();
                        //editUser editUser = new editUser(Database.getListUser(order).get(indexRow), username);
                        editUser editUser = new editUser(Database.searchAUser(id.toString()), username);
                    } else {
                        editUser editNQL = new editUser(username);
                    }
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retriveUser();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete " + id.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    ArrayList<User> t = Database.searchUser(id.toString());

                    Database.updateOccupancyNDT(t.get(0).getHospital().getId(), 1);

                    Database.deleteUser(id.toString(), username);

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Database.updateLSNQL(0, username, dtf.format(now), "removed", id.toString());

                    String hospital = Database.searchAUser(id.toString()).getHospital().getId();
                    Database.updateLSNQL(2, username, dtf.format(now), "removed " + id.toString(), hospital);

                    Database.updateLSNDT(id.toString(), dtf.format(now), hospital);

                    Database.updateOccupancyNDT(hospital, 1);
                }
            }
        });
        sortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sắp xếp bảng theo option được select
            }
        });
        lbNYP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NecManagement n = new NecManagement(username);
                setVisible(false);
                dispose();
            }
        });

        lbStatistic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Statistic statistic = new Statistic(username);
                setVisible(false);
                dispose();
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();

                    Login frame = new Login();
                    frame.setVisible(true);
                }
            }
        });*/
    }
    public void createTable(ArrayList<User> dataList){
        ArrayList<User> list = dataList;
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", "Status", "Hospital"};
        Object[] [] data = new String[list.size()][6];

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getDoB();
            data[i][3] = list.get(i).getAddress().getWard().getName() + ", " + list.get(i).getAddress().getDistrict().getName() + ", " + list.get(i).getAddress().getCityProvince().getName();
            data[i][4] = list.get(i).getStatus();
            data[i][5] = list.get(i).getHospital().getName();
        }

        userTable.setModel(new DefaultTableModel(data, tbColName));
        if (userTable.getRowCount() > 0)
            userTable.setRowSelectionInterval(0, 0);
    }

    public void retriveUser() {
        indexRow = userTable.getSelectedRow();
        if (indexRow != -1) {
            id = userTable.getValueAt(indexRow, 0);
        }
    }

    public static void main(String[] args){
        // NQLMenu d = new NQLMenu(rootId.toString());
        NQLMenu d = new NQLMenu("nttchau");
    }
}
