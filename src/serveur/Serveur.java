package serveur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Serveur {
	
	private ServerSocket ss;
	private int port;
	
	public Serveur() {
		// création de la socket
	    this.port = 1212;
	    try {
			this.ss = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.err.println("Serveur lancé sur le port : " + port);
	}
	
	public void ecoute () {
		// repeatedly wait for connections, and process
	    while (42 != port) {
	        // on reste bloqué sur l'attente d'une demande client
	        Socket clientSocket = null;
			try {
				clientSocket = this.ss.accept();
				ClientThread ct = new ClientThread(clientSocket);
				ct.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    try {
			this.ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
	    Serveur serv = new Serveur();
	    serv.ecoute();
	}
}
