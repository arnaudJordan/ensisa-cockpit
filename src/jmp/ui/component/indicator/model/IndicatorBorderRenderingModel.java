package jmp.ui.component.indicator.model;

import java.awt.Color;

public class IndicatorBorderRenderingModel extends IndicatorRenderingModel {
	private static final Color BORDER_COLOR = Color.GRAY;
	private static final int BORDER_SIZE = 2;
	
	private Color borderColor;
	private int borderSize;
	
	public IndicatorBorderRenderingModel()
	{
		setBorderColor(BORDER_COLOR);
		setBorderSize(BORDER_SIZE);
	}

	public void setBorderColor(Color borderColor) {
		if(this.borderColor == borderColor) return;
		this.borderColor = borderColor;
		this.modelChange();
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderSize(int borderSize) {
		if(this.borderSize == borderSize) return;
		this.borderSize = borderSize;
		this.modelChange();
	}

	public int getBorderSize() {
		return borderSize;
	}

}
