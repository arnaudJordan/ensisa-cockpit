package jmp.ui.component.indicator.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.model.Models;
import jmp.ui.mvc.View;

public class IndicatorMultiRenderer extends IndicatorDefaultRenderer {

	public IndicatorMultiRenderer(View view) {
		super(view);
	}
	public void renderState(Graphics2D g) {
		BooleanModels valueModel = ((BooleanModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		if(valueModel==null) return;
		
		int multX=0;
		int multY=0;
		int transX = 0;
		int transY = 0;
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
			multY=1;
		else
			multX=1;
		
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		if(labelsModel != null)
		{
			Iterator<IndicatorLabelRenderingModel> it = labelsModel.getIterator();
			Graphics g2 = this.indicatorView().getGraphics();
			while(it.hasNext())
			{
				IndicatorLabelRenderingModel label = it.next();
				g2.setFont(label.getFont());
				if(label.getPosition() == CardinalPosition.WEST)
				{
					if(multY>0)
						transX = (transX > g2.getFontMetrics().stringWidth(label.getLabel())) ? transX : g2.getFontMetrics().stringWidth(label.getLabel()) + label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.NORTH)
				{
					if(multX>0)
						transY = g2.getFontMetrics().getHeight() + label.getMargin();
				}
			}
		}
		
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel != null)
		{
			Iterator<BooleanModel> it = valueModel.getIterator();
			int i=0;
			while(it.hasNext())
			{
				BooleanModel value = (BooleanModel) it.next();
				BufferedImage stateImage;
				if(value.is())
					stateImage = pictureModel.getOnImage();
				else
					stateImage = pictureModel.getOffImage();

				int transYN = 0, transYS = 0;
				int transXW = 0, transXE = 0;
				if(labelsModel != null)
				{
					IndicatorLabelRenderingModel label = labelsModel.getLabel(i);
					Graphics g2 = this.indicatorView().getGraphics();
					g2.setFont(label.getFont());
					if(label.getPosition() == CardinalPosition.NORTH && multY>0)
					{
						transYN = g2.getFontMetrics().getHeight() + label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.SOUTH&& multY>0)
					{
						transYS = g2.getFontMetrics().getHeight()+ label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.WEST && multX>0)
					{
						transXW = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.EAST && multX>0)
					{
						transXE = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					}
				}
				transY+=transYN;
				transX+=transXW;
				g.drawImage(stateImage,transX,transY,null);
				transY+=transYS;
				transX+=transXE;
				transX+=stateImage.getWidth() * multX;
				transY+=stateImage.getHeight() * multY;
				i++;
			}
			return;
		}
		
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		if(colorModel != null)
		{
			Color oldColor = g.getColor();
			Iterator<BooleanModel> it = valueModel.getIterator();
			int i = 0;
			while(it.hasNext())
			{
				BooleanModel value = (BooleanModel) it.next();
				
				int transYN = 0, transYS = 0;
				int transXW = 0, transXE = 0;
				if(labelsModel != null)
				{
					IndicatorLabelRenderingModel label = labelsModel.getLabel(i);
					Graphics g2 = this.indicatorView().getGraphics();
					g2.setFont(label.getFont());
					if(label.getPosition() == CardinalPosition.NORTH && multY>0)
					{
						transYN = g2.getFontMetrics().getHeight()+ label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.SOUTH&& multY>0)
					{
						transYS = g2.getFontMetrics().getHeight()+ label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.WEST && multX>0)
					{
						transXW = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					}
					if(label.getPosition() == CardinalPosition.EAST && multX>0)
					{
						transXE = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					}
				}
				transY+=transYN;
				transX+=transXW;
				
				RadialGradientPaint p;
				Color color = Color.BLACK;
				if(value.is())
				{
					color=colorModel.getOnColor();
				}
				else
				{
					color=colorModel.getOffColor();
				}
				g.setColor(color);
				p = new RadialGradientPaint(new Point2D.Double(transX +colorModel.getSize().getWidth()/ 2.0,
						transY +colorModel.getSize().getHeight()/ 2.0), (float) (colorModel.getSize().getWidth() / 2.0f),
						new float[] { 0.0f, 1.0f },
						new Color[] { new Color(255, 255, 255), color});
				g.setPaint(p);
				g.fillOval(transX,transY,(int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight());
				transY+=transYS;
				transX+=transXE;
				transX+=colorModel.getSize().getWidth() * multX;
				transY+=colorModel.getSize().getHeight() * multY;
				i++;
			}
			g.setColor(oldColor);
		}
	}

	public void renderLabel(Graphics2D g) {
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		

		if(labelsModel == null) return;

		Dimension dimension = new Dimension(0,0);
		
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		if(colorModel != null)
			dimension = new Dimension(colorModel.getSize().width, colorModel.getSize().height);
		
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel != null)
			dimension = new Dimension(pictureModel.getOnImage().getWidth(), pictureModel.getOnImage().getHeight());
		
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		if(blinkMultiModel != null)
			dimension = blinkMultiModel.getSize();

		int transY = 0, transX=0;
		int multX = 0;
		int multY = 0;
		
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
			multY=1;
		else
			multX=1;

		int xw=0, xe=0, yn=0, ys=0;
		Iterator<IndicatorLabelRenderingModel> it = labelsModel.getIterator();
		while(it.hasNext())
		{
			IndicatorLabelRenderingModel label = it.next();
			g.setFont(label.getFont());
			if(label.getPosition() == CardinalPosition.NORTH)
			{
				if(multX>0)
					yn = g.getFontMetrics().getHeight()+ label.getMargin();
			}
			if(label.getPosition() == CardinalPosition.SOUTH)
			{
				if(multX>0)
					ys = g.getFontMetrics().getHeight()+ label.getMargin();
			}
			if(label.getPosition() == CardinalPosition.WEST)
			{
				if(multY>0)
					xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
			}
			if(label.getPosition() == CardinalPosition.EAST)
			{
				if(multY>0)
					xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
			}	
		}
		int middle = xw + dimension.width/2;
		int middley = yn ;

		it = labelsModel.getIterator();
		while(it.hasNext())
		{
			IndicatorLabelRenderingModel label = it.next();
			g.setColor(label.getColor());
			g.setFont(label.getFont());
			final int strHeight = g.getFontMetrics().getHeight();
			final int strWidth = g.getFontMetrics().stringWidth(label.getLabel());
			switch (label.getPosition())
			{
			case NORTH : g.drawString(label.getLabel(), middle - strWidth/2 + transX , strHeight + transY - label.getMargin()/2);transY+=(strHeight+ label.getMargin())* multY; break;
			case SOUTH : g.drawString(label.getLabel(), middle - strWidth/2 + transX, transY + dimension.height + strHeight+ yn + label.getMargin()/2);transY+=(strHeight+ label.getMargin()) * multY; break;
			case EAST : g.drawString(label.getLabel(), middle + dimension.width/2 + transX + label.getMargin()/2, yn+transY + dimension.height/2 + strHeight/2);transX+=(strWidth+ label.getMargin()) * multX; break;
			case WEST : transX+=(strWidth + label.getMargin())* multX; g.drawString(label.getLabel(), middle - dimension.width/2 - strWidth + transX - label.getMargin()/2, yn+transY + dimension.height/2 + strHeight/2);break;
			}
			transY+=dimension.getHeight()*multY;
			transX+=dimension.getWidth()*multX;
		}
	}

	public Dimension getPreferredSize() {
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		
		int nbX=1;
		int nbY=1;
		int multX = 0;
		int multY = 0;
		
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
			multY=1;
		else
			multX=1;
		
		Models valueModel = ((Models) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		if(valueModel != null)
		{
			nbY=(valueModel.getSize() - 1)*multY + 1;
			nbX=(valueModel.getSize() - 1)*multX + 1;
		}
		
		Dimension dimension = new Dimension(0,0);

		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		if(blinkModel != null)
			dimension = new Dimension(nbX * blinkModel.getSize().width, nbY * blinkModel.getSize().height);
		
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		if(coloredRangeModel != null)
			dimension = new Dimension(nbX * coloredRangeModel.getSize().width, nbY * coloredRangeModel.getSize().height);
		
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		if(colorModel != null)
			dimension = new Dimension(nbX * colorModel.getSize().width, nbY * colorModel.getSize().height);
		
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel != null)
			dimension = new Dimension(nbX * pictureModel.getOnImage().getWidth(), nbY * pictureModel.getOnImage().getHeight());
		
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		if(blinkMultiModel != null)
			dimension = new Dimension(nbX * blinkMultiModel.getSize().width, nbY * blinkMultiModel.getSize().height);

		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));

		Graphics g = this.indicatorView().getGraphics();

		if(labelsModel != null)
		{

			int xw=0, xe=0, yn=0, ys=0;
			Iterator<IndicatorLabelRenderingModel> it = labelsModel.getIterator();
			while(it.hasNext())
			{
				IndicatorLabelRenderingModel label = it.next();
				g.setFont(label.getFont());
				if(label.getPosition() == CardinalPosition.NORTH)
				{
					yn+=(g.getFontMetrics().getHeight()+ label.getMargin()) * multY;
					if(multX>0)
						yn = g.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.SOUTH)
				{
					ys+=(g.getFontMetrics().getHeight()+ label.getMargin()) * multY;
					if(multX>0)
						ys = g.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.WEST)
				{
					if(multY>0)
						xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					if(multX>0)
						xw += g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.EAST)
				{
					if(multY>0)
						xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
					if(multX>0)
						xe += g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}				
			}
			dimension = new Dimension((int) (dimension.getWidth() + xe + xw), (int)(dimension.getHeight() + yn+ys));
		}

		if(labelModel == null) 
			return dimension;
		g.setFont(labelModel.getFont());

		if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
			return new Dimension((int)Math.max(dimension.getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
					(int)dimension.getHeight() +  g.getFontMetrics().getHeight() + 1);

		return new Dimension((int)dimension.getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
				Math.max((int)dimension.getHeight(), g.getFontMetrics().getHeight() + 1));
	}
	public void renderBorder(Graphics2D g) {
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		
		IndicatorBorderRenderingModel borderModel = ((IndicatorBorderRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("border"));
		if(borderModel==null) return;

		Dimension dimension = null;
		
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		if(coloredRangeModel != null)
			dimension = coloredRangeModel.getSize();
		
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		if(blinkModel != null)
			dimension=blinkModel.getSize();
		
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		if(colorModel != null)
			dimension=colorModel.getSize();
		
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel!=null)
			dimension= new Dimension(pictureModel.getOnImage().getWidth(),pictureModel.getOnImage().getHeight());
		
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		if(blinkMultiModel != null)
			dimension=blinkMultiModel.getSize();

		if(dimension==null || borderModel.getBorderSize()==0) return;


		int multX=0;
		int multY=0;
		Models valueModel = ((Models) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		if(valueModel==null) return;
		
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
			multY=1;
		else
			multX=1;

		int middle = dimension.width/2;
		int xw=0, xe=0, yn=0, ys=0;
		
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		if(labelsModel !=null)
		{
			Iterator<IndicatorLabelRenderingModel> it = labelsModel.getIterator();
			while(it.hasNext())
			{
				IndicatorLabelRenderingModel label = it.next();
				g.setFont(label.getFont());
				if(label.getPosition() == CardinalPosition.NORTH)
				{
					if(multX>0)
						yn = g.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.SOUTH)
				{
					if(multX>0)
						ys = g.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.WEST)
				{
					if(multY>0)
						xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.EAST)
				{
					if(multY>0)
						xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}				
			}
			middle += xw;
		}	
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		int transX=xw, transY=yn;

		Color oldColor = g.getColor();
		g.setColor(borderModel.getBorderColor());
		for(int i=0; i < valueModel.getSize(); i++)
		{
			int transYN = 0, transYS = 0;
			int transXW = 0, transXE = 0;
			if(labelsModel != null)
			{
				IndicatorLabelRenderingModel label = labelsModel.getLabel(i);
				Graphics g2 = this.indicatorView().getGraphics();
				g2.setFont(label.getFont());
				if(label.getPosition() == CardinalPosition.NORTH && multY>0)
				{
					transYN = g2.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.SOUTH&& multY>0)
				{
					transYS = g2.getFontMetrics().getHeight()+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.WEST&& multX>0)
				{
					transXW = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}
				if(label.getPosition() == CardinalPosition.EAST && multX>0)
				{
					transXE = g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}
			}
			transY+=transYN;
			transX+=transXW;
			g.drawOval(transX,transY,dimension.getSize().width, dimension.getSize().height);
			transY+=transYS;
			transX+=transXE;
			transX+=dimension.getSize().getWidth() * multX;
			transY+=dimension.getSize().getHeight() * multY;
		}
		g.setColor(oldColor);
	}
}
