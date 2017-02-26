package server;

public class Nurse extends User {
	String division;

	public Nurse(String password, String division) {
		super(2, password);
		this.division = division;
	}

}
