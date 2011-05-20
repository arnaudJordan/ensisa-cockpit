package jmp.ui.component.dial;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import jmp.ui.component.Rotation;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class DialCompositRenderer extends DialDefaultRenderer {

	public DialCompositRenderer(View view) {
		super(view);
	}

	public void renderBackground(Graphics2D g)
	{
		BufferedImage mainBackground = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture")).getBackground();
		DialCompositRenderingModel compositModel = ((DialCompositRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("composit"));
		BufferedImage internBackground = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("internPicture")).getBackground();
		if (mainBackground == null) return;
		
		if (internBackground == null) return;
		Graphics2D g2 = mainBackground.createGraphics();
		
		AffineTransform trans = new AffineTransform();
		trans.translate(mainBackground.getWidth()/2 - internBackground.getWidth()/2 + compositModel.getInternDialPosition().getX(), mainBackground.getHeight()/2 - internBackground.getWidth()/2 + compositModel.getInternDialPosition().getY());
		g2.drawImage(internBackground, trans, null);
		g2.dispose();
		
		Shape clip=new Ellipse2D.Double(0, 0, mainBackground.getHeight(), mainBackground.getWidth());
		g.setClip(clip);
		g.drawImage(mainBackground,0,0,null);
	}
	public void renderNeedle(Graphics2D g)
	{
		DialPictureRenderingModel mainPicture = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("picture"));
		DialPictureRenderingModel internPicture = ((DialPictureRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("internPicture"));
		DialCompositRenderingModel compositModel = ((DialCompositRenderingModel) ((ModelComposit) (dialView().getModel())).getModel("composit"));
		DialRenderingModel renderingModel =  dialView().renderingModel();
		BufferedImage background = mainPicture.getBackground();
		BufferedImage mainNeedle = mainPicture.getNeedle();
		BufferedImage internNeedle = internPicture.getNeedle();
		BoundedModel mainValue = ((BoundedModel) this.dialView().valueModel());
		BoundedModel internValue = ((BoundedModel) ((ModelComposit) (dialView().getModel())).getModel("internValue"));
		if (mainNeedle == null || background == null) return;
		
		int Angle=0;
		if(internValue.getValue() != 0)
			Angle = internValue.getValue()*360/(internValue.getMaximum()-internValue.getMinimum());
		
		AffineTransform trans = new AffineTransform();
		trans.setToIdentity();
		trans.translate(background.getWidth()/2, background.getHeight()/2);
		trans.translate(compositModel.getInternDialPosition().getX(), compositModel.getInternDialPosition().getY());
		if(renderingModel.getSense() == Rotation.Clockwise)
			trans.rotate(Math.toRadians(Angle - renderingModel.getTicksStartAngle()));
		else
			trans.rotate(-Math.toRadians(Angle + renderingModel.getTicksStartAngle()));
		trans.translate(-internNeedle.getWidth()/2,-internNeedle.getHeight()/2);
		g.drawImage(internNeedle,trans,null);
		
		Angle=0;
		if(mainValue.getValue() != 0)
			Angle = mainValue.getValue()*360/(mainValue.getMaximum()-mainValue.getMinimum());
		
		trans = new AffineTransform();
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
