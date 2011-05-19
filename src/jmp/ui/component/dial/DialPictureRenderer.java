package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

import jmp.ui.component.Rotation;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.JMSwingUtilities;

public class DialPictureRenderer extends DialDefaultRenderer {
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);

	public DialPictureRenderer(View view) {
		super(view);
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
	}
	
	public void renderNeedle(Graphics2D g)
	{
		DialPictureRenderingModel renderingModel = (DialPictureRenderingModel) this.dialView().renderingModel();
		BufferedImage background = ((DialPictureRenderingModel) renderingModel).getBackground();
		BufferedImage needle = ((DialPictureRenderingModel) renderingModel).getNeedle();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if (needle == null || background == null) return;
		int Angle=0;
		if(valueModel.getValue() != 0)
			Angle = valueModel.getValue()*360/(valueModel.getMaximum()-valueModel.getMinimum());
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		if(renderingModel.getSense() == Rotation.Clockwise)
			trans.rotate(Math.toRadians(Angle - renderingModel.getTicksStartAngle()));
		else
			trans.rotate(-Math.toRadians(Angle + renderingModel.getTicksStartAngle()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);
		g.drawImage(needle,trans,null);
	}
	public void renderBackground(Graphics2D g)
	{
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if (background == null) return;
		g.drawImage(background,0,0,null);
	}
	
	public void renderBorder(Graphics2D g)
	{
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if(borderModel==null || borderModel.getBorderSize()==0) return;
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
		g.draw(border);
	}
	public void renderTicks(Graphics2D g)
	{
		DialPictureRenderingModel renderingModel = (DialPictureRenderingModel) this.dialView().renderingModel();
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		BufferedImage background = ((DialPictureRenderingModel) renderingModel).getBackground();
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
		//int nbValues = 360/minorTickSpacing;
		int minorTickAngleSpacing = 360 / nbValues;
		AffineTransform oldTrans = g.getTransform();
		
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
			g.transform(trans);
			g.setColor(ticksModel.getMinorGraduationColor());
			g.setStroke(ticksModel.getMinorGradutionStroke());
			g.drawLine(minorLineXStart, background.getHeight()/2, minorLineXEnd, background.getHeight()/2);
			g.setTransform(oldTrans);
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
			g.transform(trans);
			g.setColor(ticksModel.getMajorGraduationColor());
			g.setStroke(ticksModel.getMinorGradutionStroke());
			g.drawLine(majorLineXStart, background.getHeight()/2, majorLineXEnd, background.getHeight()/2);
			g.setTransform(oldTrans);
		}
	}
	public void renderLabels(Graphics2D g)
	{
		DialPictureRenderingModel renderingModel = (DialPictureRenderingModel) this.dialView().renderingModel();
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		BufferedImage background = ((DialPictureRenderingModel) renderingModel).getBackground();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(ticksModel==null) return;
		g.setColor(ticksModel.getLabelColor());
		final int majorTickSpacing = (int) ticksModel.getMajorTickSpacing();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		
		int nbValues = (valueModel.getMaximum()-valueModel.getMinimum())/majorTickSpacing;
		//nbValues = 360/majorTickSpacing;
		
		int majorTickAngleSpacing = 360 / nbValues;
		
		AffineTransform oldTrans = g.getTransform();
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			/*trans.rotate(-Math.toRadians(i*majorTickSpacing));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);*/
			final String vString = String.valueOf(i*majorTickSpacing);
			//final String vString = String.valueOf(i*majorTickAngleSpacing);
			final int strWidth = g.getFontMetrics().stringWidth(vString);
			final int strHeight = g.getFontMetrics().getHeight();
			//g.getFontMetrics().getStringBounds(vString, g).getBounds2D().intersectsLine(l);
			g.transform(trans);
		//	trans.translate(0, -strWidth);
		//	trans.rotate(-Math.toRadians(i*majorTickSpacing));
			//g.setTransform(trans);
			
			//g.drawString(vString, background.getHeight() , (background.getHeight()/2));
			//g.drawString(vString, -strWidth/2+(int)((background.getHeight()/2- majorTickSize - strWidth/2) * Math.cos(-Math.toRadians(i*majorTickSpacing))),
			//		(int)((background.getHeight()/2- majorTickSize-strHeight/2) * Math.sin(-Math.toRadians(i*majorTickSpacing))));
			Double clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), 0, 360,Arc2D.PIE);
			Point lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing))),
					(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing))));
			Ellipse2D point = new Ellipse2D.Double(lineEnd.getX()-5, lineEnd.getY()-5, 10, 10);
			int transX = 0;
			int transY = 0;
			g.draw(point);
//			if(clip.getBounds2D().getMinX() > clip.getCenterX() || clip.getBounds2D().getMaxX() < clip.getCenterX())
//	        	transX = needle.getHeight();
//	        if(clip.getBounds2D().getMinY() > clip.getCenterY() || clip.getBounds2D().getMaxY() < clip.getCenterY())
//	        	transY = needle.getHeight();
			if(renderingModel.getSense() == Rotation.Clockwise)
				g.drawString(vString, -strWidth/2+(int)((background.getHeight()/2- majorTickSize - strWidth/2) * Math.cos(Math.toRadians(i*majorTickAngleSpacing - renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize-strHeight/2) * Math.sin(Math.toRadians(i*majorTickAngleSpacing - renderingModel.getTicksStartAngle()))));
			else
				g.drawString(vString, -strWidth/2+(int)((background.getHeight()/2- majorTickSize - strWidth/2) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing + renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize-strHeight/2) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing + renderingModel.getTicksStartAngle()))));
			g.setTransform(oldTrans);
		}
	}
	public void renderLabel(Graphics2D g)
	{
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if(labelModel == null) return;
		g.setColor(labelModel.getColor());
		g.setFont(labelModel.getFont());
		final int strWidth = g.getFontMetrics().stringWidth(labelModel.getLabel());
		g.drawString(labelModel.getLabel(), background.getWidth()/2 - strWidth/2, background.getHeight()/3);
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getHeight(),((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getWidth());
	}
}
