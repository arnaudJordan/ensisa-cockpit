package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


public class DialColoredRenderingModel extends DialRenderingModel{
	private ColoredRanges colorRanges;
	
	public DialColoredRenderingModel() {
		super();
	}

	public void setColorRanges(ColoredRanges colorRanges) {
		this.colorRanges = colorRanges;
	}

	public ColoredRanges getColorRanges() {
		return colorRanges;
	}
}