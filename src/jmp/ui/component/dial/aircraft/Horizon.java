package jmp.ui.component.dial.aircraft;

import jmp.ui.component.dial.DialHorizonRenderer;
import jmp.ui.component.dial.DialHorizonRenderingModel;
import jmp.ui.component.dial.DialView;
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
		model.addModel("pitch", new DefaultBoundedModel(-180,180,0));
		model.addModel("roll", new DefaultBoundedModel(-100,100,0));
		return model;
	}
}
