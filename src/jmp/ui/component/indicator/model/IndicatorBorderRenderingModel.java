package jmp.ui.component.indicator.model;

import java.awt.Color;

public class IndicatorBorderRenderingModel extends IndicatorRenderingModel {
	private static final Color BORDER_COLOR = Color.GRAY;
	private static final int BORDER_SIZE = 2;
	
	private Color borderColor;
	private int BorderSize;
	
	public IndicatorBorderRenderingModel()
	{
		setBorderColor(BORDER_COLOR);
		setBorderSize(BORDER_SIZE);
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderSize(int borderSize) {
		BorderSize = borderSize;
	}

	public int getBorderSize() {
		return BorderSize;
	}

}
