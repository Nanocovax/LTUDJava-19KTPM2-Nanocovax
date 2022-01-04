package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class UserMenu extends JFrame{
    private JPanel menuPanel;
    private JLabel lbShop;
    private JLabel lbLogout;
    private JLabel lbInfo;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel tab1;
    private JPanel tab2;
    private JTable table;
    private JLabel header;
    private JLabel displayName;
    private JLabel displayID;
    private JLabel displayDOB;
    private JLabel displayAddress;
    private JLabel displaySTT;
    private JLabel displaHosName;
    private JLabel lbPayment;
    private JButton refreshButton2;
    private JTable sttTable;
    private JTable hosTable;
    private JButton refreshButton1;
    User root;

    UserMenu(){
        add(this.rootPanel);
        createTable();
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nút refresh ở tab 1
            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //nút refresh ở tab 2
            }
        });

        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /*setVisible(false);
                dispose();
                Login frame = new Login();
                frame.setVisible(true);*/

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();
                    Login frame = new Login();
                    frame.setVisible(true);
                }
            }
        });
        lbShop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                NecShop n = new NecShop();
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                Payment payment = new Payment();
            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //bấm đổi qua tab 2 thì mới đưa data vào bảng ở tab 2
                createHosTable();
                createSttTable();
            }
        });
    }

    UserMenu(String username){
        add(this.rootPanel);
        root = Database.searchAUser(username);
        createTable(Database.getListNLQ(username));
        setSize(1900,1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        displayID.setText(root.getId());
        displayName.setText(root.getName());
        displayAddress.setText(root.getAddress().getWard().getName() + ", " + root.getAddress().getDistrict().getName() + ", " + root.getAddress().getCityProvince().getName());
        displaySTT.setText(root.getStatus());
        displayDOB.setText(root.getDoB());
        displaHosName.setText(root.getHospital().getName());

        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.getListNLQ(username));
            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHosTable(Database.getLSNDT(username));
                createSttTable(Database.getStatusHistoryList(username));
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
        lbShop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                NecShop n = new NecShop(username);
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                Payment payment = new Payment(username);
            }
        });
        tabbedPane1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //bấm đổi qua tab 2 thì mới đưa data vào bảng ở tab 2
                createHosTable(Database.getLSNDT(username));
                createSttTable(Database.getStatusHistoryList(username));
            }
        });
    }

    public void createTable(){
        String[] tbColName = {"ID","Name","Date of birth","Address","Status","Hospital"};
        Object[] [] data = {{"01","Một ai đó","01/01/2001","Sao Kim","F1","BV2"}};
        table.setModel(new DefaultTableModel(data,tbColName));
    }
    public void createSttTable(){
        String[] tbColName = {"Date","Status"};
        Object[] [] data = {{"20/03/2001","F0"},{"15/03/2001","F1"}};
        sttTable.setModel(new DefaultTableModel(data,tbColName));
    }public void createHosTable(){
        String[] tbColName = {"Date","Hospital"};
        Object[] [] data = {{"15/03/2001","Bệnh viện nào đó"}};
        hosTable.setModel(new DefaultTableModel(data,tbColName));
    }

    public void createTable(ArrayList<User> dataList){
        ArrayList<User> list = dataList;
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", " Status", "Hospital"};
        Object[] [] data = new String[list.size()][6];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getDoB();
            data[i][3] = list.get(i).getAddress().getWard().getName() + ", " + list.get(i).getAddress().getDistrict().getName() + ", " + list.get(i).getAddress().getCityProvince().getName();
            data[i][4] = list.get(i).getStatus();
            data[i][5] = list.get(i).getHospital().getName();
        }

        table.setModel(new DefaultTableModel(data, tbColName));
        if (table.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);
    }

    public void createSttTable(ArrayList<StatusHistory> dataList){
        ArrayList<StatusHistory> list = dataList;
        String[] tbColName = {"Date","Status"};
        Object[] [] data = new String[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getDate() + " " + list.get(i).getTime();
            data[i][1] = list.get(i).getStatus();
        }

        sttTable.setModel(new DefaultTableModel(data,tbColName));
        if (sttTable.getRowCount() > 0)
            sttTable.setRowSelectionInterval(0, 0);
    }

    public void createHosTable(ArrayList<HospitalHistory> dataList){
        ArrayList<HospitalHistory> list = dataList;
        String[] tbColName = {"Date","Hospital"};
        Object[] [] data = new String[list.size()][2];

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getTime();
            data[i][1] = list.get(i).getNDT().getName();
        }

        hosTable.setModel(new DefaultTableModel(data,tbColName));
        if (hosTable.getRowCount() > 0)
            hosTable.setRowSelectionInterval(0, 0);
    }

    public static void main(String[]args){
        UserMenu u = new UserMenu("lqtlong");
    }
}
