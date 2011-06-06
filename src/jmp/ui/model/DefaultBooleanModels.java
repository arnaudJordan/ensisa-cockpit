package jmp.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmp.ui.mvc.DefaultModel;
import jmp.ui.mvc.Model;

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
		if(models.get(n).is() == state) return;
		models.get(n).setState(state);
		this.modelChange();
	}

	public void setModels(List<BooleanModel> models) {
		if (this.models == models) return;
		this.models = models;
		this.modelChange();
	}

	public List<BooleanModel> getModels() {
		return models;
	}

	public Iterator<BooleanModel> getIterator() {
		return models.iterator();
	}
	public int getSize() {
		return models.size();
	}
}
