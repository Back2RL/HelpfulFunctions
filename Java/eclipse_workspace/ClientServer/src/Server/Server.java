package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server extends Thread {

	private static final int PORT = 1235;

	private static ServerSocket connection;
	private boolean bIsRunning;
	private Socket client;

	public boolean isbIsRunning() {
		return bIsRunning;
	}

	public void setbIsRunning(boolean bIsRunning) {
		this.bIsRunning = bIsRunning;
	}

	public Server(Socket client) {
		this.client = client;
	}

	public void run() {
		bIsRunning = true;
		System.out.println(Thread.currentThread().getName() + " Server ready to receive");
		try (PrintWriter send = new PrintWriter(client.getOutputStream())) {
			String message = "Hallo Client";
			send.println(message);
			send.flush();
			System.out.println("sent: " + message);
			Scanner receive = new Scanner(client.getInputStream());
			while (bIsRunning && receive.hasNext()) {

				System.out.println("received: " + receive.nextLine());
			}
			receive.close();
			bIsRunning = false;
			System.out.println(Thread.currentThread().getName() + " Disconnect from Client");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ArrayList<Server> connections = new ArrayList<>();

		Socket client = null;
		try (ServerSocket server = new ServerSocket(PORT)) {
			while (true) {
				for (Server connection : connections) {
					if (!connection.isAlive()) {
						connections.remove(connection);
					}
				}

				System.out.println("Waiting for clients");
				client = server.accept();
				System.out.println("New Connection");
				Server newConnection = new Server(client);
				connections.add(newConnection);
				newConnection.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
