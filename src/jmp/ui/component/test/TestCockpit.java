package jmp.ui.component.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.component.dial.aircraft.Horizon;
import jmp.ui.component.dial.aircraft.drone.Compass;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorBlinkRenderer;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.ModelComposit;
import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.ModelComposit;

public class TestCockpit extends JFrame{
	private Compass compass;
	private Horizon horizon;


	private JPanel Pane;
	private BarView barView;
	private IndicatorView indicatorView;
	
	public TestCockpit()
	{
	}

	public void setup()
	{
		setTitle("Cockpit");
		this.Pane = new JPanel();
		this.Pane.setLayout(new FlowLayout());
		this.setupCompassComponentPane();
		this.setupHorizonComponentPane();
		this.setupBarColoredRangeComponentsPane();
		this.setupIndicatorBlinkComponentPane();
		this.setupSlidersPane();
		
		this.getContentPane().add(this.Pane);
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
					compass.valueModel().setValue(progressSlider.getValue());
					horizon.setPitch(progressSlider.getValue());
					((BoundedModel) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(progressSlider.getValue());
				}
			}
		});
		
		this.Pane.add(progressSlider);
	}
	private void setupIndicatorBlinkComponentPane()
	{
		JPanel blinkPane = new JPanel();
		blinkPane.setLayout(new BoxLayout(blinkPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorBlinkRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		
		BufferedImage image1;
		BufferedImage image2;
		try {
			image1 = ImageIO.read(new File("pictures/indicator/default_on.png"));
			image2 = ImageIO.read(new File("pictures/indicator/default_off.png"));
			ImageList imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			ImageList imageList2 = new ImageList();
			imageList2.add(image2);
			
			ImageList imageList0 = new ImageList();
			imageList0.add(image1);
			
			ImageListRanges imageListRanges = new ImageListRanges();
			imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
			imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
			imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
			
			
			model.addModel("blink", new IndicatorBlinkRenderingModel(imageListRanges));
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.EAST));

		indicatorView.setModel(model);
		blinkPane.add(indicatorView);
		this.Pane.add(blinkPane, BorderLayout.CENTER);
	}
	private void setupCompassComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.compass = new Compass();
		componentsPane.add(this.compass);

		this.Pane.add(componentsPane, BorderLayout.CENTER);
	}
	private void setupHorizonComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.horizon = new Horizon();
		componentsPane.add(this.horizon);
		this.Pane.add(componentsPane, BorderLayout.CENTER);
	}
	private void setupBarColoredRangeComponentsPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.barView = new BarView();
		ModelComposit model = (ModelComposit) this.barView.getModel();
		model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("border", new BarBorderRenderingModel());
		model.addModel("ticks", new BarTicksRenderingModel());
		model.addModel("label", new BarLabelRenderingModel("BAR", CardinalPosition.WEST));
		model.addModel("coloredRangeProgress", new BarColoredRangeRenderingModel());
		componentsPane.add(this.barView);

		this.Pane.add(componentsPane, BorderLayout.CENTER);
	}
	
	public static void main(String[] args)
	{
		final TestCockpit app = new TestCockpit();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
