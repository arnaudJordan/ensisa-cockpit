package jmp.ui.model;

import java.util.ArrayList;
import java.util.List;

import jmp.ui.mvc.DefaultModel;
import jmp.ui.mvc.ModelListener;

public class DefaultBooleanModels extends DefaultModel implements BooleanModels {
	private List<BooleanModel> models;
	

	public DefaultBooleanModels() {
		this.setModels(new ArrayList<BooleanModel>());
	}
	
	public DefaultBooleanModels(List<BooleanModel> models) {
		this.setModels(models);
	}

	public void set(int n) {
		setState(n, true);
	}

	public void reset(int n) {
		setState(n, false);
	}

	public boolean is(int n) {
		return models.get(n).is();
	}

	public void setState(int n, boolean state) {
		models.get(n).setState(state);
	}

	public void setModels(List<BooleanModel> models) {
		this.models = models;
	}

	public List<BooleanModel> getModels() {
		return models;
	}

}
