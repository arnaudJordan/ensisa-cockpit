package jmp.ui.component.indicator.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

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
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.BlinkDrawer;
import jmp.ui.utilities.ImageList;

public class IndicatorBlinkMultiRenderer extends IndicatorDefaultRenderer {
	private List<Timer> timer;
	
	public IndicatorBlinkMultiRenderer(View view) {
		super(view);
		this.timer = new ArrayList<Timer>();
	}
	
	public void renderState(Graphics2D g) {
		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		
		IndicatorBlinkMultiRenderingModel blinkModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		
		int multX=0;
		int multY=0;
		int transX = 0;
		int transY = 0;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=1;
			else
				multX=1;
		
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
						transX = (transX > g2.getFontMetrics().stringWidth(label.getLabel())) ? transX : g2.getFontMetrics().stringWidth(label.getLabel());
				}
				if(label.getPosition() == CardinalPosition.NORTH)
				{
					if(multX>0)
						transY = g2.getFontMetrics().getHeight();
				}
			}
		}
		
		if(blinkModel != null)
		{
			Dimension dimension = blinkModel.getSize();
			Iterator<BoundedModel> it = valueModel.getIterator();
			while(valueModel.getSize()>timer.size())
			{
				timer.add(new Timer(0, new BlinkDrawer(indicatorView())));
			}
			
			int i=0;
			while(it.hasNext())
			{
				BoundedModel value = it.next();
				AffineTransform trans = new AffineTransform();
				
				int transYN = 0, transYS = 0;
				int transXW = 0, transXE = 0;
				if(labelsModel != null)
				{
					IndicatorLabelRenderingModel label = labelsModel.getLabel(i);
					Graphics g2 = this.indicatorView().getGraphics();
					g2.setFont(label.getFont());
					if(label.getPosition() == CardinalPosition.NORTH && multY>0)
					{
						transYN = g2.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.SOUTH&& multY>0)
					{
						transYS = g2.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.WEST && multX>0)
					{
						transXW = g2.getFontMetrics().stringWidth(label.getLabel());
					}
					if(label.getPosition() == CardinalPosition.EAST && multX>0)
					{
						transXE = g2.getFontMetrics().stringWidth(label.getLabel());
					}
				}
				transY+=transYN;
				transX+=transXW;
				
				trans.translate(transX, transY);
				
				ImageList imageList = blinkModel.getImageList().get(i).getRange(value.getValue()).imageList;
				if(timer.get(i).getDelay()!=imageList.getPeriod())
				{
					timer.get(i).stop();
					timer.get(i).setDelay(imageList.getPeriod());
					timer.get(i).start();
				}
				if(((BlinkDrawer) timer.get(i).getActionListeners()[0]).isUpdate())
				{
					g.drawImage(imageList.getNext(), trans, null);
					((BlinkDrawer) timer.get(i).getActionListeners()[0]).setUpdate(false);
				}
				else
				{
					g.drawImage(imageList.getCurrent(), trans, null);
				}
				
				transY+=transYS;
				transX+=transXE;
				transX+=dimension.getWidth() * multX;
				transY+=dimension.getHeight() * multY;
				i++;
			}
			
		}
	}
	public void renderBorder(Graphics2D g) {
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
			IndicatorBorderRenderingModel borderModel = ((IndicatorBorderRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("border"));
			IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
			IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
			IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
			BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
			IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
			
			if(borderModel==null) return;
			
			Dimension dimension = null;
			
			if(blinkMultiModel != null)
				dimension=blinkMultiModel.getSize();
	
			if(dimension==null || borderModel.getBorderSize()==0) return;
			
			
			int multX=0;
			int multY=0;
			if(valueModel!=null)
				if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
					multY=1;
				else
					multX=1;
			
			int middle = dimension.width/2;
			int xw=0, xe=0, yn=0, ys=0;
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
							yn = g.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.SOUTH)
					{
						if(multX>0)
							ys = g.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.WEST)
					{
						if(multY>0)
							xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel());
					}
					if(label.getPosition() == CardinalPosition.EAST)
					{
						if(multY>0)
							xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel());
					}				
				}
				middle += xw;
			}
			
			g.setColor(borderModel.getBorderColor());
			g.setStroke(new BasicStroke(borderModel.getBorderSize()));
			int transX=xw, transY=yn;
			
			Color oldColor = g.getColor();
			g.setColor(borderModel.getBorderColor());
			Iterator<BoundedModel> it = valueModel.getIterator();
			int i=0;
			while(it.hasNext())
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
						transYN = g2.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.SOUTH&& multY>0)
					{
						transYS = g2.getFontMetrics().getHeight();
					}
					if(label.getPosition() == CardinalPosition.WEST&& multX>0)
					{
						transXW = g2.getFontMetrics().stringWidth(label.getLabel());
					}
					if(label.getPosition() == CardinalPosition.EAST && multX>0)
					{
						transXE = g2.getFontMetrics().stringWidth(label.getLabel());
					}
				}
				transY+=transYN;
				transX+=transXW;
				g.drawOval(transX,transY,dimension.getSize().width, dimension.getSize().height);
				transY+=transYS;
				transX+=transXE;
				it.next();
				transX+=dimension.getSize().getWidth() * multX;
				transY+=dimension.getSize().getHeight() * multY;
				i++;
			}
			g.setColor(oldColor);
	}
	public void renderLabel(Graphics2D g) {
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		
		if(labelsModel == null) return;
		
		Dimension dimension = new Dimension(0,0);
	
		if(blinkMultiModel != null)
			dimension = blinkMultiModel.getSize();
		
		int transY = 0, transX=0;
		int multX = 0;
		int multY = 0;
		
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
					yn = g.getFontMetrics().getHeight();
			}
			if(label.getPosition() == CardinalPosition.SOUTH)
			{
				if(multX>0)
					ys = g.getFontMetrics().getHeight();
			}
			if(label.getPosition() == CardinalPosition.WEST)
			{
				if(multY>0)
					xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel());
			}
			if(label.getPosition() == CardinalPosition.EAST)
			{
				if(multY>0)
					xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel());
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
				case NORTH : g.drawString(label.getLabel(), middle - strWidth/2 + transX, strHeight + transY);transY+=strHeight* multY; break;
				case SOUTH : g.drawString(label.getLabel(), middle - strWidth/2 + transX, transY + dimension.height + strHeight+ yn);transY+=strHeight * multY; break;
				case EAST : g.drawString(label.getLabel(), middle + dimension.width/2 + transX, yn+transY + dimension.height/2 + strHeight/2);transX+=strWidth * multX; break;
				case WEST : transX+=strWidth * multX; g.drawString(label.getLabel(), middle - dimension.width/2 - strWidth + transX, yn+transY + dimension.height/2 + strHeight/2);break;
			}
			transY+=dimension.getHeight()*multY;
			transX+=dimension.getWidth()*multX;
		}
	}
//	public Dimension getPreferredSize() {
//		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
//		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
//		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
//		IndicatorBlinkMultiRenderingModel blinkModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
//		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
//		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
//		
//		int multX=1;
//		int multY=1;
//		if(valueModel!=null)
//			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
//				multY=valueModel.getSize();
//			else
//				multX=valueModel.getSize();
//		
//		Dimension dimension = new Dimension(0,0);
//		
//		if(blinkModel != null)
//			dimension = new Dimension(multX * blinkModel.getSize().width, multY * blinkModel.getSize().height);
//		if(coloredRangeModel != null)
//			dimension = new Dimension(multX * coloredRangeModel.getSize().width, multY * coloredRangeModel.getSize().height);
//		if(colorModel != null)
//			dimension = new Dimension(multX * colorModel.getSize().width, multY * colorModel.getSize().height);
//		if(pictureModel != null)
//			dimension = new Dimension(multX * pictureModel.getOnImage().getWidth(), multY * pictureModel.getOnImage().getHeight());
//		
//		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
//		
//		if(labelModel == null) 
//			return dimension;
//		
//		Graphics g = this.indicatorView().getGraphics();
//		g.setFont(labelModel.getFont());
//		
//		if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
//			return new Dimension((int)Math.max(dimension.getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
//				(int)dimension.getHeight() +  g.getFontMetrics().getHeight() + 1);
//		
//		return new Dimension((int)dimension.getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
//				Math.max((int)dimension.getHeight(), g.getFontMetrics().getHeight() + 1));
//	}
	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		
		
		int nbX=1;
		int nbY=1;
		int multX = 0;
		int multY = 0;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=1;
			else
				multX=1;
		
		nbY=(valueModel.getSize() - 1)*multY + 1;
		nbX=(valueModel.getSize() - 1)*multX + 1;
		Dimension dimension = new Dimension(0,0);
		
		if(blinkModel != null)
			dimension = new Dimension(nbX * blinkModel.getSize().width, nbY * blinkModel.getSize().height);
		if(coloredRangeModel != null)
			dimension = new Dimension(nbX * coloredRangeModel.getSize().width, nbY * coloredRangeModel.getSize().height);
		if(colorModel != null)
			dimension = new Dimension(nbX * colorModel.getSize().width, nbY * colorModel.getSize().height);
		if(pictureModel != null)
			dimension = new Dimension(nbX * pictureModel.getOnImage().getWidth(), nbY * pictureModel.getOnImage().getHeight());
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
					yn+=g.getFontMetrics().getHeight() * multY;
					if(multX>0)
						yn = g.getFontMetrics().getHeight();
				}
				if(label.getPosition() == CardinalPosition.SOUTH)
				{
					ys+=g.getFontMetrics().getHeight() * multY;
					if(multX>0)
						ys = g.getFontMetrics().getHeight();
				}
				if(label.getPosition() == CardinalPosition.WEST)
				{
					if(multY>0)
						xw = (xw > g.getFontMetrics().stringWidth(label.getLabel())) ? xw : g.getFontMetrics().stringWidth(label.getLabel());
					if(multX>0)
						xw += g.getFontMetrics().stringWidth(label.getLabel());
				}
				if(label.getPosition() == CardinalPosition.EAST)
				{
					if(multY>0)
						xe = (xe > g.getFontMetrics().stringWidth(label.getLabel())) ? xe : g.getFontMetrics().stringWidth(label.getLabel());
					if(multX>0)
						xe += g.getFontMetrics().stringWidth(label.getLabel());
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
}
