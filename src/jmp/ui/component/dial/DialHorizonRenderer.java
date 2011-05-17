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
		BufferedImage horizonImage = renderingModel.getHorizonImage();
		final int max = ((DefaultRangeModel) model.getModel("pitch")).getMaximum();
		final int min = ((DefaultRangeModel) model.getModel("pitch")).getMinimum();
		final int majorTickSpacing = (int) renderingModel.getMajorTickSpacing();
		final int minorTickSpacing = (int) renderingModel.getMinorTickSpacing();
		final int majorLineXStart = (int) (renderingModel.getSize().getWidth() * renderingModel.getMajorGradutionRatio());
		final int majorLineXEnd = (int) (renderingModel.getSize().getWidth() - majorLineXStart);
		final int minorLineXStart = (int) (renderingModel.getSize().getWidth() * renderingModel.getMinorGradutionRatio());
		final int minorLineXEnd = (int) (renderingModel.getSize().getWidth() - minorLineXStart);
		int nbValues = (max-min)/minorTickSpacing;
		double pitchInterval = (renderingModel.getSize().getHeight() / (renderingModel.getPitchInterval()/minorTickSpacing));
		double graduationsInterval = horizonImage.getHeight() / nbValues;
		
		g2.translate(horizonImage.getWidth()/2, horizonImage.getHeight()/2);
		
		for (int i = 0; i < nbValues/2; i++)
		{
			g2.setStroke(renderingModel.getMinorGradutionStroke());
			g2.drawLine(minorLineXStart, (int) (i * pitchInterval), minorLineXEnd, (int) (i * pitchInterval));
			g2.drawLine(minorLineXStart, (int) -(i * pitchInterval), minorLineXEnd, (int) -(i * pitchInterval));
		}
		nbValues = (max-min)/majorTickSpacing;
		pitchInterval = (int) (renderingModel.getSize().getHeight() / (renderingModel.getPitchInterval()/majorTickSpacing));
		graduationsInterval = horizonImage.getHeight() / nbValues;

		for (int i = 0, value = 0; i <= nbValues/2; i++, value-=majorTickSpacing)
		{
			g2.setStroke(renderingModel.getMajorGradutionStroke());
			g2.drawLine(majorLineXStart, (int) (i * pitchInterval), majorLineXEnd, (int) (i * pitchInterval));
			
			final String vString = String.valueOf(value);
			final int strWidth = g2.getFontMetrics().stringWidth(vString);
			g2.drawString(vString, majorLineXStart - strWidth - renderingModel.getLabelSpace(), (int) (i * pitchInterval));
			g2.drawString(vString, majorLineXEnd + renderingModel.getLabelSpace(), (int) (i * pitchInterval));
			
			g2.drawLine(majorLineXStart, (int) -(i * pitchInterval), majorLineXEnd, (int) -(i * pitchInterval));
			final String vString2 = String.valueOf(-value);
			final int strWidth2 = g2.getFontMetrics().stringWidth(vString2);
			g2.drawString(vString2, majorLineXStart - strWidth2 - renderingModel.getLabelSpace(), (int) -(i * pitchInterval));
			g2.drawString(vString2, majorLineXEnd + renderingModel.getLabelSpace(), (int) -(i * pitchInterval));
		}
 	}
	public void renderDial(Graphics2D g)
	{
		DialHorizonRenderingModel renderingModel = ((DialHorizonRenderingModel) this.dialView().renderingModel());
		DefaultModelComposit model = ((DefaultModelComposit) this.dialView().getModel());
		
		if (renderingModel.getHorizonImage() == null || renderingModel.getPlaneImage()==null) return;
		final int max = ((DefaultRangeModel) model.getModel("pitch")).getMaximum();
		final int min = ((DefaultRangeModel) model.getModel("pitch")).getMinimum();
		final int value = ((DefaultBoundedModel) model.getModel("pitch")).getValue();

		Dimension size = renderingModel.getSize();
		double pitchInterval = (renderingModel.getSize().getHeight() / (renderingModel.getPitchInterval()));
		int deltaPitchTranslation = (int) (value*pitchInterval);
		//int deltaPitchTranslation = (int) (value*renderingModel.getHorizonImage().getHeight()/(max-min));
		
		int pitchTranslation = (int) (renderingModel.getHorizonImage().getHeight() /renderingModel.getBackgroundMultiplier());
		
		AffineTransform planeTransform = new AffineTransform();
		double tx = (size.getWidth()-renderingModel.getPlaneImage().getWidth())/2;
		double ty = (size.getHeight()-renderingModel.getPlaneImage().getHeight())/2;
		planeTransform.setToTranslation(tx,ty);
		
		AffineTransform horizonTransform = new AffineTransform();
		
		horizonTransform.setToIdentity();
		
		horizonTransform.translate(size.getWidth()/2, size.getHeight()/2);
		horizonTransform.rotate(Math.toRadians(((DefaultBoundedModel) model.getModel("roll")).getValue()));
		
		//horizonTransform.translate(-renderingModel.getHorizonImage().getWidth()/2, -(pitchTranslation)/2);
		horizonTransform.translate(-renderingModel.getHorizonImage().getWidth()/2, -renderingModel.getHorizonImage().getWidth()/2);
		//horizonTransform.translate(0, -(pitchTranslation)/2);
		horizonTransform.translate(-size.getWidth()/2, size.getWidth());
		
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