package jmp.ui.component.bar.model;

import java.awt.Color;
import jmp.ui.utilities.JMSwingUtilities;

public class BarColoredRenderingModel extends BarRenderingModel {
	
	private static final Color DEFAULT_TRAIL_COLOR = Color.LIGHT_GRAY;
	private static final Color DEFAULT_PROGRESS_COLOR = JMSwingUtilities.invertColor(DEFAULT_TRAIL_COLOR);
	
	private Color trailColor, progressColor;

	public BarColoredRenderingModel() {
		this(DEFAULT_TRAIL_COLOR, DEFAULT_PROGRESS_COLOR);
	}
	
	public BarColoredRenderingModel(Color trailColor, Color progressColor) {
		super();
		this.trailColor = trailColor;
		this.progressColor = progressColor;
	}

	/**
	 * @return the trailColor
	 */
	public Color getTrailColor() {
		return trailColor;
	}

	/**
	 * @param trailColor the trailColor to set
	 */
	public void setTrailColor(Color trailColor) {
		this.trailColor = trailColor;
	}

	/**
	 * @return the progressColor
	 */
	public Color getProgressColor() {
		return progressColor;
	}

	/**
	 * @param progressColor the progressColor to set
	 */
	public void setProgressColor(Color progressColor) {
		this.progressColor = progressColor;
	}
}
