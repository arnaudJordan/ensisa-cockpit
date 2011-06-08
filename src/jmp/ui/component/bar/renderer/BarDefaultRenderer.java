package jmp.ui.component.bar.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.Iterator;
import java.util.Set;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;
import jmp.ui.utilities.ColoredRange;

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
	
	public void renderBackground(Graphics2D g) {
		BarRenderingModel renderingModel = this.barView().renderingModel();	
		BoundedModel valueModel = this.barView().valueModel();
		BarColoredRenderingModel coloredModel = ((BarColoredRenderingModel) ((ModelComposit) (barView().getModel())).getModel("colored"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarColoredRangeRenderingModel coloredRangeModel = ((BarColoredRangeRenderingModel) ((ModelComposit) (barView().getModel())).getModel("coloredRangeBackground"));
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		
		if(coloredModel==null && coloredRangeModel==null && pictureModel==null) return;
		
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
				case NORTH : if(renderingModel.getOrientation() == Orientation.Vertical) progressRectHeight -= g.getFontMetrics().getHeight() + 1;
							 transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : if(renderingModel.getOrientation() == Orientation.Vertical) progressRectHeight -= g.getFontMetrics().getHeight() + 1; 
							 if(ticksModel != null && renderingModel.getOrientation() == Orientation.Horizontal){
								 g.setFont(ticksModel.getLabelFont());
								 transY = ticksModel.getLabelSpace() + g.getFontMetrics().getHeight() + 1;
							 }break;
				case EAST : if(renderingModel.getOrientation() == Orientation.Horizontal) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel()); 
							if(ticksModel != null && renderingModel.getOrientation() == Orientation.Vertical){
								 g.setFont(ticksModel.getLabelFont());
								 transX = ticksModel.getLabelSpace() + g.getFontMetrics().stringWidth(String.valueOf(valueModel.getMaximum()));
							 }break;
				case WEST : if(renderingModel.getOrientation() == Orientation.Horizontal) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel());
							transX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
			}
		}
		
		if(coloredModel != null && coloredRangeModel == null && pictureModel == null)
		{
			g.setColor(coloredModel.getTrailColor());
			g.fillRect(transX, transY, progressRectWidth, progressRectHeight);
		}
		
		if(coloredRangeModel != null)
		{
			if(renderingModel.getOrientation() == Orientation.Horizontal)
			{
				double ratio=(double) (progressRectWidth) / (double) (valueModel.getMaximum()-valueModel.getMinimum());
				for(ColoredRange range : coloredRangeModel.getColoredRanges().getRanges())
				{
					g.setColor(range.color);
					g.fillRect((int) (transX + ratio*range.range.min), transY, (int) (ratio*(range.range.max - range.range.min)), progressRectHeight);
				}
			}
			else
			{
				double ratio=(double) (progressRectHeight) / (double) (valueModel.getMaximum()-valueModel.getMinimum());
				for(ColoredRange range : coloredRangeModel.getColoredRanges().getRanges())
				{
					g.setColor(range.color);
					g.fillRect(transX, transY + (int)(progressRectHeight - ratio*range.range.max),
							progressRectWidth, (int)(ratio*(range.range.max - range.range.min)));
				}
			}
		}
		if(pictureModel != null)
		{
			AffineTransform trans = new AffineTransform();
			trans.translate(transX, transY);
			if(renderingModel.getOrientation() == Orientation.Vertical)
			{
				trans.translate(0, progressRectHeight);
				trans.rotate(Math.toRadians(-90));
			}
			g.drawImage(pictureModel.getBackground(), trans, null);
		}
	}
	
	public void renderProgress(Graphics2D g) {
		BarRenderingModel renderingModel = this.barView().renderingModel();	
		BoundedModel valueModel = this.barView().valueModel();
		BarColoredRenderingModel coloredModel = ((BarColoredRenderingModel) ((ModelComposit) (barView().getModel())).getModel("colored"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarColoredRangeRenderingModel coloredRangeModel = ((BarColoredRangeRenderingModel) ((ModelComposit) (barView().getModel())).getModel("coloredRangeProgress"));
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
		BarNeedleRenderingModel needleModel = ((BarNeedleRenderingModel) ((ModelComposit) (barView().getModel())).getModel("needle"));
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		
		if(coloredModel==null && needleModel==null && coloredRangeModel==null) return;
		
		int progressRectWidth = 0;
		int progressRectHeight = 0;
		
		if (renderingModel.getOrientation() == Orientation.Horizontal)
		{
			if(pictureModel != null)
			{
				progressRectWidth = pictureModel.getBackground().getWidth();
				progressRectHeight = pictureModel.getBackground().getHeight();
			}
			else
			{
				progressRectWidth = (int) this.getView().getSize().width;
				progressRectHeight = (int) renderingModel.getSize().getHeight();
			}
		}
		else 
		{
			
			if(pictureModel != null)
			{
				progressRectWidth = pictureModel.getBackground().getHeight();
				progressRectHeight = pictureModel.getBackground().getWidth();
			}
			else
			{
				progressRectWidth = (int) renderingModel.getSize().getHeight();
				progressRectHeight = this.getView().getSize().height;
			}
		}
		
		int transX = 0;
		int transY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			switch (labelModel.getPosition())
			{
				case NORTH : if(renderingModel.getOrientation() == Orientation.Vertical && pictureModel == null) progressRectHeight -= g.getFontMetrics().getHeight() + 1;
							 transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : if(renderingModel.getOrientation() == Orientation.Vertical && pictureModel == null) progressRectHeight -= g.getFontMetrics().getHeight() + 1; 
							if(ticksModel != null && renderingModel.getOrientation() == Orientation.Horizontal){
								 g.setFont(ticksModel.getLabelFont());
								 transY = ticksModel.getLabelSpace() + g.getFontMetrics().getHeight() + 1;
							 }break;
				case EAST : if(renderingModel.getOrientation() == Orientation.Horizontal && pictureModel == null) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel()); 
							if(ticksModel != null && renderingModel.getOrientation() == Orientation.Vertical){
								 g.setFont(ticksModel.getLabelFont());
								 transX = ticksModel.getLabelSpace() + g.getFontMetrics().stringWidth(String.valueOf(valueModel.getMaximum()));
							 }break;
				case WEST : if(renderingModel.getOrientation() == Orientation.Horizontal && pictureModel == null) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel());
							transX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
			}
		}
		
		if(coloredModel != null && coloredRangeModel == null && needleModel == null)
		{
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
		if(needleModel != null)
		{
			AffineTransform trans = new AffineTransform();
			if (renderingModel.getOrientation() == Orientation.Horizontal)
		    {
				final int fillWidth = valueModel.getValue()*progressRectWidth/(valueModel.getMaximum()-valueModel.getMinimum());
				trans.translate(transX + fillWidth - needleModel.getNeedle().getWidth()/2, transY);
		    }
			else
			{
				final int fillHeight = valueModel.getValue()*progressRectHeight/(valueModel.getMaximum()-valueModel.getMinimum());
				trans.translate(transX, transY - fillHeight + progressRectHeight + needleModel.getNeedle().getWidth()/2);
				trans.rotate(Math.toRadians(-90));
			}
			g.drawImage(needleModel.getNeedle(),trans,null);
		}
		if(coloredRangeModel != null)
		{
			BufferedImage progressBarImage = new BufferedImage(progressRectWidth, progressRectHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2 = progressBarImage.createGraphics();
			Shape clip;
			if(renderingModel.getOrientation() == Orientation.Horizontal)
			{
				final int fillWidth = valueModel.getValue()*progressRectWidth/(valueModel.getMaximum()-valueModel.getMinimum());
		        clip = new Rectangle2D.Double(0, 0, fillWidth, progressRectHeight);
				g2.clip(clip);
				double ratio=(double) (progressRectWidth) / (double) (valueModel.getMaximum()-valueModel.getMinimum());
				for(ColoredRange range : coloredRangeModel.getColoredRanges().getRanges())
				{
					g2.setColor(range.color);
					g2.fillRect((int) (ratio*range.range.min), 0, (int) (ratio*(range.range.max - range.range.min)), progressRectHeight);
				}
			}
			else
			{
				final int fillHeight = valueModel.getValue()*progressRectHeight/(valueModel.getMaximum()-valueModel.getMinimum());
		        clip = new Rectangle2D.Double(0, progressRectHeight - fillHeight, progressRectWidth, fillHeight);
				
				g2.clip(clip);
				double ratio=(double) (progressRectHeight) / (double) (valueModel.getMaximum()-valueModel.getMinimum());
				for(ColoredRange range : coloredRangeModel.getColoredRanges().getRanges())
				{
					g2.setColor(range.color);
					g2.fillRect(0, (int)(progressRectHeight - ratio*range.range.max), progressRectWidth, (int)(ratio*(range.range.max - range.range.min)));
				}
			}
			AffineTransform trans = new AffineTransform();
			trans.translate(transX, transY);
			g.drawImage(progressBarImage, trans, null);
			g2.dispose();
		}
	}

	public void renderLabel(Graphics2D g) {
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));

		if(labelModel == null) return;
			
		g.setColor(labelModel.getColor());
		g.setFont(labelModel.getFont());
		final int strHeight = g.getFontMetrics().getHeight();
		final int strWidth = g.getFontMetrics().stringWidth(labelModel.getLabel());
		int height=0;
		int width=0;
		if(pictureModel != null)
		{
			height = (int)getPreferredSize().getHeight();
			width = (int)getPreferredSize().getWidth();
		}
		else
		{
			height = this.getView().getSize().height;
			width = this.getView().getSize().width;
		}
			
		switch (labelModel.getPosition())
		{
			case NORTH : g.drawString(labelModel.getLabel(), width/2 - strWidth/2, strHeight/2); break;
			case SOUTH : g.drawString(labelModel.getLabel(), width/2 - strWidth/2, height); break;
			case EAST : g.drawString(labelModel.getLabel(), width - strWidth, height/2 + strHeight/2); break;
			case WEST : g.drawString(labelModel.getLabel(), 0, height/2 + strHeight/2); break;
		}
	}

	public void renderLabels(Graphics2D g) {
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarBorderRenderingModel borderModel = ((BarBorderRenderingModel) ((ModelComposit) (barView().getModel())).getModel("border"));
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
		BarRenderingModel renderingModel = barView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.barView().valueModel());
		if(ticksModel==null||valueModel==null) return;
		
		if(borderModel != null)
			borderModel.getBorderSize();
		int barWidth = 0;
		int barHeight = 0;
		if(pictureModel != null)
		{
			barWidth = pictureModel.getBackground().getWidth();
			barHeight = pictureModel.getBackground().getHeight();
			if(labelModel != null)
			{
				g.setFont(labelModel.getFont());
				if(renderingModel.getOrientation() == Orientation.Horizontal)
				{
					if(labelModel.getPosition() == CardinalPosition.EAST || labelModel.getPosition() == CardinalPosition.WEST)
						barWidth += g.getFontMetrics().stringWidth(labelModel.getLabel());
				}
				else
					if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
						barWidth += g.getFontMetrics().getHeight() + 1;
			}
		}
		else
		{
			if(renderingModel.getOrientation() == Orientation.Horizontal)
			{
				barWidth = (int) this.getView().getSize().getWidth();
				barHeight = renderingModel.getSize().height;
			}
			else
			{
				barWidth = (int) this.getView().getSize().getHeight();
				barHeight = renderingModel.getSize().height;
			}
		}
		
		int labelXStart = 0;
		int labelYStart = 0;
		
		labelYStart = ticksModel.getLabelSpace() + barHeight;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			if(renderingModel.getOrientation() == Orientation.Horizontal)
			{
				switch (labelModel.getPosition())
				{
				case NORTH : labelYStart += g.getFontMetrics().getHeight() + 1; break;
				case EAST : barWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				case SOUTH : labelYStart = -ticksModel.getLabelSpace(); break;
				case WEST : labelXStart = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
			}
			else
			{
				labelXStart = barHeight + ticksModel.getLabelSpace();
				labelYStart = barWidth;
				switch (labelModel.getPosition())
				{
				case NORTH : barWidth -= g.getFontMetrics().getHeight() + 1; break;
				case EAST : labelXStart = 0; break;
				case SOUTH : barWidth -= g.getFontMetrics().getHeight() + 1; 
							 labelYStart -= g.getFontMetrics().getHeight() + 1; break;
				case WEST : labelXStart += g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
			}
		}
		
		double majorTickSpacing = 0;
		double nbValues = (double)(valueModel.getMaximum()-valueModel.getMinimum())/ticksModel.getMajorTickSpacing();
		g.setColor(ticksModel.getLabelColor());
		g.setFont(ticksModel.getLabelFont());
		if(renderingModel.getOrientation() == Orientation.Horizontal)
		{
			labelYStart += g.getFontMetrics().getHeight() + 1;
			majorTickSpacing = (barWidth - labelXStart) / nbValues;
			for(int i = 0; i < nbValues+1; i++)
			{
				final String vString = String.valueOf((int)(i*ticksModel.getMajorTickSpacing()));
				g.drawString(vString, (int) (i*majorTickSpacing) + labelXStart - g.getFontMetrics().stringWidth(vString)/2, labelYStart);		
			}
		}
		else
		{
			majorTickSpacing = barWidth / nbValues;
			for(int i = 0; i < nbValues+1; i++)
			{
				final String vString = String.valueOf((int)(i*ticksModel.getMajorTickSpacing()));
				g.drawString(vString, labelXStart, (int) (-i*majorTickSpacing) + labelYStart + (g.getFontMetrics().getHeight() + 1)/4);			
			}
		}
	}

	public void renderTicks(Graphics2D g) {
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		BarLabelRenderingModel labelModel = ((BarLabelRenderingModel) ((ModelComposit) (barView().getModel())).getModel("label"));
		BarBorderRenderingModel borderModel = ((BarBorderRenderingModel) ((ModelComposit) (barView().getModel())).getModel("border"));
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
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
		if(pictureModel != null)
		{
			barWidth = pictureModel.getBackground().getWidth();
			if(labelModel != null)
			{
				g.setFont(labelModel.getFont());
				if(renderingModel.getOrientation() == Orientation.Horizontal)
				{
					if(labelModel.getPosition() == CardinalPosition.EAST || labelModel.getPosition() == CardinalPosition.WEST)
						barWidth += g.getFontMetrics().stringWidth(labelModel.getLabel());
				}
				else
					if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
						barWidth += g.getFontMetrics().getHeight() + 1;
			}
		}
		else
		{
			if(renderingModel.getOrientation() == Orientation.Horizontal)
				barWidth = (int) this.getView().getSize().getWidth();
			else
				barWidth = (int) this.getView().getSize().getHeight();
		}
		int lineXStart = 0;
		int lineXEnd = 0;
		int lineYStart = 0;
		int lineYEnd = 0;
		double nbValues = (double)(valueModel.getMaximum()-valueModel.getMinimum())/ticksModel.getMinorTickSpacing();
		double minorTickSpacing = 0;
		
		if(renderingModel.getOrientation() == Orientation.Horizontal)
		{
			lineXStart = borderSize/2 + minorTickWidth/2;
			lineXEnd = barWidth - borderSize/2 - minorTickWidth/2;
			if(labelModel != null)
			{
				g.setFont(labelModel.getFont());
				switch (labelModel.getPosition())
				{
				case NORTH : lineYStart += g.getFontMetrics().getHeight() + 1; break;
				case EAST : lineXEnd -= g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				case SOUTH : if(ticksModel != null){
								g.setFont(ticksModel.getLabelFont());
								lineYStart += ticksModel.getLabelSpace() + g.getFontMetrics().getHeight() + 1;
							}break;
				case WEST : lineXStart += g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
			}
			minorTickSpacing = (lineXEnd - lineXStart) / nbValues;
		}	
		else
		{
			lineYStart = barWidth - borderSize/2 - minorTickWidth/2;
			lineYEnd = borderSize/2 + minorTickWidth/2;
			if(labelModel != null)
			{
				g.setFont(labelModel.getFont());
				switch (labelModel.getPosition())
				{
				case EAST : if(ticksModel != null && renderingModel.getOrientation() == Orientation.Vertical){
								g.setFont(ticksModel.getLabelFont());
								lineXStart += ticksModel.getLabelSpace() + g.getFontMetrics().stringWidth(String.valueOf(valueModel.getMaximum()));
				 			}break;
				case NORTH : lineYEnd += g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : lineYStart -= g.getFontMetrics().getHeight() + 1; break;
				case WEST : lineXStart = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
				}
			}
			minorTickSpacing = (lineYStart - lineYEnd) / nbValues;
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
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		BoundedModel valueModel = ((BoundedModel) this.barView().valueModel());
		
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
			if(pictureModel != null)
			{
				progressRectWidth = pictureModel.getBackground().getWidth();
				progressRectHeight = pictureModel.getBackground().getHeight();
			}
			else
			{
				progressRectWidth = (int) this.getView().getSize().width;
				progressRectHeight = (int) renderingModel.getSize().getHeight();
			}
		}
		else 
		{
			if(pictureModel != null)
			{
				progressRectWidth = pictureModel.getBackground().getHeight();
				progressRectHeight = pictureModel.getBackground().getWidth();
			}
			else
			{
				progressRectWidth = (int) renderingModel.getSize().getHeight();
				progressRectHeight = this.getView().getSize().height;
			}
		}
		int transX = 0;
		int transY = 0;
		if(labelModel != null)
		{
			g.setFont(labelModel.getFont());
			switch (labelModel.getPosition())
			{
				case NORTH : if(renderingModel.getOrientation() == Orientation.Vertical && pictureModel == null) progressRectHeight -= g.getFontMetrics().getHeight() + 1;
							 transY = g.getFontMetrics().getHeight() + 1; break;
				case SOUTH : if(renderingModel.getOrientation() == Orientation.Vertical && pictureModel == null) progressRectHeight -= g.getFontMetrics().getHeight() + 1; 
							if(ticksModel != null && renderingModel.getOrientation() == Orientation.Horizontal){
								 g.setFont(ticksModel.getLabelFont());
								 transY = ticksModel.getLabelSpace() + g.getFontMetrics().getHeight() + 1;
							 }break;
				case EAST : if(renderingModel.getOrientation() == Orientation.Horizontal && pictureModel == null) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel()); 
							if(ticksModel != null && renderingModel.getOrientation() == Orientation.Vertical){
								 g.setFont(ticksModel.getLabelFont());
								 transX = ticksModel.getLabelSpace() + g.getFontMetrics().stringWidth(String.valueOf(valueModel.getMaximum()));
							 }break;
				case WEST : if(renderingModel.getOrientation() == Orientation.Horizontal && pictureModel == null) progressRectWidth -= g.getFontMetrics().stringWidth(labelModel.getLabel());
							transX = g.getFontMetrics().stringWidth(labelModel.getLabel()); break;
			}
		}
		g.drawRect(transX, transY, progressRectWidth-1, progressRectHeight-2);
	}

	public void renderBar(Graphics2D g) {
		BarNeedleRenderingModel needleModel = ((BarNeedleRenderingModel) ((ModelComposit) (barView().getModel())).getModel("needle"));
		
		renderBackground(g);
		if(needleModel == null)
			renderProgress(g);
		renderBorder(g);
		renderLabel(g);
		renderTicks(g);
		renderLabels(g);
		if(needleModel != null)
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
		BarPictureRenderingModel pictureModel = ((BarPictureRenderingModel) ((ModelComposit) (barView().getModel())).getModel("picture"));
		BarTicksRenderingModel ticksModel = ((BarTicksRenderingModel) ((ModelComposit) (barView().getModel())).getModel("ticks"));
		BoundedModel valueModel = ((BoundedModel) this.barView().valueModel());
		
		int labelSpace = 0;
		if(ticksModel != null)
		{
			BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			Graphics g = image.getGraphics();
			g.setFont(ticksModel.getLabelFont());
			if(renderingModel.getOrientation() == Orientation.Horizontal)
				labelSpace = ticksModel.getLabelSpace() + g.getFontMetrics().getHeight() + 1;
			else 
				labelSpace = ticksModel.getLabelSpace() + g.getFontMetrics().stringWidth(String.valueOf(valueModel.getMaximum()));
		}
		
		Dimension dimension = new Dimension(0, 0);
		if (renderingModel.getOrientation() == Orientation.Horizontal) 
		{
			if(pictureModel != null)
				dimension = new Dimension(pictureModel.getBackground().getWidth(), pictureModel.getBackground().getHeight() + labelSpace);
			else
				dimension = new Dimension((int) renderingModel.getSize().getWidth(), (int) renderingModel.getSize().getHeight() + labelSpace);
		}
		else 
		{
			if(pictureModel != null)
				dimension = new Dimension(pictureModel.getBackground().getHeight() + labelSpace, pictureModel.getBackground().getWidth());
			else
				dimension = new Dimension((int) renderingModel.getSize().getHeight() + labelSpace, (int) renderingModel.getSize().getWidth());
		}
		
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
