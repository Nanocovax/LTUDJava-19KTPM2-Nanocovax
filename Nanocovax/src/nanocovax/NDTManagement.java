package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    NDTManagement (){
        add(this.rootPanel);
        createTable();
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editNDT editNDT = new editNDT();
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check xem user chọn 1 row trên table chưa rồi thực hiện xóa
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNDT addND =new addNDT();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
            }
        });
    }
    public JPanel getRootPanel(){
        return this.rootPanel;
    }


    public void createTable(){
        String[] tbColName = {"ID", "Tên", "Sức chứa", "Đang chứa"};
        Object[] [] data = {{"01","Bệnh viện dã chiến số 1","900","1000"}};
        ndtTable.setModel(new DefaultTableModel(data,tbColName));

    }
    public static void main(String[] args){
        NDTManagement ndtManagement = new NDTManagement();
    }
    
}
