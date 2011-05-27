package jmp.ui.component.indicator.renderer;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import javax.swing.Timer;

import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.BlinkDrawer;
import jmp.ui.utilities.ImageList;

public class IndicatorBlinkRenderer extends IndicatorDefaultRenderer{
	private Timer timer;

	public IndicatorBlinkRenderer(View view) {
		super(view);
		this.timer = new Timer(1000, null);
	}
	
	public void renderState(final Graphics2D g) {
		BoundedModel valueModel = ((BoundedModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		
		if(blinkModel != null && renderingModel!=null)
		{
			
			Dimension dimension = blinkModel.getSize();
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			int transX=0, transY=0;
			if(labelModel != null) 
			{		
				switch (labelModel.getPosition())
				{
					case NORTH :transY=(int) (getPreferredSize().getHeight()-dimension.getHeight());
					transX=(int) (getPreferredSize().getWidth()-dimension.getWidth())/2;
					break;
					case SOUTH : transX=(int) (getPreferredSize().getWidth()-dimension.getWidth())/2; break;
					case EAST : ; break;
					case WEST : transX=(int) (getPreferredSize().getWidth()-dimension.getWidth()); break;
				}
			}
			AffineTransform trans = new AffineTransform();
			trans.translate(transX, transY);
			ImageList imageList = blinkModel.getImageList().getRange(valueModel.getValue()).imageList;
			
			timer.stop();
			if(imageList.size()<=1)
			{
				if(imageList.size()==1)
					g.drawImage(imageList.get(0), trans, null);
			}
			else
			{
				BlinkDrawer timerAction = new BlinkDrawer(getView());
				timerAction.setImageList(imageList);
				timerAction.setTrans(trans);
				timer = new Timer(blinkModel.getBlinkTime(), timerAction);
				timer.start();
			}
	
			
		}
	}
}
