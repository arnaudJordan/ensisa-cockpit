package jmp.ui.component.bar.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.ModelComposit;


public class TestBarPictureComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private BarView barView;

	public TestBarPictureComponent()
	{
	}

	public void setup()
	{
		setTitle("BarPicture");
		this.setupBarColoredComponentsPane();
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
		

		this.progressSlider = new JSlider(JSlider.HORIZONTAL,0,50,0);
		this.progressSlider.setMajorTickSpacing(50);
		this.progressSlider.setMinorTickSpacing(10);
		this.progressSlider.setPaintTicks(true);
		this.progressSlider.setPaintLabels(true);
		this.progressSlider.setPaintTrack(true);
		this.progressSlider.setPaintTicks(true);
		this.progressSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					barView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupBarColoredComponentsPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.barView = new BarView();
		this.barView.renderingModel().setOrientation(Orientation.Vertical);
		ModelComposit model = (ModelComposit) this.barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("picture", new BarPictureRenderingModel());
		model.addModel("needle", new BarNeedleRenderingModel());
		model.addModel("border", new BarBorderRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		//model.addModel("coloredRangeBackground", new BarColoredRangeRenderingModel());
		//model.addModel("coloredRangeProgress", new BarColoredRangeRenderingModel());
		model.addModel("label", new BarLabelRenderingModel("BAR", CardinalPosition.NORTH));
		model.addModel("value", new DefaultBoundedModel(0, 100, 0));
		this.barView.setModel(model);
		
		this.componentsPane.add(this.barView);

		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestBarPictureComponent app = new TestBarPictureComponent();

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
