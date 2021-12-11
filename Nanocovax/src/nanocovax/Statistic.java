package nanocovax;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Statistic extends JFrame {
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

    Statistic(){
        add(rootPanel);
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Gói 1",10);
        dataset.setValue("Gói 2",20);
        dataset.setValue("Gói 3",30);

        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        dataset2.setValue(80, "F0","Ngày 1");
        dataset2.setValue(69, "F0","Ngày 2");
        dataset2.setValue(29, "F0","Ngày 3");
        dataset2.setValue(44, "F1","Ngày 1");
        dataset2.setValue(34, "F1","Ngày 2");
        dataset2.setValue(57, "F1","Ngày 3");
        dataset2.setValue(10, "F2","Ngày 1");
        dataset2.setValue(87, "F2","Ngày 2");
        dataset2.setValue(90, "F2","Ngày 3");

        DefaultCategoryDataset dataset3 = new DefaultCategoryDataset();
        dataset3.setValue(10,"Deaths","Ngày 1");
        dataset3.setValue(5,"Deaths","Ngày 2");
        dataset3.setValue(3,"Deaths","Ngày 3");
        dataset3.setValue(5,"Recovers","Ngày 1");
        dataset3.setValue(3,"Recovers","Ngày 2");
        dataset3.setValue(7,"Recovers","Ngày 3");
        dataset3.setValue(10,"Being treated","Ngày 1");
        dataset3.setValue(15,"Being treated","Ngày 2");
        dataset3.setValue(20,"Being treated","Ngày 3");

        DefaultCategoryDataset dataset4 = new DefaultCategoryDataset();
        dataset4.setValue(10000,"Debt","Ngày 1");
        dataset4.setValue(50000,"Debt","Ngày 2");
        dataset4.setValue(33000,"Debt","Ngày 3");
        initLineChart(chartPanel1,dataset2,"Thống kê số người từng trạng thái theo ngày ","Ngày","Người");
        initBarChart(chartPanel2,dataset3,"Thống kê Số chuyển trạng thái","Ngày","Thông tin");
        initPieChart(chartPanel3,dataset,"Thống kê tiêu thụ nhu yếu phẩm");
        initBarChart(chartPanel4,dataset4,"Thống kê dư nợ","Ngày","VNĐ");

        setSize(1200,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //refresh để lấy data mới vẽ chart mới
        refreshButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        refreshButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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
                NQLMenu d = new NQLMenu("nttchau");
                setVisible(false);
                dispose();
            }
        });
        lbNYP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                NecManagement n = new NecManagement();
                setVisible(false);
                dispose();
            }
        });
        lbLogout.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
    void initPieChart(JPanel tab, DefaultPieDataset dataset,String name){

        JFreeChart chart = ChartFactory.createPieChart(name,dataset,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }
    void initLineChart(JPanel tab , DefaultCategoryDataset dataset,String name,String x,String y){
        JFreeChart chart = ChartFactory.createLineChart(name,x,y,dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }
    void initBarChart(JPanel tab, DefaultCategoryDataset dataset, String name,String x,String y){
        JFreeChart chart = ChartFactory.createBarChart(name,x,y,dataset, PlotOrientation.VERTICAL,true,true,false);
        ChartPanel chartPanel = new ChartPanel(chart);
        tab.add(chartPanel);
    }
    public static void main(String[] args){
        Statistic statistic = new Statistic();
    }
}
