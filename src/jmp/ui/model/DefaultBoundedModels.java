package jmp.ui.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmp.ui.mvc.DefaultModel;

public class DefaultBoundedModels extends DefaultModel implements BoundedModels{
	private List<BoundedModel> models;
	

	public DefaultBoundedModels() {
		this.setModels(new ArrayList<BoundedModel>());
	}
	
	public int getValue(int n) {
		return models.get(n).getValue();
	}

	public void setValue(int n, int v) {
		if(models.get(n).getValue() == v) return;
		models.get(n).setValue(v);
		this.modelChange();
	}

	public void update(int n, int min, int max, int v) {
		if(models.get(n).getValue() == v && models.get(n).getMaximum()==max && models.get(n).getMinimum()==min) return;
		models.get(n).update(min, max, v);
		this.modelChange();
	}

	public void setModels(List<BoundedModel> models) {
		if (this.models == models) return;
		this.models = models;
		this.modelChange();
	}

	public List<BoundedModel> getModels() {
		return models;
	}

	public Iterator<BoundedModel> getIterator() {
		return models.iterator();
	}

	public int getSize() {
		return models.size();
	}
}
