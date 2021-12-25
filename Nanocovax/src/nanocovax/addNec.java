package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addNec extends JFrame{
    private JTextField tfName;
    private JTextField tfLimit;
    private JTextField tfDuration;
    private JButton cancelButton;
    private JButton addButton;
    private JLabel header;
    private JPanel rootPanel;

    addNec(){
        add(rootPanel);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400,220);
        setResizable(false);
        setVisible(true);
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
    public static void main(String[] args){
        addNec a = new addNec();
    }
}
