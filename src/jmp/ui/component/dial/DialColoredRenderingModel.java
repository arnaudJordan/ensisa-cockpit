package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


public class DialColoredRenderingModel extends DialPartialRenderingModel{
	private ColoredRanges colorRanges;
	
	public DialColoredRenderingModel() {
		super();
		setBackground(new BufferedImage(getNeedle().getWidth(), getNeedle().getWidth(), BufferedImage.TYPE_INT_ARGB));
		setColorRanges(new ColoredRanges());
	}

	public void setColorRanges(ColoredRanges colorRanges) {
		this.colorRanges = colorRanges;
		Graphics2D g = getBackground().createGraphics();
		g.setStroke(new BasicStroke(60,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		
		int min=0;
		int max=0;
		for(ColoredRange range : getColorRanges().getRanges())
		{
			g.setColor(range.color);
			g.drawArc(0, 0, getBackground().getWidth(), getBackground().getHeight(), range.range.min, range.range.max - range.range.min);
			if(range.range.min < min)
				min=range.range.min;
			if(range.range.max > max)
				max=range.range.max;
		}
		setStartAngle(min);
		setEndAngle(max);
		g.dispose();
	}

	public ColoredRanges getColorRanges() {
		return colorRanges;
	}
}