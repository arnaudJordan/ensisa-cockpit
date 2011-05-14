package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;
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
	
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getStartAngle(), model.getEndAngle()),Arc2D.PIE);
		trans.setToIdentity();
		trans.translate(-clip.getBounds2D().getMinX(), -clip.getBounds2D().getMinY());
		
		g.transform(trans);
		g.clip(clip);
		
		g.drawImage(background,null,null);
		g.setTransform(oldTrans);
	}
	public Dimension getPreferredSize()
	{
		DialPartialRenderingModel model = (DialPartialRenderingModel)this.dialView().renderingModel();
		BufferedImage background = model.getBackground();
		BufferedImage needle = model.getNeedle();
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), extendAngle(model.getStartAngle(), model.getEndAngle()),Arc2D.PIE);
		return new Dimension((int)clip.getBounds2D().getWidth(), (int)clip.getBounds2D().getHeight());
	}
	private int extendAngle(int startAngle, int endAngle)
	{
		int extend = endAngle - startAngle;
		if(startAngle > endAngle)
			extend += 360;
		return java.lang.Math.abs(extend);
	}
}
