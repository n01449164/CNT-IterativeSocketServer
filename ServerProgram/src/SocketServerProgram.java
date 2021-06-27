import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class SocketServerProgram {

	public static void main(String[] args) throws IOException {
		
		// Server start up routine: request network address and port and then listen for client requests
			String address;
			int connectionCount = 0; // Keeps track of every time a connection to the server is made.
			int port = 0;
			int backlog = 25;
			long uptime; // tracks server uptime once initialized (namely, once serversocketobject is bound)
			int socketTimeout = 1000 * 5; // 5 second socket timeout
			Console console = System.console();
			ServerSocket serversocketobject = new ServerSocket();
	
			
			System.out.println("Enter the network address.");
			Scanner consoleInput = new Scanner(System.in);
			
			//get address
			address = consoleInput.nextLine();
			
			// get port
			boolean choosePort = true;			
			while(choosePort) {
				try {
					System.out.println("Enter a port number between 1025 and 65535.");	
					port = consoleInput.nextInt();
					
					if(port < 1025 || port > 65535) {
						System.err.println("Invalid port input. Only ports between 1025 and 65535 allowed.");
					}
					else {
						choosePort = false;
					}
				} catch(Exception badPortInput) {
					badPortInput.printStackTrace();
					consoleInput.nextLine();
					port = 0;
				}
			}
	
			consoleInput.close();
			
		System.out.println("Network address input: " + address + " port input: " + port);
		
		// Create the network address to be used to create the endpoint
		InetAddress networkaddress = InetAddress.getLoopbackAddress();
		try {
			networkaddress = InetAddress.getByName(address);
		} catch(Exception UnknownHostException) {
			UnknownHostException.printStackTrace();
		}
		
		// Checking details in console
		System.out.println("InetAddress host name: " + networkaddress.getHostName() + " host name: "
		+ networkaddress.getHostAddress() + " port: " + port);
		
		// Binds ServerSocket to a specific port number, backlog, and network address
		serversocketobject.setSoTimeout(5*1000); // Set connection timeout for 5 seconds
		try {		
		System.out.println("Attempting to create serversocketobject with " + port + " and address: " + networkaddress);
		SocketAddress endpoint  = new InetSocketAddress(networkaddress, port);
		serversocketobject.bind(endpoint, backlog);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Successfully created serversocketobject."); // Check output	
		uptime = System.currentTimeMillis(); // initialize uptime
				
		// Listen for client requests, use connectionsList to store successful connections, marking each one with a unique ID.
		List<Connection> connectionsList = new ArrayList<>(); // Keeps Connection objects
		serversocketobject.setSoTimeout(socketTimeout); // Set timeout

		while(!serversocketobject.isClosed()) {
			// Listen for client connections and create a Connection when successful
			try {
				Connection connectionObject = new Connection(connectionCount, serversocketobject.accept());		
				if(connectionObject.connectionID != -1) {
					System.out.println("Server: connection successful.");
					connectionsList.add(connectionObject);
					connectionCount++;
					System.out.println("items in connectionsList: " + connectionsList.size());
								System.out.println("Connection count: " + connectionCount);
								
								if(connectionCount == 1000) { // Setting a termination point of 1000 connections for serversocketobject
									serversocketobject.close();
								}
						}
			} catch(Exception SocketTimeoutException) {
				System.err.println("Accept timedout.");
			}
			
			// Read request data
			while(!connectionsList.isEmpty()) {
				System.out.println(connectionsList.get(0).readInput());
				connectionsList.get(0).closeConnection();
				connectionsList.remove(0);		
			}
		}
			
		
		
		/**		
		// Read request data
		InputStream input = socket.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input));
		String line = reader.readLine();
		
		// Determine operation by reading from line
		
		// Perform requested operation and collect the resulting output
		
		// Prepare OutputStream to send data to client
		OutputStream output = socket.getOutputStream();
		PrintWriter writer = new PrintWriter(output, true);
		
		// Return date and time to client (1)
		Calendar calendar = Calendar.getInstance();
		writer.println("It is currently: " + calendar.getTime());
		
		// Return server uptime since last boot-up
		writer.println("Server uptime since last boot-up: " + (System.currentTimeMillis() - uptime));
		
		// Return current memory usage by server (Total memory alloted to program - free memory available to program)
		Runtime runtimeObject = Runtime.getRuntime();
		writer.println("Memory usage: " + (runtimeObject.totalMemory() - runtimeObject.freeMemory()));
		
		// Reply to the client request with the output from the operation performed
		
		
		// Clean up activities
		
		// Return to listening to client requests
		
		// Close serversocketobject, all currently connected clients disconnected
		serversocketobject.close();
		
			} catch(Exception timeout) {
				System.out.println("Socket timeout.");
			} // try #2
		
		} catch(Exception e) {
			System.out.println("Cannot resolve host name.");
		} // try #1
		**/
	}
	
}
