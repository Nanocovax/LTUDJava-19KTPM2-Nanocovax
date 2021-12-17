package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserMenu extends JFrame{
    private JPanel menuPanel;
    private JLabel lbShop;
    private JLabel lbLogout;
    private JLabel lbNQL;
    private JPanel rootPanel;
    private JTabbedPane tabbedPane1;
    private JPanel tab1;
    private JPanel tab2;
    private JTable table;
    private JLabel header;
    private JLabel displayName;
    private JLabel displayID;
    private JLabel displayDOB;
    private JLabel displayAddress;
    private JLabel displaySTT;
    private JLabel displaHosName;
    private JLabel lbPayment;

    UserMenu(){
        add(this.rootPanel);
        createTable();
        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


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
                }
            }
        });
        lbShop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        lbPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
    public void createTable(){
        String[] tbColName = {"ID","Name","Date of birth","Address","Status","Hospital"};
        Object[] [] data = {{"01","Một ai đó","01/01/2001","Sao Kim","F1","BV2"}};
        table.setModel(new DefaultTableModel(data,tbColName));
    }
    public static void main(String[]args){
        UserMenu u = new UserMenu();
    }
}
