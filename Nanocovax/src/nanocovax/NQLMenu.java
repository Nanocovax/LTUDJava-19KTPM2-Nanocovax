package nanocovax;

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

public class NQLMenu extends JFrame{
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
    static Object rootId;

    NQLMenu(String srcId){
        add(this.rootPanel);
        createTable(Database.getListUser());
        rootId = srcId;
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        sortOption.setSelectedIndex(0);
        //table được lấy ban đầu mặc định được sắp xếp theo ID chiều tăng dần

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser a = new addUser(rootId.toString());
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
                createTable(Database.getListUser());
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userDetail u = new userDetail();
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //editUser editUser = new editUser();
                    if (indexRow != -1) {
                        retriveUser();
                        editUser editUser = new editUser(Database.getListUser().get(indexRow), rootId.toString());
                    } else {
                        editUser editNQL = new editUser(rootId.toString());
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
                    Database.deleteUser(id.toString(), rootId.toString());
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
                NecManagement n = new NecManagement();
                setVisible(false);
                dispose();
            }
        });

        lbStatistic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                Statistic statistic = new Statistic();
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
        });
    }
    public void createTable(ArrayList<User> dataList){
        ArrayList<User> list = dataList;
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", "Status", "Hospital"};
        Object[] [] data = new String[list.size()][6];
        //Object[] [] data = {{"01","Nguyễn Văn A","01/01/1990","Trái Đất","F0","001"}};

        userTable.setModel(new DefaultTableModel(data,tbColName));

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
