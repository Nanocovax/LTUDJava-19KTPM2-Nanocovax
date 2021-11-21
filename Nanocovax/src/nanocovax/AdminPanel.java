package nanocovax;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenu;
import java.awt.Toolkit;


public class AdminPanel extends JFrame implements ActionListener {
	JMenuItem itmDMKTK;
	JMenu mnTK;
	JMenu mnNQL;
	JMenuItem itmLSHD;
	JMenuItem itmKTKNQL;
	JMenu mnNDT;
	JMenuItem itmTNDT;
	JMenuItem itmDCNDT;
	ArrayList<JPanel> panels=new ArrayList<JPanel>();
	int cPanel=0;

	/**
	 * Create the frame.
	 */
	public AdminPanel() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("F:\\Working Directory\\fianl project with sql\\Bill\\logo.png"));
		setTitle("Admin Panel");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 840, 619);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		mnTK = new JMenu("Tài khoản");
		menuBar.add(mnTK);

		itmDMKTK = new JMenuItem("Đổi mật khẩu");
		mnTK.add(itmDMKTK);
		itmDMKTK.addActionListener(this);

		mnNQL = new JMenu("Người quản lý");
		menuBar.add(mnNQL);

		itmLSHD = new JMenuItem("Lịch sử hoạt động");
		mnNQL.add(itmLSHD);
		itmLSHD.addActionListener(this);

		itmKTKNQL = new JMenuItem("Khóa tài khoản");
		mnNQL.add(itmKTKNQL);
		itmKTKNQL.addActionListener(this);

		mnNDT = new JMenu("Địa điểm điều trị");
		menuBar.add(mnNDT);

		itmTNDT = new JMenuItem("Thêm");
		mnNDT.add(itmTNDT);
		itmTNDT.addActionListener(this);

		itmDCNDT = new JMenuItem("Điều chỉnh");
		mnNDT.add(itmDCNDT);
		itmDCNDT.addActionListener(this);

		JMenuItem logout = new JMenuItem("Đăng xuất");
		mnTK.add(logout);
		logout.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout(0, 0));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("Selected: " + e.getActionCommand());   
		if(e.getActionCommand().equals("Đối mật khẩu"))
		{
			System.out.println(panels.get(cPanel));
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(0));
			this.setVisible(true);
			cPanel=0;
			this.setTitle("Đổi mật khẩu");
		}
		else if(e.getActionCommand().equals("Lịch sử hoạt động"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(1));
			this.setVisible(true);
			cPanel=1;
			this.setTitle("Lịch sử hoạt động");
		}
		else if(e.getActionCommand().equals("Khóa tài khoản"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(2));
			this.setVisible(true);
			cPanel=2;
			this.setTitle("Khóa tài khoản");
		}
		else if(e.getActionCommand().equals("Thêm"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(3));
			this.setVisible(true);
			cPanel=3;
			this.setTitle("Thêm nơi điều trị");
		}
		else if(e.getActionCommand().equals("Điều chỉnh"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(3));
			this.setVisible(true);
			cPanel=3;
			this.setTitle("Điều chỉnh nơi điều trị");
		}
		else if(e.getActionCommand().equals("Đăng xuất"))
		{
			this.dispose();
		}
	}
}
