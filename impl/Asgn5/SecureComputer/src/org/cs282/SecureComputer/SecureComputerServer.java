package org.cs282.SecureComputer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SecureComputerServer {
	private final int portNumber;
	private RegisteredDevice rd;
	
	public SecureComputerServer(int port) {
		portNumber = port;
		rd = new RegisteredMac(1);
		rd.addCommand("system-information", new SystemInfoCommand());
		rd.addCommand("screenshot", new ScreenshotCommand());
	}
	
	public void startServer() {
		try {
			ServerSocket server = new ServerSocket(portNumber);
			Socket client = server.accept();
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

		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			command = in.readLine();
			System.out.println("Cmd: " + command);
			sendFile(rd.runCommand(command, true), client);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendString(String s, Socket sock) {
		PrintWriter out;
		try {
			
			out = new PrintWriter(sock.getOutputStream(), true);
			out.println(s);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFile(File img, Socket sock) {
		int bytecount = 2048;
		byte[] buf = new byte[bytecount];
		try {
			OutputStream out = sock.getOutputStream();
			BufferedOutputStream bout = new BufferedOutputStream(out, bytecount);
			FileInputStream in = new FileInputStream(img);
			
			int i = 0;
			while((i = in.read(buf, 0, bytecount)) != -1) {
				bout.write(buf, 0, i);
				bout.flush();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
