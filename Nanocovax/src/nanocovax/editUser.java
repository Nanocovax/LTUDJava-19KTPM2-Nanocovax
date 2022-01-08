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
    //private JTextField tfHos;
    private JTextField tfRelate;
    private JButton cancelButton;
    private JButton saveButton;
    private JComboBox cbbHospital;
    private JDateChooser dateChooser;
    String backupHospital, backupStatus, backupRelated;

    ArrayList<CityProvince> cPList = Database.getCityProvinceList();
    ArrayList<District> dList;
    ArrayList<Ward> wList;
    ArrayList<NoiDieuTri> hospitalList = Database.getListNDT();

    editUser(String username) throws ParseException {
        add(rootPanel);
        comboboxInit();
        setDateChooser();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(910,372);
        setResizable(false);
        setVisible(true);
        for (NoiDieuTri x: hospitalList) {
            cbbHospital.addItem(x.getTen());
        }
        cbbHospital.setSelectedItem(null);

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
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String date = format.format(dateChooser.getDate());

                String hospital = hospitalList.get(cbbHospital.getSelectedIndex()).getId();

                boolean res = Database.updateUser(tfID.getText().toString(), tfName.getText().toString(), date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), hospital, tfRelate.getText().toString());

                if (res) {
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Database.updateLSNQL(0, username, dtf.format(now), "upd", tfID.getText().toString());
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

    editUser(User root, String username) throws ParseException {
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
        for (NoiDieuTri x: hospitalList) {
            cbbHospital.addItem(x.getTen());
        }

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
        //tfHos.setText(root.getHospital().getId());
        int indexH = indexOfHospital(hospitalList, root.getHospital().getId());
        cbbHospital.setSelectedIndex(indexH);

        backupRelated = Database.getNLQId(root.getId());
        tfRelate.setText(backupRelated);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700,400);
        setResizable(false);
        setVisible(true);
        cbbCityPro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbbDistrict.removeAllItems();
                cbbWard.removeAllItems();
                if (cbbCityPro.getSelectedItem() != null) {
                    int indexCityPro = cbbCityPro.getSelectedIndex();
                    dList = Database.getDistrictList(cPList.get(indexCityPro).getId());
                    for (District x : dList) {
                        cbbDistrict.addItem(x.getName());
                    }
                    cbbDistrict.setSelectedItem(null);
                }
            }
        });
        cbbDistrict.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cbbWard.removeAllItems();
                if (cbbDistrict.getSelectedItem() != null) {
                    int indexDistrict = cbbDistrict.getSelectedIndex();
                    wList = Database.getWardList(dList.get(indexDistrict).getId());
                    for (Ward x: wList) {
                        cbbWard.addItem(x.getName());
                    }
                    cbbWard.setSelectedItem(null);
                }
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tfName.getText().isEmpty() && tfName.getText().length() <= 50 && !tfStatus.getText().isEmpty() && (cbbHospital.getSelectedItem() != null)  && (dateChooser.getDate() != null) && (cbbCityPro.getSelectedItem() != null) && (cbbDistrict.getSelectedItem() != null) && (cbbWard.getSelectedItem() != null) && Utilities.validateRelatedPerson(tfStatus.getText(), tfRelate.getText())) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    String date = format.format(dateChooser.getDate());

                    LocalDate currentDate = LocalDate.now();
                    LocalDate jdcDate = LocalDate.parse(date);

                    if (jdcDate.compareTo(currentDate) > 0) {
                        JOptionPane.showMessageDialog(null, "The input data is invalid. Please try again!");
                    } else {
                        String hospital = hospitalList.get(cbbHospital.getSelectedIndex()).getId();

                        boolean res = Database.updateUser(tfID.getText().toString(), tfName.getText().toString(), date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), hospital, tfRelate.getText().toString());

                        if (res) {
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            Database.updateLSNQL(0, username, dtf.format(now), "updated", root.getId());

                            if (!backupHospital.equals(hospital)) {
                                Database.updateLSNQL(2, username, dtf.format(now), "added " + root.getId(), hospital);
                                Database.updateLSNQL(2, username, dtf.format(now), "removed " + root.getId(), backupHospital);

                                Database.updateLSNDT(root.getId(), dtf.format(now), hospital);

                                Database.updateOccupancyNDT(backupHospital, 1);
                                Database.updateOccupancyNDT(hospital, 0);

                                backupHospital = hospital;
                            }

                            String status = tfStatus.getText().toString();

                            if (!backupStatus.equals(status)) {
                                if (status.equals("Dead")) {
                                    status = "D";
                                } else if (status.equals("Recovered")) {
                                    status = "R";
                                }
                                Database.updateUserStatus(root.getId(), status);
                                if (status.contains("F")) {
                                    if (status.compareTo(backupStatus) > 0) {
                                        int n = Integer.parseInt(String.valueOf(status.charAt(1))) - Integer.parseInt(String.valueOf(backupStatus.charAt(1)));
                                        Database.updateAllStatuses(root.getId(), 0, n, true, false);
                                        Database.updateAllStatuses(root.getId(), 1, n, true, false);
                                    } else {
                                        int n = Integer.parseInt(String.valueOf(backupStatus.charAt(1))) - Integer.parseInt(String.valueOf(status.charAt(1)));
                                        Database.updateAllStatuses(root.getId(), 0, n, false, false);
                                        Database.updateAllStatuses(root.getId(), 1, n, false, false);
                                    }
                                }

                                dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
                                String localDate = dtf.format(LocalDate.now());
                                dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                                String localTime = dtf.format(LocalTime.now());
                                Database.updateLSTT(root.getId(), localDate, localTime, status);

                                if (status.equals("D")) {
                                    status = "Dead";
                                } else if (status.equals("R")) {
                                    status = "Recovered";
                                }
                                backupStatus = status;
                            }
                        }
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null, "The input data is invalid. Please try again!");
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
        editUser editUser = new editUser("lqtlong");
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
    int indexOfHospital(ArrayList<NoiDieuTri> list, String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
