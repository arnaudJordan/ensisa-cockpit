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
	private final static int DEFAULT_LENGTH = 250;
	private final static int DEFAULT_WIDTH = 21;
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);

	
	public DialPictureRenderer(View view) {
		super(view);
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
	}
	private DialView dialView()
	{
		return (DialView) this.getView();
	}
	
	public void renderView(Graphics2D g)
	{
		DialRenderingModel rm = this.dialView().renderingModel();
		BoundedModel vm = this.dialView().valueModel();
		
		if (rm == null || vm == null) return;
		
		this.renderBackground(g);
		this.renderNeedles(g, rm, vm);
		
	}
	
	private void renderNeedles(Graphics2D g, DialRenderingModel rm, BoundedModel vm)
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

	public Dimension getMinimumSize()
	{
		return this.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		return this.getPreferredSize();
	}
}
