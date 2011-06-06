package jmp.ui.component.indicator.renderer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class IndicatorDefaultRenderer extends DefaultRenderer implements IndicatorRenderer {

	public IndicatorDefaultRenderer(View view) {
		super(view);
	}

	public void renderView(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		renderIndicator(g);
	}
	public void renderIndicator(Graphics2D g) {
		renderState(g);
		renderLabel(g);
		renderBorder(g);
	}
	
	public IndicatorView indicatorView()
	{
		return (IndicatorView) this.getView();
	}
	public void renderState(Graphics2D g) {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		BooleanModel valueModel = ((BooleanModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		
		if(pictureModel != null && renderingModel!=null)
		{
			BufferedImage stateImage;
			if(valueModel.is())
				stateImage = pictureModel.getOnImage();
			else
				stateImage = pictureModel.getOffImage();
			
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			if(labelModel != null) 
			{		
				switch (labelModel.getPosition())
				{
				case NORTH : g.drawImage(stateImage, (int) (getPreferredSize().getWidth()/2 - stateImage.getWidth()/2), (int) (getPreferredSize().getHeight() - stateImage.getHeight()), null); break;
				case SOUTH : g.drawImage(stateImage, (int) (getPreferredSize().getWidth()/2 - stateImage.getWidth()/2), 0, null); break;
				case EAST : g.drawImage(stateImage, 0, (int) getPreferredSize().getHeight()/2 - stateImage.getHeight()/2, null); break;
				case WEST : g.drawImage(stateImage, (int)getPreferredSize().getWidth() - stateImage.getWidth(), (int) getPreferredSize().getHeight()/2 - stateImage.getHeight()/2, null); break;
				}
			}
			else 
				g.drawImage(stateImage,0,0,null);
			return;
		}
		
		if(colorModel != null && renderingModel!=null)
		{
			Color oldColor = g.getColor();
			RadialGradientPaint p;
			if(valueModel.is())
			{
				g.setColor(colorModel.getOnColor());
				p = new RadialGradientPaint(new Point2D.Double(getPreferredSize().getWidth() / 2.0,
		                getPreferredSize().getHeight() / 2.0), (float) (getPreferredSize().getWidth() / 2.0f),
		                new float[] { 0.0f, 1.0f },
		                new Color[] { new Color(255, 255, 255), colorModel.getOnColor()});
			}
			else
			{
				g.setColor(colorModel.getOffColor());
				p = new RadialGradientPaint(new Point2D.Double(getPreferredSize().getWidth() / 2.0,
		                getPreferredSize().getHeight() / 2.0), (float) (getPreferredSize().getWidth() / 2.0f),
		                new float[] { 0.0f, 1.0f },
		                new Color[] { new Color(255, 255, 255), colorModel.getOffColor()});
			}
			
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			if(labelModel != null) 
			{		
				
		        g.setPaint(p);
				switch (labelModel.getPosition())
				{
				case NORTH : g.fillOval((int) (getPreferredSize().getWidth()/2 - colorModel.getSize().getWidth()/2), (int) (getPreferredSize().getHeight() - colorModel.getSize().getHeight()), (int) colorModel.getSize().getWidth(), (int)colorModel.getSize().getHeight()); break;
				case SOUTH : g.fillOval((int) (getPreferredSize().getWidth()/2 - colorModel.getSize().getWidth()/2), 0, (int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight()); break;
				case EAST : g.fillOval(0, (int) (getPreferredSize().getHeight()/2 - colorModel.getSize().getHeight()/2), (int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight()); break;
				case WEST : g.fillOval((int)(getPreferredSize().getWidth() - colorModel.getSize().getWidth()), (int) (getPreferredSize().getHeight()/2 - colorModel.getSize().getHeight()/2), (int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight()); break;
				}
			}
			else 
				g.fillOval(0,0,(int)colorModel.getSize().getWidth(), (int) colorModel.getSize().getHeight());
			g.setColor(oldColor);
		}
	}

	public void renderLabel(Graphics2D g) {
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		
		if(labelModel == null) return;
			
		g.setColor(labelModel.getColor());
		g.setFont(labelModel.getFont());
		final int strHeight = g.getFontMetrics().getHeight();
		final int strWidth = g.getFontMetrics().stringWidth(labelModel.getLabel());
		switch (labelModel.getPosition())
		{
			case NORTH : g.drawString(labelModel.getLabel(), (int) getPreferredSize().getWidth()/2 - strWidth/2, strHeight); break;
			case SOUTH : g.drawString(labelModel.getLabel(), (int) getPreferredSize().getWidth()/2 - strWidth/2, (int) getPreferredSize().getHeight()); break;
			case EAST : g.drawString(labelModel.getLabel(), (int) getPreferredSize().getWidth() - strWidth, (int) getPreferredSize().getHeight()/2 + strHeight/2); break;
			case WEST : g.drawString(labelModel.getLabel(), 0, (int) getPreferredSize().getHeight()/2 + strHeight/2); break;
		}

	}

	public void renderBorder(Graphics2D g) {
		IndicatorBorderRenderingModel borderModel = ((IndicatorBorderRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("border"));
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		IndicatorBlinkMultiRenderingModel blinkMultiModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		
		if(borderModel==null) return;
		
		Dimension dimension = null;
		if(coloredRangeModel != null)
			dimension = coloredRangeModel.getSize();
		if(blinkMultiModel != null)
			dimension=blinkMultiModel.getSize();
		if(blinkModel != null)
			dimension=blinkModel.getSize();
		if(colorModel != null)
			dimension=colorModel.getSize();
		
		if(pictureModel!=null)
			dimension= new Dimension(pictureModel.getOnImage().getWidth(),pictureModel.getOnImage().getHeight());

		if(dimension==null || borderModel.getBorderSize()==0) return;
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		int transX=0, transY=0;
		if(labelModel != null) 
		{		
			switch (labelModel.getPosition())
			{
				case NORTH :transY=(int) (getPreferredSize().getHeight()-dimension.getHeight());
				transX=(int) (getPreferredSize().getWidth()-dimension.getWidth())/2;
				break;
				case SOUTH : transX=(int) (getPreferredSize().getWidth()-dimension.getWidth())/2; break;
				case EAST : ; break;
				case WEST : transX=(int) (getPreferredSize().getWidth()-dimension.getWidth()); break;
			}
		}
		Double border = new Ellipse2D.Double(borderModel.getBorderSize()/2 + transX, borderModel.getBorderSize()/2 + transY,
				dimension.getWidth()-borderModel.getBorderSize(), dimension.getHeight()-borderModel.getBorderSize());
		g.draw(border);
	}

	public void setSize(Dimension size) {

	}

	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		Dimension dimension = new Dimension(0,0);
		
		if(blinkModel != null)
			dimension = blinkModel.getSize();
		if(coloredRangeModel != null)
			dimension = coloredRangeModel.getSize();
		if(colorModel != null)
			dimension = colorModel.getSize();
		if(pictureModel != null)
			dimension = new Dimension(pictureModel.getOnImage().getWidth(), pictureModel.getOnImage().getHeight());
		
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

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
