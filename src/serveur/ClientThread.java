package serveur;

import game.Game;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class ClientThread extends Thread {
	
	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run () {
		//System.err.println("Nouveau client connecté");

        // on ouvre un flux de converation

        BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
        BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}

        // chaque fois qu'une donnée est lue sur le réseau on la renvoi sur
        // le flux d'écriture.
        // la donnée lue est donc retournée exactement au méme client.
        String s;
        String getCommand = "";
        try {
			while ((s = in.readLine()) != null) {
				if (s.startsWith("GET")) {
					System.out.println(s);
					getCommand = s;
				} else if (s.startsWith("POST")) {
					System.out.println(s);
					getCommand = s;
				}
			    //System.out.println(s);
			    if (s.isEmpty()) {
			        break;
			    }
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

        Message msg = null;
    	String request = getCommand.replaceAll("GET ", "").replaceAll("POST ", "").replaceAll(" HTTP/.*", "").replaceAll(" ", "");
    	//System.out.println("-> " + request);
    	if(request.equals("/")) {
    		msg = this.returnIndex(out);
    	} else if (request.startsWith("/game/")) {
    		String sub = request.substring(6);
	    	msg = Game.gi.getMessage(sub);
    	} else {
    		msg = this.returnFile(request, out);
    	}

        
        try {
	        out.write("HTTP/1.0 " + msg.getCode() + " OK\n");
	        
	        Date aujourdhui = new Date();
	        DateFormat fullDateFormatEN = DateFormat.getDateTimeInstance(
	        		DateFormat.FULL,
	        		DateFormat.FULL, new Locale("EN","en")
	        );
	        out.write("Date: " + fullDateFormatEN.format(aujourdhui) + "\n");
	        out.write("Server: Yoann Serveur\n");
	        out.write("Content-Type: " + msg.getType() + "\n");
	        out.write("Content-Length: " + msg.getSize() + "\n");
	        out.write("Expires: Sat, 01 Jan 2020 00:59:59 GMT\n");
	        out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\n");
	        out.write("\n");
	        out.write(msg.getMsg());
	        
	        // on ferme les flux.
	        //System.err.println("Connexion avec le client terminée");
	        out.close();
	        in.close();
	        socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Message returnFile(String filename, BufferedWriter out) {
		String msg = "";
		File file = new File("client/" + filename);
		File root = new File("client/");
		
		if (!file.exists() || file.isDirectory() 
				|| !file.getAbsolutePath().startsWith(root.getAbsolutePath()))
			return new Message(msg, "unknown", 200);
		
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			System.out.println(filename);
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		try {
			while ((line = br.readLine()) != null) {
				msg += line + "\n";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String type = "text/html";
		if (filename.endsWith(".js"))
			type = "text/javascript";
		return new Message(msg, type, 200);
	}

	private Message returnIndex(BufferedWriter out) {
		return this.returnFile("index.html", out);
	}

}
