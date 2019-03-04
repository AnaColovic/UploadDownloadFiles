package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class CreateGUI extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
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
	public CreateGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Client.exit();
				Client.confirmExit();
			}
		});
		setResizable(false);
		setTitle("Create Account");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 409);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCreateAccount = new JButton("Create account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String user = username.getText();
				String password = pass.getText();
				if(user.contains(" ")){
					JOptionPane.showMessageDialog(contentPane,"Username can't contain blank character!");
				} else if(user.isEmpty()){
					JOptionPane.showMessageDialog(contentPane,"Username you entered is empty!");
				}else if(password.length()<5){
					JOptionPane.showMessageDialog(contentPane,"Password must contain at least 5 characters!");
				}else{
				Client.userPass(user, password, true);
				if(Client.validClient()){
					Client.openClient(user, true);
				} else {
					JOptionPane.showMessageDialog(contentPane,"Username is not available. Change it!");
				}
				}
			}
		});
		btnCreateAccount.setBounds(200, 316, 134, 38);
		contentPane.add(btnCreateAccount);
		
		JButton btnNewButton = new JButton("BACK");
		btnNewButton.setFocusable(false);
		btnNewButton.setAlignmentX(SwingConstants.LEADING);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.backSignUp(true);
			}
		});
		btnNewButton.setIcon(new ImageIcon(CreateGUI.class.getResource("/images/back (1).png")));
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBounds(10, 11, 97, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setForeground(Color.WHITE);
		lblUsername.setFont(new Font("Calibri", Font.BOLD, 15));
		lblUsername.setBounds(200, 182, 64, 14);
		contentPane.add(lblUsername);
		
		username = new JTextField();
		username.setBounds(200, 207, 134, 20);
		contentPane.add(username);
		username.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 15));
		lblPassword.setForeground(Color.WHITE);
		lblPassword.setBounds(200, 238, 64, 14);
		contentPane.add(lblPassword);
		
		pass = new JPasswordField();
		pass.setBounds(200, 263, 134, 20);
		contentPane.add(pass);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(CreateGUI.class.getResource("/images/round-account-button-with-user-inside (3).png")));
		lblNewLabel.setBounds(201, 31, 165, 128);
		contentPane.add(lblNewLabel);
		panel.setBounds(0, 0, -3, 10);
		contentPane.add(panel);
		
		JLabel img = new JLabel("");
		img.setIcon(new ImageIcon(CreateGUI.class.getResource("/images/background_img.jpg")));
		img.setBounds(0, 0, 567, 380);
		contentPane.add(img);
	}

}
