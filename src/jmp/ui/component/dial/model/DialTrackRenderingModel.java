package jmp.ui.component.dial.model;

import java.awt.Color;

public class DialTrackRenderingModel extends DialRenderingModel {
	private static final Color TRACK_COLOR = Color.BLACK;
	private static final int TRACK_SIZE = 2;
	
	private Color trackColor;
	private int trackSize;
	
	public DialTrackRenderingModel() {
		this(TRACK_COLOR, TRACK_SIZE);
	}
	public DialTrackRenderingModel(Color trackColor, int trackSize) {
		setTrackColor(trackColor);
		setTrackSize(trackSize);
	}
	public Color getTrackColor() {
		return trackColor;
	}
	public void setTrackColor(Color trackColor) {
		if(this.trackColor == trackColor) return;
		this.trackColor = trackColor;
		setChanged(true);
		this.modelChange();
	}
	public int getTrackSize() {
		return trackSize;
	}
	public void setTrackSize(int trackSize) {
		if(this.trackSize == trackSize) return;
		this.trackSize = trackSize;
		setChanged(true);
		this.modelChange();
	}
}
