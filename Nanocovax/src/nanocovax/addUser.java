package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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
    static Object rootId = null;

    ArrayList<CityProvince> cPList = Database.getCityProvinceList();
    ArrayList<District> dList;
    ArrayList<Ward> wList;

    addUser(String srcId){
        add(rootPanel);
        comboboxInit();
        rootId = srcId;
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
                cbbDistrict.removeAllItems();
                cbbWard.removeAllItems();
                int indexCityPro = cbbCityPro.getSelectedIndex();
                dList = Database.getDistrictList(cPList.get(indexCityPro).getId());
                for (District x: dList) {
                    cbbDistrict.addItem(x.getName());
                }
            }
        });

        cbbDistrict.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbbWard.removeAllItems();
                if (cbbDistrict.getItemCount() != 0) {
                    int indexDistrict = cbbDistrict.getSelectedIndex();
                    wList = Database.getWardList(dList.get(indexDistrict).getId());
                    for (Ward x: wList) {
                        cbbWard.addItem(x.getName());
                    }
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = tfID.getText().toString();
                String name = tfName.getText().toString();

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(jDateChooser.getDate());

                String status = tfStatus.getText().toString();
                String hospital = tfHospital.getText().toString();
                String idNLQ = tfRelate.getText().toString();

                Database.createUser(id, name, date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), status, hospital, idNLQ);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Database.updateLSNQL(0, rootId.toString(), dtf.format(now), "add", id);

                tfID.setText("");
                tfName.setText("");
                tfStatus.setText("");
                tfHospital.setText("");
                tfRelate.setText("");
                cbbCityPro.setSelectedIndex(0);
                cbbDistrict.removeAllItems();
                cbbWard.removeAllItems();
                jDateChooser.setCalendar(null);
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
        for (CityProvince x: cPList) {
            cbbCityPro.addItem(x.getName());
        }
    }

    public static void main(String[] args){
        addUser a = new addUser(rootId.toString());
    }
}
