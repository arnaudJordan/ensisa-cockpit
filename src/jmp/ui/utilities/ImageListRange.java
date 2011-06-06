package jmp.ui.utilities;


public class ImageListRange
{
	public final Range range;
	public ImageList imageList;
	
	public ImageListRange(int min, int max, ImageList imageList)
	{
		this.range = new Range(min, max);
		this.imageList = imageList;
	}
	
	boolean isRightImageList(int v)
	{
		return this.range.isIn(v);
	}
}
