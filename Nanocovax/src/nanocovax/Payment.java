package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Payment extends JFrame {
    private JPanel menuPanel;
    private JLabel lbLogout;
    private JLabel lbInfo;
    private JLabel lbPayment;
    private JLabel lbShop;
    private JPanel rootPanel;
    private JTable table;
    private JButton purchaseButton;
    private JTextField input;
    private JButton searchButton;
    private JButton refreshButton;
    private JComboBox sortOpt;
    private JButton detailButton;
    private JLabel totalDebt;

    Payment(){
        add(this.rootPanel);
        createTable();
        sortOpt.setSelectedIndex(0);
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        lbInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                UserMenu u = new UserMenu();
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /*setVisible(false);
                dispose();
                Login frame = new Login();
                frame.setVisible(true);*/

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();
                    Login frame = new Login();
                    frame.setVisible(true);
                }
            }
        });
        lbShop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                NecShop n = new NecShop();
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
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PaymentDetail invoice= new PaymentDetail();
            }
        });
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                JLabel label = new JLabel("Enter a password:");
                JPasswordField pass = new JPasswordField(15);
                panel.add(label);
                panel.add(pass);

                String[] options = new String[]{"OK", "Cancel"};
                int option = JOptionPane.showOptionDialog(null, panel, "Password verify",
                        JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
                        null, options, options[1]);
                if(option == 0)
                {
                    char[] password = pass.getPassword();
                    System.out.println("Your password is: " + new String(password));
                }
                //Kiểm tra hóa đơn đã được thanh toán hoàn toàn chưa nếu rồi thì đưa ra thông báo
            }
        });
    }
    public void createTable(){
        String[] tbColName = {"ID","Date","Cost","Debt"};
        Object[] [] data = {{"01","23/04/2021","100000","90000"}};
        table.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String[]args){
        Payment p = new Payment();
    }
}
