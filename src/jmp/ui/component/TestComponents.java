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

import jmp.ui.component.demo.progress.SimpleProgressView;


public class TestComponents extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private SimpleProgressView progressViewH;
	private SimpleProgressView progressViewV;

	public TestComponents()
	{
	}

	public void setup()
	{
		this.setupComponentsPane();
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
		this.progressSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					progressViewH.valueModel().setValue(progressSlider.getValue());
					progressViewV.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupComponentsPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.progressViewH = new SimpleProgressView(Orientation.Horizontal);
		this.progressViewV = new SimpleProgressView(Orientation.Vertical);

		this.componentsPane.add(this.progressViewH);
		this.componentsPane.add(this.progressViewV);

		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestComponents app = new TestComponents();

		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
