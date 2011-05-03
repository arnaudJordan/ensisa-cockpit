package jmp.ui.mvc;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public  class View extends JComponent implements ModelListener
{
	private Model model;
	private Controller controller;
	private Renderer renderer;
	
	public View(Model model)
	{
		this.initialize(model);
	}

	private void initialize(Model model)
	{
		this.controller = this.defaultController(null);
		this.setModel(model);
		this.controller.view(this);
		this.renderer = this.defaultRenderer(this);
		this.addMouseListener(this.controller);
		this.addMouseMotionListener(this.controller);
		this.addMouseWheelListener(this.controller);
		this.addKeyListener(this.controller);
	}
	
	public void setModel(Model model)
	{
		if (model == null) return;
		if (this.model != null)
		{
			if (this.model == model) return;
			this.model.removeModelListener(this);
		}
		
		this.model = model;
		this.model.addModelListener(this);
		this.controller.setModel(model);
	}

	public Model getModel()
	{
		return this.model;
	}
	
	protected Controller defaultController(Model model)
	{
		return new Controller(model);
	}
	
	final public Controller getController()
	{
		return this.controller;
	}

	public void setRenderer(Renderer renderer)
	{
		this.renderer = renderer;
		this.renderer.setView(this);
	}

	final public Renderer renderer()
	{
		return this.renderer;
	}

	protected  Renderer defaultRenderer(View view)
	{
		return new DefaultRenderer(view);
	}
	
	public Dimension getMinimumSize()
	{		
		return this.renderer.getMinimumSize();
	}

	public Dimension getPreferredSize()
	{
		return this.renderer.getPreferredSize();
	}
	
	public Dimension getMaximumSize()
	{
		return this.renderer.getMaximumSize();
	}

	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Model model = this.getModel();
		if (model != null && this.renderer != null)
			this.renderer.renderView((Graphics2D)g);
	}

	public void onChanged(Model m)
	{
		this.repaint();
	}
}


