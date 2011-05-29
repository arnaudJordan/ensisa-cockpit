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
		 models.get(n).setValue(v);		
	}

	public void update(int n, int min, int max, int v) {
		models.get(n).update(min, max, v);		
	}

	public void setModels(List<BoundedModel> models) {
		this.models = models;
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
