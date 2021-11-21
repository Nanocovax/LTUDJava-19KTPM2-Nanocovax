package nanocovax;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addModerator extends JPanel {
    JTextField idField, passwordField, usernameField, modeField, roleField;
    JLabel error;
    String id,username,password;
    String err="Enter moderator";

    public addModerator() {
        setLayout(null);
        setBounds(100, 100, 840, 619);
        JLabel lblAddProduct = new JLabel("Tạo tài khoản Người quản lý");
        lblAddProduct.setBounds(300, 45, 400, 21);
        lblAddProduct.setFont(new Font("Tahoma", Font.PLAIN, 17));
        add(lblAddProduct);

        JLabel lblModeratorID = new JLabel("ID");
        lblModeratorID.setBounds(250, 130, 124, 21);
        add(lblModeratorID);

        idField = new JTextField();
        idField.setBounds(450, 130, 136, 20);
        add(idField);
        idField.setColumns(10);

        JLabel lblModeratorUsername = new JLabel("Tên đăng nhập");
        lblModeratorUsername.setBounds(250, 160, 124, 21);
        add(lblModeratorUsername);

        usernameField = new JTextField();
        usernameField.setBounds(449, 160, 136, 20);
        add(usernameField);
        usernameField.setColumns(10);

        JLabel lblModeratorPassword = new JLabel("Mật khẩu");
        lblModeratorPassword.setBounds(250, 190, 124, 21);
        add(lblModeratorPassword);

        passwordField = new JPasswordField();
        passwordField .setBounds(449, 190, 136, 20);
        add(passwordField );
        passwordField .setColumns(10);

        JButton btnAddProduct = new JButton("Thêm");
        btnAddProduct.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if(idField.getText().equals("")||usernameField.getText().equals("")||passwordField.getText().equals(""))
                {
                    error.setText(err);
                }
                else
                {
                    error.setText("");
                    id=idField.getText().trim();
                    username=usernameField.getText().trim();
                    password=passwordField.getText().trim();

                    idField.setText("");
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });
        btnAddProduct.setBounds(449, 250, 136, 23);
        add(btnAddProduct);

        error = new JLabel("");
        error.setForeground(Color.RED);
        error.setBounds(339, 92, 265, 14);
        add(error);
    }

}
