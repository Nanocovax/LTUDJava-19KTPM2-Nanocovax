package nanocovax;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.sql.ResultSet;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private String password,username;
	private JLabel error;
	private String errorText="Invalid user name or password!";
	private JLabel lblCaddeyLogin;
	JButton btnLogin;
	private JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
//		if(!getMac().equals("90-48-9A-AC-21-17"))
	//	{
		//	JOptionPane.showMessageDialog(null,"Unknown Computer, Can not run!");
			//return;
//		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					Process process = Runtime.getRuntime().exec("E:\\xampp\\apache_start.bat");
	//				Process process2 = Runtime.getRuntime().exec("E:\\xampp\\mysql_start.bat");

					Login frame = new Login();
					//frame.setIconImage(Toolkit.getDefaultToolkit().getImage("logo.png"));
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login()
	{
		//setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"/logo.png"));
		GUI();
	}
	void GUI()
	{
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(65, 100, 531, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JLabel lblUserName = new JLabel("Tên đăng nhập");
		//lblUserName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUserName.setBounds(119, 100, 91, 14);
		contentPane.add(lblUserName);
		
		usernameField = new JTextField();
		usernameField.setBounds(247, 101, 129, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		JLabel lblPassword = new JLabel("Mật khẩu\r\n");
		//lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(119, 135, 91, 14);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(247, 136, 129, 20);
		contentPane.add(passwordField);
		
		passwordField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					btnLogin.doClick();
				}
		});
		
		btnLogin = new JButton("Đăng nhập");
	
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				password=passwordField.getText().toString().toLowerCase();
				username=usernameField.getText().toString().toLowerCase();
				passwordField.setText("");
				usernameField.setText("");
				if(password.equals("")||username.equals(""))
					error.setText(errorText);
				else
				{
					error.setText("");
					if(username.equals("admin"))
					{
						if(Database.varifyLogin(username,password))
							{
								error.setText("");
								AdminPanel p = new AdminPanel();
								p.setVisible(true);
							}
						else
							error.setText(errorText);
					}
					else
					{
						if(Database.varifyLogin(username,password))
						{
							error.setText("");
						}
					else
						error.setText(errorText);
					}
					
				}
			}
		});
		btnLogin.setBounds(247, 185, 115, 23);
		contentPane.add(btnLogin);
		
		error = new JLabel("");
		error.setForeground(Color.RED);
		error.setBounds(200, 215, 220, 14);
		contentPane.add(error);
		
		lblCaddeyLogin = new JLabel("Nanocovax");
		lblCaddeyLogin.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCaddeyLogin.setBounds(204, 26, 167, 28);
		contentPane.add(lblCaddeyLogin);

	}
	public static String getMac()
	{
		InetAddress ip;
		String mc="";
		try {
			ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);

			byte[] mac = network.getHardwareAddress();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
		
			mc= sb.toString();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return mc;
		
	
	}
	
}
