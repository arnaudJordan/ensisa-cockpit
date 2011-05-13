package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.Rectangle2D;
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
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		BufferedImage needle = ((DialPictureRenderingModel) this.dialView().renderingModel()).getNeedle();
		if (needle == null || background == null) return;
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		trans.rotate(Math.toRadians(this.dialView().valueModel().getValue()));
		trans.translate(-needle.getWidth()/2,-needle.getHeight()/2);
		
		
		g.drawImage(needle,trans,null);
		
	}
	public void renderBackground(Graphics2D g)
	{
		DialPartialRenderingModel model = ((DialPartialRenderingModel)this.dialView().renderingModel());
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		if (background == null) return;
		
		AffineTransform oldTrans = g.getTransform();
		AffineTransform trans = new AffineTransform();
		//trans.setToIdentity();
		//trans.translate(-getPreferredSize().getWidth() + needle.getHeight(),0);
		//g.transform(trans);
		//clip = new Arc2D.Double(-needle.getHeight(), 0, background.getWidth() + needle.getHeight(), background.getHeight() + needle.getHeight(), model.getStartAngle(), extendAngle(model.getEndAngle(),model.getStartAngle()),Arc2D.PIE);
		
	
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getEndAngle(),model.getStartAngle()),Arc2D.PIE);
		trans.setToIdentity();
		trans.translate(-clip.getBounds2D().getMinX(), -clip.getBounds2D().getMinY());
		
		/*AffineTransform transXtoLeft = new AffineTransform();
		transXtoLeft.translate(-clip.getBounds2D().getMinX(), 0);
		AffineTransform transYtoUpper = new AffineTransform();
		transYtoUpper.translate(0, -clip.getBounds2D().getMinY());
		
		if(getPreferredSize().getWidth() > background.getWidth()){
			g.transform(transXtoLeft);
			System.out.println("1");}
		if(getPreferredSize().getHeight() > background.getHeight()){
			g.transform(transYtoUpper);
			System.out.println("2");}
		if(getPreferredSize().getWidth() < background.getWidth()){
			g.transform(transXtoLeft);
			System.out.println("3");}
		if(getPreferredSize().getHeight() < background.getHeight()){
			g.transform(transYtoUpper);
			System.out.println("4");}*/
		
		g.transform(trans);
		g.clip(clip);
		
		/*System.out.println("transXleft'"+transXtoLeft.getTranslateX());
		System.out.println("transYup'"+transYtoUpper.getTranslateY());
		System.out.println("transXright'"+transXtoRight.getTranslateX());
		System.out.println("transYdown'"+transYtoBottom.getTranslateY());
		System.out.println("----------------");
		System.out.println("bg Height : " +background.getHeight());
		System.out.println("bg Width : " +background.getWidth());
		System.out.println("Prezfer Height : " +getPreferredSize().getHeight());
		System.out.println("Perefer Width : " +getPreferredSize().getWidth());
		System.out.println("maxX : " + clip.getMaxX());
		System.out.println("maxY : " + clip.getMaxY());
		System.out.println("minX : " + clip.getMinX());
		System.out.println("minY : " + clip.getMinY());
		System.out.println("----Avec Boubds-----");
		System.out.println("maxX : " + clip.getBounds2D().getMaxX());
		System.out.println("maxY : " + clip.getBounds2D().getMaxY());
		System.out.println("minX : " + clip.getBounds2D().getMinX());
		System.out.println("minY : " + clip.getBounds2D().getMinY());
		System.out.println("-------------");*/
		g.drawImage(background,null,null);
		g.setTransform(oldTrans);
	}
	public Dimension getPreferredSize()
	{
		DialPartialRenderingModel model = ((DialPartialRenderingModel)this.dialView().renderingModel());
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getEndAngle(),model.getStartAngle()),Arc2D.PIE);
		//clip = new Arc2D.Double(-needle.getHeight(), 0, background.getWidth() + needle.getHeight(), background.getHeight() + needle.getHeight(), model.getStartAngle(), extendAngle(model.getEndAngle(),model.getStartAngle()),Arc2D.PIE);
		return new Dimension((int)clip.getBounds2D().getWidth(), (int)clip.getBounds2D().getHeight());
	}
	private int extendAngle(int startAngle, int endAngle)
	{
		int difference = endAngle - startAngle;
		while (difference < -180) difference += 360;
		while (difference > 180) difference -= 360;
		return java.lang.Math.abs(difference);
	}
}
