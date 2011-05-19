package jmp.ui.component.dial;

public class DialPartialRenderingModel extends DialRenderingModel{
	private final static int DEFAULT_START_ANGLE = 180;
	private final static int DEFAULT_END_ANGLE = -90;
	
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
		this.startAngle = ((startAngle % 360) + 360) % 360;
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void setEndAngle(Integer endAngle) {
		this.endAngle = ((endAngle % 360) + 360) % 360;
		if(this.startAngle >= this.endAngle) this.endAngle += 360;
	}

	public int getEndAngle() {
		return endAngle;
	}
}