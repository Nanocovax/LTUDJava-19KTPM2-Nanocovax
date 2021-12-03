package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editNDT extends JFrame {
    private JTextField idInput;
    private JTextField capacityInput;
    private JTextField curInput;
    private JTextField nameInput;
    private JButton cancelButton;
    private JButton editButton;
    private JPanel rootPanel;
    private JLabel header;
    private JLabel idLabel;
    private JLabel capacityLabel;
    private JLabel nameLabel;
    private JLabel curLabel;
    editNDT(){
        add(this.rootPanel);
        setSize(530,220);
        setResizable(false);
        setVisible(true);

        // set text cho các text field

        editButton.addActionListener(new ActionListener() {
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
    }
}
