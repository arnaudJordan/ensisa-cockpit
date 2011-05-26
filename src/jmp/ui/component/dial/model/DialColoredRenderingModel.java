package jmp.ui.component.dial.model;

import java.awt.BasicStroke;
import java.awt.Stroke;

import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


public class DialColoredRenderingModel extends DialRenderingModel{
	private static final int MARGIN = 30;
	private ColoredRanges colorRanges;
	private Stroke stroke;
	private int margin;
	
	public DialColoredRenderingModel() {
		super();
		setMargin(MARGIN);
	}

	public void setColorRanges(ColoredRanges colorRanges) {
		this.colorRanges = colorRanges;
	}

	public ColoredRanges getColorRanges() {
		return colorRanges;
	}
	
	public ColoredRange getRange(int v)
	{
		return colorRanges.getRange(v);
	}

	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setMargin(int margin) {
		this.margin = margin;
		setStroke(new BasicStroke(margin,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
	}

	public int getMargin() {
		return margin;
	}
}