package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentSystem extends JFrame{
    private JPanel root;
    private JTabbedPane tabbedPane1;
    private JPanel tab1;
    private JPanel tab2;
    private JTextArea display;
    private JButton refreshButton;
    private JTable table;
    private JComboBox cbbUser;

    PaymentSystem(){
        add(root);
        createTable();
        display.setEditable(false);
        //sử dụng append đối với display
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Payment System");
        setVisible(true);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }
    public void createTable(){
        String[] col={"ID","Time","Expense"};
        table.setModel(new DefaultTableModel(col,0));
    }
    public static void main(String[] args) {
        PaymentSystem p = new PaymentSystem();
    }
}
