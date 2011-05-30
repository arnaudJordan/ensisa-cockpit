package jmp.ui.component.demo.progress;

import jmp.ui.component.Orientation;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class SimpleProgressView extends View
{
	protected final static String VALUE_MODEL = "values";
	protected final static String RENDERING_MODEL = "rendering";
	private Orientation orientation;
	
	public SimpleProgressView(Orientation orientation)
	{
		super(setupModel());
		this.orientation = orientation;
	}
			
	protected static DefaultModelComposit setupModel()
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel(VALUE_MODEL, new DefaultBoundedModel(0,100,0));
		model.addModel(RENDERING_MODEL, new SimpleProgressRenderingModel());
		return model;
	}
	
	public BoundedModel valueModel()
	{
		return (BoundedModel) ((ModelComposit)this.getModel()).getModel(VALUE_MODEL);
	}
	
	protected SimpleProgressRenderingModel renderingModel()
	{
		return (SimpleProgressRenderingModel) ((ModelComposit)this.getModel()).getModel(RENDERING_MODEL);
	}

	protected SimpleProgressViewRenderer progressViewRenderer()
	{
		return (SimpleProgressViewRenderer) this.renderer();
	}
	
	protected Renderer defaultRenderer(View view)
	{
		return new SimpleProgressViewRenderer(view);
	}
	
	public Orientation orientation()
	{
		return this.orientation;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
	
}
