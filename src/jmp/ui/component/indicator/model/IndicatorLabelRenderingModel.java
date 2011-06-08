package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Font;

import jmp.ui.component.CardinalPosition;

public class IndicatorLabelRenderingModel extends IndicatorRenderingModel {
	final static int DEFAULT_LABEL_MARGIN =5;
	final static String DEFAULT_LABEL = "INDICATOR";
	final static Color DEFAULT_COLOR = Color.BLACK;
	final static Font DEFAULT_FONT = new Font("Monospaced", Font.PLAIN, 18);
	final static CardinalPosition DEFAULT_POSITION = CardinalPosition.NORTH;
	
	private String label;
	private int margin;
	private Color color;
	private Font font;
	private CardinalPosition position;

	public IndicatorLabelRenderingModel() {
		super();
		setMargin(DEFAULT_LABEL_MARGIN);
		setLabel(DEFAULT_LABEL);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
		setPosition(DEFAULT_POSITION);
		
	}
	
	public IndicatorLabelRenderingModel(String label) {
		super();
		setLabel(label);
		setMargin(DEFAULT_LABEL_MARGIN);
		setFont(DEFAULT_FONT);
		setColor(DEFAULT_COLOR);
		setPosition(DEFAULT_POSITION);
	}

	public IndicatorLabelRenderingModel(String label, CardinalPosition position) {
		this(label);
		setPosition(position);
		setLabel(label);
		setMargin(DEFAULT_LABEL_MARGIN);
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

	public void setMargin(int margin) {
		if(this.margin == margin) return;
		this.margin = margin;
		this.modelChange();
	}

	public int getMargin() {
		return this.margin;
	}	
}
