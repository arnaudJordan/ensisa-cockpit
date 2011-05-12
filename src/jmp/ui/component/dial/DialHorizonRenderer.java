package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultRangeModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.mvc.View;

public class DialHorizonRenderer extends DialDefaultRenderer 
{
	public DialHorizonRenderer(View view)
	{
		super(view);
	}
	public void renderView(Graphics2D g)
	{
		this.renderBackground(g);
		this.renderDial(g);
	}
	public void renderBackground(Graphics2D g)
	{
		DialHorizonRenderingModel renderingModel = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		BufferedImage HorizonImage = renderingModel.getHorizonImage();
		Graphics2D g2 = HorizonImage.createGraphics();

		// drawing sky
		g2.setColor(renderingModel.getSkyColor());
		g2.fillRect(0, 0, HorizonImage.getWidth(), HorizonImage.getHeight() / 2);
		// drawing ground
		g2.setColor(renderingModel.getGroundColor());
		g2.fillRect(0, HorizonImage.getWidth()/2, HorizonImage.getWidth(), HorizonImage.getHeight() /2);

		
		this.renderTicks(g2);
		g2.dispose();
	}
	public void renderTicks(Graphics2D g2)
	{
		DialHorizonRenderingModel renderingModel = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		g2.setColor(renderingModel.getGraduationColor());
		DefaultModelComposit model = ((DefaultModelComposit) this.dialView().getModel());
		final int max = ((DefaultRangeModel) model.getModel("value_x")).getMaximum();
		final int min = ((DefaultRangeModel) model.getModel("value_x")).getMinimum();
		final int majorTickSpacing = (int) renderingModel.getMajorTickSpacing();
		final int minorTickSpacing = (int) renderingModel.getMinorTickSpacing();
		Dimension size = renderingModel.getSize();
		final int majorLineXStart = (int) (size.getWidth() * renderingModel.getMajorGradutionRatio());
		final int majorLineXEnd = (int) (size.getWidth() - majorLineXStart);
		final int minorLineXStart = (int) (size.getWidth() * renderingModel.getMinorGradutionRatio());
		final int minorLineXEnd = (int) (size.getWidth() - minorLineXStart);
		
		int nbValues = (max-min)/minorTickSpacing;
		double graduationsInterval = size.getHeight() / nbValues;

		for (int i = 0; i < nbValues; i++)
		{
			g2.setStroke(renderingModel.getMinorGradutionStroke());
			g2.drawLine(minorLineXStart + (int)size.getWidth()/2, (int) (i * graduationsInterval + size.getHeight()/2), minorLineXEnd + (int)size.getWidth()/2, (int) (i * graduationsInterval + size.getHeight()/2));
		}

		nbValues = (max-min)/majorTickSpacing;
		graduationsInterval = size.getHeight() / nbValues;

		for (int i = 0, value = max; i <= nbValues; i++, value-=majorTickSpacing)
		{
			g2.setStroke(renderingModel.getMajorGradutionStroke());
			g2.drawLine(majorLineXStart + (int)size.getWidth()/2, (int) (i * graduationsInterval + size.getHeight()/2), majorLineXEnd + (int)size.getWidth()/2, (int) (i * graduationsInterval + size.getHeight()/2));
			
			final String vString = String.valueOf(value);
			final int strWidth = g2.getFontMetrics().stringWidth(vString);
			g2.drawString(vString, majorLineXStart + (int)size.getWidth()/2 - strWidth - renderingModel.getLabelSpace(), (int) (i * graduationsInterval + size.getHeight()/2));
			g2.drawString(vString, majorLineXEnd + (int)size.getWidth()/2 + renderingModel.getLabelSpace(), (int) (i * graduationsInterval + size.getHeight()/2));
		}
 	}
	public void renderDial(Graphics2D g)
	{
		DialHorizonRenderingModel renderingModel = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		DefaultModelComposit model = ((DefaultModelComposit) this.dialView().getModel());
		final int max = ((DefaultRangeModel) model.getModel("value_x")).getMaximum();
		final int min = ((DefaultRangeModel) model.getModel("value_x")).getMinimum();
		final int value = ((DefaultBoundedModel) model.getModel("value_x")).getValue();

		Dimension size = renderingModel.getSize();
		int deltaPitchTranslation = (int) (value*size.getHeight()/(max-min));
		int pitchTranslation = (int) (size.getHeight() /renderingModel.getBackgroundMultiplier());
		
		AffineTransform planeTransform = new AffineTransform();
		double tx = (size.getWidth()-renderingModel.getPlaneImage().getWidth())/2;
		double ty = (size.getHeight()-renderingModel.getPlaneImage().getHeight())/2;
		planeTransform.setToTranslation(tx,ty);
		
		AffineTransform horizonTransform = new AffineTransform();
		horizonTransform.setToIdentity();
		
		horizonTransform.translate(renderingModel.getBorderSize()/2, renderingModel.getBorderSize()/2);

		horizonTransform.translate(size.getWidth()/2, (pitchTranslation )/2);
		horizonTransform.rotate(Math.toRadians(((DefaultBoundedModel) model.getModel("value_rot")).getValue()));
		horizonTransform.translate(-size.getWidth()/2, -(pitchTranslation)/2);

		horizonTransform.translate(-size.getWidth()/2, 0);
		horizonTransform.translate(0, -pitchTranslation + deltaPitchTranslation);
	
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);	
		
		Dimension d = size;
		//g.setPaint(new GradientPaint(d.width/2, d.height/2, Color.gray,d.width, d.height, Color.black));
		g.setColor(Color.DARK_GRAY);
		g.fillOval(renderingModel.getBorderSize()/2,renderingModel.getBorderSize()/2,(int)d.getWidth(), (int)d.getHeight());
		//g.setColor(Color.DARK_GRAY);
		//g.drawOval(0,0,(int)d.getWidth(), (int)d.getHeight());

		Shape clip = new Ellipse2D.Double(renderingModel.getBorderSize()/2, renderingModel.getBorderSize()/2, d.getWidth(), d.getHeight());
		
		Shape oldClip = g.getClip();
		g.clip(clip);
		
		g.drawImage(renderingModel.getHorizonImage(), horizonTransform, null);
		g.drawImage(renderingModel.getPlaneImage(), planeTransform, null);
		
		g.setClip(oldClip);
		
		renderBorder(g);
	}
	
	public void renderBorder(Graphics2D g) {
		DialHorizonRenderingModel renderingModel = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		if(renderingModel.getBorderSize()==0) return;
		g.setColor(renderingModel.getBorderColor());
		g.setStroke(new BasicStroke(renderingModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(renderingModel.getBorderSize()/2, renderingModel.getBorderSize()/2,renderingModel.getSize().width, renderingModel.getSize().height);
		g.draw(border);
	}
	public Dimension getPreferredSize()
	{
		DialHorizonRenderingModel model = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		return new Dimension(model.getSize().width + model.getBorderSize()*2, model.getSize().height + model.getBorderSize()*2);
	}

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		return this.getPreferredSize();
	}
}