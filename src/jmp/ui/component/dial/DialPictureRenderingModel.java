package jmp.ui.component.dial;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialPictureRenderingModel extends DialRenderingModel {
	private final static String DEFAULT_BACKGROUND_PATH = "pictures/dial/background.png";
	private final static String DEFAULT_NEEDLE_PATH = "pictures/dial/needle.png";
	private BufferedImage background;
	private BufferedImage needle;
	
	public DialPictureRenderingModel() {
		try
		{
			this.setBackground(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + DEFAULT_BACKGROUND_PATH)));
			this.setNeedle(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + DEFAULT_NEEDLE_PATH)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}
	public BufferedImage getBackground() {
		return background;
	}
	public void setNeedle(BufferedImage needle) {
		this.needle = needle;
	}
	public BufferedImage getNeedle() {
		return needle;
	}
}
