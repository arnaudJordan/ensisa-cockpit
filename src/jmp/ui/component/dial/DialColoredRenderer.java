package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.ColoredRange;


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

		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if (needle == null || background == null) return;
		int Angle=0;
		if(valueModel.getValue() != 0)
			Angle = valueModel.getValue()*360/(valueModel.getMaximum()-valueModel.getMinimum());
		
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		//trans.rotate(Math.toRadians(this.dialView().valueModel().getValue()));
		trans.rotate(Math.toRadians(Angle));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);

		g.drawImage(needle,trans,null);
	}
	public void renderBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialColoredRenderingModel coloredModel = (DialColoredRenderingModel) dialView().renderingModel();
		g.setStroke(coloredModel.getStroke());
		
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		double AngleRatio=360/(valueModel.getMaximum()-valueModel.getMinimum());
		
		int min=0;
		int max=0;
		for(ColoredRange range : coloredModel.getColorRanges().getRanges())
		{
			g.setColor(range.color);
			g.drawArc(coloredModel.getMargin()/2, coloredModel.getMargin()/2, pictureModel.getBackground().getWidth()-coloredModel.getMargin(), pictureModel.getBackground().getHeight()-coloredModel.getMargin(), (int) (range.range.min * AngleRatio),(int) ((range.range.max - range.range.min)*AngleRatio));
			System.out.println((range.range.max - range.range.min)*AngleRatio);
			if(range.range.min < min)
				min=range.range.min;
			if(range.range.max > max)
				max=range.range.max;
		}
	} 
	public void renderBorder(Graphics2D g)
	{
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		BufferedImage background = pictureModel.getBackground();
		if(borderModel==null || borderModel.getBorderSize()==0) return;
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
		g.draw(border);
	}
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialColoredRenderingModel coloredModel = (DialColoredRenderingModel) dialView().renderingModel();
        return new Dimension(pictureModel.getBackground().getWidth(), pictureModel.getBackground().getHeight());
	}
}
