package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private JComboBox cbbWard;
    private JComboBox cbbDistrict;
    private JComboBox cbbCityPro;
    private JLabel wardLabel;
    private JLabel districtLabel;
    private JLabel cpLabel;
    private JPanel calPanel;
    private JDateChooser jDateChooser;
    addUser(){
        add(rootPanel);
        comboboxInit();
        jDateChooser = new JDateChooser();
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(jDateChooser);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700,300);
        setResizable(false);
        setVisible(true);
        cbbCityPro.addActionListener(new ActionListener() {
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

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Lấy ngày từ datechooser
                // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                //String dt = format.format(jDateChooser.getDate());
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    void comboboxInit(){
        cbbCityPro.addItem("Tỉnh/Thành phố");
        cbbDistrict.addItem("Quận/huyện");
        cbbWard.addItem("Phường/xã");
        //lấy danh sách tỉnh thành phố add vào cbb tỉnh thành phố
        // có thể set mặc định là item đầu tiên
    }
    public static void main(String[] args){
        addUser a = new addUser();
    }
}
