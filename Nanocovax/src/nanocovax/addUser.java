package nanocovax;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
    // private JTextField tfHospital;
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
    private JComboBox cbbHospital;
    private JDateChooser jDateChooser;

    BufferedReader br;
    PrintWriter pw;
    private static int PORT = 1024;

    ArrayList<CityProvince> cPList = Database.getCityProvinceList();
    ArrayList<District> dList;
    ArrayList<Ward> wList;
    ArrayList<NoiDieuTri> hospitalList = Database.getListNDT();

    addUser(String username) {
        add(rootPanel);
        comboboxInit();
        jDateChooser = new JDateChooser();
        jDateChooser.setDateFormatString("dd/MM/yyyy");
        calPanel.add(jDateChooser);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(910, 380);
        setResizable(false);
        setVisible(true);

        for (NoiDieuTri x : hospitalList) {
            cbbHospital.addItem(x.getTen());
        }
        cbbHospital.setSelectedItem(null);

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
                    for (Ward x : wList) {
                        cbbWard.addItem(x.getName());
                    }
                    cbbWard.setSelectedItem(null);
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
                String hospital = hospitalList.get(cbbHospital.getSelectedIndex()).getId();
                String idNLQ = tfRelate.getText().toString();

                if (status.equals("Dead")) {
                    status = "D";
                } else if (status.equals("Recovered")) {
                    status = "R";
                }

                boolean res = Database.createUser(id, name, date, cPList.get(cbbCityPro.getSelectedIndex()).getId(), dList.get(cbbDistrict.getSelectedIndex()).getId(), wList.get(cbbWard.getSelectedIndex()).getId(), status, hospital, idNLQ);
//                Database2.createCustomer(id);
                //---------Socket----------------

                Socket socket = null;
                try {
                    socket = new Socket(InetAddress.getLocalHost(), PORT);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                try {
                    pw = new PrintWriter(socket.getOutputStream(), true);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // /name
                String message = id;
                try {
                    message = br.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (message.startsWith("/name")) {
                    pw.println(id); // get real username
                }

                // /accepted
                try {
                    message = br.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (message.startsWith("/accepted")) {

                }

                // create account
                pw.println("/add");
                pw.println(id);

                try {
                    message = br.readLine();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                if (message.startsWith("/done")) {
                    System.out.println("Created account " + id);
                } else if (message.startsWith("/failed")) {
                    System.out.println("Cannot create account " + id);
                }

                // /cancel
                pw.println("/cancel");

                //--------------------------

                tfID.setText("");
                tfName.setText("");
                tfStatus.setText("");
                tfRelate.setText("");
                cbbCityPro.setSelectedIndex(0);
                cbbDistrict.removeAllItems();
                cbbWard.removeAllItems();
                jDateChooser.setCalendar(null);

                cbbCityPro.setSelectedItem(null);
                cbbDistrict.setSelectedItem(null);
                cbbWard.setSelectedItem(null);

                cbbHospital.setSelectedItem(null);

                if (res) {
                    Database.updateOccupancyNDT(hospital, 0);
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    Database.updateLSNQL(0, username, dtf.format(now), "added", id);
                    Database.updateLSNQL(2, username, dtf.format(now), "added " + id, hospital);
                    Database.updateLSNDT(id, dtf.format(now), hospital);

                    dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
                    String localDate = dtf.format(LocalDate.now());
                    dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                    String localTime = dtf.format(LocalTime.now());
                    Database.updateLSTT(id, localDate, localTime, status);
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

    void comboboxInit() {
        for (CityProvince x : cPList) {
            cbbCityPro.addItem(x.getName());
        }
        cbbCityPro.setSelectedItem(null);
    }

    public static void main(String[] args) {
        addUser a = new addUser("lqtlong");
    }
}
