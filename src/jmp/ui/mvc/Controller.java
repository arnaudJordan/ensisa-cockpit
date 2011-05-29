package jmp.ui.mvc;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Controller implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener
{
    public static final int LEFT_MOUSE_BUTTON   = MouseEvent.BUTTON1_MASK;
    public static final int MIDDLE_MOUSE_BUTTON = MouseEvent.BUTTON2_MASK;
    public static final int RIGHT_MOUSE_BUTTON  = MouseEvent.BUTTON3_MASK;
 
    private Model model;
	private View view;
		
	public Controller(Model newModel)
	{
		this.model = newModel;
		this.view = null;
	}
	
	public void view(View view)
	{
		this.view = view;
	}
	
	final public View view()
	{
		return this.view;
	}
	
	public void setModel(Model model)
	{
		this.model = model;
	}
	
	public Model model()
	{
		return this.model;
	}
		
	public void mousePressed(MouseEvent e)
	{
	}

	public void mouseReleased(MouseEvent e)
	{
	}

	public void mouseClicked(MouseEvent e)
	{
	}
	
	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}
	
	public void mouseMoved(MouseEvent e)
	{
	}
	
	public void mouseDragged(MouseEvent e)
	{
	}
	
	public void keyTyped(KeyEvent e)
	{
	}
	
	public void keyPressed(KeyEvent e)
	{
	}

	public void keyReleased(KeyEvent e)
	{
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{	
	}
}
