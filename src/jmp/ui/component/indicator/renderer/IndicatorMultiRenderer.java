package jmp.ui.component.indicator.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
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
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class IndicatorMultiRenderer extends IndicatorDefaultRenderer {

	public IndicatorMultiRenderer(View view) {
		super(view);
	}
	public void renderState(Graphics2D g) {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		BooleanModels valueModel = ((BooleanModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		
		int multX=0;
		int multY=0;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=1;
			else
				multX=1;
		
		if(pictureModel != null)
		{
			Iterator<BooleanModel> it = valueModel.getIterator();
			int transX = 0;
			int transY = 0;
			while(it.hasNext())
			{
				BooleanModel value = it.next();
				BufferedImage stateImage;
				if(value.is())
					stateImage = pictureModel.getOnImage();
				else
					stateImage = pictureModel.getOffImage();
				g.drawImage(stateImage,transX,transY,null);
				transX+=stateImage.getWidth() * multX;
				transY+=stateImage.getHeight() * multY;
			}
			return;
		}
		
		if(colorModel != null)
		{
			Color oldColor = g.getColor();
			Iterator<BooleanModel> it = valueModel.getIterator();
			int transX = 0;
			int transY = 0;
			while(it.hasNext())
			{
				BooleanModel value = it.next();
				RadialGradientPaint p;
				if(value.is())
				{
					g.setColor(colorModel.getOnColor());
					p = new RadialGradientPaint(new Point2D.Double(transX +colorModel.getSize().getWidth()/ 2.0,
			                transY +colorModel.getSize().getHeight()/ 2.0), (float) (colorModel.getSize().getWidth() / 2.0f),
			                new float[] { 0.0f, 1.0f },
			                new Color[] { new Color(255, 255, 255), colorModel.getOnColor()});
				}
				else
				{
					g.setColor(colorModel.getOffColor());
					p = new RadialGradientPaint(new Point2D.Double(transX +colorModel.getSize().getWidth()/ 2.0,
			                transY +colorModel.getSize().getHeight()/ 2.0), (float) (colorModel.getSize().getWidth() / 2.0f),
			                new float[] { 0.0f, 1.0f },
			                new Color[] { new Color(255, 255, 255), colorModel.getOffColor()});
				}
				g.setPaint(p);
				g.fillOval(transX,transY,(int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight());
				transX+=colorModel.getSize().getWidth() * multX;
				transY+=colorModel.getSize().getHeight() * multY;
			}
			g.setColor(oldColor);
		}
	}
	
	
	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		BooleanModels valueModel = ((BooleanModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		
		int multX=1;
		int multY=1;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=valueModel.getSize();
			else
				multX=valueModel.getSize();
		
		Dimension dimension = new Dimension(0,0);
		
		if(blinkModel != null)
			dimension = new Dimension(multX * blinkModel.getSize().width, multY * blinkModel.getSize().height);
		if(coloredRangeModel != null)
			dimension = new Dimension(multX * coloredRangeModel.getSize().width, multY * coloredRangeModel.getSize().height);
		if(colorModel != null)
			dimension = new Dimension(multX * colorModel.getSize().width, multY * colorModel.getSize().height);
		if(pictureModel != null)
			dimension = new Dimension(multX * pictureModel.getOnImage().getWidth(), multY * pictureModel.getOnImage().getHeight());
		
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		
		if(labelModel == null) 
			return dimension;
		
		Graphics g = this.indicatorView().getGraphics();
		g.setFont(labelModel.getFont());
		
		if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
			return new Dimension((int)Math.max(dimension.getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
				(int)dimension.getHeight() +  g.getFontMetrics().getHeight() + 1);
		
		return new Dimension((int)dimension.getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
				Math.max((int)dimension.getHeight(), g.getFontMetrics().getHeight() + 1));
	}
	public void renderBorder(Graphics2D g) {
			IndicatorBorderRenderingModel borderModel = ((IndicatorBorderRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("border"));
			IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
			IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
			IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
			BooleanModels valueModel = ((BooleanModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
			IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
			
			if(borderModel==null) return;
			
			Dimension dimension = null;
			if(coloredRangeModel != null)
				dimension = coloredRangeModel.getSize();
			
			if(blinkModel != null)
				dimension=blinkModel.getSize();
			if(colorModel != null)
				dimension=colorModel.getSize();
			if(pictureModel!=null)
				dimension= new Dimension(pictureModel.getOnImage().getWidth(),pictureModel.getOnImage().getHeight());
	
			if(dimension==null || borderModel.getBorderSize()==0) return;
			
			
			int multX=0;
			int multY=0;
			if(valueModel!=null)
				if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
					multY=1;
				else
					multX=1;
			
			g.setColor(borderModel.getBorderColor());
			g.setStroke(new BasicStroke(borderModel.getBorderSize()));
			int transX=0, transY=0;
			
			Color oldColor = g.getColor();
			g.setColor(borderModel.getBorderColor());
			Iterator<BooleanModel> it = valueModel.getIterator();
			while(it.hasNext())
			{
				g.drawOval(transX,transY,(int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight());
				it.next();
				transX+=colorModel.getSize().getWidth() * multX;
				transY+=colorModel.getSize().getHeight() * multY;
			}
			g.setColor(oldColor);
	}
}
