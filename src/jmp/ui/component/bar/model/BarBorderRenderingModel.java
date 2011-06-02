package jmp.ui.component.bar.model;

import java.awt.Color;

public class BarBorderRenderingModel extends BarRenderingModel {
	private static final Color BORDER_COLOR = Color.BLACK;
	private static final int BORDER_SIZE = 2;
	
	private Color borderColor;
	private int borderSize;
	
	public BarBorderRenderingModel()
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
