package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    NecManagement(){
        add(rootPanel);
        createtable();
        setSize(1200,600);
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

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    void createtable(){
        String[] tbColName = {"ID", "Name", "Limit/person", "Duration (day(s))","Price"};
        Object[] [] data = {{"01","Gói 1","5","2","20000"},{"02","Gói 2","3","3","5000"}};
        necTable.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String[] args){
        NecManagement n = new NecManagement();
    }
}
