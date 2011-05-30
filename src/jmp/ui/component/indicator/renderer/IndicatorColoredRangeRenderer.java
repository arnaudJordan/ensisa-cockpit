package jmp.ui.component.indicator.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;

import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class IndicatorColoredRangeRenderer extends IndicatorDefaultRenderer {

	public IndicatorColoredRangeRenderer(View view) {
		super(view);
	}
	public void renderState(Graphics2D g) {
		BoundedModel valueModel = ((BoundedModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		
		IndicatorColoredRangeRenderingModel colorRangeModel = ((IndicatorColoredRangeRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("colorRange"));
		
		if(colorRangeModel != null && renderingModel!=null)
		{
			Color oldColor = g.getColor();
			RadialGradientPaint p;
			p = new RadialGradientPaint(new Point2D.Double(getPreferredSize().getWidth() / 2.0,
		               getPreferredSize().getHeight() / 2.0), (float) (getPreferredSize().getWidth() / 2.0f),
		                new float[] { 0.0f, 1.0f },
		                new Color[] { new Color(255, 255, 255), colorRangeModel.getColoredRanges().getRange(valueModel.getValue()).color});
			
			IndicatorLabelRenderingModel labelModel = ((IndicatorLabelRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("label"));
			if(labelModel != null) 
			{		
				
		        g.setPaint(p);
				switch (labelModel.getPosition())
				{
				case NORTH : g.fillOval((int) (getPreferredSize().getWidth()/2 - colorRangeModel.getSize().getWidth()/2), (int) (getPreferredSize().getHeight() - colorRangeModel.getSize().getHeight()), (int) colorRangeModel.getSize().getWidth(), (int)colorRangeModel.getSize().getHeight()); break;
				case SOUTH : g.fillOval((int) (getPreferredSize().getWidth()/2 - colorRangeModel.getSize().getWidth()/2), 0, (int)colorRangeModel.getSize().getWidth(), (int) colorRangeModel.getSize().getHeight()); break;
				case EAST : g.fillOval(0, (int) (getPreferredSize().getHeight()/2 - colorRangeModel.getSize().getHeight()/2), (int)colorRangeModel.getSize().getWidth(), (int) colorRangeModel.getSize().getHeight()); break;
				case WEST : g.fillOval((int)(getPreferredSize().getWidth() - colorRangeModel.getSize().getWidth()), (int) (getPreferredSize().getHeight()/2 - colorRangeModel.getSize().getHeight()/2), (int)colorRangeModel.getSize().getWidth(), (int) colorRangeModel.getSize().getHeight()); break;
				}
			}
			else 
				g.fillOval(0,0,(int)colorRangeModel.getSize().getWidth(), (int) colorRangeModel.getSize().getHeight());
			g.setColor(oldColor);
		}
	}

}
