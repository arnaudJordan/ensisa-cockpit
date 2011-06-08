package jmp.ui.utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jmp.ui.component.indicator.IndicatorView;

public class BlinkDrawer implements ActionListener {
	protected IndicatorView view;
	private boolean update;
	
	public BlinkDrawer(IndicatorView view) {
		super();
		this.view = view;
		setUpdate(true);
	}

	public void actionPerformed(ActionEvent e) {
		view.repaint();
		setUpdate(true);
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isUpdate() {
		return update;
	}
}
