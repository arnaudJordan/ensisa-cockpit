package jmp.ui.component;

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

import jmp.ui.component.dial.DialColoredRenderer;
import jmp.ui.component.dial.DialColoredRenderingModel;
import jmp.ui.component.dial.DialPartialRenderer;
import jmp.ui.component.dial.DialPartialRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;
import jmp.ui.utilities.Range;


public class TestDialColoredComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialColoredComponent()
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
		
		this.progressSlider = new JSlider(JSlider.HORIZONTAL,-90,0,0);
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
		this.dialView.setRenderer(new DialColoredRenderer(this.dialView));
		DefaultModelComposit model = new DefaultModelComposit();
		DialColoredRenderingModel colorModel = new DialColoredRenderingModel();
		ColoredRanges colorRanges = new ColoredRanges();
		colorRanges.addRange(new ColoredRange(-10, 30, Color.PINK));
		colorRanges.addRange(new ColoredRange(30, 60, Color.GREEN));
		colorRanges.addRange(new ColoredRange(60, 90, Color.RED));
		colorModel.setColorRanges(colorRanges);
		model.addModel("rendering", colorModel);
		model.addModel("value", new DefaultBoundedModel(-90,0,0));
		
		this.dialView.setModel(model);
		this.componentsPane.add(this.dialView);
		
		
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialColoredComponent app = new TestDialColoredComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
