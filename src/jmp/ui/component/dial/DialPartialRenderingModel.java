package jmp.ui.component.dial;

public class DialPartialRenderingModel extends DialRenderingModel{
	private final static int DEFAULT_START_ANGLE = 90;
	private final static int DEFAULT_END_ANGLE = 180;
	
	private int startAngle, endAngle;
	
	public DialPartialRenderingModel() {
		super();
		setStartAngle(DEFAULT_START_ANGLE);
	}
	
	public void setStartAngle(Integer startAngle) {
		this.startAngle = startAngle;
	}

	public int getStartAngle() {
		return startAngle;
	}

	public void setEndAngle(Integer endAngle) {
		this.endAngle = endAngle;
	}

	public int getEndAngle() {
		return endAngle;
	}
}