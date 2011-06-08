package jmp.ui.component.bar.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRangeRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorBlinkMultiRenderer;
import jmp.ui.component.indicator.renderer.IndicatorBlinkRenderer;
import jmp.ui.component.indicator.renderer.IndicatorColoredRangeRenderer;
import jmp.ui.component.indicator.renderer.IndicatorDefaultRenderer;
import jmp.ui.component.indicator.renderer.IndicatorMultiRenderer;
import jmp.ui.component.indicator.test.TestIndicator;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.DefaultBooleanModel;
import jmp.ui.model.DefaultBooleanModels;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultBoundedModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;

public class TestBar extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel barColoredPane;
	private JPanel barColoredRangePane;
	private JPanel barPicturePane;
	
	public TestBar()
	{
	}
	
	public void setup()
	{
		setTitle("Bar");
		this.setupBarColoredComponentsPane();
		this.setupBarColoredRangeComponentsPane();
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
	
	private void setupBarColoredRangeComponentsPane()
	{
		this.barColoredRangePane = new JPanel();
		this.barColoredRangePane.setLayout(new BoxLayout(this.barColoredRangePane, BoxLayout.X_AXIS));
		
		final BarView barView = new BarView();
		ModelComposit model = (ModelComposit) barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		model.addModel("coloredRangeProgress", new BarColoredRangeRenderingModel());
		this.barColoredRangePane.add(barView);

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
		
		this.barColoredRangePane.add(progressSlider);
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
		this.tabbedPane.addTab("BarColoredRange", this.barColoredRangePane);
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
