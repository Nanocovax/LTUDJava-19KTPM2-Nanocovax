package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.text.SimpleDateFormat;

public class activeHistory extends JFrame {
    private JTable hisTable;
    private JLabel header;
    private JScrollPane scPanel;
    private JComboBox comboBox;
    private JLabel lb;
    private JPanel rootPanel;
    String idNQL;

    int idxRow;
    Object id_nql = null, thoigian = null, hoatdong = null, id = null, id_nyp = null, id_ndt = null;

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

        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                switch (index) {
                    case 0:
                        createTableUserBranchActivity();
                        break;
                    case 1:
//                        createTableFPBranchActivity();
                        createtable(Database.getListHistoryModNes(id));
                        break;
                    case 2:
                        createTableHospitalBranchActivity();
                        break;
                }
            }
        });
    }

    /*void createTable(){
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = {{"01","Thêm người dùng","12/02/2021"}};
        hisTable.setModel(new DefaultTableModel(data,tbColName));
    }*/

    public void resetTable() {
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = new String[1][3];
        hisTable.setModel(new DefaultTableModel(data,tbColName));
    }

    public void createTableUserBranchActivity(){
        ArrayList<UserBranchActivity> list = Database.getUserBranchActivity(idNQL);
        String[] tbColName = {"ID", "Activity", "Time"};
        Object[] [] data = new String[list.size()][3];
        hisTable.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getUserID();
            data[i][1] = list.get(i).getUserName() + " is " + list.get(i).getActivity();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(list.get(i).getDate());
            data[i][2] = date;
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
            data[i][1] = list.get(i).getFPName() + " is " + list.get(i).getActivity();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(list.get(i).getDate());
            data[i][2] = date;
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
            data[i][0] = list.get(i).getHospitalId();
            data[i][1] = list.get(i).getHospitalName() + " is " + list.get(i).getActivity();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = format.format(list.get(i).getDate());
            data[i][2] = date;
        }

        hisTable.setModel(new DefaultTableModel(data, tbColName));
        if (hisTable.getRowCount() > 0)
            hisTable.setRowSelectionInterval(0, 0);
    }


    public void createtable(ArrayList<LichSuNQL> dataList) {
        String[] tbColName = {"ID", "Activity", "Time"};
        ArrayList<LichSuNQL> list = dataList;
        String data[][] = new String[list.size()][3];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getId_nyp();
            data[i][1] = list.get(i).getThoigian();
            data[i][2] = list.get(i).getHoatdong();
        }

        hisTable.setModel(new DefaultTableModel(data, tbColName));
        if (hisTable.getRowCount() > 0)
            hisTable.setRowSelectionInterval(0, 0);
    }

}
