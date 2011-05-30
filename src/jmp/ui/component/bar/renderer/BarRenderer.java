package jmp.ui.component.bar.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import jmp.ui.mvc.Renderer;

public interface BarRenderer extends Renderer{
	public void renderBackground(Graphics2D g);
	public void renderProgress(Graphics2D g);
	public void renderLabel(Graphics2D g);
	public void renderLabels(Graphics2D g);
	public void renderTicks(Graphics2D g);
	public void renderBorder(Graphics2D g);
	public void renderBar(Graphics2D g);
	
	public void setSize(Dimension size);
	public Dimension getPreferredSize();
	public Dimension getMinimumSize();
	public Dimension getMaximumSize();
}
