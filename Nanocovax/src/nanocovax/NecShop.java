package nanocovax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
import java.util.ArrayList;

public class NecShop extends JFrame implements Runnable {
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
    private JButton refreshBtn;
    int idxRowNes, idxRowCart;
    Object id = null, tengoi = null, thoihan = null, dongia = null, gioihan = null;
    Object idItemInCart = null;
    ArrayList<NhuYeuPham> nesList;
    ArrayList<NhuYeuPham> cartList;
    String sortValue = "id_nyp";
    String order = "asc";

    long totalMoney = 0;

    BufferedReader br;
    PrintWriter pw;
    private static int PORT = 1024;

    ArrayList<Long> duNo;

    private final long DEBT_LIMIT = 100000;

    String username = "245275679";
    Thread thread;
    static Logger logger = LogManager.getLogger(NecShop.class);
    NecShop() {
        add(this.rootPanel);
        sortOpt.setSelectedIndex(0);
        cartList = new ArrayList<NhuYeuPham>();
        nesList = Database.getListNYP(sortValue, order, "", "");
        createTable(nesList);
        createCart(cartList);
        setSize(1900,900);
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
                    logger.info("User - Log out");
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
                logger.info("User - Shop -> user's detail");
                UserMenu u = new UserMenu();
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                logger.info("User - Shop -> Payment");
                Payment payment = new Payment();
            }
        });
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ch???n th??? t??? ???? s???p x???p
                Database.sortListNYP(nesList, sortOpt.getSelectedItem().toString());
                createTable(nesList);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nesList = Database.searchNYP(input.getText());
                createTable(nesList);
                logger.info("User - Search for"+ input.getText());
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //item ???????c add s??? hi???n l??n table c???a ph???n cart
                //m???i l???n add 1 item th?? set grand total ????ng v???i t???ng ti???n
                if (idxRowNes != -1) {
                    retriveNYP();
                    for (NhuYeuPham item : cartList) {
                        if (item.getId_nyp() == Integer.parseInt(id.toString())) {
                            item.setSoluong(item.getSoluong() + 1);
                            createCart(cartList);
                            return;
                        }
                    }

                    NhuYeuPham newNYP = new NhuYeuPham();
                    newNYP.setId_nyp(Integer.parseInt(id.toString()));
                    newNYP.setTengoi(tengoi.toString());
                    newNYP.setGioihan(Integer.parseInt(gioihan.toString()));
                    newNYP.setDongia(Integer.parseInt(dongia.toString()));
                    newNYP.setThoihan(Integer.parseInt(thoihan.toString()));
                    cartList.add(newNYP);
                    createCart(cartList);
                    logger.info("User - add an item to cart");
                }
            }
        });
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //l??m m???i l???i table
                nesList = Database.getListNYP(sortValue, order, "", "");
                createTable(nesList);
                createCart(cartList);
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

                    //Database.saveHoaDon("username",String.valueOf(totalMoney), prePur.getText(), cartList);

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
                    String message = null;
                    try {
                        message = br.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (message.startsWith("/name")) {
                        pw.println("19127201"); // get real username
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
                    pw.println(prePur.getText().toString());

                    try {
                        message = br.readLine();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    if (message.startsWith("/broke")) {

                    } else if (message.startsWith("/done")) {

                    } else if (message.startsWith("/failed")) {

                    }

                    // /cancel
                    pw.println("/cancel");

                }
                //ki???m tra sau khi pre-purchase tr?????c 1 s??? ti???n th?? c?? l???n h??n h???n m???c  t???i thi???u kh??ng
                //purchase th??nh c??ng th?? remove cart ????a grand total v??? 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a 1 item ???????c ch???n ??? cart
                retriveNYPCart();
                for (int i = cartList.size() - 1; i >= 0; i--) {
                    if (cartList.get(i).getId_nyp() == Integer.parseInt(idItemInCart.toString())) {
                        cartList.remove(i);
                        createCart(cartList);
                        return;
                    }
                }
                logger.info("User - Remove an item from cart");
            }
        });
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a t???t c??? item kh???i cart
                cartList.clear();
                createCart(cartList);
                logger.info("User - Remove all items from cart");
            }
        });
    }

    public void run() {
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
                    logger.info("User - Log out");
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
                logger.info("User - Shop -> user's detail");
                UserMenu u = new UserMenu(username);
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                logger.info("User - Shop -> Payment");
                Payment payment = new Payment(username);
            }
        });
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ch???n th??? t??? ???? s???p x???p
                Database.sortListNYP(nesList, sortOpt.getSelectedItem().toString());
                createTable(nesList);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nesList = Database.searchNYP(input.getText());
                createTable(nesList);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //item ???????c add s??? hi???n l??n table c???a ph???n cart
                //m???i l???n add 1 item th?? set grand total ????ng v???i t???ng ti???n
                if (idxRowNes != -1) {
                    retriveNYP();
                    for (NhuYeuPham item : cartList) {
                        if (item.getId_nyp() == Integer.parseInt(id.toString())) {
                            item.setSoluong(item.getSoluong() + 1);
                            createCart(cartList);
                            return;
                        }
                    }

                    NhuYeuPham newNYP = new NhuYeuPham();
                    newNYP.setId_nyp(Integer.parseInt(id.toString()));
                    newNYP.setTengoi(tengoi.toString());
                    newNYP.setGioihan(Integer.parseInt(gioihan.toString()));
                    newNYP.setDongia(Integer.parseInt(dongia.toString()));
                    newNYP.setThoihan(Integer.parseInt(thoihan.toString()));
                    cartList.add(newNYP);
                    createCart(cartList);
                    logger.info("User - add an item to cart");
                }
            }
        });
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //l??m m???i l???i table
                nesList = Database.getListNYP(sortValue, order, "", "");
                createTable(nesList);
                createCart(cartList);
            }
        });
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (prePur.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter the pre-purchase!",
                            "Missing Pre-purchase",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
                    if (Database.varifyLogin(username, String.valueOf(password)) != 2) {
                        System.out.println("Password is not correct!");
                        JOptionPane.showMessageDialog(panel,
                                "The password is incorrect. Try again.",
                                "Incorrect Password",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (duNo.get(1) + totalMoney - Long.parseLong(prePur.getText()) > DEBT_LIMIT) {
                        System.out.println("You have exceeded the debt limit");
                        JOptionPane.showMessageDialog(panel,
                                "You have exceeded the debt limit " + DEBT_LIMIT + " VND",
                                "Debt Limit",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
//                        System.out.println("Your password is: " + new String(password));

                        Database.saveHoaDon(username, String.valueOf(totalMoney), prePur.getText(), cartList);

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
                        pw.println(prePur.getText());

                        try {
                            message = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (message.startsWith("/broke")) {

                        } else if (message.startsWith("/done")) {
                            prePur.setText("");
                            cartList.clear();
                            createCart(cartList);
                            duNo = Database.getDuNo(username);

                            System.out.println("You have paid the bill");

                        } else if (message.startsWith("/failed")) {

                        }

                        // /cancel
                        pw.println("/cancel");

                    }
                }
                //ki???m tra sau khi pre-purchase tr?????c 1 s??? ti???n th?? c?? l???n h??n h???n m???c  t???i thi???u kh??ng
                //purchase th??nh c??ng th?? remove cart ????a grand total v??? 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a 1 item ???????c ch???n ??? cart
                retriveNYPCart();
                for (int i = cartList.size() - 1; i >= 0; i--) {
                    if (cartList.get(i).getId_nyp() == Integer.parseInt(idItemInCart.toString())) {
                        cartList.remove(i);
                        createCart(cartList);
                        logger.info("User - Remove an item from cart");
                        return;
                    }
                }
            }
        });
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a t???t c??? item kh???i cart
                cartList.clear();
                createCart(cartList);
                logger.info("User - Remove all items from cart");
            }
        });
    }

    NecShop(String username) {
        add(this.rootPanel);
        sortOpt.setSelectedIndex(0);
        cartList = new ArrayList<NhuYeuPham>();
        duNo = Database.getDuNo(username);

        nesList = Database.getListNYP(sortValue, order, "", "");
        createTable(nesList);
        createCart(cartList);
        setSize(1900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        this.username = username;
        thread = new Thread(this);
        thread.start();

        /*lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

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
                UserMenu u = new UserMenu(username);
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                setVisible(false);
                dispose();
                Payment payment = new Payment(username);
            }
        });
        sortOpt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ch???n th??? t??? ???? s???p x???p
                Database.sortListNYP(nesList, sortOpt.getSelectedItem().toString());
                createTable(nesList);
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nesList = Database.searchNYP(input.getText());
                createTable(nesList);
            }
        });
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //item ???????c add s??? hi???n l??n table c???a ph???n cart
                //m???i l???n add 1 item th?? set grand total ????ng v???i t???ng ti???n
                if (idxRowNes != -1) {
                    retriveNYP();
                    for (NhuYeuPham item : cartList) {
                        if (item.getId_nyp() == Integer.parseInt(id.toString())) {
                            item.setSoluong(item.getSoluong() + 1);
                            createCart(cartList);
                            return;
                        }
                    }

                    NhuYeuPham newNYP = new NhuYeuPham();
                    newNYP.setId_nyp(Integer.parseInt(id.toString()));
                    newNYP.setTengoi(tengoi.toString());
                    newNYP.setGioihan(Integer.parseInt(gioihan.toString()));
                    newNYP.setDongia(Integer.parseInt(dongia.toString()));
                    newNYP.setThoihan(Integer.parseInt(thoihan.toString()));
                    cartList.add(newNYP);
                    createCart(cartList);
                }
            }
        });
        refreshBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //l??m m???i l???i table
                nesList = Database.getListNYP(sortValue, order, "", "");
                createTable(nesList);
                createCart(cartList);
            }
        });
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (prePur.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter the pre-purchase!",
                            "Missing Pre-purchase",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                else if (Long.parseLong(prePur.getText()) > totalMoney){
                    JOptionPane.showMessageDialog(null,
                            "Your pre-purchase is larger than total price!!!",
                            "Warning!",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
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
                    if (Database.varifyLogin(username, String.valueOf(password)) != 2) {
                        System.out.println("Password is not correct!");
                        JOptionPane.showMessageDialog(panel,
                                "The password is incorrect. Try again.",
                                "Incorrect Password",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    if (duNo.get(1) + totalMoney - Long.parseLong(prePur.getText()) > DEBT_LIMIT) {
                        System.out.println("You have exceeded the debt limit");
                        JOptionPane.showMessageDialog(panel,
                                "You have exceeded the debt limit " + DEBT_LIMIT + " VND",
                                "Debt Limit",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
//                        System.out.println("Your password is: " + new String(password));

                        Database.saveHoaDon(username, String.valueOf(totalMoney), prePur.getText(), cartList);

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
                        pw.println(prePur.getText());

                        try {
                            message = br.readLine();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        if (message.startsWith("/broke")) {

                        } else if (message.startsWith("/done")) {
                            prePur.setText("");
                            cartList.clear();
                            createCart(cartList);
                            duNo = Database.getDuNo(username);

                            System.out.println("You have paid the bill");

                        } else if (message.startsWith("/failed")) {

                        }

                        // /cancel
                        pw.println("/cancel");

                    }
                }
                //ki???m tra sau khi pre-purchase tr?????c 1 s??? ti???n th?? c?? l???n h??n h???n m???c  t???i thi???u kh??ng
                //purchase th??nh c??ng th?? remove cart ????a grand total v??? 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a 1 item ???????c ch???n ??? cart
                retriveNYPCart();
                for (int i = cartList.size() - 1; i >= 0; i--) {
                    if (cartList.get(i).getId_nyp() == Integer.parseInt(idItemInCart.toString())) {
                        cartList.remove(i);
                        createCart(cartList);
                        return;
                    }
                }
            }
        });
        removeAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //x??a t???t c??? item kh???i cart
                cartList.clear();
                createCart(cartList);
            }
        });*/
    }

    public void createTable(ArrayList<NhuYeuPham> dataList) {
        String[] tbColName = {"ID", "Name", "Limit/person", "Duration (day(s))", "Price"};
        ArrayList<NhuYeuPham> list = dataList;
        String data[][] = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = String.valueOf(list.get(i).getId_nyp());
            data[i][1] = list.get(i).getTengoi();
            data[i][2] = String.valueOf(list.get(i).getGioihan());
            data[i][3] = String.valueOf(list.get(i).getThoihan());
            data[i][4] = String.valueOf(list.get(i).getDongia());
        }

        itemList.setModel(new DefaultTableModel(data, tbColName));
        if (itemList.getRowCount() > 0)
            itemList.setRowSelectionInterval(0, 0);
    }

    public void retriveNYP() {
        idxRowNes = itemList.getSelectedRow();
        if (idxRowNes != -1) {
            id = itemList.getValueAt(idxRowNes, 0);
            tengoi = itemList.getValueAt(idxRowNes, 1);
            gioihan = itemList.getValueAt(idxRowNes, 2);
            thoihan = itemList.getValueAt(idxRowNes, 3);
            dongia = itemList.getValueAt(idxRowNes, 4);
        }
    }

    public void retriveNYPCart() {
        idxRowCart = cart.getSelectedRow();
        if (idxRowCart != -1) {
            idItemInCart = cart.getValueAt(idxRowCart, 0);
        }
    }

    public long grandTotal(ArrayList<NhuYeuPham> dataList) {
        long total = 0;
        for (NhuYeuPham item : dataList) {
            total += item.getSoluong() * item.getDongia();
        }
        return total;
    }

    public void createCart(ArrayList<NhuYeuPham> dataList) {
        String[] tbColName = {"ID", "Name", "Quanity", "Price", "Total"};
        ArrayList<NhuYeuPham> list = dataList;
        String data[][] = new String[list.size()][5];
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = String.valueOf(list.get(i).getId_nyp());
            data[i][1] = list.get(i).getTengoi();
            data[i][2] = String.valueOf(list.get(i).getSoluong());
            data[i][3] = String.valueOf(list.get(i).getDongia());
            data[i][4] = String.valueOf(list.get(i).getSoluong() * list.get(i).getDongia());
        }

        cart.setModel(new DefaultTableModel(data, tbColName));
        if (cart.getRowCount() > 0)
            cart.setRowSelectionInterval(0, 0);
        totalMoney = grandTotal(cartList);
        grandTotal.setText(String.valueOf(totalMoney) + " VND");
    }

    public static void main(String[] args) {
        NecShop n = new NecShop();
    }
}
