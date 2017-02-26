package server;

import java.util.ArrayList;

public class Patient extends User {
	private ArrayList<Record> recordList;

	public Patient(int type, String password) {
		super(0, password);
		recordList = new ArrayList<Record>();
	}
	
	public void createRecord(Doctor doctor, Nurse nurse, String division){
		recordList.add(new Record(doctor, nurse, division));
	}
	
	public void appendToRecord(int index, String append){
		recordList.get(index).append(append);
	}

	public String[] listRecords(){
		ArrayList<String> strings = new ArrayList<String>();
		for(Record r : recordList){
			strings.add(r.toString());
		}
		String[] toReturn = new String[1];
		return strings.toArray(toReturn);
	}
}
