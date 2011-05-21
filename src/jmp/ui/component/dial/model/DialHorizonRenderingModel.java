package jmp.ui.component.dial.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialHorizonRenderingModel extends DialRenderingModel 
{
	private static final Color SKY_COLOR = new Color(0, 204, 255);
	private static final Color GROUND_COLOR = new Color(153, 102, 51);
	private static final Color GRADUATION_COLOR = Color.WHITE;
	private static final Dimension SIZE = new Dimension(150, 150);
	
	private static final int PITCH_INTERVAL = 60;
	private final static String PLANE_IMAGE_PATH = "pictures/dial/horizon_plane.png";
	
	private static final int BACKGROUND_MULTIPLIER = 8;
	
	private Color skyColor;
	private Color groundColor;
	private Color graduationColor;
	private Dimension Size;
	private int pitchInterval; 
	private BufferedImage horizonImage;
	private BufferedImage planeImage;
	private int backgroundMultiplier;

	public DialHorizonRenderingModel()
	{
		this.setSkyColor(SKY_COLOR);
		this.setGroundColor(GROUND_COLOR);
		this.setGraduationColor(GRADUATION_COLOR);
		this.setSize(SIZE);
		this.setPitchInterval(PITCH_INTERVAL);
		this.setBackgroundMultiplier(BACKGROUND_MULTIPLIER);
		this.setHorizonImage(new BufferedImage((int) Size.getWidth() * getBackgroundMultiplier(), (int) Size.getHeight() * getBackgroundMultiplier(), BufferedImage.TYPE_INT_RGB));
		try
		{
			this.setPlaneImage(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + PLANE_IMAGE_PATH)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public void setSkyColor(Color skyColor) 
	{
		this.skyColor = skyColor;
	}

	public Color getSkyColor() 
	{
		return skyColor;
	}

	public void setGroundColor(Color groundColor) 
	{
		this.groundColor = groundColor;
	}

	public Color getGroundColor() 
	{
		return groundColor;
	}

	public void setGraduationColor(Color graduationColor) 
	{
		this.graduationColor = graduationColor;
	}

	public Color getGraduationColor() 
	{
		return graduationColor;
	}

	public void setSize(Dimension size) 
	{
		Size = size;
	}
	public Dimension getSize() 
	{
		return Size;
	}

	public void setPitchInterval(int pitchInterval) {
		this.pitchInterval = pitchInterval;
	}


	public int getPitchInterval() {
		return pitchInterval;
	}


	public void setHorizonImage(BufferedImage horizonImage) {
		this.horizonImage = horizonImage;
	}

	public BufferedImage getHorizonImage() {
		return horizonImage;
	}

	public void setPlaneImage(BufferedImage planeImage) {
		this.planeImage = planeImage;
	}

	public BufferedImage getPlaneImage() {
		return planeImage;
	}

	public void setBackgroundMultiplier(int backgroundMultiplier) {
		this.backgroundMultiplier = backgroundMultiplier;
	}

	public int getBackgroundMultiplier() {
		return backgroundMultiplier;
	}
}
