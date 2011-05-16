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

import jmp.ui.component.dial.DialHorizonRenderer;
import jmp.ui.component.dial.DialHorizonRenderingModel;
import jmp.ui.component.dial.DialPictureRenderer;
import jmp.ui.component.dial.DialPictureRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestDialHorizonComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSliderX, progressSliderRot;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialHorizonComponent()
	{
	}

	public void setup()
	{
		this.setupDialHorizonComponentPane();
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
		
		
		
		this.progressSliderX = new JSlider(JSlider.HORIZONTAL,-180,180,0);
		this.progressSliderX.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("pitch")).setValue(progressSliderX.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderX);
		this.getContentPane().add(this.slidersPane, BorderLayout.AFTER_LAST_LINE);
		

		this.progressSliderRot = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		this.progressSliderRot.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("roll")).setValue(progressSliderRot.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderRot);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialHorizonComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.dialView = new DialView();
		this.dialView.setRenderer(new DialHorizonRenderer(this.dialView));
		DefaultModelComposit model = new DefaultModelComposit();
		model.addModel("rendering", new DialHorizonRenderingModel());
		model.addModel("pitch", new DefaultBoundedModel(-180,180,0));
		model.addModel("roll", new DefaultBoundedModel(-100,100,0));
		this.dialView.setModel(model);
		this.componentsPane.add(this.dialView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialHorizonComponent app = new TestDialHorizonComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
