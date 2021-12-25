package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentDetail extends JFrame {
    private JPanel rootPanel;
    private JTable table;
    private JButton purchaseButton;
    private JButton closeButton;
    private JLabel paid;

    PaymentDetail(){
        add(this.rootPanel);
        createTable();
        //check nếu hóa đơn đã được thanh toán đủ thì unable nút purchase
        setSize(520,400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
                    //sau khi xác nhận được pw thì cho showInputDialog để lấy số tiền muốn trả
                }

            }
        });
    }
    public void createTable(){
        String[] tbColName = {"ID","Name","Quanity","Price","Total"};
        Object[] [] data = {{"01","Gói 1","2","10000","20000"}};
        table.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String[]args){
            PaymentDetail invoice= new PaymentDetail();
    }
}
