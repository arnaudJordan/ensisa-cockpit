package jmp.ui.component.indicator.renderer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import javax.swing.Timer;

import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;

public class IndicatorBlinkRenderer extends IndicatorDefaultRenderer implements ActionListener{

	private Graphics2D g;
	private IndicatorBlinkRenderingModel blinkModel;
	private AffineTransform trans;
	private Timer timer;

	public IndicatorBlinkRenderer(View view) {
		super(view);
	}
	
	public void renderState(final Graphics2D g) {
		BoundedModel valueModel = ((BoundedModel) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		IndicatorRenderingModel renderingModel = indicatorView().renderingModel();
		
		blinkModel = ((IndicatorBlinkRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blink"));
		
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
			trans = new AffineTransform();
			trans.translate(transX, transY);
			this.g=g;
			//g.drawImage(blinkModel.getImageList().get(0), trans, null);
			timer = new Timer(500, this);
			timer.start();
		}
	}

	public void actionPerformed(ActionEvent e) {
		g.drawImage(blinkModel.getImageList().get(0), trans, null);
		System.out.print("dkfjghdfkjg");
		g.setColor(Color.PINK);
		g.drawOval(100, 100, 100, 100);
		g.setBackground(Color.blue);
		paintComponent(g);
	}
	
	public void paintComponent(Graphics2D g) {
		g.drawOval(100, 100, 100, 100);
    }

}
