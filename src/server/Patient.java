package server;

import java.util.ArrayList;

public class Patient extends User {
	private ArrayList<Record> recordList;

	public Patient(String password, String name) {
		super(0, password, name);
		recordList = new ArrayList<Record>();
	}

	public boolean createRecord(User callee, Nurse nurse, String division) {
		if (callee.type == 1){
			if (((Doctor)callee).isTreating(this)) {
				recordList.add(new Record((Doctor)callee, nurse, division));
				return true;
			}
		}	
		return false;
	}

	public int appendToRecord(User callee, int index, String append) {
		if (index > recordList.size()) {
			return -1;
		}
		Record r = recordList.get(index - 1); // -1 because the list starts at 1
												// and not 0
		if (r.canWrite(callee)) {
			r.append(append);
			return 1;
		}
		return 0;
	}

	public String[] listRecords(User callee) {
		ArrayList<String> strings = new ArrayList<String>();
		int index = 1;
		for (Record r : recordList) {
			if (callee.equals(this) || r.canRead(callee))
				strings.add(index + ":\n" + r.toString()); // I am aware that
															// information about
															// number of records
															// might leak to
															// anyone with
															// access to a
															// single record
			index++;
		}
		String[] toReturn = new String[1];
		return strings.toArray(toReturn);
	}
	
	public int deleteRecord(User callee, int index){
		if (index > recordList.size()) {
			return -1;
		}
		if (callee.type == 3){
			recordList.remove(index);
			return 1;
		}
		return 0;
	}
}
