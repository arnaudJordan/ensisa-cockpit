package jmp.ui.component.indicator.renderer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Timer;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.BlinkDrawer;
import jmp.ui.utilities.ImageList;

public class IndicatorBlinkMultiRenderer extends IndicatorDefaultRenderer {
	private List<Timer> timer;
	
	public IndicatorBlinkMultiRenderer(View view) {
		super(view);
		this.timer = new ArrayList<Timer>();
	}
	
	public void renderState(Graphics2D g) {
		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		
		int multX=0;
		int multY=0;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=1;
			else
				multX=1;

		if(blinkModel != null && renderingModel!=null)
		{
			Dimension dimension = blinkModel.getSize();
			int transX=0, transY=0;
			Iterator<BoundedModel> it = valueModel.getIterator();
			int i=0;
			while(it.hasNext())
			{
				BoundedModel value = it.next();
				AffineTransform trans = new AffineTransform();
				trans.translate(transX, transY);

				ImageList imageList = blinkModel.getImageList().getRange(value.getValue()).imageList;
				
				if(timer.size()>i)
					timer.get(i).stop();
				else
					timer.add(i, new Timer(0, null));
				
				if(imageList.size()>0)
				{
					g.drawImage(imageList.get(0), trans, null);
					if(imageList.size()>1)
					{
						BlinkDrawer timerAction = new BlinkDrawer(indicatorView());
						timerAction.setImageList(imageList);
						timerAction.setTrans(trans);
						timer.set(i, new Timer(blinkModel.getBlinkTime(), timerAction));
						timer.get(i).start();
					}
				}
				transX+=dimension.getWidth() * multX;
				transY+=dimension.getHeight() * multY;
				i++;
			}
			
		}
	}
	public Dimension getPreferredSize() {
		IndicatorPictureRenderingModel pictureModel = ((IndicatorPictureRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("picture"));
		IndicatorColoredRenderingModel colorModel = ((IndicatorColoredRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("color"));
		IndicatorColoredRangeRenderingModel coloredRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		IndicatorBlinkRenderingModel blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));
		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		
		int multX=1;
		int multY=1;
		if(valueModel!=null)
			if(orientationModel!=null && orientationModel.getOrientation()==Orientation.Vertical)
				multY=valueModel.getSize();
			else
				multX=valueModel.getSize();
		
		Dimension dimension = new Dimension(0,0);
		
		if(blinkModel != null)
			dimension = new Dimension(multX * blinkModel.getSize().width, multY * blinkModel.getSize().height);
		if(coloredRangeModel != null)
			dimension = new Dimension(multX * coloredRangeModel.getSize().width, multY * coloredRangeModel.getSize().height);
		if(colorModel != null)
			dimension = new Dimension(multX * colorModel.getSize().width, multY * colorModel.getSize().height);
		if(pictureModel != null)
			dimension = new Dimension(multX * pictureModel.getOnImage().getWidth(), multY * pictureModel.getOnImage().getHeight());
		
		IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
		
		if(labelModel == null) 
			return dimension;
		
		Graphics g = this.indicatorView().getGraphics();
		g.setFont(labelModel.getFont());
		
		if(labelModel.getPosition() == CardinalPosition.NORTH || labelModel.getPosition() == CardinalPosition.SOUTH)
			return new Dimension((int)Math.max(dimension.getWidth(), g.getFontMetrics().stringWidth(labelModel.getLabel())) + 1,
				(int)dimension.getHeight() +  g.getFontMetrics().getHeight() + 1);
		
		return new Dimension((int)dimension.getWidth() + g.getFontMetrics().stringWidth(labelModel.getLabel()) + 1,
				Math.max((int)dimension.getHeight(), g.getFontMetrics().getHeight() + 1));
	}
}
