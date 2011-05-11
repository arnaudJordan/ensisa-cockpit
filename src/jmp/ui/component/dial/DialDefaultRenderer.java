package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;

import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class DialDefaultRenderer extends DefaultRenderer implements DialRenderer {
	

	public DialDefaultRenderer(View view) {
		super(view);
		// TODO Auto-generated constructor stub
	}
	protected DialView dialView()
	{
		return (DialView) this.getView();
	}
	@Override
	public void renderTick(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderTicks(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderLabel(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderLabels(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderTrack(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderNeedle(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderBackground(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderBorder(Graphics2D g) {
		// TODO Auto-generated method stub

	}

	@Override
	public void renderDial(Graphics2D g) {
		this.renderBackground(g);
		this.renderTrack(g);
		this.renderTicks(g);
		this.renderLabels(g);
		this.renderNeedle(g);
		this.renderBorder(g);
	}

	@Override
	public void setSize(Dimension size) {
		// TODO Auto-generated method stub

	}

	@Override
	public Dimension getPreferredSize() {
		// TODO Auto-generated method stub
		return new Dimension();
	}

	@Override
	public Dimension getMinimumSize() {
		// TODO Auto-generated method stub
		return new Dimension();
	}

	@Override
	public Dimension getMaximumSize() {
		// TODO Auto-generated method stub
		return new Dimension();
	}

}
