package jmp.ui.component.dial;

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
		setChanged(true);
		this.trackColor = trackColor;
	}
	public int getTrackSize() {
		return trackSize;
	}
	public void setTrackSize(int trackSize) {
		setChanged(true);
		this.trackSize = trackSize;
	}
}
