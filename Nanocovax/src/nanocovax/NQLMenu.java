package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

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
    }
    public void createTable(){
        String[] tbColName = {"ID", "Name","Birthdate", "Address", "Status", "Hospital"};
        Object[] [] data = {{"01","Nguyễn Văn A","01/01/1990","Trái Đất","F0","001"}};
        userTable.setModel(new DefaultTableModel(data,tbColName));

    }
    public static void main(String[] args){
        NQLMenu d = new NQLMenu();
    }
}
