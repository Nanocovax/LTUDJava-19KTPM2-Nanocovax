package nanocovax;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class ChangePassword extends JFrame {
    private JPanel contentPane;
    private JPasswordField passwordField, confirmedPasswordField;
    private String password, confirmedPassword;
    private JLabel error;
    private String errorText="Invalid confirmed password!";
    JButton btnChange;
    private JLabel lblChangePassword;
    private JLabel label;
    private static String username;

    public static void main(String[] args, String srcName) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ChangePassword frame = new ChangePassword(srcName);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ChangePassword(String srcName) {
        GUI();
        username = srcName;
    }

    void GUI()
    {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(65, 100, 531, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(119, 100, 91, 14);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(247, 101, 129, 20);
        contentPane.add(passwordField);
        passwordField.setColumns(10);

        JLabel lblConfirmedPassword = new JLabel("Confirmed\r\n");
        lblConfirmedPassword.setBounds(119, 135, 91, 14);
        contentPane.add(lblConfirmedPassword);

        confirmedPasswordField = new JPasswordField();
        confirmedPasswordField.setBounds(247, 136, 129, 20);
        contentPane.add(confirmedPasswordField);
        /*passwordField = new JPasswordField();
        passwordField.setBounds(247, 136, 129, 20);
        contentPane.add(passwordField);*/

        passwordField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnChange.doClick();
            }
        });

        confirmedPasswordField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnChange.doClick();
            }
        });


        btnChange = new JButton("Change");

        btnChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                password=passwordField.getText().toString().toLowerCase();
                confirmedPassword=confirmedPasswordField.getText().toString().toLowerCase();
                passwordField.setText("");
                confirmedPasswordField.setText("");
                if(password.equals("")||confirmedPassword.equals("")||!password.equals(confirmedPassword))
                    error.setText(errorText);
                else
                {
                    error.setText("");
                    if (Database.updateNQLPassword(username, password)) {
                        error.setText("");
                        NQLMenu p = new NQLMenu(username);
                        p.setVisible(true);
                    }
                }
                setVisible(false);
                dispose();
            }
        });
        btnChange.setBounds(247, 185, 115, 23);
        contentPane.add(btnChange);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(200, 215, 220, 14);
        contentPane.add(error);

        lblChangePassword = new JLabel("Nanocovax");
        lblChangePassword.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblChangePassword.setBounds(204, 26, 167, 28);
        contentPane.add(lblChangePassword);
    }
}
