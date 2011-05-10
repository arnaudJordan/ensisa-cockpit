package jmp.ui.component.dial;

import java.awt.Color;

import jmp.ui.mvc.DefaultModel;

public class DialRenderingModel extends DefaultModel{
	private int startAngle = 0;
	private int endAngle = 90;
	private Color backgroundColor;
	
	DialRenderingModel()
	{
		this.setBackgroundColor(Color.GRAY);
	}
	public void setStartAngle(int startAngle) {
		this.startAngle = startAngle;
	}
	public int getStartAngle() {
		return startAngle;
	}
	public void setEndAngle(int endAngle) {
		this.endAngle = endAngle;
	}
	public int getEndAngle() {
		return endAngle;
	}
	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	public Color getBackgroundColor() {
		return backgroundColor;
	}

}
