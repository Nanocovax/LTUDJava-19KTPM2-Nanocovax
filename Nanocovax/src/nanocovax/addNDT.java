package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class addNDT extends JFrame{
//    private JTextField idInput;
    private JTextField textField2;
    private JTextField nameInput;
    private JTextField curInput;
    private JButton addButton;
    private JPanel rootPanel;
    private JLabel header;
    private JLabel capacityLabel;
    private JLabel nameLabel;
    private JLabel curLabel;
    private JButton cancelButton;

    addNDT(){
        add(this.rootPanel);
        setSize(530,220);
        setResizable(false);
        setVisible(true);
        NumberFormat nf = new DecimalFormat("000");
        //optional: tự generate id cho ndt và set vào idLabel
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ten = nameInput.getText();

                if (!ten.isEmpty() && ten.length() <= 45 && !textField2.getText().toString().isEmpty() && !curInput.getText().toString().isEmpty() && Utilities.validateIfOnlyNumber(textField2.getText().toString()) && Utilities.validateIfOnlyNumber(curInput.getText().toString())) {
                    int sucChua =  Integer.parseInt(textField2.getText());
                    int dangChua = Integer.parseInt(curInput.getText());

                    if (dangChua <= sucChua) {
                        boolean addSuccess =  Database.createNDT( ten, sucChua, dangChua);
                        if (addSuccess){
                            nameInput.setText("");
                            textField2.setText("");
                            curInput.setText("");
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The input data is invalid. Please try again!");
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "The input data is invalid. Please try again!");
                }
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
