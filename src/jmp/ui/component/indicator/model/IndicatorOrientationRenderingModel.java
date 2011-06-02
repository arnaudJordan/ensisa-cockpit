package jmp.ui.component.indicator.model;

import jmp.ui.component.Orientation;

public class IndicatorOrientationRenderingModel extends IndicatorRenderingModel {
	private final static Orientation DEFAULT_ORIENTATION=Orientation.Horizontal;
	private Orientation orientation;
	public IndicatorOrientationRenderingModel() {
		this(DEFAULT_ORIENTATION);
	}
	public IndicatorOrientationRenderingModel(Orientation orientation) {
		this.setOrientation(orientation);
	}
	public void setOrientation(Orientation orientation) {
		if(this.orientation == orientation) return;
		this.orientation = orientation;
		this.modelChange();
	}
	public Orientation getOrientation() {
		return orientation;
	}

}
