package com.example.securecomputerclient;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class SCActivity extends Activity {
	
	final String host = "ngrok.com";
	final int port = 40247;
	final String fname = "screenshot.png";
	private Bitmap screenshot;
	private ImageView ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sc);
		ss = (ImageView) findViewById(R.id.screenshot_field);	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sc, menu);
		return true;
	}
	
	public void recieveBitmapStream(View v) {
		try {
			Socket sock = new Socket(host, port); 
			
			FileOutputStream outFile = new FileOutputStream(fname);
			int bytecount = 2048;
			byte[] buf = new byte[bytecount];
			
			InputStream in = sock.getInputStream();
			BufferedInputStream bin = new BufferedInputStream(in, bytecount);
			
			int i = 0;
			int filelength = 0;
			while((i = bin.read(buf, 0, bytecount)) != -1) {
				filelength += 1;
				outFile.write(buf, 0, i);
				outFile.flush();
			}
			
			screenshot = BitmapFactory.decodeFile(fname);
			screenshot.compress(Bitmap.CompressFormat.PNG, 100, outFile);
			SCActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					ScreenshotCommandProcessor scp = new ScreenshotCommandProcessor(ss);
					scp.setScreenshot(screenshot);
					scp.handleCommand();
				}
			});
			outFile.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
