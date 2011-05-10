package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;

import jmp.ui.mvc.Renderer;

public interface DialRenderer extends Renderer
{
	public void renderTick(Graphics2D g);
	public void renderTicks(Graphics2D g);
	public void renderLabel(Graphics2D g);
	public void renderLabels(Graphics2D g);
	public void renderTrack(Graphics2D g);
	public void renderNeedle(Graphics2D g);
	public void renderBackground(Graphics2D g);
	public void renderBorder(Graphics2D g);
	public void renderDial(Graphics2D g);
	
	public void setSize(Dimension size);
	public Dimension getPreferredSize();
	public Dimension getMinimumSize();
	public Dimension getMaximumSize();


}
