package server;

public class Nurse extends User {
	private String division;

	public Nurse(String password, String division, String name) {
		super(2, password, name);
		this.division = division;
	}
	
	public String getDivision(){
		return division;
	}

}
