package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;

public class IndicatorBlinkRenderingModel extends IndicatorRenderingModel {
	private final static ImageListRanges DEFAULT_IMAGELIST = new ImageListRanges();
	private final static Dimension DEFAULT_SIZE = new Dimension(100, 100);
	
	private ImageListRanges imageListRanges;
	
	public IndicatorBlinkRenderingModel()
	{
		setImageList(DEFAULT_IMAGELIST);
			
		BufferedImage image1 = new BufferedImage(DEFAULT_SIZE.width, DEFAULT_SIZE.height, BufferedImage.TYPE_INT_ARGB);
		BufferedImage image2 = new BufferedImage(DEFAULT_SIZE.width, DEFAULT_SIZE.height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g = image1.createGraphics();
		g.setColor(Color.GREEN);
		g.fillOval(0, 0, image1.getWidth(), image1.getHeight());
		
		g = image2.createGraphics();
		g.setColor(Color.RED);
		g.fillOval(0, 0, image1.getWidth(), image1.getHeight());
		
		ImageList imageList1 = new ImageList();
		imageList1.add(image1);
		imageList1.add(image2);
		
		ImageList imageList2 = new ImageList();
		imageList2.add(image2);
		
		ImageList imageList0 = new ImageList();
		imageList0.add(image1);
		
		imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
		imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
		imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
	}
	
	public IndicatorBlinkRenderingModel(ImageListRanges imageList)
	{
		setImageList(imageList);
	}

	public void setImageList(ImageListRanges imageList) {
		this.imageListRanges = imageList;
	}

	public ImageListRanges getImageList() {
		return imageListRanges;
	}
	public Dimension getSize()
	{
		if(imageListRanges.getRanges().isEmpty())
			return  new Dimension(0,0);
		Iterator<ImageListRange> it = imageListRanges.getRanges().iterator();
		BufferedImage tmp = it.next().imageList.get(0);
		return new Dimension(tmp.getWidth(), tmp.getHeight());
	}
}
