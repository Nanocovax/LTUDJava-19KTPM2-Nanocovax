package nanocovax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AdminMenu extends JFrame implements Runnable {
    private JPanel rootPanel;
    private JButton addButton;
    private JTextField searchBar;
    private JButton searchButton;
    private JLabel lbNQL;
    private JLabel lbNDT;
    private JLabel lbLogout;
    private JLabel header;
    private JButton historyButton;
    private JButton removeButton;
    private JTable table;
    private JButton editButton;
    private JButton refreshButton;
    private JPanel menuPanel;
    private JPanel NQLPanel;
    int indexRow;
    Object id = null, status = null;
    Thread thread;
    static Logger logger = LogManager.getLogger(AdminMenu.class);
    public void run() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNQL addNQL= new AddNQL();
                logger.info("Admin - add Moderater");
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.searchNQL(searchBar.getText()));
                logger.info("Admin - search "+ searchBar.getText());
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retriveNQL();
                activeHistory activeHistory= new activeHistory(id.toString());
                logger.info("Admin - View Mod history");
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check xem user ch???n 1 row tr??n table ch??a r???i th???c hi???n x??a
                retriveNQL();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete " + id.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    Database.deleteNQL(id.toString());
                    logger.info("Admin - Remove Mod");
                }

            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //editNQL editNQL = new editNQL();

                if (indexRow != -1) {
                    retriveNQL();
                    editNQL editNQL = new editNQL(id.toString(), status.toString());
                } else {
                    editNQL editNQL = new editNQL();
                }
                logger.info("Admin - Edit Mod's info");
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.getListNQL());

            }
        });
        lbNQL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        lbNDT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //chuy???n sang m??n h??nh qu???n l?? n??i ??i???u tr???
                NDTManagement ndtManagement= new NDTManagement();
                setVisible(false);
                dispose();
                logger.info("Admin - Moderator Management -> Hospital Management");
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                /*setVisible(false);
                dispose();
                Login frame = new Login();
                frame.setVisible(true);*/

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();

                    Login frame = new Login();
                    frame.setVisible(true);
                    logger.info("Admin - Log out");
                }

            }
        });
    }

    AdminMenu(){
        add(this.rootPanel);
        createTable(Database.getListNQL());
        setSize(1900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        thread = new Thread(this);
        thread.start();
        /*addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddNQL addNQL= new AddNQL();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.searchNQL(searchBar.getText()));
            }
        });

        historyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retriveNQL();
                activeHistory activeHistory= new activeHistory(id.toString());
            }
        });
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check xem user ch???n 1 row tr??n table ch??a r???i th???c hi???n x??a
                retriveNQL();
                int dialogResult = JOptionPane.showConfirmDialog(null, "Delete " + id.toString() + "?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    Database.deleteNQL(id.toString());
                }
            }
        });
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //editNQL editNQL = new editNQL();

                if (indexRow != -1) {
                    retriveNQL();
                    editNQL editNQL = new editNQL(id.toString(), status.toString());
                } else {
                    editNQL editNQL = new editNQL();
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTable(Database.getListNQL());
            }
        });
        lbNQL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

            }
        });
        lbNDT.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //chuy???n sang m??n h??nh qu???n l?? n??i ??i???u tr???
                NDTManagement ndtManagement= new NDTManagement();
                setVisible(false);
                dispose();

            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                *//*setVisible(false);
                dispose();
                Login frame = new Login();
                frame.setVisible(true);*//*

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();

                    Login frame = new Login();
                    frame.setVisible(true);
                }
            }
        });*/
    }
    public JPanel getRootPanel(){
        return this.rootPanel;
    }


    public void createTable(ArrayList<NguoiQuanLy> dataList){
        ArrayList<NguoiQuanLy> list = dataList;
        String[] tbColName = {"No. ID Card", "Role", "Status"};
        Object[] [] data = new String[list.size()][3];
        //table.setModel(new DefaultTableModel(data,tbColName));

        for (int i = 0; i < list.size(); i++) {
            NumberFormat nf = new DecimalFormat("000");
            data[i][0] = list.get(i).getId();
            data[i][1] = list.get(i).getPhanQuyen();
            data[i][2] = list.get(i).getTinhtrang();
        }

        table.setModel(new DefaultTableModel(data, tbColName));
        if (table.getRowCount() > 0)
            table.setRowSelectionInterval(0, 0);
    }

    public void retriveNQL() {
        indexRow = table.getSelectedRow();
        if (indexRow != -1) {
            id = table.getValueAt(indexRow, 0);
            status = table.getValueAt(indexRow, 2);
        }
    }

    public static void main(String[] args){
        AdminMenu adminMenu = new AdminMenu();
    }
}
