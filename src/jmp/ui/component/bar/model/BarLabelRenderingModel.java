package jmp.ui.component.bar.model;

import java.awt.Color;
import java.awt.Font;

import jmp.ui.component.CardinalPosition;

public class BarLabelRenderingModel extends BarRenderingModel {

	final static String DEFAULT_LABEL = "BAR";
	final static Color DEFAULT_COLOR = Color.BLACK;
	final static Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 18);
	final static CardinalPosition DEFAULT_POSITION = CardinalPosition.NORTH;
	
	private String label;
	private Color color;
	private Font font;
	private CardinalPosition position;

	public BarLabelRenderingModel() {
		super();
		setLabel(DEFAULT_LABEL);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
		setPosition(DEFAULT_POSITION);
	}
	
	public BarLabelRenderingModel(String label) {
		super();
		setLabel(label);
	}

	public BarLabelRenderingModel(String label, CardinalPosition position) {
		this(label);
		setPosition(position);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		if(this.label == label) return;
		this.label = label;
		this.modelChange();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		if(this.color == color) return;
		this.color = color;
		this.modelChange();
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		if(this.font == font) return;
		this.font = font;
		this.modelChange();
	}

	public void setPosition(CardinalPosition position) {
		if(this.position == position) return;
		this.position = position;
		this.modelChange();
	}

	public CardinalPosition getPosition() {
		return position;
	}	
}
