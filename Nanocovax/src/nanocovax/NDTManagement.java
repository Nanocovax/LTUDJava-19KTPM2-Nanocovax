package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class NDTManagement extends JFrame {
    private JPanel menuPanel;
    private JLabel lbNDT;
    private JLabel lbLogout;
    private JLabel lbNQL;
    private JPanel NDTPanel;
    private JLabel header;
    private JButton addButton;
    private JTextField searchBar;
    private JTable ndtTable;
    private JButton searchButton;
    private JButton editButton;
    private JButton removeButton;
    private JScrollPane scPanel;
    private JPanel rootPanel;
    private JButton refreshButton;
    int idxRow;
    Object id = null, ten = null, sucChua = null, dangChua = null;

    NDTManagement() {
        add(this.rootPanel);
        createTable(Database.getListNDT());
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idxRow != -1) {
                    retriveNDT();
                    editNDT editNDT = new editNDT(id.toString(), ten.toString(), sucChua.toString(), dangChua.toString());
                } else {
                    editNDT editNDT = new editNDT();
                }
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check xem user chọn 1 row trên table chưa rồi thực hiện xóa
                retriveNDT();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa " + ten.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    Database.deleteNDT(id.toString());
                }
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNDT addND = new addNDT();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.searchNDT(searchBar.getText()));
            }
        });

        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //hỏi user are u sure?
                //rồi quay về màn hình log in
            }
        });
        lbNQL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                AdminMenu adminMenu = new AdminMenu();
                setVisible(false);
                dispose();
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //làm mới lại table
                createTable(Database.getListNDT());
            }
        });
    }

    public void retriveNDT() {
        idxRow = ndtTable.getSelectedRow();
        if (idxRow != -1) {
            id = ndtTable.getValueAt(idxRow, 0);
            ten = ndtTable.getValueAt(idxRow, 1);
            sucChua = ndtTable.getValueAt(idxRow, 2);
            dangChua = ndtTable.getValueAt(idxRow, 3);
        }
    }

    public JPanel getRootPanel() {
        return this.rootPanel;
    }


    public void createTable(ArrayList<NoiDieuTri> dataList) {
        String[] tbColName = {"ID", "Name", "Capacity", "Occupied"};
        ArrayList<NoiDieuTri> list = dataList;
        String data[][] = new String[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = nf.format(list.get(i).getId());
            data[i][1] = list.get(i).getTen();
            data[i][2] = String.valueOf(list.get(i).getSucChua());
            data[i][3] = String.valueOf(list.get(i).getDangChua());
        }

        ndtTable.setModel(new DefaultTableModel(data, tbColName));
        if (ndtTable.getRowCount() > 0)
            ndtTable.setRowSelectionInterval(0, 0);
    }

    public static void main(String[] args) {
        NDTManagement ndtManagement = new NDTManagement();
    }

}
