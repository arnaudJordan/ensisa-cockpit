package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;

public class IndicatorBlinkMultiRenderingModel extends IndicatorRenderingModel {
	private final static Dimension DEFAULT_SIZE = new Dimension(100, 100);
	
	private List<ImageListRanges> imageListRangesList;
	
	public IndicatorBlinkMultiRenderingModel()
	{
		imageListRangesList = new ArrayList<ImageListRanges>();
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
		
		ImageListRanges imageList = new ImageListRanges();
		imageList.addRange(new ImageListRange(0, 25, imageList0));
		imageList.addRange(new ImageListRange(25, 75, imageList1));
		imageList.addRange(new ImageListRange(75, 100, imageList2));
		
		imageList1 = new ImageList();
		imageList1.add(image1);
		imageList1.add(image2);
		
		imageList2 = new ImageList();
		imageList2.add(image2);
		
		imageList0 = new ImageList();
		imageList0.add(image1);
		
		ImageListRanges imageList22 = new ImageListRanges();
		imageList22.addRange(new ImageListRange(0, 25, imageList0));
		imageList22.addRange(new ImageListRange(25, 75, imageList1));
		imageList22.addRange(new ImageListRange(75, 100, imageList2));
		
		imageList1 = new ImageList();
		imageList1.add(image1);
		imageList1.add(image2);
		
		imageList2 = new ImageList();
		imageList2.add(image2);
		
		imageList0 = new ImageList();
		imageList0.add(image1);
		
		ImageListRanges imageList33 = new ImageListRanges();
		imageList33.addRange(new ImageListRange(0, 25, imageList0));
		imageList33.addRange(new ImageListRange(25, 75, imageList1));
		imageList33.addRange(new ImageListRange(75, 100, imageList2));
		
		imageList1 = new ImageList();
		imageList1.add(image1);
		imageList1.add(image2);
		
		imageList2 = new ImageList();
		imageList2.add(image2);
		
		imageList0 = new ImageList();
		imageList0.add(image1);
		
		ImageListRanges imageList44 = new ImageListRanges();
		imageList44.addRange(new ImageListRange(0, 25, imageList0));
		imageList44.addRange(new ImageListRange(25, 75, imageList1));
		imageList44.addRange(new ImageListRange(75, 100, imageList2));
		
		imageListRangesList.add(imageList);
		imageListRangesList.add(imageList22);
		imageListRangesList.add(imageList33);
		imageListRangesList.add(imageList44);
	}
	
	public IndicatorBlinkMultiRenderingModel(List<ImageListRanges> imageList)
	{
		setImageList(imageList);
	}

	public void setImageList(List<ImageListRanges> imageList) {
		if(this.imageListRangesList == imageList) return;
		this.imageListRangesList = imageList;
		this.modelChange();
	}

	public List<ImageListRanges> getImageList() {
		return imageListRangesList;
	}
	public Dimension getSize()
	{
		if(imageListRangesList.isEmpty())
			return  new Dimension(0,0);
		Iterator<ImageListRange> it = imageListRangesList.get(0).getRanges().iterator();
		BufferedImage tmp = it.next().imageList.get(0);
		return new Dimension(tmp.getWidth(), tmp.getHeight());
	}
}
