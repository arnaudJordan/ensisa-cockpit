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
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.View;
import jmp.ui.utilities.BlinkDrawer;
import jmp.ui.utilities.ImageList;

public class IndicatorBlinkMultiRenderer extends IndicatorMultiRenderer {
	private List<Timer> timer;

	public IndicatorBlinkMultiRenderer(View view) {
		super(view);
		this.timer = new ArrayList<Timer>();
	}

	public void renderState(Graphics2D g) {
		BoundedModels valueModel = ((BoundedModels) ((ModelComposit) (indicatorView().getModel())).getModel("value"));
		if(valueModel == null) return;
		
		IndicatorOrientationRenderingModel orientationModel = ((IndicatorOrientationRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("orientation"));

		int multX = 0;
		int multY = 0;
		int transX = 0;
		int transY = 0;
		if (orientationModel != null && orientationModel.getOrientation() == Orientation.Vertical)
			multY = 1;
		else
			multX = 1;
		
		IndicatorLabelMultiRenderingModel labelsModel = ((IndicatorLabelMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("labels"));
		if (labelsModel != null)
		{
			Iterator<IndicatorLabelRenderingModel> it = labelsModel.getIterator();
			Graphics g2 = this.indicatorView().getGraphics();
			while (it.hasNext())
			{
				IndicatorLabelRenderingModel label = it.next();
				g2.setFont(label.getFont());
				if (label.getPosition() == CardinalPosition.WEST)
				{
					if (multY > 0)
						transX = (transX > g2.getFontMetrics().stringWidth(label.getLabel())) ? transX : g2.getFontMetrics().stringWidth(label.getLabel())+ label.getMargin();
				}
				if (label.getPosition() == CardinalPosition.NORTH)
				{
					if (multX > 0)
						transY = g2.getFontMetrics().getHeight()+ label.getMargin();
				}
			}
		}
		
		IndicatorBlinkMultiRenderingModel blinkModel = ((IndicatorBlinkMultiRenderingModel) ((ModelComposit) (indicatorView().getModel())).getModel("blinkMulti"));
		if (blinkModel != null) {
			Dimension dimension = blinkModel.getSize();
			Iterator<BoundedModel> it = valueModel.getIterator();
			while (valueModel.getSize() > timer.size())
			{
				timer.add(new Timer(0, new BlinkDrawer(indicatorView())));
			}

			int i = 0;
			while (it.hasNext())
			{
				BoundedModel value = it.next();
				AffineTransform trans = new AffineTransform();

				int transYN = 0, transYS = 0;
				int transXW = 0, transXE = 0;
				if (labelsModel != null) {
					IndicatorLabelRenderingModel label = labelsModel.getLabel(i);
					Graphics g2 = this.indicatorView().getGraphics();
					g2.setFont(label.getFont());
					if (label.getPosition() == CardinalPosition.NORTH
							&& multY > 0) {
						transYN = g2.getFontMetrics().getHeight()+ label.getMargin();
					}
					if (label.getPosition() == CardinalPosition.SOUTH
							&& multY > 0) {
						transYS = g2.getFontMetrics().getHeight()+ label.getMargin();
					}
					if (label.getPosition() == CardinalPosition.WEST
							&& multX > 0) {
						transXW = g2.getFontMetrics().stringWidth(
								label.getLabel())+ label.getMargin();
					}
					if (label.getPosition() == CardinalPosition.EAST
							&& multX > 0) {
						transXE = g2.getFontMetrics().stringWidth(
								label.getLabel())+ label.getMargin();
					}
				}
				transY += transYN;
				transX += transXW;
				trans.translate(transX, transY);
				ImageList imageList = blinkModel.getImageList().get(i).getRange(value.getValue()).imageList;
				if (timer.get(i).getDelay() != imageList.getPeriod())
				{
					timer.get(i).stop();
					timer.get(i).setDelay(imageList.getPeriod());
					timer.get(i).start();
				}
				if (((BlinkDrawer) timer.get(i).getActionListeners()[0]).isUpdate())
				{
					g.drawImage(imageList.getNext(), trans, null);
					((BlinkDrawer) timer.get(i).getActionListeners()[0]).setUpdate(false);
				}
				else
				{
					g.drawImage(imageList.getCurrent(), trans, null);
				}

				transY += transYS;
				transX += transXE;
				transX += dimension.getWidth() * multX;
				transY += dimension.getHeight() * multY;
				i++;
			}
		}
	}
}
