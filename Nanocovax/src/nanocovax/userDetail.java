package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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

    userDetail(){
        add(this.rootPanel);
        createHospitalTable();
        createRelateTable();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900,600);
        setVisible(true);
    }

    void createHospitalTable(){
        String[] tbColName = {"ID", "Name","Date"};
        Object[] [] data = {{"01","Bệnh viện giả chiến số 1","20/03/2021"},{"02","Bệnh viện giả chiến số 2", "20/02/2021"}};
        hTable.setModel(new DefaultTableModel(data,tbColName));
    }
    void createRelateTable(){
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", " Status", "Hospital"};
        Object[] [] data = {{"02","Trần Thị B","20/09/1989","Sao Hỏa","F0","02"}};
        rTable.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String args[]){
        userDetail u = new userDetail();
    }
}
