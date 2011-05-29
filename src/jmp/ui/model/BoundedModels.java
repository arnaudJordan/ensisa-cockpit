package jmp.ui.model;

import jmp.ui.mvc.Model;

public interface BoundedModels extends Model{
	public int getValue(int n);
	public void setValue(int n, int v);
	public void update(int n, int min, int max, int v)	;
}
