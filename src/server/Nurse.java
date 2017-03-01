package server;

import java.io.Serializable;

public class Nurse extends User {
	private String division;

	public Nurse(String password, String name, String division) {
		super(2, password, name);
		this.division = division;
	}
	
	public String getDivision(){
		return division;
	}

}
