package server;

import java.io.Serializable;

public class Record implements Serializable {
	private Doctor doctor;
	private Nurse nurse;
	private String division;
	private String info;
	
	public Record(Doctor doctor, Nurse nurse, String division){
		this.doctor = doctor;
		this.nurse = nurse;
		this.division = division;
		info = "";
	}
	
	public void append(String append){
		info += append + "\n";
	}
	
	public String toString(){
		return "Doctor: " + doctor + "\n" + "Nurse: " + nurse + "\n" + "Division: " + division + "\n" + "Info: " + info + "\n"; 
	}
	
	public boolean canRead(User callee){
		if (callee.type == 3){
			return true;
		}
		if (callee.type == 2){
			if (nurse.toString().equals(callee.toString())){
				return true;
			} else if (division.equals(((Nurse)callee).getDivision())){
				return true;
			}
		}
		if (callee.type == 1){
			if (doctor.toString().equals(callee.toString())){
				return true;
			} else if (division.equals(((Doctor)callee).getDivision())){
				return true;
			}
		}
		return false;
	}
	
	public boolean canWrite(User callee){
		if (callee.type == 2){
			if (nurse.toString().equals(callee.toString())){
				return true;
			}
		}
		if (callee.type == 1){
			if (doctor.toString().equals(callee.toString())){
				return true;
			}
		}
		return false;
	}
}
