package jmp.ui.component.dial;

import jmp.ui.component.Rotation;
import jmp.ui.mvc.DefaultModel;

public class DialRenderingModel extends DefaultModel{
	
	private final static Rotation SENSE = Rotation.Clockwise;
	private final static int TICKS_START_ANGLE = 90;
	
	private Rotation sense;
	private int ticksStartAngle;
	
	DialRenderingModel()
	{
		this(SENSE, TICKS_START_ANGLE);
	}
	
	DialRenderingModel(Rotation sense) {
		this(sense, TICKS_START_ANGLE);
	}
	
	DialRenderingModel(Rotation sense, int tickStartAngle)
	{
		setSense(SENSE);
		setTicksStartAngle(TICKS_START_ANGLE);
	}
	
	public DialRenderingModel(int tickStartAngle) 
	{
		this(SENSE, tickStartAngle);	
	}

	public void setSense(Rotation sense) {
		this.sense = sense;
	}

	public Rotation getSense() {
		return sense;
	}

	public void setTicksStartAngle(int ticksStartAngle) {
		this.ticksStartAngle = ticksStartAngle;
	}

	public int getTicksStartAngle() {
		return ticksStartAngle;
	}
}
