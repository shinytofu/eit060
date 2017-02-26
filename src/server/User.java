package server;

public abstract class User {
	public final int type;
	private String password;
	
	public User(int type, String password){
		this.type = type;
		this.password = password;
	}
}
