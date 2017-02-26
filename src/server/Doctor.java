package server;

public class Doctor extends User {
	String division;

	public Doctor(String password, String division) {
		super(1, password);
		this.division = division;
	}

}
