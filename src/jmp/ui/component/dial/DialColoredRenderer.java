package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;

import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.JMSwingUtilities;


public class DialColoredRenderer extends DialDefaultRenderer {
	
	public DialColoredRenderer(View view) {
		super(view);
	}
	public void renderNeedle(Graphics2D g)
	{
		AffineTransform trans = new AffineTransform();
		
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if (needle == null || background == null) return;
		
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		trans.rotate(Math.toRadians(this.dialView().valueModel().getValue()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);

		g.drawImage(needle,trans,null);
	}
	public void renderBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialColoredRenderingModel coloredModel = (DialColoredRenderingModel) dialView().renderingModel();
		g.setStroke(coloredModel.getStroke());
		int min=0;
		int max=0;
		for(ColoredRange range : coloredModel.getColorRanges().getRanges())
		{
			g.setColor(range.color);
			g.drawArc(coloredModel.getMargin()/2, coloredModel.getMargin()/2, pictureModel.getBackground().getWidth()-coloredModel.getMargin(), pictureModel.getBackground().getHeight()-coloredModel.getMargin(), range.range.min, range.range.max - range.range.min);
			if(range.range.min < min)
				min=range.range.min;
			if(range.range.max > max)
				max=range.range.max;
		}
	}
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialColoredRenderingModel coloredModel = (DialColoredRenderingModel) dialView().renderingModel();
        return new Dimension(pictureModel.getBackground().getWidth(), pictureModel.getBackground().getHeight());
	}
}
