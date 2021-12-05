package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class activeHistory extends JFrame {
    private JTable hisTable;
    private JLabel header;
    private JScrollPane scPanel;
    private JComboBox comboBox;
    private JLabel lb;
    private JPanel rootPanel;
    String idNQL;
    int indexRow;

    activeHistory(){

        add(this.rootPanel);
        setSize(700,550);
        setResizable(false);
        createTableUserBranchActivity();
        setVisible(true);
    }

    activeHistory(String id){
        add(this.rootPanel);
        setSize(700,550);
        setResizable(false);
        createTableUserBranchActivity();
        setVisible(true);

        idNQL = id;
        int index = comboBox.getSelectedIndex();
        switch (index) {
            case 0:
                createTableUserBranchActivity();
                break;
            case 1:
                createTableFPBranchActivity();
                break;
            case 2:
                createTableHospitalBranchActivity();
                break;
        }
    }

    /*void createTable(){
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = {{"01","Thêm người dùng","12/02/2021"}};
        hisTable.setModel(new DefaultTableModel(data,tbColName));
    }*/

    public void createTableUserBranchActivity(){
        ArrayList<UserBranchActivity> list = Database.getUserBranchActivity(idNQL);
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = new String[list.size()][3];
        hisTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = nf.format(list.get(i).getUserID());
            data[i][1] = list.get(i).getActivity() + " " + list.get(i).getUserName();
            data[i][2] = list.get(i).getDate();
        }

        hisTable.setModel(new DefaultTableModel(data, tbColName));
        if (hisTable.getRowCount() > 0)
            hisTable.setRowSelectionInterval(0, 0);
    }

    public void createTableFPBranchActivity(){
        ArrayList<FoodPackageBranchActivity> list = Database.getFPBranchActivity(idNQL);
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = new String[list.size()][3];
        hisTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = nf.format(list.get(i).getFPID());
            data[i][1] = list.get(i).getActivity() + " " + list.get(i).getFPName();
            data[i][2] = list.get(i).getDate();
        }

        hisTable.setModel(new DefaultTableModel(data, tbColName));
        if (hisTable.getRowCount() > 0)
            hisTable.setRowSelectionInterval(0, 0);
    }

    public void createTableHospitalBranchActivity(){
        ArrayList<HospitalBranchActivity> list = Database.getHospitalBranchActivity(idNQL);
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = new String[list.size()][3];
        hisTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = nf.format(list.get(i).getHospitalId());
            data[i][1] = list.get(i).getActivity() + " " + list.get(i).getHospitalName();
            data[i][2] = list.get(i).getDate();
        }

        hisTable.setModel(new DefaultTableModel(data, tbColName));
        if (hisTable.getRowCount() > 0)
            hisTable.setRowSelectionInterval(0, 0);
    }
}
