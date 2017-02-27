package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Database {
	private ArrayList<User> users;
	
	public Database(User[] users){
		if (users != null) {			
			this.users = new ArrayList<User>();
			for(User u:users){
				this.users.add(u);
			}
		}
	}
	
	public void save() {
		try {			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database"));
			oos.writeObject(users);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void load() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("database"));
			users = (ArrayList<User>) ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public User search(String name){
		User u = new Patient("temp", name);
		int index = users.lastIndexOf(u);
		if (index == -1) {
			return null;
		} else {
			return users.get(index);
		}		
	}
	
//	public Patient find(String name){
//		User u = new Patient("temp", name);
//		int index = users.lastIndexOf(u);
//		if (index == -1) {
//			return null;
//		} else {
//			u = users.get(index);
//			if (u.type == 0){
//				return (Patient) u;
//			} else {
//				return null;
//			}
//		}		
//	}
	
	public User authenticate(String name, String password){
		User u = search(name);
		if (u.loginAttempt(password)){
			return u;
		} else {
			return null;
		}
	}
}
