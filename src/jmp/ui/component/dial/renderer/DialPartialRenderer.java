package jmp.ui.component.dial.renderer;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.JMSwingUtilities;

/**
 * @author cockpit
 *
 */
public class DialPartialRenderer extends DialDefaultRenderer {

	private Arc2D.Double clip;
	public DialPartialRenderer(View view) {
		super(view);
	}

	public void renderNeedle(Graphics2D g)
	{
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
	public void renderBackground(Graphics2D g)
	{
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

		AffineTransform oldTrans = g.getTransform();
		g.transform(trans);
		g.clip(clip);
		g.drawImage(this.background,0,0,null);
		g.setClip(null);
		g.transform(oldTrans);
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
		g.setClip(clip);
		g.draw(border);
	}
	public void renderLabel(Graphics2D g)
	{
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		
		if(labelModel == null || pictureModel==null) return;
		
		if(background==null) return;
		Graphics2D g2 = background.createGraphics();
		g2.setRenderingHints(g.getRenderingHints());		
		g2.setColor(labelModel.getColor());
		g2.setFont(labelModel.getFont());
		final int strWidth = g2.getFontMetrics().stringWidth(labelModel.getLabel());
		g2.drawString(labelModel.getLabel(), (int)clip.getBounds2D().getCenterX()*4/5 - strWidth/2, (int)clip.getBounds2D().getCenterY()*4/5);
		
		g2.dispose();
	}
	
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = (DialPartialRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("partial");
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), partialModel.getStartAngle(), JMSwingUtilities.extendAngle(partialModel.getStartAngle(), partialModel.getEndAngle()),Arc2D.PIE);
		double transX = 0;
		double transY = 0;
		
		/*System.out.println("----------------");
        System.out.println("bg Height : " +background.getHeight());
        System.out.println("bg Width : " +background.getWidth());
        System.out.println("maxX : " + clip.getMaxX());
        System.out.println("maxY : " + clip.getMaxY());
        System.out.println("minX : " + clip.getMinX());
        System.out.println("minY : " + clip.getMinY());
        System.out.println("centerX : " + clip.getCenterX());
        System.out.println("centerY : " + clip.getCenterY());
        System.out.println("----Avec Boubds-----");
        System.out.println("maxX : " + clip.getBounds2D().getMaxX());
        System.out.println("maxY : " + clip.getBounds2D().getMaxY());
        System.out.println("minX : " + clip.getBounds2D().getMinX());
        System.out.println("minY : " + clip.getBounds2D().getMinY());
        System.out.println("-------------");*/

        if((clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight()) || (clip.getBounds2D().getMaxX() < clip.getCenterX() + needle.getHeight()))
        	transX = needle.getHeight();
        if((clip.getBounds2D().getMinY() > clip.getCenterY() -  needle.getHeight()) || (clip.getBounds2D().getMaxY() < clip.getCenterY() + needle.getHeight()))
        	transY = needle.getHeight();
        return new Dimension((int)(clip.getBounds2D().getWidth() + transX), (int)(clip.getBounds2D().getHeight() + transY));
	}
}
