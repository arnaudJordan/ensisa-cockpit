package jmp.ui.component.dial.aircraft;

import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialHorizonRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.renderer.DialHorizonRenderer;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.mvc.Model;

public class Horizon extends DialView {
	
	public Horizon() {
		super(setupModel());
		setRenderer(new DialHorizonRenderer(this));
	}
	
	protected static Model setupModel() 
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel("rendering", new DialHorizonRenderingModel());
		model.addModel("ticks", new DialTicksRenderingModel());
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("pitch", new DefaultBoundedModel(-180,180,0));
		model.addModel("roll", new DefaultBoundedModel(-100,100,0));
		return model;
	}
}
