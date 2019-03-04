package main;

import java.awt.EventQueue;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client{
	static BufferedReader serverInput = null;
	static PrintStream serverOutput = null;
	static Socket socket = null;
	static InputStream serverInputB = null;
	private static ClientGUI pocetni;
	private static LoginClient login;
	private static CreateGUI signup;
	private static LoginGuest guest;
	
	public static void main(String[] args) {
		try {
			socket = new Socket("localhost", 9000);
			serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			serverOutput = new PrintStream(socket.getOutputStream());
			
			
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						pocetni = new ClientGUI();
						pocetni.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
		} catch (UnknownHostException e) {
			System.out.println("Unknown host!");
		} catch (IOException e) {
			System.out.println("There's been a mistake. Server is down!");
		}
		
	}
	
	public static void userPass(String user, String pass,boolean signup){
		if(signup==true){
			serverOutput.println("SGN:"+user+" "+pass);
		} else{
			serverOutput.println("LOG:"+user+" "+pass);
		}
	}
	
	public static void exit(){
		serverOutput.println("EXIT");
	}
	
	public static void confirmExit(){
		String exit;
		try {
			exit = serverInput.readLine();
			socket.close();
		} catch (IOException e) {
		}
	}

	public static void upload(String username, String file){
		String[] niz = file.split("\n");
		serverOutput.println("UPL:"+username+" "+niz.length);
		for(int i = 0; i<niz.length;i++){
			serverOutput.println(niz[i]);
		}
	}
	
	public static void backSignUp(boolean sign){
		pocetni = new ClientGUI();
		pocetni.setVisible(true);
		if(sign==true){
		signup.dispose();
		} else guest.dispose();
	}
	
	public static void download(String privateKey){
		serverOutput.println("DOW:"+privateKey);
	}
	
	public static void filesIUploaded(String username){
		serverOutput.println("MYF:"+username);
	}
	
	public static void logOut(){
		pocetni = new ClientGUI();
		pocetni.setVisible(true);
		login.dispose();
	}
	
	public static boolean validClient(){
		String valid;
		try {
			valid = serverInput.readLine();
			if(valid.equals("LOG_OK") || valid.equals("SGN_OK")){
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static String returnPrivateKey(){
		String valid = null;;
		try {
			valid = serverInput.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valid;
	}
	
	public static boolean getFile(String key){
		String valid;
		try {
			valid = serverInput.readLine();
			if(valid.equals("DOW_ER")){
				return false;
			}
			RandomAccessFile random = new RandomAccessFile(key+".txt", "rw");
			serverInputB =  socket.getInputStream();
			byte[] buffer = new byte[1024];
			int n;

			while(true){
				n=serverInputB.read(buffer,0,1024);
				random.write(buffer,0,n);
				if(n < 1024){
					break;
				}
			}
			random.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static String getMyFiles(){
		String myFiles;
		try {
			myFiles = serverInput.readLine();
			return myFiles;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getTextFromFile(String privateKey){
		BufferedReader in;
		String s="";
		boolean end = false;
		try {
			in = new BufferedReader(new FileReader(privateKey+".txt"));
			while(!end){
				String pom = in.readLine();
				if(pom==null) end=true;
				else s=s+pom+'\n';
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
	
	public static void openClient(String username, boolean choose){
		login = new LoginClient(username);
		login.setVisible(true);
		if(choose==false){
			pocetni.dispose();
		}else signup.dispose();
		
	}
	
	public static void openSignUp(){
		signup = new CreateGUI();
		signup.setVisible(true);
		pocetni.dispose();
	}
	
	public static void openGuest(){
		guest = new LoginGuest();
		guest.setVisible(true);
		pocetni.dispose();
	}

	
}

