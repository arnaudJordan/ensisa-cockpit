package jmp.ui.utilities;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.renderer.IndicatorDefaultRenderer;

public class BlinkDrawer implements ActionListener {
	private IndicatorView view;
	private ImageList imageList;
	private int currentIndex;
	private AffineTransform trans;
	
	
	public BlinkDrawer(IndicatorView view) {
		super();
		this.view = view;
		this.trans = new AffineTransform();
		currentIndex=0;
	}


	public void actionPerformed(ActionEvent e) {
		Graphics2D g = (Graphics2D) view.getGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); 
		g.setRenderingHints(rh);
		
		g.clearRect((int)trans.getTranslateX(), (int)trans.getTranslateY(), imageList.get(currentIndex).getWidth(), imageList.get(currentIndex).getHeight());
		g.drawImage(imageList.get(currentIndex), trans, null);
		currentIndex=(++currentIndex)%imageList.size();
		((IndicatorDefaultRenderer) view.renderer()).renderBorder(g);
	}


	public void setImageList(ImageList imageList) {
		this.imageList = imageList;
	}

	public void setTrans(AffineTransform trans) {
		this.trans = trans;
	}

}
