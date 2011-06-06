package jmp.ui.model;

import java.util.Iterator;

import jmp.ui.mvc.Model;

public interface BoundedModels extends Models{
	public int getValue(int n);
	public void setValue(int n, int v);
	public void update(int n, int min, int max, int v);
	public Iterator<BoundedModel> getIterator();
}
