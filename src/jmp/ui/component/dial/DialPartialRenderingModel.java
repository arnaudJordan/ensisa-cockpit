package jmp.ui.component.dial;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialPartialRenderingModel extends DialPictureRenderingModel{
	private final static String DEFAULT_BACKGROUND_PATH = "pictures/dial/partial_background.png";
	private final static String DEFAULT_NEEDLE_PATH = "pictures/dial/partial_needle.png";
	private final static int DEFAULT_START_ANGLE = 0;
	private final static int DEFAULT_END_ANGLE = 180;
	private int startAngle, endAngle;
	
	public DialPartialRenderingModel() {
		super();
		setStartAngle(DEFAULT_START_ANGLE);
		setEndAngle(DEFAULT_END_ANGLE);
		try
		{
			setBackground(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + DEFAULT_BACKGROUND_PATH)));
			setNeedle(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + DEFAULT_NEEDLE_PATH)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void setStartAngle(Integer startAngle) {
		this.startAngle = startAngle;
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void setEndAngle(Integer endAngle) {
		this.endAngle = endAngle;
	}

	public int getEndAngle() {
		return endAngle;
	}
}