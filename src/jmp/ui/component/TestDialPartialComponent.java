package jmp.ui.component;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.dial.DialPartialRenderer;
import jmp.ui.component.dial.DialPartialRenderingModel;
import jmp.ui.component.dial.DialPictureRenderer;
import jmp.ui.component.dial.DialPictureRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestDialPartialComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialPartialComponent()
	{
	}

	public void setup()
	{
		this.setupDialPartialComponentPane();
		this.setupSlidersPane();

		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(WindowEvent winEvt)
			{
				System.exit(0);
			}
		});

		this.pack();
		this.setVisible(true);
	}

	private void setupSlidersPane()
	{
		this.slidersPane = new JPanel();
		this.slidersPane.setLayout(new BoxLayout(this.slidersPane, BoxLayout.Y_AXIS));
		
		DialPartialRenderingModel renderingModel = new DialPartialRenderingModel();
		//this.progressSlider = new JSlider(JSlider.HORIZONTAL,0,360,0);
		this.progressSlider = new JSlider(JSlider.HORIZONTAL,renderingModel.getStartAngle(),renderingModel.getEndAngle(),renderingModel.getEndAngle());
		this.progressSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					dialView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialPartialComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
				
		this.dialView = new DialView();
		
		DefaultModelComposit model = new DefaultModelComposit();
		this.dialView.setRenderer(new DialPartialRenderer(this.dialView));
		DialPartialRenderingModel renderingModel = new DialPartialRenderingModel();
		model.addModel("rendering", renderingModel);
		model.addModel("picture", new DialPictureRenderingModel());
		//model.addModel("value", new DefaultBoundedModel(0,360,0));
		model.addModel("value", new DefaultBoundedModel(renderingModel.getStartAngle(),renderingModel.getEndAngle(),renderingModel.getEndAngle()));
		this.dialView.setModel(model);
		
		this.componentsPane.add(this.dialView);
		
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialPartialComponent app = new TestDialPartialComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
