package jmp.ui.component.dial.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.JMSwingUtilities;

public class DialPictureRenderer extends DialDefaultRenderer {
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);
	private BufferedImage background;

	public DialPictureRenderer(View view) {
		super(view);
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
	}
	public void renderDial(Graphics2D g) {
		this.renderBackground(g);
		this.renderNeedle(g);
		this.renderBorder(g);
	}
	public void renderNeedle(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
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
		if(renderingModel.getSense() == Rotation.Clockwise)
			trans.rotate(Math.toRadians(Angle - renderingModel.getTicksStartAngle()));
		else
			trans.rotate(-Math.toRadians(Angle + renderingModel.getTicksStartAngle()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);
		
		g.drawImage(needle,trans,null);
		
	}
	public void renderBackground(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialTrackRenderingModel trackModel = ((DialTrackRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("track"));
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		if(pictureModel == null || renderingModel==null) return;

		if(renderingModel.isChanged() || pictureModel.isChanged())
			generateBackground(g);
		if(ticksModel!=null&&ticksModel.isChanged())
			generateBackground(g);
		if(trackModel!=null&&trackModel.isChanged())
			generateBackground(g);
		if(labelModel!=null&&labelModel.isChanged())
			generateBackground(g);
		renderingModel.setChanged(false);
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
		this.renderTrack(g);
		this.renderTicks(g);
		this.renderLabels(g);
		this.renderLabel(g);
		if(pictureModel!=null)
			pictureModel.setChanged(false);
		if(ticksModel!=null)
			ticksModel.setChanged(false);
		if(trackModel!=null)
			trackModel.setChanged(false);
		if(labelModel!=null)
			labelModel.setChanged(false);
	}
	public void renderBorder(Graphics2D g)
	{
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
	public void renderTrack(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialTrackRenderingModel trackModel = ((DialTrackRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("track"));
		if(pictureModel==null|ticksModel==null||trackModel==null) return;
		//BufferedImage background = pictureModel.getBackground();
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
	public void renderTicks(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(pictureModel==null|ticksModel==null||valueModel==null) return;
		
		//BufferedImage background = pictureModel.getBackground();
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
		AffineTransform oldTrans = g.getTransform();
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.translate(background.getWidth()/2 +background.getMinX(), background.getHeight()/2+background.getMinY());
			if(pictureModel.getSense() == Rotation.Clockwise)
				trans.rotate(-Math.toRadians(i*minorTickAngleSpacing + pictureModel.getTicksStartAngle()));
			else
				trans.rotate(Math.toRadians(i*minorTickAngleSpacing - pictureModel.getTicksStartAngle()));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g2.transform(trans);
			g2.setColor(ticksModel.getMinorGraduationColor());
			g2.setStroke(ticksModel.getMinorGradutionStroke());
			g2.drawLine(minorLineXStart, background.getHeight()/2, minorLineXEnd, background.getHeight()/2);
			g2.setTransform(oldTrans);
		}
		
		nbValues = (valueModel.getMaximum()-valueModel.getMinimum())/majorTickSpacing;
		int majorTickAngleSpacing = 360 / nbValues;
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			if(pictureModel.getSense() == Rotation.Clockwise)
				trans.rotate(-Math.toRadians(i*majorTickAngleSpacing + pictureModel.getTicksStartAngle()));
			else
				trans.rotate(Math.toRadians(i*majorTickAngleSpacing - pictureModel.getTicksStartAngle()));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g2.transform(trans);
			g2.setColor(ticksModel.getMajorGraduationColor());
			g2.setStroke(ticksModel.getMinorGradutionStroke());
			g2.drawLine(majorLineXStart, background.getHeight()/2, majorLineXEnd, background.getHeight()/2);
			g2.setTransform(oldTrans);
		}
		g2.dispose();
	}
	public void renderLabels(Graphics2D g)
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		DialRenderingModel renderingModel = dialView().renderingModel();
		BoundedModel valueModel = ((BoundedModel) this.dialView().valueModel());
		if(pictureModel==null || valueModel==null || renderingModel==null||ticksModel==null) return;
		
		//BufferedImage background = pictureModel.getBackground();
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
			
			Point lineEnd,lineStart, textCorner1;
			if(renderingModel.getSense() == Rotation.Clockwise)
			{
				lineEnd = new Point((int)((background.getHeight()/2- majorTickSize) * Math.cos(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))),
					(int)((background.getHeight()/2- majorTickSize) * Math.sin(Math.toRadians(i*majorTickAngleSpacing- renderingModel.getTicksStartAngle()))));
				lineStart = new Point((int)((background.getHeight()/2) * Math.cos(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))),
						(int)((background.getHeight()/2) * Math.sin(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle()))));
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
				
			}
			
			AffineTransform trans2 = new AffineTransform();
			Rectangle2D cadre = g.getFontMetrics().getStringBounds(vString, g).getBounds2D();
			trans2.translate(textCorner1.getX()-cadre.getCenterX(), textCorner1.getY()-cadre.getCenterY());
			g2.transform(trans2);
			
			int deltaX=0;
			int deltaY=0;
			
//			System.out.println("--------------");
//			System.out.println("Valeur : " + vString);
//			System.out.println("transX : " + trans2.getTranslateX());
//			System.out.println("transY : " + trans2.getTranslateY());
//			System.out.println("cadre.getCenterY() : " + cadre.getCenterY());
//			System.out.println("cadre.getCenterX() : " + cadre.getCenterX());
//			System.out.println("cadre.getMinY() : " + cadre.getMinY());
//			System.out.println("cadre.getMinX() : " + cadre.getMinX());
//			System.out.println("cadre.getMaxY() : " + cadre.getMaxY());
//			System.out.println("cadre.getMaxX() : " + cadre.getMaxX());
//			System.out.println("point.getY() : " + point.getY());
//			System.out.println("point.getX() : " + point.getX());
//			System.out.println("fiffY : " + (trans2.getTranslateY()-cadre.getMaxY()));
//			System.out.println("angle : " + i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle());
//			System.out.println("cos : " + Math.cos(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
//			System.out.println("cos : " + Math.sin(-Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
//			System.out.println("diag : " + Math.sqrt(cadre.getHeight()+cadre.getWidth()));

//			if(trans2.getTranslateY()+cadre.getCenterY()>lineStart.getY())
//				deltaY=(int) (trans2.getTranslateY()+cadre.getMinY() - lineEnd.getY());
//			if(trans2.getTranslateY()+cadre.getCenterY()<lineStart.getY())
//				deltaY=(int) (trans2.getTranslateY()+cadre.getMaxY() - lineEnd.getY());
//			
//			if(trans2.getTranslateX()+cadre.getCenterX()>lineStart.getX())
//				deltaX=(int) (trans2.getTranslateX()+cadre.getMaxX() - lineEnd.getX());
//			if(trans2.getTranslateX()+cadre.getCenterX()<lineStart.getX())
//				deltaX=(int) (trans2.getTranslateX()+cadre.getMinX() - lineEnd.getX());
			
			
			if(renderingModel.getSense() == Rotation.Clockwise)
			{
				deltaX=(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
						Math.cos(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
				deltaY=(int) (Math.sqrt(cadre.getHeight()+cadre.getWidth()+400)*
						Math.sin(Math.toRadians(i*majorTickAngleSpacing+ renderingModel.getTicksStartAngle())));
				System.out.println("test");
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
	
	public void renderLabel(Graphics2D g)
	{
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		if(labelModel == null || pictureModel==null) return;
		
		//BufferedImage background = pictureModel.getBackground();
		if(background==null) return;
		
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());		
		g2.setColor(labelModel.getColor());
		g2.setFont(labelModel.getFont());
		final int strWidth = g2.getFontMetrics().stringWidth(labelModel.getLabel());
		g2.drawString(labelModel.getLabel(), background.getWidth()/2 - strWidth/2, background.getHeight()/3);
		g2.dispose();
	}
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		return new Dimension(pictureModel.getBackground().getHeight(),pictureModel.getBackground().getWidth());
	}
}
