package jmp.ui.component.dial.renderer;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialCompositRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class DialCompositRenderer extends DialDefaultRenderer {

	public DialCompositRenderer(View view) {
		super(view);
	}

	public void renderBackground(Graphics2D g)
	{		
		ModelComposit compositModel = ((ModelComposit) ((ModelComposit) (dialView().getModel())).getModel("composit"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		
		if(pictureModel !=null && pictureModel.getBackground() != null)
		{
			background=pictureModel.getBackground();
		}
		
		if(compositModel!=null)
		{
			DialPictureRenderingModel compositPictureModel = (DialPictureRenderingModel) compositModel.getModel("picture");
			DialCompositRenderingModel compositRenderingModel = (DialCompositRenderingModel) compositModel.getModel("rendering");
			if(compositPictureModel!=null && compositPictureModel.getBackground() != null)
			{
				BufferedImage compositBackground = compositPictureModel.getBackground();
				
				Graphics2D g2 = background.createGraphics();
				AffineTransform trans = new AffineTransform();
				trans.translate(background.getWidth()/2 - compositBackground.getWidth()/2 + compositRenderingModel.getInternDialPosition().getX(), background.getHeight()/2 - compositBackground.getWidth()/2 + compositRenderingModel.getInternDialPosition().getY());
				g2.drawImage(compositBackground, trans, null);
				g2.dispose();
			}
		}
		clip=new Arc2D.Double(0, 0, background.getHeight(), background.getWidth(), 0, 360, Arc2D.PIE);
	 	g.setClip(clip);
		g.drawImage(background, 0, 0, null);
	}
	public void renderNeedle(Graphics2D g)
	{
		ModelComposit compositModel = ((ModelComposit) ((ModelComposit) (dialView().getModel())).getModel("composit"));
		DialPictureRenderingModel pictureModel = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		
		
		if(compositModel!=null)
		{
			DialPictureRenderingModel compositPictureModel = (DialPictureRenderingModel) compositModel.getModel("picture");
			DialCompositRenderingModel compositRenderingModel = (DialCompositRenderingModel) compositModel.getModel("rendering");
			if(compositPictureModel!=null && compositPictureModel.getNeedle() != null)
			{
				BufferedImage compositNeedle = compositPictureModel.getNeedle();
				BoundedModel internValue = (BoundedModel) compositModel.getModel("value");
				
				int Angle=0;
				if(internValue.getValue() != 0)
					Angle = internValue.getValue()*360/(internValue.getMaximum()-internValue.getMinimum());
				AffineTransform trans = new AffineTransform();
				trans.setToIdentity();
				trans.translate(background.getWidth()/2, background.getHeight()/2);
				trans.translate(compositRenderingModel.getInternDialPosition().getX(), compositRenderingModel.getInternDialPosition().getY());
				if(compositRenderingModel.getSense() == Rotation.Clockwise)
					trans.rotate(Math.toRadians(Angle - compositRenderingModel.getTicksStartAngle()));
				else
					trans.rotate(-Math.toRadians(Angle + compositRenderingModel.getTicksStartAngle()));
				trans.translate(-compositNeedle.getWidth()/2,-compositNeedle.getHeight()/2);
				g.drawImage(compositNeedle,trans,null);

			}
		}
		
		if(pictureModel !=null && pictureModel.getNeedle() != null)
		{
			BufferedImage mainNeedle = pictureModel.getNeedle();
			BoundedModel mainValue = ((BoundedModel) this.dialView().valueModel());
			DialRenderingModel renderingModel =  dialView().renderingModel();
			
			int Angle=0;
			if(mainValue.getValue() != 0)
				Angle = mainValue.getValue()*360/(mainValue.getMaximum()-mainValue.getMinimum());
			
			AffineTransform trans = new AffineTransform();
			trans.setToIdentity();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			if(renderingModel.getSense() == Rotation.Clockwise)
				trans.rotate(Math.toRadians(Angle - renderingModel.getTicksStartAngle()));
			else
				trans.rotate(-Math.toRadians(Angle + renderingModel.getTicksStartAngle()));
			trans.translate(-mainNeedle.getWidth()/2,-mainNeedle.getHeight()/2);
			g.drawImage(mainNeedle,trans,null);
		}
	}
	public void renderBorder(Graphics2D g) {
		ModelComposit compositModel = ((ModelComposit) ((ModelComposit) (dialView().getModel())).getModel("composit"));		
		DialBorderRenderingModel borderModel = ((DialBorderRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("border"));
		
		Shape oldClip = g.getClip();
		g.setClip(clip);
		
		
		if(borderModel!=null && borderModel.getBorderSize()!=0)
		{
			g.setColor(borderModel.getBorderColor());
			g.setStroke(new BasicStroke(borderModel.getBorderSize()));
			Shape border = new Ellipse2D.Double(borderModel.getBorderSize()/2, borderModel.getBorderSize()/2,
					background.getWidth()-borderModel.getBorderSize(), background.getHeight()-borderModel.getBorderSize());
			g.draw(border);
		}
		if(compositModel!=null)
		{
			DialCompositRenderingModel compositRenderingModel = (DialCompositRenderingModel) compositModel.getModel("rendering");
			DialPictureRenderingModel compositPictureModel = (DialPictureRenderingModel) compositModel.getModel("picture");
			DialBorderRenderingModel compositBorderModel = (DialBorderRenderingModel) compositModel.getModel("border");
			if(compositBorderModel != null && compositPictureModel != null && compositPictureModel.getBackground() != null)
			{
				BufferedImage compositBackground = compositPictureModel.getBackground();
				g.setColor(compositBorderModel.getBorderColor());
				g.setStroke(new BasicStroke(compositBorderModel.getBorderSize()));
				Shape border = new Ellipse2D.Double(compositBorderModel.getBorderSize()/2, compositBorderModel.getBorderSize()/2,
						compositBackground.getWidth()-compositBorderModel.getBorderSize(), compositBackground.getHeight()-compositBorderModel.getBorderSize());
				AffineTransform trans = new AffineTransform();
				trans.translate(background.getWidth()/2 - compositBackground.getWidth()/2 + compositRenderingModel.getInternDialPosition().getX(), background.getHeight()/2 - compositBackground.getWidth()/2 + compositRenderingModel.getInternDialPosition().getY());
				AffineTransform oldTrans = g.getTransform();
				g.transform(trans);
				g.draw(border);
				g.setTransform(oldTrans);
			}
		}
		g.setClip(oldClip);
	}
	public void renderLabel(Graphics2D g) {
		DialLabelRenderingModel labelModel = ((DialLabelRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("label"));
		ModelComposit compositModel = ((ModelComposit) ((ModelComposit) (dialView().getModel())).getModel("composit"));

		if(background==null) return;
		g.setRenderingHints(g.getRenderingHints());	
		if(labelModel != null)
		{
			g.setColor(labelModel.getColor());
			g.setFont(labelModel.getFont());
			final int strWidth = g.getFontMetrics().stringWidth(labelModel.getLabel());
			AffineTransform trans = new AffineTransform();
			AffineTransform oldTrans = g.getTransform();
			trans.translate(background.getWidth()/2, background.getHeight()/2);
			g.transform(trans);
			g.drawString(labelModel.getLabel(), (int) labelModel.getPosition().getX() - strWidth/2, (int) labelModel.getPosition().getY());
			g.setTransform(oldTrans);
		}
		if(compositModel!=null)
		{
			DialCompositRenderingModel compositRenderingModel = (DialCompositRenderingModel) compositModel.getModel("rendering");
			DialLabelRenderingModel compositLabelModel = (DialLabelRenderingModel) compositModel.getModel("label");
			
			if(compositLabelModel != null && compositRenderingModel != null)
			{
				g.setColor(compositLabelModel.getColor());
				g.setFont(compositLabelModel.getFont());
				AffineTransform trans = new AffineTransform();
				trans.translate(background.getWidth()/2  + compositRenderingModel.getInternDialPosition().getX(), background.getHeight()/2 + compositRenderingModel.getInternDialPosition().getY());
				AffineTransform oldTrans = g.getTransform();
				g.transform(trans);
				final int strWidth = g.getFontMetrics().stringWidth(compositLabelModel.getLabel());
				g.drawString(compositLabelModel.getLabel(), (int) compositLabelModel.getPosition().getX() - strWidth/2, (int) compositLabelModel.getPosition().getY());
				g.setTransform(oldTrans);
			}
		}
	}
	public void renderDial(Graphics2D g) {
		this.renderBackground(g);
		this.renderBorder(g);
		this.renderLabel(g);
		this.renderNeedle(g);
	}
}
