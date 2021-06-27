import java.io.Console;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

class ClientThread extends Thread {
	private Socket socket;
	private int requestType = 1;
	
	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void sendOutput(String outputString) {
		try(OutputStream output = socket.getOutputStream();) {
			PrintWriter writer = new PrintWriter(output, true);
			writer.println(outputString);
		} catch(Exception IOException) {
			IOException.printStackTrace();
		}
		return;
	}
	
	@Override
	public void run() {
		// Attempt connection to server
		if(this.requestType == 1) {
			sendOutput("Client has sent output successfully. Server received output.");
		}
		System.out.println("Connection established. Terminating.");
	}
}
