package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

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

    NQLMenu(){

        add(this.rootPanel);
        createTable();

        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        sortOption.setSelectedIndex(0);
        //table được lấy ban đầu mặc định được sắp xếp theo ID chiều tăng dần

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
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userDetail u = new userDetail();
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
        sortOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public void createTable(){
        String[] tbColName = {"ID", "Name","Date of Birth", "Address", "Status", "Hospital"};
        Object[] [] data = {{"01","Nguyễn Văn A","01/01/1990","Trái Đất","F0","001"}};
        userTable.setModel(new DefaultTableModel(data,tbColName));

    }
    public static void main(String[] args){
        NQLMenu d = new NQLMenu();
    }
}
