package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AdminMenu {
    private JPanel rootPanel;
    private JLabel ndtLabel;
    private JLabel nqlLabel;
    private JPanel Panel1;
    private JLabel logoutLabel;
    private JButton addBtn;
    private JTextField textField1;
    private JButton searchBtn;
    private JTable jTable;
    private JScrollPane scrollPanel;
    private JButton hisBtn;
    private JButton removeBtn;


    public AdminMenu() {


        nqlLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        ndtLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        logoutLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("Admin");
        frame.setContentPane(new AdminMenu().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,600);
        frame.setVisible(true);
    }
}
