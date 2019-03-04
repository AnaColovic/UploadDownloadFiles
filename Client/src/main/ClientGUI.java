package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGUI extends JFrame {

	private JPanel contentPane;
	private JTextField username;
	private JPasswordField pass;
	public static String user;
	public static String password;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public ClientGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Client.exit();
				Client.confirmExit();
			}
		});
		setResizable(false);
		setTitle("Upload/Download Files");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 409);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setFont(new Font("Calibri", Font.BOLD, 15));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton btnSignUp = new JButton("Sign up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openSignUp();
			}
		});
		btnSignUp.setBounds(117, 319, 135, 23);
		contentPane.add(btnSignUp);
		
		JButton btnLogInAs = new JButton("Log in as guest");
		btnLogInAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.openGuest();
			}
		});
		btnLogInAs.setBounds(279, 319, 135, 23);
		contentPane.add(btnLogInAs);
		
		JLabel lblYoureNew = new JLabel("You're new?");
		lblYoureNew.setForeground(Color.WHITE);
		lblYoureNew.setBounds(117, 298, 104, 20);
		contentPane.add(lblYoureNew);
		
		pass = new JPasswordField();
		pass.setBounds(208, 204, 128, 20);
		contentPane.add(pass);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setBounds(208, 123, 79, 14);
		contentPane.add(lblUsername);
		
		username = new JTextField();
		username.setBounds(208, 148, 128, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(208, 179, 104, 14);
		contentPane.add(lblPassword);
		
		JLabel lblLogIn = new JLabel("LOG IN");
		lblLogIn.setForeground(Color.WHITE);
		lblLogIn.setFont(new Font("Cambria", Font.BOLD, 25));
		lblLogIn.setBackground(Color.BLACK);
		lblLogIn.setBounds(223, 66, 104, 34);
		contentPane.add(lblLogIn);
		
		JButton btnLogIn = new JButton("Log in");
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String user = username.getText();
				String password = pass.getText();
				Client.userPass(user, password, false);
				if(Client.validClient()){
					Client.openClient(user, false);
				} else {
					JOptionPane.showMessageDialog(contentPane,"You entered the wrong useraname. Try again!");
				}
				}
		});
		btnLogIn.setBounds(223, 249, 89, 23);
		contentPane.add(btnLogIn);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, -2, -13, -2);
		contentPane.add(panel);
		JLabel img = new JLabel("New label");
		img.setForeground(SystemColor.desktop);
		img.setIcon(new ImageIcon(ClientGUI.class.getResource("/images/background_img.jpg")));
		img.setBounds(0, 0, 569, 382);
		contentPane.add(img);
	}
	
	
}
