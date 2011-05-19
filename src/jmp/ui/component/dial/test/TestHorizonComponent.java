package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.aircraft.Horizon;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestHorizonComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSliderPitch;
	private JSlider progressSliderRoll;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestHorizonComponent()
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
		
		
		
		this.progressSliderPitch = new JSlider(JSlider.HORIZONTAL,-180,180,0);
		progressSliderPitch.setMajorTickSpacing(90);
		progressSliderPitch.setMinorTickSpacing(30);
		progressSliderPitch.setPaintTicks(true);
		progressSliderPitch.setPaintLabels(true);
		progressSliderPitch.setPaintTrack(true);
		progressSliderPitch.setPaintTicks(true);
		this.progressSliderPitch.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("pitch")).setValue(progressSliderPitch.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderPitch);
		this.getContentPane().add(this.slidersPane, BorderLayout.AFTER_LAST_LINE);
		

		this.progressSliderRoll = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		progressSliderRoll.setMajorTickSpacing(50);
		progressSliderRoll.setMinorTickSpacing(10);
		progressSliderRoll.setPaintTicks(true);
		progressSliderRoll.setPaintLabels(true);
		progressSliderRoll.setPaintTrack(true);
		progressSliderRoll.setPaintTicks(true);
		this.progressSliderRoll.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("roll")).setValue(progressSliderRoll.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderRoll);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialPictureComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.dialView = new Horizon();
		this.componentsPane.add(this.dialView);

		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestHorizonComponent app = new TestHorizonComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
