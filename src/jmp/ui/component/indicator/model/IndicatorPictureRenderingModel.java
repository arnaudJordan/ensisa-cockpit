package jmp.ui.component.indicator.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class IndicatorPictureRenderingModel extends IndicatorRenderingModel{
	private final static String DEFAULT_ON_IMAGE_PATH = "pictures/indicator/default_on.png";
	private final static String DEFAULT_OFF_IMAGE_PATH = "pictures/indicator/default_off.png";
	
	private BufferedImage onImage;
	private BufferedImage offImage;
	
	public IndicatorPictureRenderingModel() {
		this(DEFAULT_ON_IMAGE_PATH, DEFAULT_OFF_IMAGE_PATH);
	}
	
	public IndicatorPictureRenderingModel(String onPath, String offPath) {
		try
		{
			this.setOnImage(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + onPath)));
			this.setOffImage(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + offPath)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public BufferedImage getOnImage() {
		return onImage;
	}
	public void setOnImage(BufferedImage onImage) {
		this.onImage = onImage;
	}
	public BufferedImage getOffImage() {
		return offImage;
	}
	public void setOffImage(BufferedImage offImage) {
		this.offImage = offImage;
	}
	
}
