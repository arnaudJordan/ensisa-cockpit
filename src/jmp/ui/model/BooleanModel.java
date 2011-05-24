package jmp.ui.model;

import jmp.ui.mvc.Model;

public interface BooleanModel extends Model
{
	public void set();
	public void reset();
	public boolean is();
}
