package jmp.ui.component.bar.model;

import java.awt.Color;

import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;

public class BarColoredRangeRenderingModel extends BarRenderingModel {
	private final static ColoredRanges DEFAULT_COLORED_RANGES = new ColoredRanges();
	
	private ColoredRanges coloredRanges;
	
	public BarColoredRangeRenderingModel() {
		setColoredRanges(DEFAULT_COLORED_RANGES);
		coloredRanges.addRange(new ColoredRange(0, 30, Color.GREEN));
		coloredRanges.addRange(new ColoredRange(30, 70, Color.YELLOW));
		coloredRanges.addRange(new ColoredRange(70, 100, Color.RED));
	}

	public void setColoredRanges(ColoredRanges coloredRanges) {
		if(this.coloredRanges == coloredRanges) return;
		this.coloredRanges = coloredRanges;
		this.modelChange();
	}

	public ColoredRanges getColoredRanges() {
		return coloredRanges;
	}
}
