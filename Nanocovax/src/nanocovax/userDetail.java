package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class userDetail extends JFrame {

    private JPanel rootPanel;
    private JTable hTable;
    private JLabel header;
    private JLabel infoLabel;
    private JLabel historyLabel;
    private JTable rTable;
    private JPanel infoPanel;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel idInfo;
    private JLabel dobLabel;
    private JLabel addressLabel;
    private JLabel sttLabel;
    private JLabel hospitalLabel;
    private JLabel nameInfo;
    private JLabel dobInfo;
    private JLabel addrInfo;
    private JLabel hosInfo;
    private JLabel sttInfo;
    static Object rootId = null;

    userDetail(){
        add(this.rootPanel);
        createHospitalTable();
        createRelateTable();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900,600);
        setVisible(true);
    }

    userDetail(User root, String srcId) {
        add(this.rootPanel);
        rootId = srcId;

        idInfo.setText(root.getId());
        nameInfo.setText(root.getName());
        dobInfo.setText(root.getDoB());
        addrInfo.setText(root.getAddress().getWard().getName() + ", " + root.getAddress().getDistrict().getName() + ", " + root.getAddress().getCityProvince().getName());
        sttInfo.setText(root.getStatus());
        hosInfo.setText(root.getHospital().getName());

        createHospitalTable(Database.getLSNDT(root.getId()));
        createRelateTable(Database.getListNLQ(root.getId()));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900,600);
        setVisible(true);
    }

    void createHospitalTable(){
        String[] tbColName = {"ID", "Name","Date"};
        Object[] [] data = {{"01","Bệnh viện giả chiến số 1","20/03/2021"},{"02","Bệnh viện giả chiến số 2", "20/02/2021"}};
        hTable.setModel(new DefaultTableModel(data,tbColName));
    }

    void createHospitalTable(ArrayList<HospitalHistory> dataList){
        ArrayList<HospitalHistory> list = dataList;
        String[] tbColName = {"ID", "Name", "Date"};
        Object[] [] data = new String[list.size()][3];
        hTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = list.get(i).getNDT().getId();
            data[i][1] = list.get(i).getNDT().getName();
            data[i][2] = list.get(i).getTime();
        }

        hTable.setModel(new DefaultTableModel(data, tbColName));
        if (hTable.getRowCount() > 0)
            hTable.setRowSelectionInterval(0, 0);
    }

    void createRelateTable(){
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", " Status", "Hospital"};
        Object[] [] data = {{"02","Trần Thị B","20/09/1989","Sao Hỏa","F0","02"}};
        rTable.setModel(new DefaultTableModel(data,tbColName));
    }

    void createRelateTable(ArrayList<User> dataList){
        ArrayList<User> list = dataList;
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", " Status", "Hospital"};
        Object[] [] data = new String[list.size()][6];
        rTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getName();
            data[i][2] = list.get(i).getDoB();
            data[i][3] = list.get(i).getAddress().getWard().getName() + ", " + list.get(i).getAddress().getDistrict().getName() + ", " + list.get(i).getAddress().getCityProvince().getName();
            data[i][4] = list.get(i).getStatus();
            data[i][5] = list.get(i).getHospital().getName();
        }

        rTable.setModel(new DefaultTableModel(data, tbColName));
        if (rTable.getRowCount() > 0)
            rTable.setRowSelectionInterval(0, 0);
    }

    public static void main(String args[]){
        userDetail u = new userDetail();
    }
}
