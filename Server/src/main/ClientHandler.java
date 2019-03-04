package main;

import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.LinkedList;


public class ClientHandler extends Thread{
	Socket clientSocket = null;
	BufferedReader clientInput = null;
	PrintStream clientOutput = null;
	OutputStream clientOutputB = null;
	 String username;
	 String pass;
	LinkedList<String> fileKeys;
	

	public ClientHandler(Socket communicationSocket) {
		clientSocket = communicationSocket;
	}
	
	@Override
	public void run() {
		try {
			clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			clientOutput = new PrintStream(clientSocket.getOutputStream());
			clientOutputB = clientSocket.getOutputStream();
			
			String message;
				while(true){
					message = clientInput.readLine();
					System.out.println(message);
					if(message.substring(0, 3).equals("LOG")){
						username = message.substring(message.indexOf(':')+1, message.indexOf(' '));
						pass = message.substring(message.indexOf(' ')+1);
						if(daLiPostoji(username)){
							clientOutput.println("LOG_OK");
							System.out.println("********LOG_OK");
						} else{
							System.out.println("********LOG_ER");
							clientOutput.println("LOG_ER");
						}
					} else if(message.substring(0, 3).equals("SGN")){
						username = message.substring(message.indexOf(':')+1, message.indexOf(' '));
						pass = message.substring(message.indexOf(' ')+1);
						if(daLiPostoji(username)){
							System.out.println("********SGN_ER");
							clientOutput.println("SGN_ER");
						} else{
							addClient(username, pass);
							ServerClass.saveClients(ServerClass.clients);
							System.out.println("********SGN_OK");
							clientOutput.println("SGN_OK");
						}
					} else if(message.substring(0, 3).equals("UPL")){
						int numberOfRows = Integer.parseInt(message.substring(message.indexOf(' ')+1));
						username = message.substring(message.indexOf(':')+1, message.indexOf(' '));
						boolean excist = false;
						String key = "";
						
						do{
							key = randomKey();
							for(Client c : ServerClass.clients){
								for(String pk : c.filekeys){
									if(pk.equals(key)){
										excist = true;
										break;
									}
								}
								if(excist==true) break;
							} 
						}while(excist==true);
						
						PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(key+".txt")));
						for(int i=0;i<numberOfRows;i++){
							String file = clientInput.readLine();
							out.println(file);
						}
					out.close();
						for(Client c : ServerClass.clients){
							if(c.username.equals(username)){
								c.filekeys.add(key);
								ServerClass.saveClients(ServerClass.clients);
								break;
							}
						}
						System.out.println("********UPL_OK");
						clientOutput.println(key);
					} else if(message.substring(0, 3).equals("DOW")){
						String key = message.substring(message.indexOf(':')+1);
						File file = new File(key+".txt");
						if(file.exists()){
							clientOutput.println("DOW_OK");
							RandomAccessFile random = new RandomAccessFile(key+".txt", "r");
							int n;
							byte[] buffer = new byte[1024];
							while(true){
								n = random.read(buffer);
								if(n==-1){
									break;
								}
								clientOutputB.write(buffer,0,n);
							}
							random.close();
						} else {
							System.out.println("********DOW_ER");
							clientOutput.println("DOW_ER");
						}
					} else if(message.substring(0, 3).equals("MYF")){
						String username = message.substring(message.indexOf(':')+1);
						int prvi = 1;
						String myFiles="";
						for(Client c : ServerClass.clients){
							if(c.username.equals(username)){
								if(c.filekeys.isEmpty()){
									clientOutput.println("");
									break;
								}else{
								for(String pk : c.filekeys){
									if(prvi==1){
										myFiles=pk;
										prvi=0;
									}else{
									myFiles = myFiles + " " + pk;
									}
								}
								System.out.println("********MYF_OK");
								clientOutput.println(myFiles);
								break;
							}
							}
						}
						
					} else if(message.equals("EXIT")){
						System.out.println("********EXIT_OK");
						clientOutput.println("EXIT_OK");
						break;
					}
						}
				
				//clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	
	synchronized void addClient(String username, String pass){
		Client client = new Client(username, pass);
		ServerClass.clients.add(client);
	}
	
	public String randomKey(){
		String ab = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();
		 StringBuilder sb = new StringBuilder(10);
		   for(int i = 0; i < 10; i++) {
		      sb.append( ab.charAt( rnd.nextInt(ab.length()) ) );
		   }
		   return sb.toString();
	}
	
	public boolean daLiPostoji(String username){
		for(Client c : ServerClass.clients){
			if(c.username.equals(username)){
				return true;
			}
		}
		return false;
	}

}
