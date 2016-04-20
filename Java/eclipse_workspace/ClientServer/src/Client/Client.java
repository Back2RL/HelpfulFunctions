package Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client extends Thread {

	private static final String HOST = "127.0.0.1";
	private static final int PORT = 1235;

	private static Socket connection;
	private boolean bIsRunning;

	public static Socket getConnection() {
		return connection;
	}

	public static void setConnection(Socket connection) {
		Client.connection = connection;
	}

	public boolean isbIsRunning() {
		return bIsRunning;
	}

	public void setbIsRunning(boolean bIsRunning) {
		this.bIsRunning = bIsRunning;
	}

	public static String getHost() {
		return HOST;
	}

	public static int getPort() {
		return PORT;
	}

	public Client(Socket in) {
		connection = in;
	}

	public void run() {
		bIsRunning = true;
		System.out.println(Thread.currentThread().getName() + " Client ready to receive");
		try {
			PrintWriter send = new PrintWriter(connection.getOutputStream());
			String message = "Hallo Server";
			send.println(message);
			send.flush();
			System.out.println("sent: " + message);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try (Scanner receive = new Scanner(connection.getInputStream())) {
			while (bIsRunning && receive.hasNext()) {

				System.out.println("received: " + receive.nextLine());
			}
			bIsRunning = false;
			System.out.println(Thread.currentThread().getName() + " Disconnect from Server");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try (Socket server = new Socket(HOST, PORT)) {
			Client receiveThread = new Client(server);
			receiveThread.start();
			receiveThread.join();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Connecting to server failed! EXIT");
			System.exit(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Exit");

	}

}
