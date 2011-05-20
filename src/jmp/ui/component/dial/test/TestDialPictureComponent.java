package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.DialBorderRenderingModel;
import jmp.ui.component.dial.DialLabelRenderingModel;
import jmp.ui.component.dial.DialPictureRenderer;
import jmp.ui.component.dial.DialPictureRenderingModel;
import jmp.ui.component.dial.DialTicksRenderingModel;
import jmp.ui.component.dial.DialTrackRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestDialPictureComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialPictureComponent()
	{
	}

	public void setup()
	{
		this.setupDialPictureComponentPane();
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
		
		this.progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		this.progressSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					dialView.valueModel().setValue(progressSlider.getValue());
					if(progressSlider.getValue()==50)
					{
						DefaultModelComposit model = (DefaultModelComposit) dialView.getModel();
						//model.removeModel("label");
						((DialLabelRenderingModel) model.getModel("label")).setLabel("tesjbgfjdxfxckfj");
						dialView.renderingModel().setChanged(true);
					}
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialPictureComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.dialView = new DialView();
		this.dialView.setRenderer(new DialPictureRenderer(this.dialView));
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		this.dialView.renderingModel().setSense(Rotation.Anticlockwise);
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,00));
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("ticks", new DialTicksRenderingModel());
		model.addModel("label", new DialLabelRenderingModel());
		model.addModel("track", new DialTrackRenderingModel());

		this.dialView.setModel(model);
		this.componentsPane.add(this.dialView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialPictureComponent app = new TestDialPictureComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
