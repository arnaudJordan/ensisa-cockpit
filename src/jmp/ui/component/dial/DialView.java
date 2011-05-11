package jmp.ui.component.dial;

import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Model;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class DialView extends View {
	protected final static String RENDERING_MODEL = "rendering";
	protected final static String VALUE_MODEL = "value";

	public DialView() 
	{
		super(setupModel());
	}

	protected static Model setupModel() 
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel(RENDERING_MODEL, new DialRenderingModel());
		model.addModel(VALUE_MODEL, new DefaultBoundedModel(0,100,0));
		return model;
	}
	protected DialRenderingModel renderingModel()
	{
		return (DialRenderingModel) ((ModelComposit)this.getModel()).getModel(RENDERING_MODEL);
	}

	protected DialDefaultRenderer dialViewRenderer()
	{
		return (DialDefaultRenderer) this.renderer();
	}
	
	protected Renderer defaultRenderer(View view)
	{
		return new DialDefaultRenderer(view);
	}
	public BoundedModel valueModel()
	{
		return (BoundedModel) ((ModelComposit)this.getModel()).getModel(VALUE_MODEL);
	}

}
