package org.cs282.SecureComputer;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenshotCommand extends Command {
	private final String fname = "./screenshot.png";
	private File savedFile;
	
	public File invoke(Device d, boolean file) {
		savedFile = new File(fname);
		try {
			BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
			ImageIO.write(image, "png", savedFile);
			return savedFile;
		} catch (HeadlessException | AWTException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
