package jmp.ui.mvc;

import java.awt.Dimension;
import java.awt.Graphics2D;

public interface Renderer
{
	public void setView(View view);
	public View getView();
	public void renderView(Graphics2D g);
	public Dimension getMinimumSize();
	public Dimension getPreferredSize();
	public Dimension getMaximumSize();
}
