package jmp.ui.component.bar.model;

import java.awt.Dimension;

import jmp.ui.component.Orientation;
import jmp.ui.mvc.DefaultModel;

public class BarRenderingModel extends DefaultModel {

	private final static Orientation DEFAULT_ORIENTATION = Orientation.Horizontal;
	private final static Dimension DEFAULT_SIZE = new Dimension(200, 50);
	
	private Orientation orientation;
	private Dimension size;
	
	public BarRenderingModel()
	{
		this(DEFAULT_ORIENTATION);
	}
	
	public BarRenderingModel(Orientation orientation)
	{
		setOrientation(orientation);
		setSize(DEFAULT_SIZE);
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}
}
