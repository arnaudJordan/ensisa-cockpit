package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import javax.swing.border.EmptyBorder;

import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class DialPictureRenderer extends DialDefaultRenderer {
	private final static Insets DEFAULT_INSETS = new Insets(5,5,5,5);

	public DialPictureRenderer(View view) {
		super(view);
		this.getView().setBorder(new EmptyBorder(DEFAULT_INSETS));
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
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if(ticksModel==null) return;
		g.setColor(ticksModel.getGraduationColor());
		final int majorTickSpacing = (int) ticksModel.getMajorTickSpacing();
		final int minorTickSpacing = (int) ticksModel.getMinorTickSpacing();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		final int minorTickSize = (int) ticksModel.getMinorTickSize();
		final int majorLineXStart = (int) (background.getWidth() - majorTickSize);
		final int majorLineXEnd = (int) (background.getWidth()-ticksModel.getMajorGraduationWidth());
		final int minorLineXStart = (int) (background.getWidth() - minorTickSize);
		final int minorLineXEnd = (int) (background.getWidth()-ticksModel.getMinorGraduationWidth());
		int nbValues = 360/minorTickSpacing;
		
		AffineTransform oldTrans = g.getTransform();
		
		
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.translate(background.getWidth()/2 +background.getMinX(), background.getHeight()/2+background.getMinY());
			trans.rotate(Math.toRadians(i*minorTickSpacing));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g.transform(trans);
			g.setColor(ticksModel.getGraduationColor());
			g.setStroke(ticksModel.getMinorGradutionStroke());
			g.drawLine(minorLineXStart, background.getHeight()/2, minorLineXEnd, background.getHeight()/2);
			g.setTransform(oldTrans);
		}
		
		nbValues = 360/majorTickSpacing;
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			trans.rotate(-Math.toRadians(i*majorTickSpacing));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);
			g.transform(trans);
			g.setColor(ticksModel.getGraduationColor());
			g.setStroke(ticksModel.getMinorGradutionStroke());
			g.drawLine(majorLineXStart, background.getHeight()/2, majorLineXEnd, background.getHeight()/2);
			g.setTransform(oldTrans);
		}
	}
	public void renderLabels(Graphics2D g)
	{
		DialTicksRenderingModel ticksModel = ((DialTicksRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("ticks"));
		BufferedImage background = ((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground();
		if(ticksModel==null) return;
		g.setColor(ticksModel.getGraduationColor());
		final int majorTickSpacing = (int) ticksModel.getMajorTickSpacing();
		final int majorTickSize = (int) ticksModel.getMajorTickSize();
		int nbValues = 360/majorTickSpacing;
		
		AffineTransform oldTrans = g.getTransform();
		for(int i = 0; i < nbValues; i++)
		{
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			/*trans.rotate(-Math.toRadians(i*majorTickSpacing));
			trans.translate(-background.getWidth()/2,-background.getHeight()/2);*/
			final String vString = String.valueOf(i*majorTickSpacing);
			final int strWidth = g.getFontMetrics().stringWidth(vString);
			final int strHeight = g.getFontMetrics().getHeight();
			g.transform(trans);
		//	trans.translate(0, -strWidth);
		//	trans.rotate(-Math.toRadians(i*majorTickSpacing));
			//g.setTransform(trans);
			
			//g.drawString(vString, background.getHeight() , (background.getHeight()/2));
			g.drawString(vString, -strWidth/2+(int)((background.getHeight()/2- majorTickSize - strWidth/2) * Math.cos(-Math.toRadians(i*majorTickSpacing))),
					(int)((background.getHeight()/2- majorTickSize-strHeight/2) * Math.sin(-Math.toRadians(i*majorTickSpacing))));
			
			g.setTransform(oldTrans);
		}
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getHeight(),((DialPictureRenderingModel) this.dialView().renderingModel()).getBackground().getWidth());
	}
}
