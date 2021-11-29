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

public class AdminPanel extends JFrame implements ActionListener {
	JMenu mnTK, mnNQL, mnNDT;
	JMenuItem itmDMKTK;
	JMenuItem itmTKNQL, itmXDSNQL, itmTNQL;
	JMenuItem itmTNDT, itmDCNDT;
	ArrayList<JPanel> panels=new ArrayList<JPanel>();
	int cPanel=0;

	public AdminPanel() {
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

		itmTNQL = new JMenuItem("Tạo tài khoản");
		mnNQL.add(itmTNQL);
		itmTNQL.addActionListener(this);

		itmXDSNQL = new JMenuItem("Xem danh sách");
		mnNQL.add(itmXDSNQL);
		itmXDSNQL.addActionListener(this);

		itmTKNQL = new JMenuItem("Tìm kiếm");
		mnNQL.add(itmTKNQL);
		itmTKNQL.addActionListener(this);

		mnNDT = new JMenu("Địa điểm điều trị");
		menuBar.add(mnNDT);

		itmTNDT = new JMenuItem("Thêm mới");
		mnNDT.add(itmTNDT);
		itmTNDT.addActionListener(this);

		itmDCNDT = new JMenuItem("Điều chỉnh");
		mnNDT.add(itmDCNDT);
		itmDCNDT.addActionListener(this);

		JMenuItem logout = new JMenuItem("Đăng xuất");
		mnTK.add(logout);
		logout.addActionListener(this);
		
		getContentPane().setLayout(new BorderLayout(0, 0));

		//panels.add(new addModerator());
		//getContentPane().add(panels.get(0));
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
			getContentPane().add(panels.get(5));
			this.setVisible(true);
			cPanel=5;
			this.setTitle("Đổi mật khẩu");
		}
		else if(e.getActionCommand().equals("Tạo tài khoản"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(0));
			this.setVisible(true);
			cPanel=0;
			this.setTitle("Tạo tài khoản Người quản lý");
		}
		else if(e.getActionCommand().equals("Xem danh sách"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(1));
			this.setVisible(true);
			cPanel=1;
			this.setTitle("Xem danh sách Người quản lý");
		}
		else if(e.getActionCommand().equals("Tìm kiếm"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(2));
			this.setVisible(true);
			cPanel=2;
			this.setTitle("Tìm kiếm khoản Người quản lý");
		}
		else if(e.getActionCommand().equals("Thêm mới"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(3));
			this.setVisible(true);
			cPanel=3;
			this.setTitle("Thêm mới nơi điều trị");
		}
		else if(e.getActionCommand().equals("Điều chỉnh"))
		{
			this.remove(panels.get(cPanel));
			this.revalidate();
			this.repaint();
			getContentPane().add(panels.get(4));
			this.setVisible(true);
			cPanel=4;
			this.setTitle("Điều chỉnh nơi điều trị");
		}
		else if(e.getActionCommand().equals("Đăng xuất"))
		{
			this.dispose();
		}
	}
}
