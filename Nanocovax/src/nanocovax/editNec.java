package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editNec extends JFrame {
    private JTextField tfName;
    private JTextField tfLimit;
    private JTextField tfDuration;
    private JTextField tfPrice;
    private JLabel tfID;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    editNec() {
        add(rootPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 220);
        setResizable(false);
        setVisible(true);
        saveButton.addActionListener(new ActionListener() {
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

    editNec(String id_nql, String id, String tengoi, String thoihan, String dongia, String gioihan) {
        tfID.setText(String.valueOf(id));
//      tfID.setEditable(false);
        tfName.setText(tengoi);
        tfDuration.setText(String.valueOf(thoihan));
        tfPrice.setText(String.valueOf(dongia));
        tfLimit.setText(String.valueOf(gioihan));

        add(this.rootPanel);
        setSize(530, 280);
        setResizable(false);
        setVisible(true);

        // set text cho các text field

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfName.getText().isEmpty() && tfName.getText().length() <= 45 && !tfDuration.getText().isEmpty() && !tfPrice.getText().isEmpty() && !tfLimit.getText().isEmpty() && Utilities.validateIfOnlyNumber(tfDuration.getText().toString()) && Utilities.validateIfOnlyNumber(tfPrice.getText().toString()) && Utilities.validateIfOnlyNumber(tfLimit.getText().toString())) {
                    Database.updateNYP(id_nql, Integer.valueOf(tfID.getText()), tfName.getText(), Integer.valueOf(tfDuration.getText()), Integer.valueOf(tfPrice.getText()), Integer.valueOf(tfLimit.getText()));
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
        editNec e = new editNec();
    }
}
