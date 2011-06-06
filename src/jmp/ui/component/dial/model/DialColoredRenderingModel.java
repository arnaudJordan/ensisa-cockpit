package jmp.ui.component.dial.model;

import java.awt.BasicStroke;
import java.awt.Stroke;

import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


public class DialColoredRenderingModel extends DialRenderingModel{
	private static final int MARGIN = 0;
	private static final int THICKNESS = 30;
	
	private ColoredRanges colorRanges;
	private Stroke stroke;
	private int margin;
	private int thickness;
	
	public DialColoredRenderingModel() {
		super();
		setMargin(MARGIN);
		setThickness(THICKNESS);
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
		this.modelChange();
	}

	public int getMargin() {
		return margin;
	}

	public void setThickness(int thickness) {
		if(this.thickness == thickness) return;
		this.thickness = thickness;
		setStroke(new BasicStroke(thickness,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		this.modelChange();
	}

	public int getThickness() {
		return thickness;
	}
}