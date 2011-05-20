package jmp.ui.component.dial;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialPictureRenderingModel extends DialRenderingModel {
	private final static String DEFAULT_BACKGROUND_PATH = "pictures/dial/default_background.png";
	private final static String DEFAULT_NEEDLE_PATH = "pictures/dial/default_needle.png";
	
	private BufferedImage background;
	private BufferedImage needle;
	
	public DialPictureRenderingModel() {
		this(DEFAULT_BACKGROUND_PATH, DEFAULT_NEEDLE_PATH);
	}
	
	public DialPictureRenderingModel(String backgroundPath, String needlePath) {
		try
		{
			this.setBackground(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + backgroundPath)));
			this.setNeedle(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + needlePath)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
		setChanged(true);
	}
	public BufferedImage getBackground() {
		return background;
	}
	public void setNeedle(BufferedImage needle) {
		this.needle = needle;
		setChanged(true);
	}
	public BufferedImage getNeedle() {
		return needle;
	}
}
