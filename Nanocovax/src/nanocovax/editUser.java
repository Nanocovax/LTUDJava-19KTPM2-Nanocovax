package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class editUser extends JFrame {
    private JPanel rootPanel;
    private JTextField tfID;
    private JTextField tfName;
    private JPanel calPanel;
    private JComboBox cbbCityPro;
    private JComboBox cbbDistrict;
    private JComboBox cbbWard;
    private JLabel header;
    private JTextField tfStatus;
    private JTextField tfHos;
    private JTextField tfRelate;
    private JButton cancelButton;
    private JButton saveButton;
    private JDateChooser dateChooser;
    editUser() throws ParseException {
        add(rootPanel);
        comboboxInit();
        setDateChooser();
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
        saveButton.addActionListener(new ActionListener() {
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
        // set selected là item  cho các cbb giống với db
    }
    void setDateChooser() throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2021");// string lấy từ db
        dateChooser = new JDateChooser(d);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(dateChooser);
    }
    public static void main(String[] args) throws ParseException {
        editUser editUser = new editUser();
    }
}
