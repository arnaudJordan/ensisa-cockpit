package jmp.ui.component.dial.model;

import java.awt.Dimension;

import jmp.ui.component.Rotation;
import jmp.ui.mvc.DefaultModel;

public class DialRenderingModel extends DefaultModel{
	
	private final static Rotation SENSE = Rotation.Clockwise;
	private final static int TICKS_START_ANGLE = 90;
	private final static Dimension SIZE = new Dimension(200,200);
	
	private Rotation sense;
	private int ticksStartAngle;
	private boolean changed;
	private Dimension size;
	
	public DialRenderingModel()
	{
		this(SENSE, TICKS_START_ANGLE);
	}
	
	DialRenderingModel(Rotation sense) {
		this(sense, TICKS_START_ANGLE);
	}
	
	DialRenderingModel(Rotation sense, int tickStartAngle)
	{
		setSense(sense);
		setTicksStartAngle(tickStartAngle);
		setChanged(true);
		setSize(SIZE);
	}
	
	public DialRenderingModel(int tickStartAngle) 
	{
		this(SENSE, tickStartAngle);
	}

	public void setSense(Rotation sense) {
		this.sense = sense;
		setChanged(true);
	}

	public Rotation getSense() {
		return sense;
	}

	public void setTicksStartAngle(int ticksStartAngle) {
		this.ticksStartAngle = ticksStartAngle;
		setChanged(true);
	}

	public int getTicksStartAngle() {
		return ticksStartAngle;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}
}
