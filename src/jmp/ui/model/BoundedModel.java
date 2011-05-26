package jmp.ui.model;

public interface BoundedModel extends RangeModel
{
	public int getValue();
	public void setValue(int v);
	public void update(int min, int max, int v)	;
}
