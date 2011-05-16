package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


public class DialColoredRenderer extends DialDefaultRenderer {
	public DialColoredRenderer(View view) {
		super(view);
		//8==/setupRenderingModel();
	}

	private void setupRenderingModel() {
		if(((ModelComposit) (dialView().getModel())).getModel("colored") == null || ((ModelComposit) (dialView().getModel())).getModel("picture") == null) return;
		DialColoredRenderingModel coloredModel = ((DialColoredRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("colored"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		if(coloredModel == null || pictureModel == null) return;
		pictureModel.setBackground(new BufferedImage(pictureModel.getNeedle().getWidth(), pictureModel.getNeedle().getWidth(), BufferedImage.TYPE_INT_ARGB));
		coloredModel.setColorRanges(new ColoredRanges());
		
		
		Graphics2D g = pictureModel.getBackground().createGraphics();
		g.setStroke(new BasicStroke(60,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		
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
	
	public void renderBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		g.drawImage(pictureModel.getBackground(),0,0,null);
	}
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		return new Dimension( pictureModel.getBackground().getWidth(),pictureModel.getBackground().getHeight());
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		return this.getPreferredSize();
	}
}
