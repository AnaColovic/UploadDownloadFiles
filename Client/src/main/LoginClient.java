package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ClientInfoStatus;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginClient extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LoginClient(String user) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Client.exit();
				Client.confirmExit();
			}
		});
		String username = user;
		setTitle("Welcome!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 409);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 201, 537, 158);
		contentPane.add(scrollPane);
		scrollPane.setVisible(false);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setVisible(false);
		textArea.setEditable(false);
		
		JLabel lblChooseAnOption = new JLabel("Choose an option bellow:");
		lblChooseAnOption.setForeground(Color.WHITE);
		lblChooseAnOption.setFont(new Font("Calibri", Font.PLAIN, 20));
		lblChooseAnOption.setBounds(10, 11, 388, 45);
		contentPane.add(lblChooseAnOption);
		
		JLabel lblPrivateKey = new JLabel("Private key");
		lblPrivateKey.setForeground(Color.WHITE);
		lblPrivateKey.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPrivateKey.setBounds(146, 132, 82, 26);
		contentPane.add(lblPrivateKey);
		lblPrivateKey.setVisible(false);
		
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileKey = textField.getText();
				File file = new File("C:\\Users\\ANA\\workspace\\neon\\Client\\"+fileKey+".txt");
				Client.download(fileKey);
				boolean valid = Client.getFile(fileKey);
				if(valid==true){
					JOptionPane.showMessageDialog(contentPane,"You succesfully downloaded this file!");
					scrollPane.setVisible(true);
					scrollPane.setBounds(10, 205, 537, 158);
					textArea.setVisible(true);
					String s = Client.getTextFromFile(fileKey);
					textArea.setText(s);
					textArea.setEditable(false);
				} else {
					JOptionPane.showMessageDialog(contentPane,"The file doesn't excist!");
				}
			}
		});
		button.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button.setBounds(300, 156, 149, 23);
		contentPane.add(button);
		button.setVisible(false);
		
		textField = new JTextField();
		textField.setBounds(146, 157, 131, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.setVisible(false);
		
		JButton button_1 = new JButton("Submit");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String file = textArea.getText();
				if(file.equals("")){
					JOptionPane.showMessageDialog(contentPane,"The file you entered is empty!");
				} else if(file.length()>500){
					JOptionPane.showMessageDialog(contentPane,"The file you entered is too long!");
				} else{
				Client.upload(username, file);
				String key = Client.returnPrivateKey();
				if(key==null){
					JOptionPane.showMessageDialog(contentPane,"There has been a mistake! Try again!");
				} else{
					JOptionPane.showMessageDialog(contentPane,"You succesfully uploaded file! Private key: "+key);
					scrollPane.setVisible(false);
					textArea.setVisible(false);
					textArea.setText("");
					button_1.setVisible(false);
					lblPrivateKey.setVisible(false);
					textField.setVisible(false);
				}
			}
			}
		});
		
		button_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		button_1.setBounds(230, 330, 89, 23);
		button_1.setVisible(false);
		contentPane.add(button_1);
		
		JButton btnNewButton = new JButton("UPLOAD");
		btnNewButton.setFocusable(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.setVisible(true);
				textArea.setVisible(true);
				textArea.setEditable(true);
				textArea.setText("");
				scrollPane.setBounds(10, 170, 537, 158);
				button_1.setVisible(true);
				button.setVisible(false);
				textField.setVisible(false);
				textField.setText("");
				lblPrivateKey.setVisible(false);
			}
		});
		
		JButton btnFilesIUploaded = new JButton("Files I \r\nuploaded");
		btnFilesIUploaded.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnFilesIUploaded.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.filesIUploaded(username);
				String myFiles = Client.getMyFiles();
				if(myFiles.equals("")){
					JOptionPane.showMessageDialog(contentPane,"You haven't uploaded any files!");
				} else {
					lblPrivateKey.setVisible(false);
					button_1.setVisible(false);
					button.setVisible(false);
					textField.setVisible(false);
					scrollPane.setVisible(true);
					textArea.setVisible(true);
					myFiles = myFiles.replace(' ', '\n');
					textArea.setText("Files you uploaded:\n\n"+myFiles);
				}
			}
		});
		btnFilesIUploaded.setBounds(300, 76, 153, 26);
		contentPane.add(btnFilesIUploaded);
		btnNewButton.setIcon(new ImageIcon(LoginClient.class.getResource("/images/file (1).png")));
		btnNewButton.setBounds(118, 54, 102, 67);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("DOWNLOAD");
		btnNewButton_1.setFocusable(false);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				button.setVisible(true);
				lblPrivateKey.setVisible(true);
				textField.setVisible(true);
				textField.setText("");
				
				scrollPane.setVisible(false);
				textArea.setVisible(false);
				textArea.setText("");
				button_1.setVisible(false);
			}
		});
		
		JButton btnNewButton_2 = new JButton("LOG OUT");
		btnNewButton_2.setFocusable(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Client.logOut();
			}
		});
		btnNewButton_2.setForeground(Color.WHITE);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setIcon(new ImageIcon(LoginClient.class.getResource("/images/logout.png")));
		btnNewButton_2.setBounds(455, 3, 102, 43);
		btnNewButton_2.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_2.setVerticalTextPosition(SwingConstants.BOTTOM);
		contentPane.add(btnNewButton_2);
		btnNewButton_1.setIcon(new ImageIcon(LoginClient.class.getResource("/images/file.png")));
		btnNewButton_1.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnNewButton_1.setHorizontalTextPosition(SwingConstants.CENTER);
		btnNewButton_1.setForeground(Color.WHITE);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setBounds(198, 59, 105, 57);
		contentPane.add(btnNewButton_1);
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, -2, 10);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setVerticalTextPosition(SwingConstants.TOP);
		lblNewLabel.setIcon(new ImageIcon(LoginClient.class.getResource("/images/background_img.jpg")));
		lblNewLabel.setBounds(0, 0, 557, 370);
		contentPane.add(lblNewLabel);
		
		
}
}
