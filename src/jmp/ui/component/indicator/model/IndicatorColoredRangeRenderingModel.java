package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Dimension;

import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;

public class IndicatorColoredRangeRenderingModel extends IndicatorRenderingModel {
	private final static ColoredRanges DEFAULT_COLORED_RANGES = new ColoredRanges();
	private final static Dimension DEFAULT_SIZE = new Dimension(100, 100);
	
	private ColoredRanges coloredRanges;
	private Dimension size;
	
	public IndicatorColoredRangeRenderingModel() {
		setColoredRanges(DEFAULT_COLORED_RANGES);
		setSize(DEFAULT_SIZE);
		coloredRanges.addRange(new ColoredRange(0, 30, Color.PINK));
		coloredRanges.addRange(new ColoredRange(30, 60, Color.GREEN));
		coloredRanges.addRange(new ColoredRange(60, 100, Color.RED));
	}

	public void setColoredRanges(ColoredRanges coloredRanges) {
		this.coloredRanges = coloredRanges;
	}

	public ColoredRanges getColoredRanges() {
		return coloredRanges;
	}
	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}
}
