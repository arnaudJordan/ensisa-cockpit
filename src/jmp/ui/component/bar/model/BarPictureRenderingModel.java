package jmp.ui.component.bar.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarPictureRenderingModel extends BarRenderingModel {
	private final static String DEFAULT_BACKGROUND_PATH = "pictures/bar/default_background.png";
	
	private BufferedImage background;
	
	public BarPictureRenderingModel() {
		this(DEFAULT_BACKGROUND_PATH);
	}
	
	public BarPictureRenderingModel(String backgroundPath) {
		try
		{
			this.setBackground(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + backgroundPath)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public BarPictureRenderingModel(BufferedImage background) {
		this.setBackground(background);
	}
	
	public void setBackground(BufferedImage background) {
		if(this.background==background) return;
		this.background = background;
		this.modelChange();
	}
	
	public BufferedImage getBackground() {
		return background;
	}
}
