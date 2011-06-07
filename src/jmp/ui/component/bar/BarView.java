package jmp.ui.component.bar;

import jmp.ui.component.bar.model.BarRenderingModel;
import jmp.ui.component.bar.renderer.BarDefaultRenderer;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class BarView extends View
{
	protected final static String VALUE_MODEL = "values";
	protected final static String RENDERING_MODEL = "rendering";
	
	public BarView()
	{
		super(setupModel());
	}
			
	protected static DefaultModelComposit setupModel()
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel(VALUE_MODEL, new DefaultBoundedModel(0,100,0));
		model.addModel(RENDERING_MODEL, new BarRenderingModel());
		return model;
	}
	
	public BoundedModel valueModel()
	{
		return (BoundedModel) ((ModelComposit)this.getModel()).getModel(VALUE_MODEL);
	}
	
	public BarRenderingModel renderingModel()
	{
		return (BarRenderingModel) ((ModelComposit)this.getModel()).getModel(RENDERING_MODEL);
	}

	protected BarDefaultRenderer barViewRenderer()
	{
		return (BarDefaultRenderer) this.renderer();
	}
	
	protected Renderer defaultRenderer(View view)
	{
		return new BarDefaultRenderer(view);
	}	
}
