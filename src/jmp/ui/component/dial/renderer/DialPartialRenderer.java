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
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
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
		DialPartialRenderingModel renderingModel = (DialPartialRenderingModel) dialView().renderingModel();
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		BoundedModel valueModel = this.dialView().valueModel();
		if (needle == null || background == null) return;
		int Angle=0;
		if(valueModel.getValue() != 0)
			Angle = valueModel.getValue()*(renderingModel.getEndAngle()-renderingModel.getStartAngle())/(valueModel.getMaximum()-valueModel.getMinimum());
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		double transX = 0;
		double transY = 0;

		if(clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight())
			transX = needle.getHeight();
		if(clip.getBounds2D().getMinY() > clip.getCenterY() - needle.getHeight())
			transY = needle.getHeight();
		trans.translate(-clip.getBounds2D().getMinX() + transX, -clip.getBounds2D().getMinY() + transY);
		if(valueModel.getValue() > renderingModel.getEndAngle())
			valueModel.setValue(renderingModel.getEndAngle());
		if(valueModel.getValue() < renderingModel.getStartAngle())
			valueModel.setValue(renderingModel.getStartAngle());
		//trans.rotate(Math.toRadians(-this.dialView().valueModel().getValue()));
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
		DialPartialRenderingModel partialModel = (DialPartialRenderingModel) dialView().renderingModel();
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if (background == null) return;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();		
		
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
	public void renderBorder(Graphics2D g)
	{
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = (DialPartialRenderingModel) dialView().renderingModel();
		BufferedImage background = pictureModel.getBackground();
		BufferedImage needle = pictureModel.getNeedle();
		if(borderModel==null || borderModel.getBorderSize()==0) return;;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();		
		
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
		
		g.setColor(borderModel.getBorderColor());
		g.setStroke(new BasicStroke(borderModel.getBorderSize()));
		Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
		g.draw(border);
		g.setClip(null);
		g.setTransform(oldTrans);
	}
	
	public Dimension getPreferredSize()
	{
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPartialRenderingModel partialModel = (DialPartialRenderingModel)this.dialView().renderingModel();
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