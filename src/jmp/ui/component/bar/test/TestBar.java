package jmp.ui.component.bar.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.model.ModelComposit;

public class TestBar extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel barColoredPane;
	private JPanel barColoredRangeBackgroundPane;
	private JPanel barColoredRangeProgressPane;
	private JPanel barPicturePane;
	
	public TestBar()
	{
	}
	
	public void setup()
	{
		setTitle("Bar");
		this.setupBarColoredComponentsPane();
		this.setupBarColoredRangeBackgroundComponentsPane();
		this.setupBarColoredRangeProgressComponentsPane();
		this.setupBarPictureComponentsPane();
		
		this.setupTabbedPane();
	
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
	

	private void setupBarColoredComponentsPane()
	{
		this.barColoredPane = new JPanel();
		this.barColoredPane.setLayout(new BoxLayout(this.barColoredPane, BoxLayout.X_AXIS));
		
		final BarView barView = new BarView();
		ModelComposit model = (ModelComposit) barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		model.addModel("label", new BarLabelRenderingModel("BAR", CardinalPosition.NORTH));
		this.barColoredPane.add(barView);
		
		final JSlider progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		progressSlider.addChangeListener(new ChangeListener()
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
		
		this.barColoredPane.add(progressSlider);
		
	}
	
	private void setupBarColoredRangeBackgroundComponentsPane()
	{
		this.barColoredRangeBackgroundPane = new JPanel();
		this.barColoredRangeBackgroundPane.setLayout(new BoxLayout(this.barColoredRangeBackgroundPane, BoxLayout.X_AXIS));
		
		final BarView barView = new BarView();
		ModelComposit model = (ModelComposit) barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		model.addModel("coloredRangeBackground", new BarColoredRangeRenderingModel());
		this.barColoredRangeBackgroundPane.add(barView);

		final JSlider progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		progressSlider.addChangeListener(new ChangeListener()
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
		
		this.barColoredRangeBackgroundPane.add(progressSlider);
	}
	
	private void setupBarColoredRangeProgressComponentsPane()
	{
		this.barColoredRangeProgressPane = new JPanel();
		this.barColoredRangeProgressPane.setLayout(new BoxLayout(this.barColoredRangeProgressPane, BoxLayout.X_AXIS));
		
		final BarView barView = new BarView();
		ModelComposit model = (ModelComposit) barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		model.addModel("coloredRangeProgress", new BarColoredRangeRenderingModel());
		this.barColoredRangeProgressPane.add(barView);

		final JSlider progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		progressSlider.addChangeListener(new ChangeListener()
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
		
		this.barColoredRangeProgressPane.add(progressSlider);
	}
	
	private void setupBarPictureComponentsPane()
	{
		this.barPicturePane = new JPanel();
		this.barPicturePane.setLayout(new BoxLayout(this.barPicturePane, BoxLayout.X_AXIS));
		
		final BarView barView = new BarView();
		ModelComposit model = (ModelComposit) barView.getModel();
		model.addModel("picture", new BarPictureRenderingModel());
		model.addModel("needle", new BarNeedleRenderingModel());
		this.barPicturePane.add(barView);

		final JSlider progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		progressSlider.addChangeListener(new ChangeListener()
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
		
		this.barPicturePane.add(progressSlider);
	}
	
	private void setupTabbedPane()
	{
		this.tabbedPane = new JTabbedPane();
		this.getContentPane().add(this.tabbedPane, BorderLayout.PAGE_START);
		this.tabbedPane.addTab("BarColored", this.barColoredPane);
		this.tabbedPane.addTab("BarColoredRangeBackground", this.barColoredRangeBackgroundPane);
		this.tabbedPane.addTab("BarColoredRangeProgress", this.barColoredRangeProgressPane);
		this.tabbedPane.addTab("BarPicture", this.barPicturePane);
	}

	
	public static void main(String[] args)
	{
		final TestBar app = new TestBar();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
