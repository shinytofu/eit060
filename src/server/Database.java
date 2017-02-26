package server;

import java.util.ArrayList;

public class Database {
	private ArrayList<User> users;
	
	public Database(User[] users){
		for(User u:users){
			this.users.add(u);
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
