package server;

import java.util.ArrayList;

public class Doctor extends User {
	private String division;
	private ArrayList<Patient> patients;

	public Doctor(String password, String division, String name) {
		super(1, password, name);
		this.division = division;
		patients = new ArrayList<Patient>();
	}
	
	public void addPatient(Patient patient){
		patients.add(patient);
	}
	
	public boolean isTreating(Patient patient){
		return patients.contains(patient);
	}
	
	public String getDivision(){
		return division;
	}
}
