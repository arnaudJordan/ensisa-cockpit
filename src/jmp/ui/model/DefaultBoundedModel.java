package jmp.ui.model;



public class DefaultBoundedModel extends DefaultRangeModel implements BoundedModel
{
	protected int value;
	
	public DefaultBoundedModel(int min, int max, int value)
	{
		super(min, max);
		this.checkValue(value);
		this.value = value;
	}
	
	protected void checkBoundedModel(int min, int max, int value)
	{
		this.checkRange(min, max);
		this.checkValue(value);
	}

	protected void checkValue(int value)
	{
		if (!this.isIn(value)) throw new IllegalArgumentException("invalid value properties");
	}
	
	public int getValue()
	{
		return this.value;
	}
	
	public void setValue(int value)
	{
		if (this.value == value) return;
		this.checkValue(value);
		this.value = value;
		this.modelChange();		
	}

	public void update(int min, int max, int value)
	{
		if(this.getMinimum() == min && this.getMaximum() == max && this.value == value) return;
		this.checkBoundedModel(min, max, value);
		this.min = min;
		this.max = max;
		this.value = value;
		this.modelChange();		
	}
	
	public String toString()  {
        String modelString =
            "min=" + this.min + ", " +
            "max=" + this.max + ", " +
            "value=" + this.value;

        return getClass().getName() + "[" + modelString + "]";
    }
}
