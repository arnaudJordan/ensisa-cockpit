package jmp.ui.component.dial.model;

import java.awt.Color;

public class DialBorderRenderingModel extends DialRenderingModel {
	private static final Color BORDER_COLOR = Color.GRAY;
	private static final int BORDER_SIZE = 2;
	
	private Color borderColor;
	private int borderSize;
	
	public DialBorderRenderingModel()
	{
		this(BORDER_SIZE, BORDER_COLOR);
	}

	public DialBorderRenderingModel(int size) {
		this(size, BORDER_COLOR);
	}
	
	public DialBorderRenderingModel(Color color) {
		this(BORDER_SIZE, color);
	}
	
	public DialBorderRenderingModel(int size, Color color) {
		setBorderSize(size);
		setBorderColor(color);
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
