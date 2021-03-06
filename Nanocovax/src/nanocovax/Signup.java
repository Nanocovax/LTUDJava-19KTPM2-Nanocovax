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

public class Signup extends JFrame {
    private JPanel contentPane;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private String password,username;
    private JLabel error;
    private String errorText="Invalid user name or password!";
    private JLabel lblSignup;
    JButton btnLogin;
    private JLabel label;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Signup frame = new Signup();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Signup() {
        GUI();
    }

    void GUI()
    {
        setTitle("Sign Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(65, 100, 531, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUserName = new JLabel("Username");
        lblUserName.setBounds(119, 100, 91, 14);
        contentPane.add(lblUserName);

        usernameField = new JTextField();
        usernameField.setBounds(247, 101, 129, 20);
        contentPane.add(usernameField);
        usernameField.setColumns(10);

        JLabel lblPassword = new JLabel("Password\r\n");
        lblPassword.setBounds(119, 135, 91, 14);
        contentPane.add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(247, 136, 129, 20);
        contentPane.add(passwordField);

        passwordField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnLogin.doClick();
            }
        });

        btnLogin = new JButton("Sign Up");

        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                password=passwordField.getText().toString().toLowerCase();
                username=usernameField.getText().toString().toLowerCase();
                passwordField.setText("");
                usernameField.setText("");
                if(password.equals("")||username.equals(""))
                    error.setText(errorText);
                else
                {
                    error.setText("");
                    Database.createAdmin(username, password);
                }

                setVisible(false);
                dispose();

                Login frame = new Login();
                frame.setVisible(true);
            }
        });
        btnLogin.setBounds(247, 185, 115, 23);
        contentPane.add(btnLogin);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(200, 215, 220, 14);
        contentPane.add(error);

        lblSignup = new JLabel("Create Admin Account");
        lblSignup.setFont(new Font("Tahoma", Font.PLAIN, 17));
        lblSignup.setBounds(204, 26, 200, 28);
        contentPane.add(lblSignup);
    }
}
