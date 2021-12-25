package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addNec extends JFrame {
    private JTextField tfName;
    private JTextField tfLimit;
    private JTextField tfDuration;
    private JTextField tfPrice;
    private JButton cancelButton;
    private JButton addButton;
    private JLabel header;
    private JPanel rootPanel;

    addNec(String id_nql) {
        add(rootPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 220);
        setResizable(false);
        setVisible(true);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfName.getText();
                int duration = Integer.parseInt(tfDuration.getText());
                int price = Integer.parseInt(tfPrice.getText());
                int limit = Integer.parseInt(tfLimit.getText());
                boolean addSuccess = Database.createNYP(id_nql, name, duration, price, limit);
                if (addSuccess) {
                    tfName.setText("");
                    tfLimit.setText("");
                    tfDuration.setText("");
                    tfPrice.setText("");
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

    public static void main(String[] args) {
//        addNec a = new addNec();
    }
}
