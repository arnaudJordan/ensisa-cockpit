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
		if(this.colorRanges == colorRanges) return;
		this.colorRanges = colorRanges;
		this.modelChange();
	}

	public ColoredRanges getColorRanges() {
		return colorRanges;
	}
	
	public ColoredRange getRange(int v)
	{
		return colorRanges.getRange(v);
	}

	public void setStroke(Stroke stroke) {
		if(this.stroke == stroke) return;
		this.stroke = stroke;
		this.modelChange();
	}

	public Stroke getStroke() {
		return stroke;
	}

	public void setMargin(int margin) {
		if(this.margin == margin) return;
		this.margin = margin;
		setStroke(new BasicStroke(margin,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		this.modelChange();
	}

	public int getMargin() {
		return margin;
	}
}