package nanocovax;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.jdbc.JDBCCategoryDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.sql.*;

public class Statistic extends JFrame implements Runnable {
    private JPanel menuPanel;
    private JLabel lbNYP;
    private JLabel lbLogout;
    private JLabel lbUser;
    private JLabel lbStatistic;
    private JTabbedPane tabbedPane;
    private JPanel tab1;
    private JPanel tab2;
    private JPanel statisticPanel;
    private JPanel chartPanel2;
    private JButton refreshButton1;
    private JPanel chartPanel1;
    private JButton refreshButton2;
    private JPanel rootPanel;
    private JPanel tab3;
    private JButton refreshButton3;
    private JPanel chartPanel3;
    private JPanel tab4;
    private JButton refreshButton4;
    private JPanel chartPanel4;
    String username;
    Thread thread;
    static Logger logger = LogManager.getLogger(Statistic.class);
    public void run() {
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dataset2 = Database.getSumStatus();
                initBarChart(chartPanel1,dataset2,"Thống kê Số người từng trạng thái theo ngày","Ngày","Người");
            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dataset3 = Database.getStatusChange();
                initBarChart(chartPanel2,dataset3,"Thống kê Số chuyển trạng thái","Ngày","Thông tin");
            }
        });
        refreshButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NQLMenu d = new NQLMenu(username);
                setVisible(false);
                dispose();
                logger.info("Mod - Statistic -> User Management");
            }
        });
        lbNYP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NecManagement n = new NecManagement(username);
                setVisible(false);
                dispose();
                logger.info("Mod - Statistic -> NecManagement");
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int dialogResult = JOptionPane.showConfirmDialog(null, "Would you like to log out?", "Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    setVisible(false);
                    dispose();
                    logger.info("Mod - Log out");
                    Login frame = new Login();
                    frame.setVisible(true);
                }
            }
        });
    }

    Statistic(String username){
        add(rootPanel);
        this.username = username;
        thread = new Thread(this);
        thread.start();
//        DefaultPieDataset dataset = new DefaultPieDataset();
//        dataset.setValue("Gói 1",10);
//        dataset.setValue("Gói 2",20);
//        dataset.setValue("Gói 3",30);
        DefaultCategoryDataset dataset = Database.getSumNec();

        DefaultCategoryDataset dataset2 = Database.getSumStatus();

        DefaultCategoryDataset dataset3 = Database.getStatusChange();

        DefaultCategoryDataset dataset4 = Database.getSumDebt();
//        dataset4.setValue(10000,"Debt","Ngày 1");
//        dataset4.setValue(50000,"Debt","Ngày 2");
//        dataset4.setValue(33000,"Debt","Ngày 3");

        initBarChart(chartPanel1,dataset2,"Thống kê Số người từng trạng thái theo ngày","Ngày","Người");
        //initLineChart(chartPanel2,dataset3,"Thống kê Số chuyển trạng thái","Ngày","Thông tin");
        initBarChart(chartPanel2,dataset3,"Thống kê Số chuyển trạng thái","Ngày","Thông tin");
        initBarChart(chartPanel3,dataset,"Thống kê tiêu thụ nhu yếu phẩm", "Tên gói","Số lượng");
        initBarChart(chartPanel4,dataset4,"Thống kê dư nợ","Ngày","VNĐ");

        setSize(1900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //refresh để lấy data mới vẽ chart mới
        /*refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dataset2 = Database.getSumStatus();
                initBarChart(chartPanel1,dataset2,"Thống kê Số người từng trạng thái theo ngày","Ngày","Người");
            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultCategoryDataset dataset3 = Database.getStatusChange();
                initBarChart(chartPanel2,dataset3,"Thống kê Số chuyển trạng thái","Ngày","Thông tin");
            }
        });
        refreshButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        lbUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NQLMenu d = new NQLMenu(username);
                setVisible(false);
                dispose();
            }
        });
        lbNYP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NecManagement n = new NecManagement(username);
                setVisible(false);
                dispose();
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

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
    void initPieChart(JPanel tab, DefaultPieDataset dataset,String name){

        JFreeChart chart = ChartFactory.createPieChart(name,dataset,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }
    void initLineChart(JPanel tab , DefaultCategoryDataset dataset,String name,String x,String y){
        JFreeChart chart = ChartFactory.createLineChart(name,x,y,dataset, PlotOrientation.VERTICAL,true,true,false);
        CategoryPlot p = chart.getCategoryPlot();
        p.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }

    void initBarChart(JPanel tab, DefaultCategoryDataset dataset, String name,String x,String y){
        JFreeChart chart = ChartFactory.createBarChart(name,x,y,dataset, PlotOrientation.VERTICAL,true,true,false);
        CategoryPlot p = chart.getCategoryPlot();
        p.getRangeAxis().setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }
    public static void main(String[] args){
        Statistic statistic = new Statistic("nttchau");
    }
}
