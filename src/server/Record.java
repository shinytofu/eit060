package server;

public class Record {
	private Doctor doctor;
	private Nurse nurse;
	private String division;
	private String info;
	
	public Record(Doctor doctor, Nurse nurse, String division){
		this.doctor = doctor;
		this.nurse = nurse;
		this.division = division;
	}
	
	public void append(String append){
		info = info+"/n"+append;
	}
	
	public String toString(){
		return "Doctor: " + doctor + "/n" + "Nurse: " + nurse + "/n" + "Division: " + division + "/n" + info; 
	}
}
