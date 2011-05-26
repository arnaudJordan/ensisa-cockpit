package jmp.ui.component.indicator.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import jmp.ui.mvc.Renderer;

public interface IndicatorRenderer extends Renderer{
	public void renderState(Graphics2D g);
	public void renderLabel(Graphics2D g);
	public void renderBorder(Graphics2D g);
	public void renderIndicator(Graphics2D g);
	
	public void setSize(Dimension size);
	public Dimension getPreferredSize();
	public Dimension getMinimumSize();
	public Dimension getMaximumSize();
}
