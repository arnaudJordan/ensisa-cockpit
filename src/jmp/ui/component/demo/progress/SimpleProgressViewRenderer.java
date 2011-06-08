package jmp.ui.component.demo.progress;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.border.EmptyBorder;

import jmp.ui.component.Orientation;
import jmp.ui.model.BoundedModel;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class SimpleProgressViewRenderer extends DefaultRenderer
{
	private final static int DEFAULT_LENGTH = 250;
	private final static int DEFAULT_WIDTH = 21;
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);
	private Rectangle progressRect;
	
	public SimpleProgressViewRenderer(View view)
	{
		super(view);
		this.progressRect = new Rectangle();
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
	}

	private SimpleProgressView progressView()
	{
		return (SimpleProgressView) this.getView();
	}

	private boolean isHorizontal()
	{
		return this.progressView().orientation() == Orientation.Horizontal;
	}

	protected Rectangle updateProgressRect()
	{
		Insets insetCache = this.getView().getInsets();
		Dimension size = this.getView().getSize();
		
		this.progressRect.x = insetCache.left;
		this.progressRect.y =  insetCache.top;
		this.progressRect.width =  size.width -insetCache.left-insetCache.right;
		this.progressRect.height =  size.height -insetCache.top-insetCache.bottom;
		
		return this.progressRect;
	}
	
	public void renderView(Graphics2D g)
	{
		SimpleProgressRenderingModel rm = this.progressView().renderingModel();
		BoundedModel vm = this.progressView().valueModel();
		
		if (rm == null || vm == null) return;
		this.renderProgressRect(g, rm, vm,  this.updateProgressRect());
	}
	
	protected void renderProgressRect(Graphics2D g, SimpleProgressRenderingModel rm, BoundedModel vm, Rectangle pRect)
	{
		g.setColor(rm.trailColor());
		g.fill(pRect);
		
		g.setColor(rm.progressColor());

		if (this.isHorizontal()) 
		{
			final int fillWidth = vm.getValue()*pRect.width/(vm.getMaximum()-vm.getMinimum());
			g.fillRect(pRect.x, pRect.y, fillWidth, pRect.height);
		}
		else
		{
			final int fillHeight = vm.getValue()*pRect.height/(vm.getMaximum()-vm.getMinimum());
			g.fillRect(pRect.x, pRect.y+pRect.height-fillHeight, pRect.width, fillHeight);
		}
		
		if (rm.stroke())
		{
			g.setColor(rm.strokeColor());
			g.draw(pRect);
		}
	}

	public Dimension getPreferredSize()
	{
		Dimension d = null;
		Insets insetCache = this.getView().getInsets();

		if (this.isHorizontal()) d = new Dimension(DEFAULT_LENGTH, DEFAULT_WIDTH);
		else d = new Dimension(DEFAULT_WIDTH, DEFAULT_LENGTH);
		
		d.width += insetCache.left + insetCache.right;
		d.height += insetCache.top + insetCache.bottom;

		return d;
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		Dimension d = this.getPreferredSize();
		if (this.isHorizontal()) d.width = Short.MAX_VALUE;
		else d.height = Short.MAX_VALUE;
		return d;
	}	
}
