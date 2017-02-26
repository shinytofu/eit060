package server;

import java.util.ArrayList;

public class Patient extends User {
	private ArrayList<Record> recordList;

	public Patient(String password, String name) {
		super(0, password, name);
		recordList = new ArrayList<Record>();
	}

	public boolean createRecord(Doctor callee, Nurse nurse, String division) {
		if (callee.isTreating(this)) {
			recordList.add(new Record(callee, nurse, division));
			return true;
		}
		return false;
	}

	public boolean appendToRecord(User callee, int index, String append) {
		Record r = recordList.get(index - 1); // -1 because the list starts at 1
												// and not 0
		if (r.canWrite(callee)) {
			r.append(append);
			return true;
		}
		return false;
	}

	public String[] listRecords(User callee) {
		ArrayList<String> strings = new ArrayList<String>();
		int index = 1;
		for (Record r : recordList) {
			if (callee.equals(this) || r.canRead(callee))
				strings.add(index + ":/n" + r.toString()); // I am aware that
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
	
	public boolean removeRecord(User callee, int index){
		if (callee.type == 3){
			recordList.remove(index);
			return true;
		}
		return false;
	}
}
