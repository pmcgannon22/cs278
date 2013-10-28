package com.example.securecomputerclient;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ScreenshotCommandProcessor implements CommandProcessor {
	private Bitmap sshot;
	private ImageView iview;
	
	public ScreenshotCommandProcessor(ImageView i) {
		iview = i;
	}

	@Override
	public void handleCommand() {
		iview.setImageBitmap(sshot);
	}
	
	public void setScreenshot(Bitmap img) {
		sshot = img;
	}

	@Override
	public String getCommandName() {
		return "screenshot";
	}

}
