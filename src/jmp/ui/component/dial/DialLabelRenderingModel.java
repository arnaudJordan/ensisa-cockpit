package jmp.ui.component.dial;

import java.awt.Color;
import java.awt.Font;

public class DialLabelRenderingModel extends DialRenderingModel {

	final static String DEFAULT_LABEL = "DIAL";
	final static Color DEFAULT_COLOR = Color.PINK;
	final static Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 18);
	
	private String label;
	private Color color;
	private Font font;

	public DialLabelRenderingModel() {
		super();
		setLabel(DEFAULT_LABEL);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
	}
	
	public DialLabelRenderingModel(String label) {
		super();
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}	
}
