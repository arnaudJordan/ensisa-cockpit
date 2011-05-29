package jmp.ui.component.demo.progress;

import java.awt.Color;

import jmp.ui.mvc.DefaultModel;
import jmp.ui.utilities.JMSwingUtilities;

public class SimpleProgressRenderingModel extends DefaultModel
{
	private static final Color DEFAULT_STROKE_COLOR = Color.BLACK;
	private static final Color DEFAULT_TRAIL_COLOR = Color.LIGHT_GRAY;
	private static final Color DEFAULT_PROGRESS_COLOR = JMSwingUtilities.invertColor(DEFAULT_TRAIL_COLOR);

	private boolean stroke;
	private Color strokeColor, progressColor;
	private Color trailColor;
	
	
	public SimpleProgressRenderingModel()
	{
		this.strokeColor = DEFAULT_STROKE_COLOR;
		this.trailColor = DEFAULT_TRAIL_COLOR;
		this.progressColor = DEFAULT_PROGRESS_COLOR;

		this.stroke = true;
	}
	
	public void strokeOn()
	{
		if (this.stroke) return;
		this.stroke = true;
		this.modelChange();
	}

	public void strokeOff()
	{
		if (!this.stroke) return;
		this.stroke = false;
		this.modelChange();
	}
	
	public boolean stroke()
	{
		return this.stroke;
	}
	
	public void setStrokeColor(Color c)
	{
		if (this.strokeColor == c) return;
		this.strokeColor = c;
		this.modelChange();
	}
	
	public void setProgressColor(Color c)
	{
		if (this.progressColor == c) return;
		this.progressColor = c;
		this.modelChange();
	}
	
	public void setTrailColor(Color c)
	{
		if (this.trailColor == c) return;
		this.trailColor = c;
		this.modelChange();
	}

	public Color progressColor()
	{
		return this.progressColor;
	}

	public Color strokeColor()
	{
		return this.strokeColor;
	}

	public Color trailColor()
	{
		return this.trailColor;
	}
}
