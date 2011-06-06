package jmp.ui.model;

import java.util.Iterator;

import jmp.ui.mvc.Model;

public interface BooleanModels extends Models{
	public void set(int n);
	public void reset(int n);
	public boolean is(int n);
	public void setState(int n, boolean state);
	public Iterator<BooleanModel> getIterator();
}
