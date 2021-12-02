package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddNQL extends JFrame {
    private JPanel rootPanel;
    private JTextField idInput;
    private JButton addButton;
    private JLabel head;
    private JPasswordField passwordInput;
    private JButton displayPwButton;
    private JButton cancelButton;

    AddNQL(){
        add(this.rootPanel);
        setSize(560,220);
        setResizable(false);
        setVisible(true);
        passwordInput.setEchoChar('*');

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        displayPwButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                passwordInput.setEchoChar((char)0);
            }
        });
        displayPwButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                passwordInput.setEchoChar('*');
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
