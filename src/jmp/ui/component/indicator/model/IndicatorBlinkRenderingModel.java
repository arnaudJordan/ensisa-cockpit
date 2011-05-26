package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import jmp.ui.utilities.ImageList;

public class IndicatorBlinkRenderingModel extends IndicatorRenderingModel {
	private final static ImageList DEFAULT_IMAGELIST = new ImageList();
	private ImageList imageList;
	
	public IndicatorBlinkRenderingModel()
	{
		setImageList(DEFAULT_IMAGELIST);
		BufferedImage image1 = new BufferedImage((int) 500, 500, BufferedImage.TYPE_INT_RGB);
		BufferedImage image2 = new BufferedImage((int) 500, 500, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = image1.createGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, image1.getWidth(), image1.getHeight());
		
		g = image2.createGraphics();
		g.setColor(Color.RED);
		g.fillOval(0, 0, image1.getWidth(), image1.getHeight());
		
		imageList.add(0, image1);
		imageList.add(1, image2);
	}

	public void setImageList(ImageList imageList) {
		this.imageList = imageList;
	}

	public ImageList getImageList() {
		return imageList;
	}
	public Dimension getSize()
	{
		if(imageList.isEmpty())
			return  new Dimension(0,0);
		return new Dimension(imageList.get(0).getWidth(),imageList.get(0).getWidth());
	}
}
