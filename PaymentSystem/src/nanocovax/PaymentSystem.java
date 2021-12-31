package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class PaymentSystem extends JFrame implements Runnable {
    private JPanel root;
    private JTabbedPane tabbedPane1;
    private JPanel tab1;
    private JPanel tab2;
    private JTextArea display;
    private JButton refreshButton;
    private JTable table;
    private JComboBox cbbUser;

    Thread thread;
    BackEnd backEnd;

    PaymentSystem(){
        add(root);
        //createTable();
        display.setEditable(false);
        //sử dụng append đối với display
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Payment System");
        setVisible(true);
        thread = new Thread(this);
        thread.start();

        ArrayList<Customer> list = Database.getCustomerList();
        for (Customer s : list) {
            cbbUser.addItem(s.getId());
        }
        cbbUser.setSelectedItem(null);

        backEnd = new BackEnd(display);
    }

    public void run() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbbUser.removeAllItems();

                ArrayList<Customer> list = Database.getCustomerList();
                for (Customer s : list) {
                    cbbUser.addItem(s.getId());
                }
                cbbUser.setSelectedItem(null);
            }
        });

        cbbUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbbUser.getSelectedItem() != null) {
                    createTable();
                }
            }
        });
    }

    public void createTable(){
        ArrayList<TransactionHistory> dataList = Database.getTransactionList(cbbUser.getSelectedItem().toString());
        String[] col={"ID","Time","Expense"};
        Object[] [] data = new String[dataList.size()][3];

        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i).getId();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            data[i][1] = dateFormat.format(dataList.get(i).getDate());
            data[i][2] = Integer.toString(dataList.get(i).getExpense());
        }

        table.setModel(new DefaultTableModel(data,col));
        if (table.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);
    }

    public static void main(String[] args) throws Exception {
        PaymentSystem p = new PaymentSystem();
    }
}

class BackEnd implements Runnable {
    private static final int PORT = 1024;
    private static HashSet<String> clientList = new HashSet<String>();
    private static HashSet<PrintWriter> writerList = new HashSet<PrintWriter>();
    static JTextArea display;

    Thread thread;

    BackEnd(JTextArea src) {
        display = src;
        thread = new Thread(this);
        thread.start();
    }

    private static class Operating extends Thread {
        private String name;
        private Socket socket;
        private BufferedReader br;
        private PrintWriter pw;

        public Operating(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream(), true);

                while (true) {
                    pw.println("/name");
                    name = br.readLine();
                    if (name == null) {
                        return;
                    }
                    synchronized (clientList) {
                        if (!clientList.contains(name)) {
                            clientList.add(name);
                            break;
                        }
                    }
                }

                pw.println("/accepted");
                display.append(name + " has been accepted to connect to the service.\n");
                writerList.add(pw);

                while (true) {
                    String input = br.readLine();
                    if (input == null) {
                        return;
                    }

                    if (input.contains("/add")) {
                        boolean res = Database.createCustomer(name);
                        if (res) {
                            pw.println("/done");
                            display.append(name + " has registered the service.\n");
                        }
                        else {
                            pw.println("/failed");
                            display.append(name + " has failed to register the service.\n");
                        }
                    }
                    else if (input.contains("/pay")) {
                        input = br.readLine();
                        int expense = Integer.parseInt(input);
                        int res = Database.doATransaction(name, expense);
                        if (res == 0) {
                            pw.println("/broke");
                            display.append(name + " has failed to do a transaction due to inadequate account balance.\n");
                        }
                        else if (res == 1) {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            Database.updateTransactionHistory(name, dtf.format(now), expense);

                            display.append(name + " has done a transaction.\n");
                            pw.println("/done");
                        }
                        else {
                            display.append(name + " has failed to do a transaction due to system error.\n");
                            pw.println("/failed");
                        }
                    }
                    else if (input.contains("/cancel")) {
                        display.append(name + " has closed connection to the service.\n");
                        pw.println("/canceled");
                        break;
                    }

                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                if (name != null) {
                    clientList.remove(name);
                }
                if (pw != null) {
                    writerList.remove(pw);
                }
                try {
                    socket.close();
                } catch (IOException e) {
                }
            }
        }
    }

    public void run() {
        System.out.println("The server is running at port " + PORT);
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                new Operating(server.accept()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
