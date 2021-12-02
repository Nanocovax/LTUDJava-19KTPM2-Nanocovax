package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addNDT extends JFrame{
    private JTextField idInput;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton addButton;
    private JPanel rootPanel;
    private JLabel header;
    private JLabel idLabel;
    private JLabel capacityLabel;
    private JLabel nameLabel;
    private JLabel curLabel;
    private JButton cancelButton;
    addNDT(){
        add(this.rootPanel);
        setSize(530,220);
        setResizable(false);
        setVisible(true);
        //optional: tự generate id cho ndt và set vào idLabel
        addButton.addActionListener(new ActionListener() {
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
