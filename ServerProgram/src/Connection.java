import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

final class Connection {
		int connectionID = -1;
		Socket socket;
		
		// Constructor
		public Connection(int connectionID, Socket sock) {
			try {
				this.socket = sock;
				this.connectionID = connectionID;
			} catch(Exception SocketTimeoutException) {
				SocketTimeoutException.printStackTrace();
			}
		}
		
		// Reads input from connection's input stream.
		public String readInput() {
			String inputString = "";
			try(InputStream input = socket.getInputStream();) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));
				inputString = reader.readLine();
			} catch(Exception IOException) {
				IOException.printStackTrace();
			}
			return inputString;
		}
		
		// Send output to socket
		public void sendOutput(String outputString) {
			try(OutputStream output = socket.getOutputStream();) {
				PrintWriter writer = new PrintWriter(output, true);
				writer.println(outputString);
			} catch(Exception IOException) {
				IOException.printStackTrace();
			}
			return;
		}
		
		// Closes socket
		public void closeConnection() {
			try {
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
}
