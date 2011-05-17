package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;

import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.JMSwingUtilities;


public class DialColoredRenderer extends DialDefaultRenderer {
	public DialColoredRenderer(View view) {
		super(view);
	}
	
	public void renderBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		g.setStroke(new BasicStroke(60,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		DialColoredRenderingModel coloredModel = ((DialColoredRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("colored"));
		int min=0;
		int max=0;
		for(ColoredRange range : coloredModel.getColorRanges().getRanges())
		{
			g.setColor(range.color);
			g.drawArc(0, 0, pictureModel.getBackground().getWidth(), pictureModel.getBackground().getHeight(), range.range.min, range.range.max - range.range.min);
			if(range.range.min < min)
				min=range.range.min;
			if(range.range.max > max)
				max=range.range.max;
		}
		g.dispose();
	}
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		Double clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
		int transX = 0;
		int transY = 0;
		
		System.out.println("----------------");
        System.out.println("bg Height : " +background.getHeight());
        System.out.println("bg Width : " +background.getWidth());
        System.out.println("maxX : " + clip.getMaxX());
        System.out.println("maxY : " + clip.getMaxY());
        System.out.println("minX : " + clip.getMinX());
        System.out.println("minY : " + clip.getMinY());
        System.out.println("centerX : " + clip.getCenterX());
        System.out.println("centerY : " + clip.getCenterY());
        System.out.println("----Avec Boubds-----");
        System.out.println("maxX : " + clip.getBounds2D().getMaxX());
        System.out.println("maxY : " + clip.getBounds2D().getMaxY());
        System.out.println("minX : " + clip.getBounds2D().getMinX());
        System.out.println("minY : " + clip.getBounds2D().getMinY());
        System.out.println("-------------");

        if(clip.getBounds2D().getMaxX() < clip.getCenterX() + needle.getHeight())
        {
        	System.out.println(1);
        	transX = needle.getHeight();
        }
        if(clip.getBounds2D().getMaxY() < clip.getCenterY() + needle.getHeight())
        {
        	System.out.println(2);
        	transY = needle.getHeight();
        }
        if(clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight())
        {
        	System.out.println(3);
        	transX = needle.getHeight();
        }
        if(clip.getBounds2D().getMinY() > clip.getCenterY() -  needle.getHeight())
        {
        	System.out.println(4);
        	transY = needle.getHeight();
        }
        return new Dimension((int)clip.getBounds2D().getWidth() + transX, (int)clip.getBounds2D().getHeight() + transY);
	}
}
