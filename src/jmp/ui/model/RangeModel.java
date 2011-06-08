package jmp.ui.model;

import jmp.ui.mvc.Model;

public interface RangeModel extends Model
{
	public int getMaximum();
	public int getMinimum();
	public void setMaximum(int v);
	public void setMinimum(int v);
	public void update(int min, int max);
	public boolean isIn(int v);
}
