package jmp.ui.component.indicator.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorColoredRangeRenderer;
import jmp.ui.component.indicator.renderer.IndicatorDefaultRenderer;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.ModelComposit;


public class TestIndicatorColoredRangeComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private IndicatorView indicatorView;
	
	public TestIndicatorColoredRangeComponent()
	{
	}

	public void setup()
	{
		setTitle("IndicatorColorRange");
		this.setupIndicatorColoredComponentPane();
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
		
		this.progressSlider = new JSlider(JSlider.VERTICAL,0,100,0);
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
					((BoundedModel) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(progressSlider.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.LINE_END);
	}

	private void setupIndicatorColoredComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		this.indicatorView.setRenderer(new IndicatorColoredRangeRenderer(this.indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		model.addModel("colorRange", new IndicatorColoredRangeRenderingModel());
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.EAST));

		this.indicatorView.setModel(model);
		this.componentsPane.add(this.indicatorView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestIndicatorColoredRangeComponent app = new TestIndicatorColoredRangeComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
