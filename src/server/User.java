package server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public abstract class User {
	public final int type;
	private byte[] password;
	private String name;
	
	public User(int type, String password, String name){
		this.type = type;
		this.name = name;
		try {
			this.password = SecurityTools.getPasswordHash(password);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginAttempt(String password){
		try {
			byte [] attempt = SecurityTools.getPasswordHash(password);
			boolean equal = true;
			int index = 0;
			while (equal && index < attempt.length){
				equal = this.password[index] == attempt[index];
				index ++;
			}
			return equal;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
			return false;
		}		
	}
	
	public boolean equals(Object o){
		if (o instanceof User){
			if (((User)o).name.equals(name)){
				return true;
			}
		}
		return false;
	}
	
	public String toString(){
		return name;
	}
}
