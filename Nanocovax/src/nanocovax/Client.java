package nanocovax;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JFrame {
    int width = 800;
    int height = 600;

    SpringLayout layout;
    JFrame f;
    JPanel p;
    JTextArea a;
    JTextField t;
    JScrollPane sp;

    BufferedReader br;
    PrintWriter pw;

    String username, money;

    private static int PORT = 1024;

    Client(String username, String money) throws IOException {
        this.username = username;
        this.money = money;

        layout = new SpringLayout();

        // Main client
        f = new JFrame("Client Chat");
        f.setSize(width, height);
        f.setVisible(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setBackground(Color.white);
        f.setResizable(false);

        p = new JPanel();
        p.setLayout(layout);
        a = new JTextArea(32, 64);
//        a.setEditable(false);
        a.setPreferredSize(new Dimension(52, 26));
        t = new JTextField(52);
        sp = new JScrollPane(a, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        a.setLineWrap(true);

        layout.putConstraint(SpringLayout.WEST, t, 4, SpringLayout.WEST, p);
        layout.putConstraint(SpringLayout.SOUTH, t, -4, SpringLayout.SOUTH, p);
        layout.putConstraint(SpringLayout.EAST, p, 4, SpringLayout.EAST, t);

        layout.putConstraint(SpringLayout.NORTH, sp, 4, SpringLayout.NORTH, p);
        layout.putConstraint(SpringLayout.WEST, sp, 0, SpringLayout.WEST, t);
        layout.putConstraint(SpringLayout.SOUTH, sp, -4, SpringLayout.NORTH, t);
        layout.putConstraint(SpringLayout.EAST, sp, 0, SpringLayout.EAST, t);

        p.add(sp); p.add(t);
        f.add(p);

        t.addActionListener(e -> {
            pw.println(t.getText());
            t.setText("");
        });

        f.setVisible(true);
        run();
//        return;
    }

    private String getClientName() {
        return JOptionPane.showInputDialog(f, "Please enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
    }

    private void run() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), PORT);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream(), true);

        while (true) {
            String message = br.readLine();
            if (message.startsWith("/name")) {
//                String name = getClientName();
                String name = username;
                f.setTitle(name);
//                pw.println(name);
            } else if (message.startsWith("/accepted")) {
                t.setEditable(true);
            } else if (message.startsWith("/pay")) {
                String[] parts = message.split("/pay");
                pw.println(message);

            } else {
                a.append(message + "\n");
            }
            pw.println(message);
        }
    }


    public static void main(String[] args) throws Exception {
        Client client = new Client("123","0");
//        client.f.setVisible(true);
//        client.run();
    }
}
