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
	
	public boolean canRead(User callee){
		if (callee.type == 3){
			return true;
		}
		if (callee.type == 2){
			if (nurse.toString().equals(callee.toString())){
				return true;
			} else if (nurse.getDivision().equals(((Nurse)callee).getDivision())){
				return true;
			}
		}
		if (callee.type == 1){
			if (doctor.toString().equals(callee.toString())){
				return true;
			} else if (doctor.getDivision().equals(((Doctor)callee).getDivision())){
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
