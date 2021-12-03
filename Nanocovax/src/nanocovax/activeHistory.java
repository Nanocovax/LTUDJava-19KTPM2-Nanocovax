package nanocovax;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class activeHistory extends JFrame {
    private JTable hisTable;
    private JLabel header;
    private JScrollPane scPanel;
    private JComboBox comboBox;
    private JLabel lb;
    private JPanel rootPanel;

    activeHistory(){

        add(this.rootPanel);
        setSize(700,550);
        setResizable(false);
        createTable();
        setVisible(true);
    }
    void createTable(){
        String[] tbColName = {"ID", "Hoạt động", "Thời gian"};
        Object[] [] data = {{"01","Thêm người dùng","12/02/2021"}};
        hisTable.setModel(new DefaultTableModel(data,tbColName));
    }
}
