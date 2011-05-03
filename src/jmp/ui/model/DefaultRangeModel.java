package jmp.ui.model;

import jmp.ui.mvc.DefaultModel;


public class DefaultRangeModel extends DefaultModel implements RangeModel
{
	protected int min, max;
	
	public DefaultRangeModel(int min, int max)
	{
		this.checkRange(min, max);
		this.min=min;
		this.max = max;
	}
	
	protected void checkRange(int min, int max)
	{
		if (max <= min) throw new IllegalArgumentException("invalid range properties");
	}

	public int getMaximum()
	{
		return this.max;
	}
	
	public int getMinimum()
	{
		return this.min;
	}
	
	public void setMaximum(int v)
	{
		if (this.max == v) return;
		this.checkRange(this.min, v);
		this.max = v;
		this.modelChange();
	}
	
	public void setMinimum(int v)
	{
		if(this.min == v) return;
		this.checkRange(v, this.max);
		this.min = v;
		this.modelChange();
	}
	
	public void update(int min, int max)
	{
		if (this.min == min && this.max == max) return;
		this.checkRange(min, max);
		this.min = min;
		this.max = max;
		this.modelChange();
	}
	
	public boolean isIn(int v)
	{
		return v >= this.min && v <= this.max;
	}
	
	public String toString()  {
        String modelString =
            "min=" + this.min + ", " +
            "max=" + this.max;

        return getClass().getName() + "[" + modelString + "]";
    }
}
