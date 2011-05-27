package jmp.ui.component.bar.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
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
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		
		if(coloredModel==null) return;
		
		g.setColor(coloredModel.getTrailColor());
		int progressRectWidth = 0;
		int progressRectHeight = 0;
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			progressRectWidth = (int) this.getView().getSize().width;
			progressRectHeight = (int) renderingModel.getSize().getHeight();
		}
		else 
		{
			progressRectWidth = (int) renderingModel.getSize().getHeight();
			progressRectHeight = this.getView().getSize().height;
		}
		int transX = 0;
		int transY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			
			switch (labelModel.getPosition())
			{
				case NORTH : transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : break;
				case EAST :  break;
				case WEST :  break;
			}
		}
		g.fillRect(transX, transY, progressRectWidth, progressRectHeight);
		
		g.setColor(coloredModel.getProgressColor());
		
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			final int fillWidth = valueModel.getValue()*progressRectWidth/(valueModel.getMaximum()-valueModel.getMinimum());
			g.fillRect(transX, transY, fillWidth, progressRectHeight);
		}
		else
		{
			final int fillHeight = valueModel.getValue()*progressRectHeight/(valueModel.getMaximum()-valueModel.getMinimum());
			g.fillRect(transX, progressRectHeight - fillHeight + transY, progressRectWidth, fillHeight);
		}
	}

	public void renderLabel(Graphics2D g) {
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		
		if(labelModel == null) return;
			
		g.setColor(labelModel.getColor());
		g.setFont(labelModel.getFont());
		final int strHeight = g.getFontMetrics().getHeight();
		final int strWidth = g.getFontMetrics().stringWidth(labelModel.getLabel());
		switch (labelModel.getPosition())
		{
			case NORTH : g.drawString(labelModel.getLabel(), this.getView().getSize().width/2 - strWidth/2, strHeight); break;
			case SOUTH : g.drawString(labelModel.getLabel(), (int) getPreferredSize().getWidth()/2 - strWidth/2, (int) getPreferredSize().getHeight()); break;
			case EAST : g.drawString(labelModel.getLabel(), (int) getPreferredSize().getWidth() - strWidth, (int) getPreferredSize().getHeight()/2 + strHeight/2); break;
			case WEST : g.drawString(labelModel.getLabel(), 0, (int) getPreferredSize().getHeight()/2 + strHeight/2); break;
		}
	}

	public void renderLabels(Graphics2D g) {
		
	}

	public void renderTicks(Graphics2D g) {
		
	}

	public void renderBorder(Graphics2D g) {
		BarRenderingModel renderingModel = this.barView().renderingModel();	
		BarBorderRenderingModel borderModel = ((BarBorderRenderingModel) ((ModelComposit) (barView().getModel())).getModel("border"));
		BarColoredRenderingModel coloredModel = ((BarColoredRenderingModel) ((ModelComposit) (barView().getModel())).getModel("colored"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		
		if(borderModel == null) return;
		
		Dimension dimension = null;
		if(coloredModel != null)
			dimension = coloredModel.getSize();
		
		if(dimension==null || borderModel.getBorderSize()==0) return;
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		
		int progressRectWidth = 0;
		int progressRectHeight = 0;
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			progressRectWidth = (int) this.getView().getSize().width;
			progressRectHeight = (int) renderingModel.getSize().getHeight();
		}
		else 
		{
			progressRectWidth = (int) renderingModel.getSize().getHeight();
			progressRectHeight = this.getView().getSize().height;
		}
		int transX = 0;
		int transY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			
			switch (labelModel.getPosition())
			{
				case NORTH : transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : break;
				case EAST :  break;
				case WEST :  break;
			}
		}
		g.drawRect(transX, transY, progressRectWidth-1, progressRectHeight-1);
	}

	public void renderBar(Graphics2D g) {
		renderProgress(g);
		renderBorder(g);
		renderLabel(g);
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
		Dimension dimension = new Dimension(0, 0);
		if (renderingModel.getOrientation() == Orientation.Horizontal) 
			dimension = new Dimension((int) renderingModel.getSize().getWidth(), (int) renderingModel.getSize().getHeight());
		else 
			dimension = new Dimension((int) renderingModel.getSize().getHeight(), (int) renderingModel.getSize().getWidth());
		
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		
		if(labelModel != null) 
		{
			Graphics g = this.barView().getGraphics();
			g.setFont(labelModel.getFont());
			
			if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
				return new Dimension((int)Math.max(dimension.getSize().getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
					(int)dimension.getHeight() +  g.getFontMetrics().getHeight() + 1);
			return new Dimension((int)dimension.getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
					Math.max((int)dimension.getHeight(), g.getFontMetrics().getHeight() + 1));
		}
		return dimension;
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
