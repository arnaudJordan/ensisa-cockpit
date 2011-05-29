package jmp.ui.component.bar.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.Rotation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
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
		int dimWidthX = 0;
		int dimWidthY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			
			switch (labelModel.getPosition())
			{
				case NORTH : transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : if(renderingModel.getOrientation() == Orientation.Vertical) dimWidthY = g.getFontMetrics().getHeight() + 1; break;
				case EAST : if(renderingModel.getOrientation() == Orientation.Horizontal) dimWidthX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				case WEST : transX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
			}
		}
		g.fillRect(transX, transY, progressRectWidth - dimWidthX, progressRectHeight - dimWidthY);
		
		g.setColor(coloredModel.getProgressColor());
		
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			final int fillWidth = valueModel.getValue()*progressRectWidth/(valueModel.getMaximum()-valueModel.getMinimum());
			g.fillRect(transX, transY, fillWidth - dimWidthX, progressRectHeight);
		}
		else
		{
			final int fillHeight = valueModel.getValue()*progressRectHeight/(valueModel.getMaximum()-valueModel.getMinimum());
			g.fillRect(transX, progressRectHeight - fillHeight + transY - dimWidthY, progressRectWidth, fillHeight);
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
			case SOUTH : g.drawString(labelModel.getLabel(), this.getView().getSize().width/2 - strWidth/2, this.getView().getSize().height); break;
			case EAST : g.drawString(labelModel.getLabel(), this.getView().getSize().width - strWidth, this.getView().getSize().height/2 + strHeight/2); break;
			case WEST : g.drawString(labelModel.getLabel(), 0, this.getView().getSize().height/2 + strHeight/2); break;
		}
	}

	public void renderLabels(Graphics2D g) {
		
	}

	public void renderTicks(Graphics2D g) {
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarBorderRenderingModel borderModel = ((BarBorderRenderingModel) ((ModelComposit) (barView().getModel())).getModel("border"));
		BarRenderingModel renderingModel = barView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.barView().valueModel());
		if(ticksModel==null||valueModel==null) return;
		
		int borderSize = 0;
		if(borderModel != null)
			borderSize = borderModel.getBorderSize();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		final int minorTickSize = (int) ticksModel.getMinorTickSize();
		final int majorTickWidth = (int) ticksModel.getMajorGraduationWidth();
		final int minorTickWidth = (int) ticksModel.getMinorGraduationWidth();
		int barWidth = 0;
		if(renderingModel.getOrientation() == Orientation.Horizontal)
			barWidth = (int) this.getView().getSize().getWidth();
		else
			barWidth = (int) this.getView().getSize().getHeight();
		int lineXStart = 0;
		int lineXEnd = 0;
		int lineYStart = 0;
		int lineYEnd = 0;
		double nbValues = (double)(valueModel.getMaximum()-valueModel.getMinimum())/ticksModel.getMinorTickSpacing();
		double minorTickSpacing = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			if(renderingModel.getOrientation() == Orientation.Horizontal)
			{
				lineXStart = borderSize/2 + minorTickWidth/2;
				lineXEnd = barWidth - borderSize/2 - minorTickWidth/2;
				switch (labelModel.getPosition())
				{
					case NORTH : lineYStart += g.getFontMetrics().getHeight() + 1; break;
					case EAST : lineXEnd -= g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
					case WEST : lineXStart += g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
				minorTickSpacing = (lineXEnd - lineXStart) / nbValues;
			}	
			else
			{
				lineYStart = barWidth - borderSize/2 - minorTickWidth/2;
				lineYEnd = borderSize/2 + minorTickWidth/2;
				switch (labelModel.getPosition())
				{
					case NORTH : lineYEnd += g.getFontMetrics().getHeight() + 1; break;
					case SOUTH : lineYStart -= g.getFontMetrics().getHeight() + 1; break;
					case WEST : lineXStart = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
				minorTickSpacing = (lineYStart - lineYEnd) / nbValues;
			}
		}
		g.setColor(ticksModel.getMinorGraduationColor());
		g.setStroke(ticksModel.getMinorGradutionStroke());
		if(renderingModel.getOrientation() == Orientation.Horizontal)
		{
			for(int i = 0; i < nbValues+1; i++)
				g.drawLine((int) (i*minorTickSpacing) + lineXStart, lineYStart + borderSize/2, (int) (i*minorTickSpacing) + lineXStart, lineYStart + borderSize/2 + minorTickSize);		
		}
		else
		{
			for(int i = 0; i < nbValues+1; i++)
				g.drawLine(lineXStart + borderSize/2, (int) (-i*minorTickSpacing) + lineYStart, lineXStart + borderSize/2 + minorTickSize, (int) (-i*minorTickSpacing) + lineYStart);		
		}
		nbValues = (double)(valueModel.getMaximum()-valueModel.getMinimum())/ticksModel.getMajorTickSpacing();
		
		g.setColor(ticksModel.getMajorGraduationColor());
		g.setStroke(ticksModel.getMajorGradutionStroke());
		double majorTickSpacing = 0;
		if(renderingModel.getOrientation() == Orientation.Horizontal)
		{
			lineXStart += -minorTickWidth/2 + majorTickWidth/2;
			lineXEnd += minorTickWidth/2 - majorTickWidth/2;
			majorTickSpacing = (lineXEnd - lineXStart) / nbValues;
			for(int i = 0; i < nbValues+1; i++)
				g.drawLine((int) (i*majorTickSpacing) + lineXStart, lineYStart + borderSize/2, (int) (i*majorTickSpacing) + lineXStart, lineYStart + borderSize/2 + majorTickSize);		
		}
		else
		{
			lineYStart += minorTickWidth/2 - majorTickWidth/2;
			lineYEnd += -minorTickWidth/2 + majorTickWidth/2;
			majorTickSpacing = (lineYStart - lineYEnd) / nbValues;
			for(int i = 0; i < nbValues+1; i++)
				g.drawLine(lineXStart + borderSize/2, (int) (-i*majorTickSpacing) + lineYStart, lineXStart + borderSize/2 + majorTickSize, (int) (-i*majorTickSpacing) + lineYStart);			
		}
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
		int dimWidthX = 0;
		int dimWidthY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			
			switch (labelModel.getPosition())
			{
				case NORTH : transY = g.getFontMetrics().getHeight() + 1; 
						     if (renderingModel.getOrientation() == Orientation.Vertical) dimWidthY = transY; break;
				case SOUTH : if (renderingModel.getOrientation() == Orientation.Vertical) dimWidthY = g.getFontMetrics().getHeight() + 1; break;
				case EAST : if (renderingModel.getOrientation() == Orientation.Horizontal) dimWidthX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				case WEST : transX = g.getFontMetrics().stringWidth(labelModel.getLabel()); 
							if (renderingModel.getOrientation() == Orientation.Horizontal) dimWidthX = transX; break;
			}
		}
		g.drawRect(transX, transY, progressRectWidth-1 - dimWidthX, progressRectHeight-2 - dimWidthY);
	}

	public void renderBar(Graphics2D g) {
		renderProgress(g);
		renderBorder(g);
		renderLabel(g);
		renderTicks(g);
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
