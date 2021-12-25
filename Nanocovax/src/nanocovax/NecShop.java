package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NecShop extends JFrame{
    private JPanel menuPanel;
    private JLabel lbLogout;
    private JLabel lbInfo;
    private JLabel lbPayment;
    private JLabel lbShop;
    private JTextField input;
    private JButton searchButton;
    private JTable itemList;
    private JButton addButton;
    private JComboBox sortOpt;
    private JTable cart;
    private JButton purchaseButton;
    private JButton removeButton;
    private JLabel Cart;
    private JButton removeAllButton;
    private JPanel rootPanel;
    private JLabel grandTotal;
    private JTextField prePur;

    NecShop(){
        add(this.rootPanel);
        sortOpt.setSelectedIndex(0);
        createTable();
        createCart();
        setSize(1320,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


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
        lbInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                UserMenu u = new UserMenu();
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                Payment payment = new Payment();
            }
        });
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //chọn thứ tự đê sắp xếp
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //item được add sẽ hiện lên table của phần cart
                //mỗi lần add 1 item thì set grand total đúng với tổng tiền
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
                //kiểm tra sau khi pre-purchase trước 1 số tiền thì có lớn hơn hạn mức  tối thiểu không
                //purchase thành công thì remove cart đưa grand total về 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xóa 1 item được chọn ở cart
            }
        });
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xóa tất cả item khỏi cart
            }
        });
    }
    public void createTable(){
        String[] tbColName = {"ID","Name","Limit","Duration","Price"};
        Object[] [] data = {{"01","Gói 1","5","3","10000"},{"02","Gói 2","2","7","50000"}};
        itemList.setModel(new DefaultTableModel(data,tbColName));
    }
    public void createCart(){
        String[] tbColName = {"ID","Name","Quanity","Price","Total"};
        Object[] [] data = {{"01","Gói 1","2","10000","20000"}};
        cart.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String[]args){
        NecShop n = new NecShop();
    }
}
