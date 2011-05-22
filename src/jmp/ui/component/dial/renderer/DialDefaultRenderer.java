package jmp.ui.component.dial.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.Model;
import jmp.ui.mvc.View;
import jmp.ui.utilities.JMSwingUtilities;

public class DialDefaultRenderer extends DefaultRenderer implements DialRenderer {
	
	private Arc2D.Double clip;
	private BufferedImage background;

	public DialDefaultRenderer(View view) {
		super(view);
	}
	protected DialView dialView()
	{
		return (DialView) this.getView();
	}
	public void renderTick(Graphics2D g) {
	}

	public void renderTicks(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(pictureModel==null|ticksModel==null||valueModel==null) return;
		
		if(background==null) return;
		
		final int majorTickSpacing = (int) ticksModel.getMajorTickSpacing();
		final int minorTickSpacing = (int) ticksModel.getMinorTickSpacing();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		final int minorTickSize = (int) ticksModel.getMinorTickSize();
		final int majorLineXStart = (int) (background.getWidth() - majorTickSize);
		final int majorLineXEnd = (int) (background.getWidth()-ticksModel.getMajorGraduationWidth());
		final int minorLineXStart = (int) (background.getWidth() - minorTickSize);
		final int minorLineXEnd = (int) (background.getWidth()-ticksModel.getMinorGraduationWidth());
		int nbValues = (valueModel.getMaximum()-valueModel.getMinimum())/minorTickSpacing;
		int minorTickAngleSpacing = 360 / nbValues;
		
		if(partialModel != null)
			minorTickAngleSpacing = (partialModel.getEndAngle()-partialModel.getStartAngle()) / nbValues;
		AffineTransform oldTrans = g.getTransform();
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.translate(background.getWidth()/2 +background.getMinX(), background.getHeight()/2+background.getMinY());
			if(renderingModel.getSense() == Rotation.Clockwise)
				trans.rotate(-Math.toRadians(i*minorTickAngleSpacing + renderingModel.getTicksStartAngle()));
			else
				trans.rotate(Math.toRadians(i*minorTickAngleSpacing - renderingModel.getTicksStartAngle()));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g2.transform(trans);
			g2.setColor(ticksModel.getMinorGraduationColor());
			g2.setStroke(ticksModel.getMinorGradutionStroke());
			g2.drawLine(minorLineXStart, background.getHeight()/2, minorLineXEnd, background.getHeight()/2);
			g2.setTransform(oldTrans);
		}
		
		nbValues = (valueModel.getMaximum()-valueModel.getMinimum())/majorTickSpacing;
		int majorTickAngleSpacing = 360 / nbValues;
		if(partialModel != null)
			majorTickAngleSpacing = (partialModel.getEndAngle()-partialModel.getStartAngle()) / nbValues;
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			if(renderingModel.getSense() == Rotation.Clockwise)
				trans.rotate(-Math.toRadians(i*majorTickAngleSpacing + renderingModel.getTicksStartAngle()));
			else
				trans.rotate(Math.toRadians(i*majorTickAngleSpacing - renderingModel.getTicksStartAngle()));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g2.transform(trans);
			g2.setColor(ticksModel.getMajorGraduationColor());
			g2.setStroke(ticksModel.getMinorGradutionStroke());
			g2.drawLine(majorLineXStart, background.getHeight()/2, majorLineXEnd, background.getHeight()/2);
			g2.setTransform(oldTrans);
		}
		g2.dispose();
	}

	public void renderLabel(Graphics2D g) {
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		if(labelModel == null || pictureModel==null) return;
		
		if(background==null) return;
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());		
		g2.setColor(labelModel.getColor());
		g2.setFont(labelModel.getFont());
		final int strWidth = g2.getFontMetrics().stringWidth(labelModel.getLabel());
		g2.drawString(labelModel.getLabel(), background.getWidth()/2 - strWidth/2, background.getHeight()/3);
		g2.dispose();
	}

	public void renderLabels(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(pictureModel==null || valueModel==null || renderingModel==null||ticksModel==null) return;
		
		if(background==null) return;
			
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());
		
		
		g2.setColor(ticksModel.getLabelColor());
		final int majorTickSpacing = (int) ticksModel.getMajorTickSpacing();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		int nbValues = (valueModel.getMaximum()-valueModel.getMinimum())/majorTickSpacing;
		
		int majorTickAngleSpacing = 360 / nbValues;
		AffineTransform oldTrans = g2.getTransform();
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			final String vString = String.valueOf(i*majorTickSpacing);
			g2.transform(trans);
			
			Point textCorner;
			if(renderingModel.getSense() == Rotation.Clockwise)
				textCorner = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle())))-5,
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle())))-5);
			else
				textCorner = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
			
			AffineTransform trans2 = new AffineTransform();
			Rectangle2D cadre = g.getFontMetrics().getStringBounds(vString, g).getBounds2D();
			trans2.translate(textCorner.getX()-cadre.getCenterX(), textCorner.getY()-cadre.getCenterY());
			g2.transform(trans2);
			
			int deltaX=0;
			int deltaY=0;			
			
			if(renderingModel.getSense() == Rotation.Clockwise)
			{
				deltaX=(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
						Math.cos(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
				deltaY=(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
						Math.sin(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			}
			else
			{
				deltaX=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
					Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
				deltaY=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
					Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			}
			
			g2.drawString(vString, deltaX, deltaY);
			g2.setTransform(oldTrans);
		}
		g2.dispose();
	}

	public void renderTrack(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialTrackRenderingModel trackModel = ((DialTrackRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("track"));
		if(pictureModel==null || ticksModel==null || trackModel==null) return;

		if(background==null || trackModel.getTrackSize()==0) return;
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());
		g2.setColor(trackModel.getTrackColor());
		g2.setStroke(new BasicStroke(trackModel.getTrackSize()));
		Shape border = new Ellipse2D.Double(ticksModel.getMinorTickSize()/2, ticksModel.getMinorTickSize()/2,
				background.getWidth() - ticksModel.getMinorTickSize(),
				background.getHeight()- ticksModel.getMinorTickSize());
		g2.draw(border);
		g2.dispose();
	}

	public void renderNeedle(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(pictureModel == null || renderingModel == null || valueModel==null) return;
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if (needle == null || background == null) return;
		
		int Angle=0;
		if(valueModel.getValue() != 0)
			Angle = valueModel.getValue()*360/(valueModel.getMaximum()-valueModel.getMinimum());
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		
		if(partialModel != null)
		{
			if(valueModel.getValue() != 0)
				Angle = valueModel.getValue()*(partialModel.getEndAngle()-partialModel.getStartAngle())/(valueModel.getMaximum()-valueModel.getMinimum());
			if(valueModel.getValue() <= valueModel.getMinimum())
				valueModel.setValue(valueModel.getMinimum());
			if(valueModel.getValue() > valueModel.getMaximum())
				valueModel.setValue(valueModel.getMaximum());
		}
		
		if(renderingModel.getSense() == Rotation.Clockwise)
			trans.rotate(Math.toRadians(Angle - renderingModel.getTicksStartAngle()));
		else
			trans.rotate(-Math.toRadians(Angle + renderingModel.getTicksStartAngle()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);
		
		g.drawImage(needle,trans,null);
	}

	public void renderBackground(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialTrackRenderingModel trackModel = ((DialTrackRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("track"));
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BufferedImage needle = pictureModel.getNeedle();
		if(pictureModel == null || renderingModel==null) return;

		if(renderingModel.isChanged() || pictureModel.isChanged())
			generateBackground(g);
		if(partialModel!=null && partialModel.isChanged())
			generateBackground(g);
		if(ticksModel!=null && ticksModel.isChanged())
			generateBackground(g);
		if(trackModel!=null && trackModel.isChanged())
			generateBackground(g);
		if(labelModel!=null && labelModel.isChanged())
			generateBackground(g);
		renderingModel.setChanged(false);
		
		if(partialModel != null)
		{
			clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
			AffineTransform trans = new AffineTransform();		
			trans.setToIdentity();
			double transX = 0;
			double transY = 0;
	
			if(clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight())
				transX = needle.getHeight();
			if(clip.getBounds2D().getMinY() > clip.getCenterY() - needle.getHeight())
				transY = needle.getHeight();
			trans.translate(-clip.getBounds2D().getMinX() + transX, -clip.getBounds2D().getMinY() + transY);

			g.transform(trans);
			g.clip(clip);
			g.drawImage(this.background,0,0,null);
			g.setClip(null);
		}
		else
			g.drawImage(this.background,0,0,null);
	}

	private void generateBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		BufferedImage background = pictureModel.getBackground();
		if(background == null) return;
		this.background= JMSwingUtilities.copyBufferedImage(background);
		
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialTrackRenderingModel trackModel = ((DialTrackRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("track"));
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		this.renderTrack(g);
		this.renderTicks(g);
		this.renderLabels(g);
		this.renderLabel(g);
		if(pictureModel!=null)
			pictureModel.setChanged(false);
		if(partialModel!=null)
			partialModel.setChanged(false);
		if(ticksModel!=null)
			ticksModel.setChanged(false);
		if(trackModel!=null)
			trackModel.setChanged(false);
		if(labelModel!=null)
			labelModel.setChanged(false);
	}
	
	public void renderBorder(Graphics2D g) {
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		if(borderModel==null || pictureModel==null) return;
		
		BufferedImage background = pictureModel.getBackground();
		if(borderModel==null || borderModel.getBorderSize()==0) return;
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,
				background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
		g.draw(border);
	}

	public void renderDial(Graphics2D g) {
		this.renderBackground(g);
		this.renderNeedle(g);
		this.renderBorder(g);
	}
	public void renderView(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		this.renderDial(g);
	}

	public void setSize(Dimension size) {
	}

	public Dimension getPreferredSize() {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		
		if(background == null) return null;
		
		if(partialModel == null)
			return new Dimension(background.getWidth(), background.getHeight());
		
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
		double transX = 0;
		double transY = 0;

		if((clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight()) || (clip.getBounds2D().getMaxX() < clip.getCenterX() + needle.getHeight()))
			transX = needle.getHeight();
		if((clip.getBounds2D().getMinY() > clip.getCenterY() -  needle.getHeight()) || (clip.getBounds2D().getMaxY() < clip.getCenterY() + needle.getHeight()))
			transY = needle.getHeight();
		return new Dimension((int)(clip.getBounds2D().getWidth() + transX), (int)(clip.getBounds2D().getHeight() + transY));
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
}
