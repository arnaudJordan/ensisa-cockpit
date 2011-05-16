package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

import jmp.ui.model.BoundedModel;
import jmp.ui.mvc.View;

/**
 * @author cockpit
 *
 */
public class DialPartialRenderer extends DialPictureRenderer {

	private Arc2D.Double clip;
	public DialPartialRenderer(View view) {
		super(view);
	}
	public void renderNeedle(Graphics2D g)
	{
		DialPartialRenderingModel model = (DialPartialRenderingModel)this.dialView().renderingModel();
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		BoundedModel valueModel = this.dialView().valueModel();
		if (needle == null || background == null) return;
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		trans.translate(-clip.getBounds2D().getMinX(), -clip.getBounds2D().getMinY());
		if(valueModel.getValue() > model.getEndAngle())
			valueModel.setValue(model.getEndAngle());
		if(valueModel.getValue() < model.getStartAngle())
			valueModel.setValue(model.getStartAngle());
		trans.rotate(Math.toRadians(-this.dialView().valueModel().getValue()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);
		
		g.drawImage(needle,trans,null);
	}
	public void renderBackground(Graphics2D g)
	{
		DialPartialRenderingModel model = (DialPartialRenderingModel)this.dialView().renderingModel();
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		if (background == null) return;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();		
		Shape oldClip = g.getClip();
		
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getStartAngle(), model.getEndAngle()),Arc2D.PIE);
		trans.setToIdentity();
		trans.translate(-clip.getBounds2D().getMinX(), -clip.getBounds2D().getMinY());
		//trans.translate(getPreferredSize().getWidth()-clip.getBounds2D().getHeight(), getPreferredSize().getHeight() -clip.getBounds2D().getWidth()));
		g.transform(trans);
		g.clip(clip);
		g.drawImage(background,null,null);
		g.setClip(null);
		g.setTransform(oldTrans);
	}
	public Dimension getPreferredSize()
	{
		DialPartialRenderingModel model = (DialPartialRenderingModel)this.dialView().renderingModel();
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getStartAngle(), model.getEndAngle()),Arc2D.PIE);
		int transX = 0;
		int transY = 0;
        System.out.println("----------------");
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
        System.out.println("-------------");

        if(clip.getBounds2D().getMaxX() < clip.getCenterX() + needle.getHeight())
        {
        	transX = needle.getHeight();
        	System.out.println(1);
        }
        if(clip.getBounds2D().getMaxY() < clip.getCenterY() + needle.getHeight())
        {
        	transY = needle.getHeight();
        	System.out.println(2);
        }
        if(clip.getBounds2D().getMinX() > clip.getCenterX() - needle.getHeight())
        {
        	transX = needle.getHeight();
        	System.out.println(3);
        }
        if(clip.getBounds2D().getMinY() > clip.getCenterY() -  needle.getHeight())
        {
        	transY = needle.getHeight();
        	System.out.println(4);
        }
        return new Dimension((int)clip.getBounds2D().getWidth() + transX, (int)clip.getBounds2D().getHeight() + transY);
	}
	private int extendAngle(int startAngle, int endAngle)
	{
		if(startAngle < 0)
			startAngle += 360;
		if(endAngle < 0)
			endAngle += 360;
		int extend = endAngle - startAngle;
		if(startAngle > endAngle)
			extend += 360;
		return java.lang.Math.abs(extend);
	}
}
