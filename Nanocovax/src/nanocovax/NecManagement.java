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

public class NecManagement extends JFrame {
    private JPanel menuPanel;
    private JLabel lbNYP;
    private JLabel lbLogout;
    private JLabel lbUser;
    private JLabel lbStatistic;
    private JPanel rootPanel;
    private JPanel managPanel;
    private JButton addButton;
    private JTextField textField1;
    private JButton searchButton;
    private JComboBox filterOpt;
    private JButton filterButton;
    private JTable necTable;
    private JButton removeButton;
    private JButton editButton;
    private JButton refreshButton;
    private JComboBox sortOpt;
    private JTextField tfValue;
    int idxRow;
    Object id = null, tengoi = null, thoihan = null, dongia = null, gioihan = null;
    String sortValue = "id_nyp";
    String order = "asc";

    NecManagement() {
        add(rootPanel);
        createtable(Database.getListNYP(sortValue, order));
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //lọc ra giá trị trong tfValue trong cột chọn bởi cbb filterOpt

            }
        });

        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //sắp xếp bảng theo item được select
                    sortValue = sortOpt.getSelectedItem().toString();
                    if (sortValue.equals("ID: Ascending")){
                        sortValue="id_nyp";
                        order = "asc";
                    }
                    else if (sortValue.equals("ID: Descending")){
                        sortValue="id_nyp";
                        order = "desc";
                    }
                    else if (sortValue.equals("Price: Ascending")){
                        sortValue="dongia";
                        order = "asc";
                    }
                    else if (sortValue.equals("Price: Descending")){
                        sortValue="dongia";
                        order = "desc";
                    }
                    else if (sortValue.equals("Duration: Ascending")){
                        sortValue="thoihan";
                        order = "asc";
                    }
                    else if (sortValue.equals("Duration: Descending")){
                        sortValue="thoihan";
                        order = "desc";
                    }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNec a = new addNec();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createtable(Database.searchNYP(textField1.getText()));
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //làm mới lại table
                createtable(Database.getListNYP(sortValue, order));
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idxRow != -1) {
                    retriveNYP();
                    editNec editNYP = new editNec(id.toString(), tengoi.toString(), thoihan.toString(), dongia.toString(), gioihan.toString());
                } else {
                    editNec editNYP = new editNec();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check xem user chọn 1 row trên table chưa rồi thực hiện xóa
                retriveNYP();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete " + tengoi.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    Database.deleteNYP(id.toString());
                }
            }
        });
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NQLMenu d = new NQLMenu("nttchau");
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
            }
        });
    }

    public void createtable(ArrayList<NhuYeuPham> dataList) {
//        String[] tbColName = {"ID", "Name", "Limit/person", "Duration (day(s))","Price"};
//        Object[] [] data = {{"01","Gói 1","5","2","20000"},{"02","Gói 2","3","3","5000"}};
//        necTable.setModel(new DefaultTableModel(data,tbColName));
        String[] tbColName = {"ID", "Name", "Limit/person", "Duration (day(s))", "Price"};
        ArrayList<NhuYeuPham> list = dataList;
        String data[][] = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {

            data[i][0] = String.valueOf(list.get(i).getId_nyp());
            data[i][1] = list.get(i).getTengoi();
            data[i][2] = String.valueOf(list.get(i).getGioihan());
            data[i][3] = String.valueOf(list.get(i).getThoihan());
            data[i][4] = String.valueOf(list.get(i).getDongia());
        }

        necTable.setModel(new DefaultTableModel(data, tbColName));
        if (necTable.getRowCount() > 0)
            necTable.setRowSelectionInterval(0, 0);
    }

    public void retriveNYP() {
        idxRow = necTable.getSelectedRow();
        if (idxRow != -1) {
            id = necTable.getValueAt(idxRow, 0);
            tengoi = necTable.getValueAt(idxRow, 1);
            gioihan = necTable.getValueAt(idxRow, 2);
            thoihan = necTable.getValueAt(idxRow, 3);
            dongia = necTable.getValueAt(idxRow, 4);
        }
    }

    public static void main(String[] args) {
        NecManagement n = new NecManagement();
    }
}
