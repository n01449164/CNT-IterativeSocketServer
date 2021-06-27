import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Scanner;

public class Main  {

	public static void main(String[] args) throws IOException {
		String hostname;
		int port = 0;
		int numberOfRequests = 25;
		
		// Input host name
		System.out.println("Enter the host name");
		Scanner consoleInput = new Scanner(System.in);
		hostname = consoleInput.nextLine();
		
		// Input port
		boolean choosePort = true;			
		while(choosePort) {
			try {
				System.out.println("Enter a port number between 1025 and 65535.");	
				port = consoleInput.nextInt();
				
				if(port < 1025 || port > 65535) {
					System.out.println("Invalid port input.");
				}
				else {
					choosePort = false;
				}
			} catch(Exception badPortInput) {
				System.err.println("Invalid port input.");
				consoleInput.nextLine();
				port = 0;
			}
		}
		
		// Specify number of requests
		
		boolean chooseRequests = true;
		while(chooseRequests) {
			System.out.printf("What kind of request would you like to send?%n1. Date & Time %n2. Uptime %n3. Memory use. %n4."
					+ " Netstat %n5. Current users%n6. Running processes.%n%n");
			System.out.printf("Specify the number of requests: %n1. One request.%n2. 5 requests.%n3. 10 requests%n"
					+ "4. 15 requests.%n5. 20 requests%n6. 25 requests.%n");
			chooseRequests = false;
			
		}
		
		consoleInput.close(); // close scanner
		
		// Attempt connection to server
		SocketAddress endpoint = new InetSocketAddress(hostname, port);
		int requestsAttempted = 0; // Change numberOfRequests once input Chooserequests is made.
		
		while(requestsAttempted < numberOfRequests) {
			try(Socket socket = new Socket()) {
				socket.connect(endpoint, (1 * 1000)); // One second, change later
				new ClientThread(socket).run();
				System.out.println("Request number: " + requestsAttempted);
				requestsAttempted++;
			} catch (Exception e) {
				System.err.println("Request number: " + requestsAttempted + " Result: " + "Connection refused: no further information.");
				requestsAttempted++;
			}
		}
	}

}
