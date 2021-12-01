package nanocovax;

import javax.swing.*;
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
        setVisible(true);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //lưu thông tin chỉnh sửa
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
