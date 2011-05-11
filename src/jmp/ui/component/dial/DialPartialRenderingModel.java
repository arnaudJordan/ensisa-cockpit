package jmp.ui.component.dial;

public class DialPartialRenderingModel extends DialPictureRenderingModel{
	private final static int DEFAULT_START_ANGLE = 90;
	private final static int DEFAULT_END_ANGLE = 0;
	private int startAngle, endAngle;
	
	public DialPartialRenderingModel() {
		super();
		this.startAngle = DEFAULT_START_ANGLE;
		this.endAngle = DEFAULT_END_ANGLE;
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
