package jmp.ui.component.dial.model;

import java.awt.Point;

public class DialCompositRenderingModel extends DialRenderingModel {

	private final static Point INTERN_DIAL_POSITION = new Point(0, 0);
	
	private Point internDialPosition;

	public DialCompositRenderingModel() {
		this(INTERN_DIAL_POSITION);
	}
	
	public DialCompositRenderingModel(Point internDialPosition) {
		super();
		this.setInternDialPosition(internDialPosition);
	}

	public void setInternDialPosition(Point internDialPosition) {
		this.internDialPosition = internDialPosition;
	}

	public Point getInternDialPosition() {
		return internDialPosition;
	}
}
