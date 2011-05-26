package jmp.ui.model;

import jmp.ui.mvc.DefaultModel;

public class DefaultBooleanModel extends DefaultModel implements BooleanModel {
	private final static boolean DEFAULT_STATE  = false;
	private boolean state;

	public DefaultBooleanModel()
	{
		this.state=DEFAULT_STATE;
	}
	public void set() {
		setState(true);
	}

	public void reset() {
		setState(false);
	}

	public boolean is() {
		return state;
	}

	public void setState(boolean state) {
		if (this.state == state) return;
		this.state = state;
		this.modelChange();
	}
}
