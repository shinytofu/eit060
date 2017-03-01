package server;

import java.io.*;
import java.net.*;
import java.security.KeyStore;
import javax.net.*;
import javax.net.ssl.*;
import javax.security.cert.X509Certificate;

public class Server implements Runnable {
	private ServerSocket serverSocket = null;
	private static int numConnectedClients = 0;
	private Database database;
	private Logger logger;

	public Server(ServerSocket ss) throws IOException {
		serverSocket = ss;
		newListener();

		// init database
		/*
		Doctor master = new Doctor("master", "master", "master");
		Nurse maester = new Nurse("master", "master", "master");
		User[] users = new User[6];
		users[0] = new Gov("gov", "Gov");
		users[1] = new Doctor("smith", "JohnSmith", "Division1");
		users[2] = new Nurse("duck", "DonaldDuck", "Division1");

		Patient one = new Patient("white", "AnnaWhite");
		master.addPatient(one);
		one.createRecord(master, maester, "Division2");
		one.createRecord(master, maester, "Division1");
		one.createRecord(master, maester, "Division1");
		users[3] = one;

		Patient two = new Patient("black", "JasonBlack");
		master.addPatient(two);
		((Doctor) users[1]).addPatient(two);
		two.createRecord((Doctor) users[1], maester, "Division2");
		two.createRecord((Doctor) users[1], maester, "Division1");
		two.createRecord(master, (Nurse) users[2], "Division1");
		users[4] = two;

		Patient three = new Patient("green", "BrunoGreen");
		master.addPatient(three);
		((Doctor) users[1]).addPatient(three);
		three.createRecord(master, (Nurse) users[2], "Division2");
		users[5] = three;*/

		
		logger = new Logger();
		database = new Database(null);
		//database.save();
		database.load();
	}

	public void run() {
		try {
			SSLSocket socket = (SSLSocket) serverSocket.accept();
			newListener();
			SSLSession session = socket.getSession();
			X509Certificate cert = (X509Certificate) session.getPeerCertificateChain()[0];
			String subject = cert.getSubjectDN().getName();
			numConnectedClients++;
			System.out.println("client connected");
			System.out.println("client name (cert subject DN field): " + subject);
			System.out.println("issuer: " + cert.getIssuerDN().getName());
			System.out.println("serialno: " + cert.getSerialNumber().toString());
			System.out.println(numConnectedClients + " concurrent connection(s)\n");
			PrintWriter out = null;
			BufferedReader in = null;
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String clientMsg = null;

			// password implementation
			String[] CNAndName = subject.split("=");
			User currentUser = database.authenticate(CNAndName[1], in.readLine());

			if (currentUser != null) {
				out.println("Authenticated");
				logger.append(currentUser.toString() + " was successfully authenticated");
				out.flush();
				CommandHandler cmd = new CommandHandler(currentUser, database, logger);
				while ((clientMsg = in.readLine()) != null) {
					String reply = cmd.handle(clientMsg);
					// System.out.println("received '" + clientMsg + "' from client");
					// System.out.print("sending '" + rev + "' to client...");
					out.println(reply);
					out.flush();
				}
			} else {
				logger.append("Failed Authentication: " + subject);
				out.println("Bad Credentials. Closing connection ..");
				out.flush();
			}
			in.close();
			out.close();
			socket.close();
			numConnectedClients--;
			System.out.println("client disconnected");
			System.out.println(numConnectedClients + " concurrent connection(s)\n");
		} catch (IOException e) {
			System.out.println("Client died: " + e.getMessage());
			e.printStackTrace();
			return;
		}
	}

	private void newListener() {
		(new Thread(this)).start();
	} // calls run()

	public static void main(String args[]) {
		System.out.println("\nServer Started\n");
		int port = 1337;
		if (args.length >= 1) {
			port = Integer.parseInt(args[0]);
		}
		String type = "TLS";
		try {
			ServerSocketFactory ssf = getServerSocketFactory(type);
			ServerSocket ss = ssf.createServerSocket(port);
			((SSLServerSocket) ss).setNeedClientAuth(true); // enables client
															// authentication
			new Server(ss);
		} catch (IOException e) {
			System.out.println("Unable to start Server: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static ServerSocketFactory getServerSocketFactory(String type) {
		if (type.equals("TLS")) {
			SSLServerSocketFactory ssf = null;
			try { // set up key manager to perform server authentication
				SSLContext ctx = SSLContext.getInstance("TLS");
				KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
				TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
				KeyStore ks = KeyStore.getInstance("JKS");
				KeyStore ts = KeyStore.getInstance("JKS");
				char[] password = "password".toCharArray();

				ks.load(new FileInputStream("serverkeystore"), password); // keystore
																			// password
																			// (storepass)
				ts.load(new FileInputStream("servertruststore"), password); // truststore
																			// password
																			// (storepass)
				kmf.init(ks, password); // certificate password (keypass)
				tmf.init(ts); // possible to use keystore as truststore here
				ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
				ssf = ctx.getServerSocketFactory();
				return ssf;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			return ServerSocketFactory.getDefault();
		}
		return null;
	}
}
