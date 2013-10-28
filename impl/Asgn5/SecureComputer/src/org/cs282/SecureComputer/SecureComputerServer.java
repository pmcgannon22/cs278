package org.cs282.SecureComputer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SecureComputerServer {
	private final int portNumber;
	
	public SecureComputerServer(int port) {
		portNumber = port;
	}
	
	public void startServer() {
		try {
			ServerSocket server = new ServerSocket(portNumber);
			System.out.println("1");
			Socket client = server.accept();
			System.out.println("2");
			handleRequest(client);
			/*File file = new File("filename"); 
			FileInputStream stream = new FileInputStream(file);
			DataOutputStream writer = new DataOutputStream(sender.getOutputStream());*/
			System.out.println("here");
			server.close();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
	}
	
	public void handleRequest(Socket client) {
		String command;
		RegisteredDevice rd = new RegisteredMac(1);
		rd.addCommand("system-information", new SystemInfoCommand());
		rd.addCommand("screenshot", new ScreenshotCommand());
		command = "screenshot";
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			//command = in.readLine();
			System.out.println("Cmd: " + command);
			sendFile(rd.runCommand(command), client);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendString(String s, Socket sock) {
		PrintWriter out;
		try {
			
			out = new PrintWriter(sock.getOutputStream(), true);
			//BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out.println(s);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFile(File img, Socket sock) {
		
	}
}
