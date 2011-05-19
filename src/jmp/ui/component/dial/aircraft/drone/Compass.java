package jmp.ui.component.dial.aircraft.drone;

import jmp.ui.component.dial.DialPictureRenderer;
import jmp.ui.component.dial.DialPictureRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.mvc.Model;

public class Compass extends DialView{

	private final static String BACKGROUND_PATH = "pictures/dial/drone/compass_background.png";
	private final static String NEEDLE_PATH = "pictures/dial/drone/compass_needle.png";
	
	public Compass() {
		super(setupModel());
		setRenderer(new DialPictureRenderer(this));
	}
	
	protected static Model setupModel() 
	{
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel(RENDERING_MODEL, new DialPictureRenderingModel(BACKGROUND_PATH, NEEDLE_PATH));
		model.addModel(VALUE_MODEL, new DefaultBoundedModel(0,360,0));
		return model;
	}
}