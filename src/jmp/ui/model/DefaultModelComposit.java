package jmp.ui.model;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import jmp.ui.mvc.Model;
import jmp.ui.mvc.ModelListener;

public class DefaultModelComposit implements ModelComposit
{
	private Map<String, Model> models;

	public DefaultModelComposit()
	{
		this.models = new TreeMap<String, Model>();
	}

	public void addModelListener(ModelListener l)
	{
		for(Model m: this.models.values())
			m.addModelListener(l);
	}

	public void removeModelListener(ModelListener l)
	{
		for(Model m: this.models.values())
			m.removeModelListener(l);
	}

	public void addModel(String key, Model model)
	{
		this.models.put(key, model);
	}

	public void removeModel(String key)
	{
		this.models.remove(key);
	}

	public Model getModel(String key)
	{
		return this.models.get(key);
	}
	
	public Collection<Model> models()
	{
		return this.models.values();
	}

	public void modelChange()
	{	
	}
}
