package jmp.ui.utilities;

public class Range
{
	public final int min;
	public final int max;
	
	
	public Range(int min, int max)
	{
		this.min = min;
		this.max = max;
	}
	
	public boolean isIn(int v)
	{
		return v >= this.min && v <= this.max;
	}
}
