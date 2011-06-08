package jmp.ui.mvc;

import java.util.ArrayList;
import java.util.List;


public class DefaultModel implements Model
{
	private List<ModelListener> listeners;

	public DefaultModel()
	{
		this.listeners = new ArrayList<ModelListener>();	
	}
	
	public void addModelListener(ModelListener l)
	{
		this.listeners.add(l);
	}

	public void removeModelListener(ModelListener l)
	{
		this.listeners.remove(l);
	}
	
	public void modelChange()
	{
		for(ModelListener m:this.listeners)
			m.onChanged(this);
	}

}
