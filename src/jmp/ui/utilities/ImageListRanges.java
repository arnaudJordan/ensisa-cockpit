package jmp.ui.utilities;

import java.util.HashSet;
import java.util.Set;

public class ImageListRanges
{
	private Set<ImageListRange> ranges;
	
	public ImageListRanges()
	{
		this.ranges = new HashSet<ImageListRange>();
	}
	
	public void addRange(ImageListRange cr)
	{
		this.ranges.add(cr);
	}
	
	public void removeRange(ImageListRange cr)
	{
		this.removeRange(cr);
	}
	
	public ImageListRange getRange(int v)
	{

		for(ImageListRange cr:this.ranges)
		{
			if (cr.isRightImageList(v))
				return cr;
		}
		return null;
	}
	public Set<ImageListRange> getRanges()
	{
		return ranges;
	}
	public Set<ImageListRange> getRanges(int v)
	{
		HashSet<ImageListRange> res = new HashSet<ImageListRange>();
		for(ImageListRange cr:this.ranges)
		{
			if (cr.isRightImageList(v))
				res.add(cr);
		}
		return res;
	}
}
