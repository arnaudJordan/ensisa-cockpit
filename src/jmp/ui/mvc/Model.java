package jmp.ui.mvc;

public interface Model
{
	public void addModelListener(ModelListener l);
	public void removeModelListener(ModelListener l);
	public void modelChange();

}
