package jmp.ui.component.indicator.renderer;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
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
	protected IndicatorView indicatorView()
	{
		return (IndicatorView) this.getView();
	}
	public void renderState(Graphics2D g) {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		BooleanModel valueModel = ((BooleanModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		if(pictureModel == null || renderingModel==null) return;
		
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
		
		g.dispose();

	}

	public void renderBorder(Graphics2D g) {
		IndicatorBorderRenderingModel borderModel = ((IndicatorBorderRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("border"));
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(borderModel==null || pictureModel==null) return;
		
		BufferedImage onImage = pictureModel.getOnImage();
		if(borderModel==null || borderModel.getBorderSize()==0) return;
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,
				onImage.getWidth()-borderModel.getBorderSize(), onImage.getHeight()-borderModel.getBorderSize());
		g.draw(border);
	}

	public void renderIndicator(Graphics2D g) {
		renderState(g);
		renderLabel(g);
		renderBorder(g);
	}

	public void setSize(Dimension size) {

	}

	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel == null) return new Dimension(40,40);;
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		if(labelModel == null) 
			return new Dimension(pictureModel.getOnImage().getWidth() + 1, pictureModel.getOnImage().getHeight() + 1);
		Graphics2D g = pictureModel.getOnImage().createGraphics();
		g.setFont(labelModel.getFont());
		if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
			return new Dimension(Math.max(pictureModel.getOnImage().getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
				pictureModel.getOnImage().getHeight() +  g.getFontMetrics().getHeight() + 1);
		
		return new Dimension(pictureModel.getOnImage().getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
				Math.max(pictureModel.getOnImage().getHeight(), g.getFontMetrics().getHeight() + 1));
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
