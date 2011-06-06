package jmp.ui.component.dial.model;

public class DialPartialRenderingModel extends DialRenderingModel{
	private final static int DEFAULT_START_ANGLE = -90;
	private final static int DEFAULT_END_ANGLE = 0;
	
	private int startAngle, endAngle;
	
	public DialPartialRenderingModel() {
		this(DEFAULT_START_ANGLE, DEFAULT_END_ANGLE);
	}
	
	public DialPartialRenderingModel(int startAngle, int endAngle) {
		super(endAngle);
		setStartAngle(startAngle);
		setEndAngle(endAngle);
	}
	
	public void setStartAngle(Integer startAngle) {
		if(this.startAngle== startAngle) return;
		this.startAngle = ((startAngle % 360) + 360) % 360;
		this.modelChange();
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void setEndAngle(Integer endAngle) {
		if(this.endAngle == endAngle) return;
		this.endAngle = ((endAngle % 360) + 360) % 360;
		if(this.startAngle >= this.endAngle) this.endAngle += 360;
		this.modelChange();
	}

	public int getEndAngle() {
		return endAngle;
	}
}