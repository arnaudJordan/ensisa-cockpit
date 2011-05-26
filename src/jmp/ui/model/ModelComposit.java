package jmp.ui.model;

import java.util.Collection;

import jmp.ui.mvc.Model;

public interface ModelComposit extends Model 
{
	public void addModel(String key, Model model);
	public void removeModel(String key);
	public Model getModel(String key);
	public Collection<Model>models();
}
