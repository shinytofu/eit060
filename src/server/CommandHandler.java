package server;

public class CommandHandler {
	private User currentUser;
	private Database database;

	public CommandHandler(User currentUser, Database database) {
		this.currentUser = currentUser;
		this.database = database;
	}

	public String handle(String clientMsg) {
		String[] commands = clientMsg.split(" ");
		if (check(commands)) {
			User user = database.search(commands[2]);
			if (user == null || user.type != 0){
				return "No such patient";
			}
			Patient patient = (Patient)user;
			switch (commands[0]) {
			case "createRecord":
				User nurse = database.search(commands[4]);
				if (nurse == null || nurse.type != 2){
					return "No such nurse";
				}
				if (patient.createRecord((Doctor)currentUser, (Nurse)nurse, commands[6])) {
					return "Record created";
				} else {
					return "Access not granted";
				}
			case "append":
				int index;
				try {
					index = Integer.parseInt(commands[4]);
				} catch (NumberFormatException e) {
					return "-record must be index of existing record";
				}
				String info = "";
				for (int i = 6; i< commands.length; i++){
					info += commands[i]+ " ";
				}
				int status = patient.appendToRecord(currentUser, index, info);			
				if (status == 1) {
					return "Append successful";
				} else if (status == 0){
					return "Access not granted";
				} else {
					return "-record must be index of existing record";
				}
			case "list":
				String [] records = patient.listRecords(currentUser);
				String oneString = "";
				for(String s: records){
					oneString += s;
				}
				return oneString;
			case "deleteRecord":
				int index2;
				try {
					index2 = Integer.parseInt(commands[4]);
				} catch (NumberFormatException e) {
					return "-record must be index of existing record";
				}
				int status2 = patient.deleteRecord(currentUser, index2);			
				if (status2 == 1) {
					return "Record removed";
				} else if (status2 == 0){
					return "Access not granted";
				} else {
					return "-record must be index of existing record";
				}
			default:
				commands[0] = "Invalid command";
				return "error";
			}
		}
		return "Bad commands";
	}

	private boolean check(String[] commands) {
		if (commands[0].equals("createRecord") && commands.length == 7 && commands[1].equals("-patient")
				&& commands[3].equals("-record") && commands[5].equals("-info")) {
			return true;
		}
		if (commands[0].equals("append") && commands.length >= 7 && commands[1].equals("-patient")
				&& commands[3].equals("-record") && commands[5].equals("-info")) {
			return true;
		}
		if (commands[0].equals("list") && commands.length == 3 && commands[1].equals("-patient")) {
			return true;
		}
		if (commands[0].equals("deleteRecord") && commands.length == 5 && commands[1].equals("-patient")
				&& commands[3].equals("-record")) {
			return true;
		}
		return false;
	}

}
