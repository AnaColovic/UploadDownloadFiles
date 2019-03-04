package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;


public class ServerClass {
	public static LinkedList<Client> clients = getClients();
	
	public static void main(String[] args) {
		int port = 9000;
		Socket communicationSocket = null;
		ServerSocket socket = null;
		
			try {
				socket = new ServerSocket(port);
				while(true){
					System.out.println("Waiting for connection...");
					communicationSocket = socket.accept();
					System.out.println("Communication established!");
					
					ClientHandler client = new ClientHandler(communicationSocket);
					client.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	
	public static LinkedList<Client> getClients(){
		LinkedList<Client> fileKeys = new LinkedList<Client>();
		try {
			ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream("clients.out")));
			try {
				while(true) {
					Client c= (Client) in.readObject();
					fileKeys.add(c);
				}
			} catch (EOFException e) {
				in.close();
				return fileKeys;
			}
			//in.close();
		} catch (Exception e) {
			
		}
		return fileKeys;
	}
	
	public static void saveClients(LinkedList<Client> updateClients){
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("clients.out")));
			for(int i=0;i<updateClients.size();i++){
				out.writeObject(updateClients.get(i));
			}
			
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
