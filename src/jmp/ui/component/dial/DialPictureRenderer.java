package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
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
		if(ticksModel==null || !renderingModel.isChanged()) return;
			
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
			final int strWidth = g2.getFontMetrics().stringWidth(vString);
			final int strHeight = g2.getFontMetrics().getHeight();
			
			g2.transform(trans);
			
			Point lineEnd,lineStart = null, textCorner1, textCorner2 = null, textCorner3 = null, textCorner4 = null;
			if(renderingModel.getSense() == Rotation.Clockwise)
			{
				lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))),
					(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))));
				
				textCorner1 = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle())))-5,
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle())))-5);
				
			}
			else
			{
				lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				lineStart = new Point((int)((background.getHeight()/2) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				textCorner1 = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				textCorner2 = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						-strHeight/2 +(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				textCorner3 = new Point(strWidth +(int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				textCorner4 = new Point(strWidth +(int)((background.getHeight()/2- majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						-strHeight/2 +(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
				
			}
			Ellipse2D point = new Ellipse2D.Double(lineEnd.getX()-1, lineEnd.getY()-1, 2, 2);
			Ellipse2D point2 = new Ellipse2D.Double(lineStart.getX()-2, lineStart.getY()-2, 4, 4);
			Ellipse2D textPoint1 = new Ellipse2D.Double(textCorner1.getX()-1, textCorner1.getY()-1, 2, 2);
			Ellipse2D textPoint2 = new Ellipse2D.Double(textCorner2.getX()-1, textCorner2.getY()-1, 2, 2);
			Ellipse2D textPoint3 = new Ellipse2D.Double(textCorner3.getX()-1, textCorner3.getY()-1, 2, 2);
			Ellipse2D textPoint4 = new Ellipse2D.Double(textCorner4.getX()-1, textCorner4.getY()-1, 2, 2);
			//g2.draw(point);g2.draw(point2);
			//g2.draw(textPoint1);
			//g2.draw(textPoint2);
			//g2.draw(textPoint3);
			//g2.draw(textPoint4);
			
			AffineTransform trans2 = new AffineTransform();
			Rectangle2D cadre = g.getFontMetrics().getStringBounds(vString, g).getBounds2D();
			trans2.translate(textCorner1.getX()-cadre.getCenterX(), textCorner1.getY()-cadre.getCenterY());
			g2.transform(trans2);
			
			//g2.draw(cadre);
			//g2.transform(trans);
			
			int deltaX=0;
			int deltaY=0;
			
			System.out.println("--------------");
			System.out.println("Valeur : " + vString);
			System.out.println("transX : " + trans2.getTranslateX());
			System.out.println("transY : " + trans2.getTranslateY());
			System.out.println("cadre.getCenterY() : " + cadre.getCenterY());
			System.out.println("cadre.getCenterX() : " + cadre.getCenterX());
			System.out.println("cadre.getMinY() : " + cadre.getMinY());
			System.out.println("cadre.getMinX() : " + cadre.getMinX());
			System.out.println("cadre.getMaxY() : " + cadre.getMaxY());
			System.out.println("cadre.getMaxX() : " + cadre.getMaxX());
			System.out.println("point.getY() : " + point.getY());
			System.out.println("point.getX() : " + point.getX());
			System.out.println("fiffY : " + (trans2.getTranslateY()-cadre.getMaxY()));
			System.out.println("angle : " + i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle());
			System.out.println("cos : " + Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			System.out.println("cos : " + Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			System.out.println("diag : " + Math.sqrt(cadre.getHeight()+cadre.getWidth()));
			
			if(trans2.getTranslateY()+cadre.getCenterY()>point2.getY())
				deltaY=-(int) (trans2.getTranslateY()+cadre.getMinY() - point.getY());
			if(trans2.getTranslateY()+cadre.getCenterY()<point2.getY())
				deltaY=-(int) (trans2.getTranslateY()+cadre.getMaxY() - point.getY());
			
			if(trans2.getTranslateX()+cadre.getCenterX()>point2.getX())
				deltaX=(int) (trans2.getTranslateX()+cadre.getMaxX() - point.getX());
			if(trans2.getTranslateX()+cadre.getCenterX()<point2.getX())
				deltaX=(int) (trans2.getTranslateX()+cadre.getMinX() - point.getX());
			
			deltaX=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			deltaY=-(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
			g2.drawString(vString, deltaX, deltaY);
			AffineTransform trans3 = new AffineTransform();
			trans3.translate(deltaX, deltaY);
			g2.transform(trans3);
			//g2.draw(cadre);
//			if(renderingModel.getSense() == Rotation.Clockwise)
//				g2.drawString(vString, (int)((background.getHeight()/2 - majorTickSize - strWidth/2) * Math.cos(Math.toRadians(i*majorTickAngleSpacing - renderingModel.getTicksStartAngle()))),
//						(int)((background.getHeight()/2- majorTickSize-strHeight/2) * Math.sin(Math.toRadians(i*majorTickAngleSpacing - renderingModel.getTicksStartAngle()))));
//			else{
//				g2.drawString(vString, deltaX + (int)((background.getHeight()/2 - majorTickSize) * Math.cos(-Math.toRadians(i*majorTickAngleSpacing + renderingModel.getTicksStartAngle()))),
//						(int)((background.getHeight()/2- majorTickSize) * Math.sin(-Math.toRadians(i*majorTickAngleSpacing + renderingModel.getTicksStartAngle()))));
//				System.out.println("fesfdsfsfdsfds");}
			g2.setTransform(oldTrans);
		}
		g2.dispose();
	}
	
	public void renderLabel(Graphics2D g)
	{
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPictureRenderingModel renderingModel = (DialPictureRenderingModel) this.dialView().renderingModel();
		if(labelModel == null || !renderingModel.isChanged()) return;
		
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		
		Graphics2D g2 = background.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g2.setRenderingHints(rh);
		
		g2.setColor(labelModel.getColor());
		g2.setFont(labelModel.getFont());
		final int strWidth = g2.getFontMetrics().stringWidth(labelModel.getLabel());
		g2.drawString(labelModel.getLabel(), background.getWidth()/2 - strWidth/2, background.getHeight()/3);
		g2.dispose();
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getHeight(),((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getWidth());
	}
}
