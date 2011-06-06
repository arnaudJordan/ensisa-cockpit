package jmp.ui.component.indicator;

import jmp.ui.component.dial.model.DialRenderingModel;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.component.indicator.model.IndicatorRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorDefaultRenderer;
import jmp.ui.component.indicator.renderer.IndicatorRenderer;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBooleanModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Model;
import jmp.ui.mvc.Renderer;
import jmp.ui.mvc.View;

public class IndicatorView extends View {
	protected final static String RENDERING_MODEL = "rendering";
	protected final static String VALUE_MODEL = "value";

	public IndicatorView() 
	{
		super(setupModel());
	}
	public IndicatorView(Model model)
	{
		super(model);
	}

	protected static Model setupModel() 
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel(RENDERING_MODEL, new IndicatorRenderingModel());
		model.addModel(VALUE_MODEL, new DefaultBooleanModel());
		return model;
	}
	public IndicatorRenderingModel renderingModel()
	{
		return (IndicatorRenderingModel) ((ModelComposit)this.getModel()).getModel(RENDERING_MODEL);
	}

	protected IndicatorRenderer dialViewRenderer()
	{
		return (IndicatorRenderer) this.renderer();
	}
	
	protected Renderer defaultRenderer(View view)
	{
		return new IndicatorDefaultRenderer(view);
	}
	public BooleanModel valueModel()
	{
		return (BooleanModel) ((ModelComposit)this.getModel()).getModel(VALUE_MODEL);
	}
}
