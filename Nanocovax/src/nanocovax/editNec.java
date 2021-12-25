package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class editNec extends JFrame{
    private JTextField tfName;
    private JTextField tfLimit;
    private JTextField tfDuration;
    private JButton saveButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    editNec(){
        add(rootPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,220);
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
    public static void main(String[] args){
        editNec e = new editNec();
    }
}
