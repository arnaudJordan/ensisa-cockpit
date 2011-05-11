package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;

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
		this.renderNeedle(g);
		this.renderBorder(g);
	}
	public void renderView(Graphics2D g) {
		this.renderDial(g);
	}

	public void setSize(Dimension size) {
	}

	public Dimension getPreferredSize() {
		return new Dimension();
	}

	public Dimension getMinimumSize() {
		return new Dimension();
	}
	
	public Dimension getMaximumSize() {
		return new Dimension();
	}

}
