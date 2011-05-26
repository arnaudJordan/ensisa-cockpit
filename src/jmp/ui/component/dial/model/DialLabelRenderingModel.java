package jmp.ui.component.dial.model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

public class DialLabelRenderingModel extends DialRenderingModel {

	final static String DEFAULT_LABEL = "DIAL";
	final static Color DEFAULT_COLOR = Color.BLACK;
	final static Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 18);
	final static Point DEFAULT_POSITION = new Point(0, 0);
	
	private String label;
	private Color color;
	private Font font;
	private Point position;

	public DialLabelRenderingModel() {
		this(DEFAULT_LABEL);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
		setPosition(DEFAULT_POSITION);
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

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}	
}
