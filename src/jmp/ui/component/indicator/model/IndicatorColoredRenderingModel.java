package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Dimension;

public class IndicatorColoredRenderingModel extends IndicatorRenderingModel {
	private final static Color DEFAULT_ON_COLOR = Color.GREEN;
	private final static Color DEFAULT_OFF_COLOR = Color.RED;
	private final static Dimension DEFAULT_SIZE = new Dimension(200, 200);
	
	private Color onColor;
	private Color offColor;
	private Dimension size;
	
	public IndicatorColoredRenderingModel() {
		this(DEFAULT_ON_COLOR, DEFAULT_OFF_COLOR);
	}
	
	public IndicatorColoredRenderingModel(Color onColor, Color offColor) {
		setOnColor(onColor);
		setOffColor(offColor);
		setSize(DEFAULT_SIZE);
	}

	public void setOffColor(Color offColor) {
		this.offColor = offColor;
	}

	public Color getOffColor() {
		return offColor;
	}

	public void setOnColor(Color onColor) {
		this.onColor = onColor;
	}

	public Color getOnColor() {
		return onColor;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}
}