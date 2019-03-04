package main;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class LoginGuest extends JFrame {

	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JTextField textField;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 */
	public LoginGuest() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Client.exit();
				Client.confirmExit();
			}
		});
		setTitle("Guest");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 573, 409);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Private key");
		lblNewLabel_1.setBounds(196, 71, 107, 29);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setForeground(Color.WHITE);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Client.backSignUp(false);
			}
		});
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setFocusable(false);
		btnNewButton.setAlignmentX(SwingConstants.LEADING);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnNewButton.setIcon(new ImageIcon(LoginGuest.class.getResource("/images/back (1).png")));
		btnNewButton.setBounds(0, 11, 101, 23);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setAutoscrolls(true);
		scrollPane.setBounds(24, 197, 522, 172);
		contentPane.add(scrollPane);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setAutoscrolls(false);
		textArea.setVisible(false);
		scrollPane.setViewportView(textArea);
		scrollPane.setVisible(false);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String fileKey = textField.getText();
				File file = new File("C:\\Users\\ANA\\workspace\\neon\\Client\\"+fileKey+".txt");
				Client.download(fileKey);
				boolean valid = Client.getFile(fileKey);
				if(valid){
					JOptionPane.showMessageDialog(contentPane,"You succesfully downloaded this file!");
					scrollPane.setVisible(true);
					textArea.setVisible(true);
					String s = Client.getTextFromFile(fileKey);
					textArea.setText(s);
					textArea.setEditable(false);
				} else {
					JOptionPane.showMessageDialog(contentPane,"The file doesn't excist!");
				}
			}
		});
		
		btnSubmit.setBounds(237, 151, 89, 23);
		btnSubmit.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(btnSubmit);
		
		textField = new JTextField();
		textField.setBounds(196, 99, 168, 29);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblDonwloadFilesYou = new JLabel("Download files using their private key:");
		lblDonwloadFilesYou.setBounds(24, 45, 557, 29);
		lblDonwloadFilesYou.setForeground(Color.WHITE);
		lblDonwloadFilesYou.setFont(new Font("Calibri", Font.PLAIN, 20));
		contentPane.add(lblDonwloadFilesYou);
		panel.setBounds(0, 0, -2, 10);
		contentPane.add(panel);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 0, 567, 391);
		lblNewLabel.setIcon(new ImageIcon(LoginGuest.class.getResource("/images/background_img.jpg")));
		contentPane.add(lblNewLabel);
		
		
		
		
	}
}
