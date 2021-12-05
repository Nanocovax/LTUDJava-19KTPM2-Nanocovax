package nanocovax;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addUser extends JFrame {
    private JPanel rootPanel;
    private JLabel header;
    private JTextField tfName;
    private JTextField tfStatus;
    private JTextField tfHospital;
    private JTextField tfRelate;
    private JButton cancelButton;
    private JTextField tfID;
    private JLabel idLabel;
    private JLabel nameLabel;
    private JLabel dobLabel;
    private JLabel addrLabel;
    private JLabel sttLabel;
    private JLabel hosLabel;
    private JLabel relateLabel;
    private JButton addButton;
    private JTextField houseNo;
    private JComboBox cbbWard;
    private JComboBox cbbDistrict;
    private JComboBox cbbCityPro;
    private JLabel wardLabel;
    private JLabel districtLabel;
    private JLabel cpLabel;

    addUser(){
        add(rootPanel);
        comboboxInit();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800,600);
        setVisible(true);
        cbbWard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lấy item cho cbbDistrict dựa vào cbbCityPro

            }
        });
        cbbDistrict.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // lấy item cho cbbWard dựa vào cbbDistrict
            }
        });
    }
    void comboboxInit(){
        cbbCityPro.addItem("Tỉnh/Thành phố");
        cbbDistrict.addItem("Quận/huyện");
        cbbWard.addItem("Phường/xã");
    }
    public static void main(String[] args){
        addUser a = new addUser();
    }
}
