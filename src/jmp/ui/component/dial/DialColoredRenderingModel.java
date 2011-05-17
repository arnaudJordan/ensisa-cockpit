package jmp.ui.component.dial;

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