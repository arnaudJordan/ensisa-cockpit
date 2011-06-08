package jmp.ui.mvc;

import java.awt.Dimension;
import java.awt.Graphics2D;

public  class DefaultRenderer implements Renderer
{
	private final static Dimension DEFAULT_DIMENSION = new Dimension(200,200);
	private View view;
	
	public DefaultRenderer(View view)
	{
		this.view = view;
	}
	
	public View getView()
	{
		return this.view;
	}
	
	public void setView(View view)
	{
		this.view = view;
	}

	public void renderView(Graphics2D g)
	{		
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}

	public Dimension getPreferredSize()
	{
		return DEFAULT_DIMENSION;
	}

	public Dimension getMaximumSize()
	{
		return this.getPreferredSize();
	}
}
