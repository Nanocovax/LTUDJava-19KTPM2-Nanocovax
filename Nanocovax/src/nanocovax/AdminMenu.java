package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class AdminMenu extends JFrame {
    private JPanel rootPanel;
    private JButton addButton;
    private JTextField searchBar;
    private JButton searchButton;
    private JLabel lbNQL;
    private JLabel lbNDT;
    private JLabel lbLogout;
    private JLabel header;
    private JButton historyButton;
    private JButton removeButton;
    private JTable table;
    private JButton editButton;
    private JButton refreshButton;
    private JPanel menuPanel;
    private JPanel NQLPanel;

    AdminMenu(){
        add(this.rootPanel);
        createTable();
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //làm mới lại table
            }
        });
        lbNQL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        lbNDT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //chuyển sang màn hình quản lí nơi điều trị
                NDTManagement ndtManagement= new NDTManagement();
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
    public JPanel getRootPanel(){
        return this.rootPanel;
    }


    public void createTable(){
        String[] tbColName = {"ID", "Phân quyền", "Tình trạng"};
        Object[] [] data = {{"01","Admin","bt"}};
        table.setModel(new DefaultTableModel(data,tbColName));

    }
    public static void main(String[] args){
        AdminMenu adminMenu = new AdminMenu();
    }
}
