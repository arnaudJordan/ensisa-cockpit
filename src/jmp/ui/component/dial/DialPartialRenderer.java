package jmp.ui.component.dial;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.image.BufferedImage;

import jmp.ui.model.BoundedModel;
import jmp.ui.mvc.View;

public class DialPartialRenderer extends DialPictureRenderer {

	private Double clip;
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
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if (background == null) return;
		
		DialPartialRenderingModel model = ((DialPartialRenderingModel)this.dialView().renderingModel());
		clip = new Arc2D.Double(0, 0, background.getWidth(), background.getHeight(), model.getStartAngle(), model.getEndAngle()-model.getStartAngle(),Arc2D.PIE);
		g.clip(clip);
		
		g.drawImage(background,null,null);
	}
}
