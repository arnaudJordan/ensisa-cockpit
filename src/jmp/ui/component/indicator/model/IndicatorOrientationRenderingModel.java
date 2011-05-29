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
		this.orientation = orientation;
	}
	public Orientation getOrientation() {
		return orientation;
	}

}
