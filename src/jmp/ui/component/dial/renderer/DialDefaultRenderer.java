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
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.Model;
import jmp.ui.mvc.View;
import jmp.ui.utilities.JMSwingUtilities;

public class DialDefaultRenderer extends DefaultRenderer implements DialRenderer {
	
	private Arc2D.Double clip;

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
		DialRenderingModel renderingModel = dialView().renderingModel();
		BufferedImage background = ((DialPictureRenderingModel) pictureModel).getBackground();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(ticksModel==null) return;
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
		AffineTransform oldTrans = g.getTransform();
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.translate(background.getWidth()/2 +background.getMinX(), background.getHeight()/2+background.getMinY());
			//trans.rotate(Math.toRadians(i*minorTickSpacing));
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
		//nbValues = 360/majorTickSpacing;
		
		int majorTickAngleSpacing = 360 / nbValues;
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			//trans.rotate(-Math.toRadians(i*majorTickSpacing));
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
		if(labelModel == null || !pictureModel.isChanged()) return;
		
		BufferedImage background = pictureModel.getBackground();
		
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
		BufferedImage background = ((DialPictureRenderingModel) pictureModel).getBackground();
		if(ticksModel==null || !pictureModel.isChanged()) return;
			
		Graphics2D g2 = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g2.setRenderingHints(rh);
		
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
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
			
			Point lineEnd,lineStart = null, textCorner1;
			if(renderingModel.getSense() == Rotation.Clockwise)
			{
				lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))));
				lineStart = new Point((int)((background.getHeight()/2) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))));
				textCorner1 = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))));
				
			}
			else
			{
				lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				lineStart = new Point((int)((background.getHeight()/2) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				textCorner1 = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				
			}
			Ellipse2D point = new Ellipse2D.Double(lineEnd.getX()-1, lineEnd.getY()-1, 2, 2);
			Ellipse2D point2 = new Ellipse2D.Double(lineStart.getX()-2, lineStart.getY()-2, 4, 4);
			
			AffineTransform trans2 = new AffineTransform();
			Rectangle2D cadre = g.getFontMetrics().getStringBounds(vString, g).getBounds2D();
			trans2.translate(textCorner1.getX()-cadre.getCenterX(), textCorner1.getY()-cadre.getCenterY());
			g2.transform(trans2);
			
			int deltaX=0;
			int deltaY=0;
			
			if(trans2.getTranslateY()+cadre.getCenterY()>point2.getY())
				deltaY=-(int) (trans2.getTranslateY()+cadre.getMinY() - point.getY());
			if(trans2.getTranslateY()+cadre.getCenterY()<point2.getY())
				deltaY=-(int) (trans2.getTranslateY()+cadre.getMaxY() - point.getY());
			
			if(trans2.getTranslateX()+cadre.getCenterX()>point2.getX())
				deltaX=(int) (trans2.getTranslateX()+cadre.getMaxX() - point.getX());
			if(trans2.getTranslateX()+cadre.getCenterX()<point2.getX())
				deltaX=(int) (trans2.getTranslateX()+cadre.getMinX() - point.getX());
			
			deltaX=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth())*Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			deltaY=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth())*Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			g2.drawString(vString, deltaX, deltaY);
			AffineTransform trans3 = new AffineTransform();
			trans3.translate(deltaX, deltaY);
			g2.transform(trans3);
			g2.setTransform(oldTrans);
		}
		g2.dispose();
	}

	public void renderTrack(Graphics2D g) {
	}

	public void renderNeedle(Graphics2D g) {
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		BoundedModel valueModel = this.dialView().valueModel();
		if (needle == null || background == null) return;
		
		int Angle=0;
		if (partialModel != null)
		{
			if(valueModel.getValue() != 0)
				Angle = valueModel.getValue()*(partialModel.getEndAngle()-partialModel.getStartAngle())/(valueModel.getMaximum()-valueModel.getMinimum());
			if(valueModel.getValue() > partialModel.getEndAngle())
				valueModel.setValue(partialModel.getEndAngle());
			if(valueModel.getValue() < partialModel.getStartAngle())
				valueModel.setValue(partialModel.getStartAngle());
		} else {
			if(valueModel.getValue() != 0)
				Angle = valueModel.getValue()*360/(valueModel.getMaximum()-valueModel.getMinimum());
		}
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
	
		if (partialModel != null)
		{
			double transX = 0;
			double transY = 0;
			if(clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight())
				transX = needle.getHeight();
			if(clip.getBounds2D().getMinY() > clip.getCenterY() - needle.getHeight())
				transY = needle.getHeight();
			trans.translate(-clip.getBounds2D().getMinX() + transX, -clip.getBounds2D().getMinY() + transY);
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
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if (background == null) return;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();		
		
		if(partialModel == null)
			g.drawImage(background,0,0,null);
		else
		{
			clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
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
			g.drawImage(background,null,null);
			g.setClip(null);
			g.setTransform(oldTrans);
		}
		
	}

	public void renderBorder(Graphics2D g) {
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = ((DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial"));
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if(background == null || borderModel == null || borderModel.getBorderSize() == 0) return;;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();		
		
		if(partialModel == null  || needle == null)
			g.transform(trans);
		else
		{
			clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
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
		}
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
		g.draw(border);
		//g.setClip(null);
		g.setTransform(oldTrans);
		//borderModel.setChanged(false);
	}

	public void renderDial(Graphics2D g) {
		this.renderTrack(g);
		this.renderTicks(g);
		this.renderLabels(g);
		this.renderLabel(g);
		this.renderBackground(g);
		this.renderNeedle(g);
		this.renderBorder(g);
	}
	public void renderView(Graphics2D g) {
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		this.renderDial(g);
		//for(Model model : ((ModelComposit) (dialView().getModel())).models())
		//	((DialRenderingModel) model).setChanged(false);
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
		{
			clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), 0, 360, Arc2D.PIE);
			return new Dimension(background.getWidth(), background.getHeight());
		}
		
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
