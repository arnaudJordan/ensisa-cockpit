package jmp.ui.component.indicator.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;

import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.DefaultRenderer;
import jmp.ui.mvc.View;

public class IndicatorDefaultRenderer extends DefaultRenderer implements IndicatorRenderer {

	public IndicatorDefaultRenderer(View view) {
		super(view);
	}

	public void renderView(Graphics2D g) {
		renderState(g);
		renderLabel(g);
		renderBorder(g);
	}
	protected IndicatorView indicatorView()
	{
		return (IndicatorView) this.getView();
	}
	public void renderState(Graphics2D g) {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		BooleanModel valueModel = ((BooleanModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		if(pictureModel == null || renderingModel==null) return;
		
		if(valueModel.is())
			g.drawImage(pictureModel.getOnImage(),0,0,null);
		else
			g.drawImage(pictureModel.getOffImage(),0,0,null);
	}

	public void renderLabel(Graphics2D g) {

	}

	public void renderBorder(Graphics2D g) {

	}

	public void renderIndicator(Graphics2D g) {

	}

	public void setSize(Dimension size) {

	}

	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		if(pictureModel == null) return new Dimension(40,40);;
		return new Dimension(pictureModel.getOnImage().getWidth(), pictureModel.getOnImage().getHeight());
	}

	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

}
