package jmp.ui.component.bar.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import jmp.ui.component.Orientation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class BarDefaultRenderer  extends DefaultRenderer implements BarRenderer {

	public BarDefaultRenderer(View view) {
		super(view);
	}

	public void renderView(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		renderBar(g);
	}
	
	public void renderProgress(Graphics2D g) {
		BarRenderingModel renderingModel = this.barView().renderingModel();	
		BoundedModel valueModel = this.barView().valueModel();
		BarColoredRenderingModel coloredModel = ((BarColoredRenderingModel) ((ModelComposit) (barView().getModel())).getModel("colored"));
		
		if(coloredModel==null) return;
		
		g.setColor(coloredModel.getTrailColor());
		int progressRectWidth = 0;
		int progressRectHeight = 0;
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			progressRectWidth = (int) getMaximumSize().width;
			progressRectHeight = (int) renderingModel.getSize().getHeight();
		}
		else 
		{
			progressRectWidth = (int) renderingModel.getSize().getHeight();
			progressRectHeight = getMaximumSize().width;
		}
		g.fillRect(0, 0, progressRectWidth, progressRectHeight);
		
		g.setColor(coloredModel.getProgressColor());

		final int fillWidth = valueModel.getValue()*progressRectWidth/(valueModel.getMaximum()-valueModel.getMinimum());
		g.fillRect(0, 0, fillWidth, progressRectHeight);
	}

	public void renderLabel(Graphics2D g) {
		
	}

	public void renderLabels(Graphics2D g) {
		
	}

	public void renderTicks(Graphics2D g) {
		
	}

	public void renderBorder(Graphics2D g) {
		
	}

	public void renderBar(Graphics2D g) {
		renderProgress(g);
	}

	protected BarView barView()
	{
		return (BarView) this.getView();
	}
	
	public void setSize(Dimension size) {
		
	}
	
	public Dimension getPreferredSize()
	{
		BarRenderingModel renderingModel = this.barView().renderingModel();		
		if (renderingModel.getOrientation() == Orientation.Horizontal) 
			return new Dimension((int) renderingModel.getSize().getWidth(), (int) renderingModel.getSize().getHeight());
		else 
			return new Dimension((int) renderingModel.getSize().getHeight(), (int) renderingModel.getSize().getWidth());
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		Dimension d = this.getPreferredSize();
		BarRenderingModel renderingModel = this.barView().renderingModel();		
		if (renderingModel.getOrientation() == Orientation.Horizontal) d.width = Short.MAX_VALUE;
		else d.height = Short.MAX_VALUE;
		return d;
	}	
}
