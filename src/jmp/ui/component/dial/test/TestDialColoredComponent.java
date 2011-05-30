package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialColoredRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.renderer.DialColoredRenderer;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;


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
		setTitle("DialColored");
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
		
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		
		ColoredRanges colorRanges = new ColoredRanges();
		colorRanges.addRange(new ColoredRange(0, 30, Color.PINK));
		colorRanges.addRange(new ColoredRange(30, 60, Color.GREEN));
		colorRanges.addRange(new ColoredRange(60, 100, Color.RED));
		DialColoredRenderingModel colorModel = new DialColoredRenderingModel();
		colorModel.setColorRanges(colorRanges);
		colorModel.setMargin(10);
		DialPartialRenderingModel partialModel = new DialPartialRenderingModel();
		this.dialView.renderingModel().setSense(Rotation.Anticlockwise);
		this.dialView.renderingModel().setTicksStartAngle(partialModel.getStartAngle());
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setPosition(new Point(dialPictureRenderingModel.getBackground().getHeight()/6, dialPictureRenderingModel.getBackground().getHeight()/6));
		model.addModel("partial", partialModel);
		model.addModel("colored", colorModel);
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("label", dialLabelRenderingModel);
		model.addModel("ticks", new DialTicksRenderingModel());
		
		this.dialView.setModel(model);
		this.dialView.setRenderer(new DialDefaultRenderer(this.dialView));
		
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
