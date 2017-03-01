package server;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
	public Logger() {
	}

	public void append(String message) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try (
			FileWriter fw = new FileWriter("log", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw)
		) {
			out.println(dateFormat.format(date) + " " + message);
			// more code
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
			e.printStackTrace();
		}
	}
}
