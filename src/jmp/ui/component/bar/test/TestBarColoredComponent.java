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

import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.mvc.Model;


public class TestBarColoredComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private BarView progressView;

	public TestBarColoredComponent()
	{
	}

	public void setup()
	{
		setTitle("BarColored");
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
		

		this.progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
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
					progressView.valueModel().setValue(progressSlider.getValue());
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
		
		this.progressView = new BarView();
		ModelComposit model = (ModelComposit) this.progressView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("border", new BarBorderRenderingModel());
		this.componentsPane.add(this.progressView);

		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestBarColoredComponent app = new TestBarColoredComponent();

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
