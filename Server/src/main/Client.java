package main;

import java.io.Serializable;
import java.util.LinkedList;

public class Client implements Serializable{
	 String username;
	 String pass;
	 LinkedList<String> filekeys;
	
	public Client(String user, String password) {
		username = user;
		pass = password;
		filekeys = new LinkedList<>();
	}
}
