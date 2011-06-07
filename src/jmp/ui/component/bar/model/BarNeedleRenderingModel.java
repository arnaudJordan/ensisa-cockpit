package jmp.ui.component.bar.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BarNeedleRenderingModel extends BarRenderingModel {
	private final static String DEFAULT_NEEDLE_PATH = "pictures/bar/default_needle.png";
	
	private BufferedImage needle;
	
	public BarNeedleRenderingModel() {
		this(DEFAULT_NEEDLE_PATH);
	}
	
	public BarNeedleRenderingModel(String needlePath) {
		try
		{
			this.setNeedle(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + needlePath)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public BarNeedleRenderingModel(BufferedImage needle) {
		this.setNeedle(needle);
	}
	
	public void setNeedle(BufferedImage needle) {
		if(this.needle==needle) return;
		this.needle = needle;
		this.modelChange();
	}
	
	public BufferedImage getNeedle() {
		return needle;
	}
}
