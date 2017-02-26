package server;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

public abstract class User {
	public final int type;
	private byte[] password;
	private String name;
	
	public User(int type, String password, String name){
		this.type = type;
		this.name = name;
		try {
			this.password = SecurityTools.getPasswordHash(password, null);
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
	
	public boolean loginAttempt(String password){
		try {
			byte[] salt = Arrays.copyOf(this.password, 16);
			byte [] attempt = SecurityTools.getPasswordHash(password, salt);
			return Arrays.equals(this.password, attempt);
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
