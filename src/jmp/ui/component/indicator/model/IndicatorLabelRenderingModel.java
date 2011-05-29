package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Font;

import jmp.ui.component.CardinalPosition;

public class IndicatorLabelRenderingModel extends IndicatorRenderingModel {

	final static String DEFAULT_LABEL = "INDICATOR";
	final static Color DEFAULT_COLOR = Color.BLACK;
	final static Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 18);
	final static CardinalPosition DEFAULT_POSITION = CardinalPosition.NORTH;
	
	private String label;
	private Color color;
	private Font font;
	private CardinalPosition position;

	public IndicatorLabelRenderingModel() {
		super();
		setLabel(DEFAULT_LABEL);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
		setPosition(DEFAULT_POSITION);
	}
	
	public IndicatorLabelRenderingModel(String label) {
		super();
		setLabel(label);
	}

	public IndicatorLabelRenderingModel(String label, CardinalPosition position) {
		this(label);
		setPosition(position);
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

	public void setPosition(CardinalPosition position) {
		this.position = position;
	}

	public CardinalPosition getPosition() {
		return position;
	}	
}
