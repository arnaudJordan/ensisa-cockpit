package jmp.ui.utilities;

import java.awt.Color;


public class ColoredRange
{
	public final Range range;
	public Color color;
	
	public ColoredRange(int min, int max, Color color)
	{
		this.range = new Range(min, max);
		this.color = color;
	}
	
	boolean isRightColor(int v)
	{
		return this.range.isIn(v);
	}
}
