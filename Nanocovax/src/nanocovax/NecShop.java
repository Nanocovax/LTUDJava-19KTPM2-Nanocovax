package nanocovax;

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

public class NecShop extends JFrame {
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

    long totalMoney= 0;
    long purchaseMoney = 0;

    BufferedReader br;
    PrintWriter pw;
    private static int PORT = 1024;

    NecShop() {
        add(this.rootPanel);
        sortOpt.setSelectedIndex(0);
        cartList = new ArrayList<NhuYeuPham>();
        nesList = Database.getListNYP(sortValue, order, "", "");
        createTable(nesList);
        createCart(cartList);
        setSize(1320, 800);
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
                //item được add sẽ hiện lên table của phần cart
                //mỗi lần add 1 item thì set grand total đúng với tổng tiền
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
                //làm mới lại table
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
                    System.out.println("Your password is: " + new String(password));

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

                    }
                    else if (message.startsWith("/done")) {

                    }
                    else if (message.startsWith("/failed")) {

                    }

                    // /cancel
                    pw.println("/cancel");

                    /*try {
                        new Client("username",prePur.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }*/
                }
                //kiểm tra sau khi pre-purchase trước 1 số tiền thì có lớn hơn hạn mức  tối thiểu không
                //purchase thành công thì remove cart đưa grand total về 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xóa 1 item được chọn ở cart
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
                //xóa tất cả item khỏi cart
                cartList.clear();
                createCart(cartList);
            }
        });
    }

    NecShop(String username) {
        add(this.rootPanel);
        sortOpt.setSelectedIndex(0);
        cartList = new ArrayList<NhuYeuPham>();
        nesList = Database.getListNYP(sortValue, order, "", "");
        createTable(nesList);
        createCart(cartList);
        setSize(1320, 800);
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
                //chọn thứ tự đê sắp xếp
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
                //item được add sẽ hiện lên table của phần cart
                //mỗi lần add 1 item thì set grand total đúng với tổng tiền
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
                //làm mới lại table
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
                    System.out.println("Your password is: " + new String(password));

                    Database.saveHoaDon("username",String.valueOf(totalMoney), prePur.getText(), cartList);

                    try {
                        new Client(username, prePur.getText());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                //kiểm tra sau khi pre-purchase trước 1 số tiền thì có lớn hơn hạn mức  tối thiểu không
                //purchase thành công thì remove cart đưa grand total về 0
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //xóa 1 item được chọn ở cart
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
                //xóa tất cả item khỏi cart
                cartList.clear();
                createCart(cartList);
            }
        });
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

    public long grandTotal(ArrayList<NhuYeuPham> dataList){
        long total = 0;
        for(NhuYeuPham item:dataList){
            total += item.getSoluong()*item.getDongia();
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
        grandTotal.setText(String.valueOf(totalMoney)+" VND");
    }

    public static void main(String[] args) {
        NecShop n = new NecShop();
    }
}
