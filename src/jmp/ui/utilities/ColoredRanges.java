package jmp.ui.utilities;

import java.util.HashSet;
import java.util.Set;

public class ColoredRanges
{
	private Set<ColoredRange> ranges;
	
	public ColoredRanges()
	{
		this.ranges = new HashSet<ColoredRange>();
	}
	
	public void addRange(ColoredRange cr)
	{
		this.ranges.add(cr);
	}
	
	public void removeRange(ColoredRange cr)
	{
		this.removeRange(cr);
	}
	
	public ColoredRange getRange(int v)
	{

		for(ColoredRange cr:this.ranges)
		{
			if (cr.isRightColor(v))
				return cr;
		}
		return null;
	}
	
	public Set<ColoredRange> getRanges(int v)
	{
		HashSet<ColoredRange> res = new HashSet<ColoredRange>();
		for(ColoredRange cr:this.ranges)
		{
			if (cr.isRightColor(v))
				res.add(cr);
		}
		return res;
	}
}
