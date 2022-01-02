package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

public class PaymentDetail extends JFrame {
    private JPanel rootPanel;
    private JTable table;
    private JButton purchaseButton;
    private JButton closeButton;
    private JLabel debtLabel;
    private JLabel totalLabel;
//    private long total = 0;
//    private long debt = 0;


    ArrayList<NhuYeuPham> paymentDetailList;

    PaymentDetail(String sohd, String tongtien, String sono) {

        debtLabel.setText(sono);
        totalLabel.setText(tongtien);

        add(this.rootPanel);
        paymentDetailList = Database.getListPaymentDetail(sohd);
        createTable(paymentDetailList);


        //check nếu hóa đơn đã được thanh toán đủ thì unable nút purchase
        setSize(520, 400);
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
                if (option == 0) {
                    char[] password = pass.getPassword();
                    System.out.println("Your password is: " + new String(password));
                    //sau khi xác nhận được pw thì cho showInputDialog để lấy số tiền muốn trả
                }

            }
        });
    }

    public void createTable(ArrayList<NhuYeuPham> dataList) {
        String[] tbColName = {"ID", "Name", "Quanity", "Price", "Total"};
        ArrayList<NhuYeuPham> list = dataList;
        String data[][] = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = String.valueOf(list.get(i).getId_nyp());
            data[i][1] = list.get(i).getTengoi();
            data[i][2] = String.valueOf(list.get(i).getSoluong());
            data[i][3] = String.valueOf(list.get(i).getDongia());
            data[i][4] = String.valueOf(list.get(i).getDongia() * list.get(i).getSoluong());
        }

        table.setModel(new DefaultTableModel(data, tbColName));
        if (table.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);
    }


    public static void main(String[] args) {
//        PaymentDetail invoice = new PaymentDetail();
    }
}
