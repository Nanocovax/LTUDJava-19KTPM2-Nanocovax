package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class addNDT extends JFrame{
    private JTextField idInput;
    private JTextField textField2;
    private JTextField nameInput;
    private JTextField curInput;
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
        NumberFormat nf = new DecimalFormat("000");
        idInput.setText(nf.format(Database.countNDT()));
        idInput.setEditable(false);
        //optional: tự generate id cho ndt và set vào idLabel
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idInput.getText();
                String ten = nameInput.getText();
                int sucChua =  Integer.parseInt(textField2.getText());
                int dangChua = Integer.parseInt(curInput.getText());
                boolean addSuccess =  Database.createNDT(id, ten, sucChua, dangChua);
                if (addSuccess){
                    idInput.setText(String.valueOf(Database.countNDT()));
                    nameInput.setText("");
                    textField2.setText("");
                    curInput.setText("");
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
