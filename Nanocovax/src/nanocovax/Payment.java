package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Payment extends JFrame {
    private JPanel menuPanel;
    private JLabel lbLogout;
    private JLabel lbInfo;
    private JLabel lbPayment;
    private JLabel lbShop;
    private JPanel rootPanel;
    private JTable table;
    private JButton purchaseButton;
    private JButton searchButton;
    private JButton refreshButton;
    private JComboBox sortOpt;
    private JButton detailButton;
    private JLabel totalDebt;
    private JPanel calPanel;

    private JDateChooser jDateChooser;


    int idxRow;
    Object id = null, date = null, cost = null, debt = null;

    ArrayList<HoaDon> paymentList;
    String sortValue = "sohd";
    String order = "asc";


    BufferedReader br;
    PrintWriter pw;
    private static int PORT = 1024;

    Payment() {
        add(this.rootPanel);
        refreshTable("username");
        sortOpt.setSelectedIndex(0);
        setSize(1200, 600);
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
//làm mới lại table
//                paymentList = Database.getListNYP("username",sortValue, order);
//                createTable(paymentList);
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
//                PaymentDetail invoice = new PaymentDetail();
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
//                    System.out.println("Your password is: " + new String(password));
                }
                //Kiểm tra hóa đơn đã được thanh toán hoàn toàn chưa nếu rồi thì đưa ra thông báo
            }
        });
    }

    Payment(String username) {


        add(this.rootPanel);

        jDateChooser = new JDateChooser();
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(jDateChooser);

        refreshTable(username);
        sortOpt.setSelectedIndex(0);
        setSize(1200, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        lbInfo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                UserMenu u = new UserMenu(username);
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
                NecShop n = new NecShop(username);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(jDateChooser.getDate());
                System.out.println(date);

                paymentList = Database.searchPayment(username, date);
                createTable(paymentList);
                totalDebt.setText(String.valueOf(totalPayment(paymentList)));
            }
        });
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshTable(username);
            }
        });
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Database.sortListPayment(paymentList, sortOpt.getSelectedItem().toString());
                createTable(paymentList);
                totalDebt.setText(String.valueOf(totalPayment(paymentList)));
            }
        });
        detailButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idxRow != -1) {
                    retrivePayment();
                    PaymentDetail invoice = new PaymentDetail(id.toString(), String.valueOf(cost), String.valueOf(debt));
                }

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
//                    System.out.println("Your password is: " + new String(password));

                    if (Database.varifyLogin(username, String.valueOf(password)) != 2) {
                        System.out.println("Password is not correct!");
                        JOptionPane.showMessageDialog(panel,
                                "The password is incorrect. Try again.",
                                "Incorrect Password",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
//                    System.out.println("Your password is: " + new String(password));
                    retrivePayment();

                    boolean res = Database.updateHoaDon(username, id.toString(), debt.toString());

                    if (res) {
                        Socket socket = null;
                        try {
                            socket = new Socket(InetAddress.getLocalHost(), PORT);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        try {
                            pw = new PrintWriter(socket.getOutputStream(), true);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }

                        // /name
                        String message = username;
                        try {
                            message = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (message.startsWith("/name")) {
                            pw.println(username); // get real username
                        }

                        // /accepted
                        try {
                            message = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (message.startsWith("/accepted")) {

                        }
                        // /pay
                        pw.println("/pay");
                        pw.println(debt.toString());

                        try {
                            message = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (message.startsWith("/broke")) {

                        } else if (message.startsWith("/done")) {
                            refreshTable(username);
                            System.out.println("You have paid the bill");

                        } else if (message.startsWith("/failed")) {
                        }
                        // /cancel
                        pw.println("/cancel");
                    }
                }
                //Kiểm tra hóa đơn đã được thanh toán hoàn toàn chưa nếu rồi thì đưa ra thông báo
            }
        });
    }

    public void createTable(ArrayList<HoaDon> dataList) {
        String[] tbColName = {"ID", "Date", "Cost", "Debt"};
        ArrayList<HoaDon> list = dataList;
        String data[][] = new String[list.size()][4];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getDate();
            data[i][2] = list.get(i).getCost();
            data[i][3] = list.get(i).getDebt();
        }

        table.setModel(new DefaultTableModel(data, tbColName));
        if (table.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);
    }

    public void retrivePayment() {
        idxRow = table.getSelectedRow();
        if (idxRow != -1) {
            id = table.getValueAt(idxRow, 0);
            date = table.getValueAt(idxRow, 1);
            cost = table.getValueAt(idxRow, 2);
            debt = table.getValueAt(idxRow, 3);
        }
    }

    public void refreshTable(String username) {
        paymentList = Database.getListPayment(username, sortValue, order);
        createTable(paymentList);
        totalDebt.setText(String.valueOf(totalPayment(paymentList)));
    }

    public long totalPayment(ArrayList<HoaDon> dataList) {
        long total = 0;
        for (HoaDon item : dataList) {
            total += Long.parseLong(item.getDebt());
        }
        return total;
    }

    public static void main(String[] args) {
        Payment p = new Payment();
    }
}
