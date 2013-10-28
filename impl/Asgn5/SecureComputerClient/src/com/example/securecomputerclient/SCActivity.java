package com.example.securecomputerclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class SCActivity extends Activity {
	private String message;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sc);
		
		final String host = "ngrok.com";
		final int port = 40247;
		new Thread(new Runnable() {   

			@Override
			public void run() {
				try { 
					Socket s = new Socket(host, port); 
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					String input;  
					try { 
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					PrintWriter out = new PrintWriter(s.getOutputStream(), true);
					out.write("system-information");
					
					try { 
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					while((input = in.readLine()) != null) {
						Log.d("MSG", input);
						message = input;
					}
					SCActivity.this.runOnUiThread(new Runnable() {
						public void run(){
							TextView t = (TextView) findViewById(R.id.text);
							t.setText(message);
						}
					});
					in.close();
					out.close();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}).start();
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sc, menu);
		return true;
	}

}
