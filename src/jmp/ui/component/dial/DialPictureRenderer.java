package jmp.ui.component.dial;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

import jmp.ui.model.BoundedModel;
import jmp.ui.mvc.View;

public class DialPictureRenderer extends DialDefaultRenderer {
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);

	public DialPictureRenderer(View view) {
		super(view);
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
	}
	
	public void renderNeedle(Graphics2D g)
	{
		AffineTransform trans = new AffineTransform();
		
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		BufferedImage needle = ((DialPictureRenderingModel) this.dialView().renderingModel()).getNeedle();
		if (needle == null || background == null) return;
		
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
		g.drawImage(background,0,0,null);
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getHeight(),((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getWidth());
	}
}
