package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    static Object rootId = null;
    static String backupHospital;
    static String backupStatus;

    ArrayList<CityProvince> cPList = Database.getCityProvinceList();
    ArrayList<District> dList;
    ArrayList<Ward> wList;

    editUser(String srcId) throws ParseException {
        add(rootPanel);
        comboboxInit();
        setDateChooser();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700,300);
        setResizable(false);
        setVisible(true);
        rootId = srcId;
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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Lấy ngày từ datechooser
                // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                //String dt = format.format(jDateChooser.getDate());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(dateChooser.getDate());

                boolean res = Database.updateUser(tfID.getText().toString(), tfName.getText().toString(), date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), tfStatus.getText().toString(), tfHos.getText().toString(), tfRelate.getText().toString());

                if (res) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Database.updateLSNQL(0, rootId.toString(), dtf.format(now), "upd", tfID.getText().toString());
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

    editUser(User root, String srcId) throws ParseException {
        add(rootPanel);
        comboboxInit();
        dList = Database.getDistrictList(root.getAddress().getCityProvince().getId());
        for (District x: dList) {
            cbbDistrict.addItem(x.getName());
        }
        wList = Database.getWardList(root.getAddress().getDistrict().getId());
        for (Ward x: wList) {
            cbbWard.addItem(x.getName());
        }
        rootId = srcId;
        //setDateChooser();

        tfID.setText(root.getId());
        tfID.setEditable(false);
        tfName.setText(root.getName());

        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(root.getDoB());
        dateChooser = new JDateChooser(date);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(dateChooser);

        int indexCP = indexOfCP(cPList, root.getAddress().getCityProvince().getId());
        cbbCityPro.setSelectedIndex(indexCP);
        int indexD = indexOfDistrict(dList, root.getAddress().getDistrict().getId());
        cbbDistrict.setSelectedIndex(indexD);
        int indexW = indexOfWard(wList, root.getAddress().getWard().getId());
        cbbWard.setSelectedIndex(indexW);

        backupStatus = root.getStatus();
        tfStatus.setText(root.getStatus());

        backupHospital = root.getHospital().getId();
        tfHos.setText(root.getHospital().getId());

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
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Lấy ngày từ datechooser
                // SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                //String dt = format.format(jDateChooser.getDate());

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(dateChooser.getDate());

                Database.updateUser(tfID.getText().toString(), tfName.getText().toString(), date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), tfStatus.getText().toString(), tfHos.getText().toString(), tfRelate.getText().toString());

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Database.updateLSNQL(0, rootId.toString(), dtf.format(now), "updated", root.getId());

                if (!backupHospital.equals(tfHos.getText().toString())) {
                    Database.updateLSNQL(2, rootId.toString(), dtf.format(now), "added " + root.getId(), tfHos.getText().toString());
                    Database.updateLSNQL(2, rootId.toString(), dtf.format(now), "removed " + root.getId(), backupHospital);

                    Database.updateLSNDT(root.getId(), dtf.format(now), tfHos.getText().toString());

                    Database.updateOccupancyNDT(backupHospital, 1);
                    Database.updateOccupancyNDT(tfHos.getText().toString(), 0);

                    backupHospital = tfHos.getText().toString();
                }

                if (!backupStatus.equals(tfStatus.getText().toString())) {
                    dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
                    String localDate = dtf.format(LocalDate.now());
                    dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String localTime = dtf.format(LocalTime.now());
                    Database.updateLSTT(root.getId(), localDate, localTime, tfStatus.getText().toString());
                    //Database.updateLSTT(root.getId(), dtf.format(now), tfStatus.getText().toString());

                    backupStatus = tfStatus.getText().toString();
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

    void comboboxInit(){
        //cbbCityPro.addItem("Tỉnh/Thành phố");
        //cbbDistrict.addItem("Quận/huyện");
        //cbbWard.addItem("Phường/xã");
        //lấy danh sách tỉnh thành phố add vào cbb tỉnh thành phố
        // set selected là item  cho các cbb giống với db

        for (CityProvince x: cPList) {
            cbbCityPro.addItem(x.getName());
        }
    }
    void setDateChooser() throws ParseException {
        Date d = new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2021");// string lấy từ db
        dateChooser = new JDateChooser(d);
        dateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(dateChooser);
    }
    public static void main(String[] args) throws ParseException {
        editUser editUser = new editUser(rootId.toString());
    }

    int indexOfCP(ArrayList<CityProvince> list, String idCP) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(idCP)) {
                return i;
            }
        }
        return -1;
    }
    int indexOfDistrict(ArrayList<District> list, String idD) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(idD)) {
                return i;
            }
        }
        return -1;
    }
    int indexOfWard(ArrayList<Ward> list, String idW) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(idW)) {
                return i;
            }
        }
        return -1;
    }
}
