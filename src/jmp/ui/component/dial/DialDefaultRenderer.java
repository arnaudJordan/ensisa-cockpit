package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class DialDefaultRenderer extends DefaultRenderer implements DialRenderer {
	

	public DialDefaultRenderer(View view) {
		super(view);
	}
	protected DialView dialView()
	{
		return (DialView) this.getView();
	}
	public void renderTick(Graphics2D g) {
	}

	public void renderTicks(Graphics2D g) {
	}

	public void renderLabel(Graphics2D g) {
	}

	public void renderLabels(Graphics2D g) {
	}

	public void renderTrack(Graphics2D g) {
	}

	public void renderNeedle(Graphics2D g) {
	}

	public void renderBackground(Graphics2D g) {
	}

	public void renderBorder(Graphics2D g) {
	}

	public void renderDial(Graphics2D g) {
		this.renderBackground(g);
		this.renderTrack(g);
		this.renderTicks(g);
		this.renderLabels(g);
		this.renderLabel(g);
		this.renderNeedle(g);
		this.renderBorder(g);
	}
	public void renderView(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		this.renderDial(g);
	}

	public void setSize(Dimension size) {
	}

	public Dimension getPreferredSize() {
		return getPreferredSize();
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
