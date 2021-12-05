package nanocovax;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class editNQL extends JFrame {
    private JTextField idInput;
    private JButton confirmButton;
    private JComboBox comboBox;
    private JLabel header;
    private JButton cancelButton;
    private JPasswordField passwordInput;
    private JButton showButton;
    private JPanel rootPanel;

    editNQL(){
        //Lấy set data từ db vào idInput, passwordInput
        //set selected cho comboBox giống với tình trạng trong db 

        passwordInput.setEchoChar('*');
        add(this.rootPanel);
        setSize(500,300);
        setResizable(false);
        setVisible(true);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                passwordInput.setEchoChar((char)0);
            }
        });
        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                passwordInput.setEchoChar('*');
            }
        });
    }

    editNQL(String id, String status){
        idInput.setText(id);
        idInput.setEditable(false);
        switch (status) {
            case "Normal":
                comboBox.setSelectedIndex(0);
                break;
            case "Non-activated":
                comboBox.setSelectedIndex(1);
                break;
            case "Blocked":
                comboBox.setSelectedIndex(2);
                break;
        }

        passwordInput.setEchoChar('*');
        add(this.rootPanel);
        setSize(500,300);
        setResizable(false);
        setVisible(true);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox.getSelectedIndex();
                String stt = "";
                switch (index) {
                    case 0:
                        stt = "bt";
                        break;
                    case 1:
                        stt = "ckh";
                        break;
                    case 2:
                        stt = "khoa";
                        break;
                }
                Database.updateNQL(idInput.getText().toString(), passwordInput.getText().toString(), stt);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                passwordInput.setEchoChar((char)0);
            }
        });
        showButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                passwordInput.setEchoChar('*');
            }
        });
    }

}
