package jmp.ui.component.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.Rotation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.aircraft.Horizon;
import jmp.ui.component.dial.aircraft.drone.Compass;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialColoredRenderingModel;
import jmp.ui.component.dial.model.DialCompositRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.renderer.DialCompositRenderer;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorBlinkMultiRenderer;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultBoundedModels;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;
import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;
import jmp.ui.model.BoundedModel;

public class TestCockpit extends JFrame{
	private Compass compass;
	private Horizon horizon;


	private BarView barView;
	private IndicatorView indicatorView;
	private DialView dialView;
	private DialView dialPartialView;
	private BarView barVertical1View;
	private BarView barVertical2View;
	
	public TestCockpit()
	{
	}

	public void setup()
	{
		setTitle("Cockpit");
		setSize(1024, 768);
		setPreferredSize(getSize());
		this.getContentPane().setLayout(null);
		this.setupCompassComponentPane();
		this.setupDialPartialComponentPane();
		this.setupDialPictureComponentPane();
		this.setupIndicatorBlinkComponentPane();
		
		this.setupHorizonComponentPane();
		this.setupBarPictureVerticalComponentsPane();
		this.setupBarPictureComponentsPane();
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
		JPanel sliderPane = new JPanel();
		sliderPane.setPreferredSize(new Dimension(1000,600));
		final JSlider compassSlider = new JSlider(JSlider.HORIZONTAL,0,360,0);
		compassSlider.setToolTipText("Compass");
		compassSlider.setMajorTickSpacing(90);
		compassSlider.setMinorTickSpacing(30);
		compassSlider.setPaintTicks(true);
		compassSlider.setPaintLabels(true);
		compassSlider.setPaintTrack(true);
		compassSlider.setPaintTicks(true);
		compassSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					compass.valueModel().setValue(compassSlider.getValue());
				}
			}
		});
		
		compassSlider.setBorder(BorderFactory.createTitledBorder("Compass"));
		sliderPane.add(compassSlider);
		
		final JSlider pitchSlider = new JSlider(JSlider.HORIZONTAL,-180,180,0);
		pitchSlider.setToolTipText("CPitch");
		pitchSlider.setMajorTickSpacing(90);
		pitchSlider.setMinorTickSpacing(30);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setPaintLabels(true);
		pitchSlider.setPaintTrack(true);
		pitchSlider.setPaintTicks(true);
		pitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					horizon.setPitch(pitchSlider.getValue());
					dialView.valueModel().setValue(pitchSlider.getValue());
				}
			}
		});
		
		pitchSlider.setBorder(BorderFactory.createTitledBorder("Pitch"));
		sliderPane.add(pitchSlider);
		
		final JSlider rollSlider = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		rollSlider.setToolTipText("Roll");
		rollSlider.setMajorTickSpacing(50);
		rollSlider.setMinorTickSpacing(10);
		rollSlider.setPaintTicks(true);
		rollSlider.setPaintLabels(true);
		rollSlider.setPaintTrack(true);
		rollSlider.setPaintTicks(true);
		rollSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					horizon.setRoll(rollSlider.getValue());
					((DefaultBoundedModel)((ModelComposit) ((ModelComposit) dialView.getModel()).getModel("composit")).getModel("value")).setValue(rollSlider.getValue());
				}
			}
		});
		
		rollSlider.setBorder(BorderFactory.createTitledBorder("Roll"));
		sliderPane.add(rollSlider);
		
		final JSlider speedSlider = new JSlider(JSlider.HORIZONTAL,0,50,0);
		speedSlider.setToolTipText("Speed");
		speedSlider.setMajorTickSpacing(50);
		speedSlider.setMinorTickSpacing(10);
		speedSlider.setPaintTicks(true);
		speedSlider.setPaintLabels(true);
		speedSlider.setPaintTrack(true);
		speedSlider.setPaintTicks(true);
		speedSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					dialPartialView.valueModel().setValue(speedSlider.getValue());
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(0, speedSlider.getValue());
				}
			}
		});
		
		speedSlider.setBorder(BorderFactory.createTitledBorder("Speed"));
		sliderPane.add(speedSlider);
		
		final JSlider batterySlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		batterySlider.setToolTipText("Battery");
		batterySlider.setMajorTickSpacing(50);
		batterySlider.setMinorTickSpacing(10);
		batterySlider.setPaintTicks(true);
		batterySlider.setPaintLabels(true);
		batterySlider.setPaintTrack(true);
		batterySlider.setPaintTicks(true);
		batterySlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					barView.valueModel().setValue(batterySlider.getValue());
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(1, batterySlider.getValue());
				}
			}
		});
		
		batterySlider.setBorder(BorderFactory.createTitledBorder("Battery"));
		sliderPane.add(batterySlider);
		
		final JSlider altitudeSlider = new JSlider(JSlider.HORIZONTAL,0,15,0);
		altitudeSlider.setToolTipText("Altitude");
		altitudeSlider.setMajorTickSpacing(5);
		altitudeSlider.setMinorTickSpacing(1);
		altitudeSlider.setPaintTicks(true);
		altitudeSlider.setPaintLabels(true);
		altitudeSlider.setPaintTrack(true);
		altitudeSlider.setPaintTicks(true);
		altitudeSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					barVertical1View.valueModel().setValue(altitudeSlider.getValue());
				}
			}
		});
		
		altitudeSlider.setBorder(BorderFactory.createTitledBorder("Altitude"));
		sliderPane.add(altitudeSlider);
		this.getContentPane().add(sliderPane);
		
		final JSlider trimSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		trimSlider.setToolTipText("Trim");
		trimSlider.setMajorTickSpacing(25);
		trimSlider.setMinorTickSpacing(10);
		trimSlider.setPaintTicks(true);
		trimSlider.setPaintLabels(true);
		trimSlider.setPaintTrack(true);
		trimSlider.setPaintTicks(true);
		trimSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					barVertical2View.valueModel().setValue(trimSlider.getValue());
				}
			}
		});
		
		trimSlider.setBorder(BorderFactory.createTitledBorder("Trim"));
		sliderPane.add(trimSlider);
		this.getContentPane().add(sliderPane);
		sliderPane.setBounds(0, 550, sliderPane.getPreferredSize().width, sliderPane.getPreferredSize().height);
	}
	private void setupIndicatorBlinkComponentPane()
	{
		JPanel blinkMultiPane = new JPanel();
		blinkMultiPane.setLayout(new BoxLayout(blinkMultiPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorBlinkMultiRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		DefaultBoundedModels valueModel = new DefaultBoundedModels();
		List<BoundedModel> list = new ArrayList<BoundedModel>();
		list.add(new DefaultBoundedModel(0,100,0));
		list.add(new DefaultBoundedModel(0,100,0));
		valueModel.setModels(list);
		model.addModel("value", valueModel);
		
		BufferedImage image1;
		BufferedImage image2;
		try {
			image1 = ImageIO.read(new File("pictures/indicator/default_on-mini.png"));
			image2 = ImageIO.read(new File("pictures/indicator/default_off-mini.png"));
			ImageList imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			ImageList imageList2 = new ImageList();
			imageList2.add(image2);
			
			ImageList imageList0 = new ImageList();
			imageList0.add(image1);
			
			ImageListRanges imageListRanges = new ImageListRanges();
			imageListRanges.addRange(new ImageListRange(0, 40, imageList0));
			imageListRanges.addRange(new ImageListRange(40, 80, imageList1));
			imageListRanges.addRange(new ImageListRange(80, 100, imageList2));
			
			List<ImageListRanges> imageListRangesList=new ArrayList<ImageListRanges>();
			imageListRangesList.add(imageListRanges);
			
			imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			imageList2 = new ImageList();
			imageList2.add(image2);
			
			imageList0 = new ImageList();
			imageList0.add(image1);
			
			imageListRanges = new ImageListRanges();
			imageListRanges.addRange(new ImageListRange(0, 33, imageList0));
			imageListRanges.addRange(new ImageListRange(33, 66, imageList1));
			imageListRanges.addRange(new ImageListRange(66, 100, imageList2));
			imageListRangesList.add(imageListRanges);
			
			imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			imageList2 = new ImageList();
			imageList2.add(image2);
			
			imageList0 = new ImageList();
			imageList0.add(image1);
			
			
			List<IndicatorLabelRenderingModel> labels = new ArrayList<IndicatorLabelRenderingModel>();
			labels.add(new IndicatorLabelRenderingModel("ON", CardinalPosition.NORTH));
			labels.add(new IndicatorLabelRenderingModel("LOW", CardinalPosition.NORTH));
			model.addModel("orientation", new IndicatorOrientationRenderingModel(Orientation.Vertical));
			model.addModel("labels", new IndicatorLabelMultiRenderingModel(labels));
			model.addModel("blinkMulti", new IndicatorBlinkMultiRenderingModel(imageListRangesList));
		} catch (IOException e) {
			e.printStackTrace();
			
		}

		indicatorView.setModel(model);
		blinkMultiPane.add(indicatorView);
		this.getContentPane().add(blinkMultiPane);
		blinkMultiPane.setBounds(550, 180, this.indicatorView.getPreferredSize().width, this.indicatorView.getPreferredSize().height);
	}
	private void setupDialPictureComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.dialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		this.dialView.setRenderer(new DialCompositRenderer(this.dialView));
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/background-mini.png", "pictures/dial/needle-mini.png");
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setColor(Color.WHITE);
		dialLabelRenderingModel.setPosition(new Point(0, -dialPictureRenderingModel.getBackground().getHeight()/6));
		dialLabelRenderingModel.setLabel("Pitch");
		dialLabelRenderingModel.setFont(new Font("Monospaced", Font.PLAIN, 14));
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("label", dialLabelRenderingModel);
		model.addModel("value", new DefaultBoundedModel(-180,180,0));
		
		DefaultModelComposit compositModel = new DefaultModelComposit();
		DialPictureRenderingModel dialCompositPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/intern_background-mini.png", "pictures/dial/default_intern_needle-mini.png");
		compositModel.addModel("rendering", new DialCompositRenderingModel(new Point(30,20)));
		compositModel.addModel("picture", dialCompositPictureRenderingModel);
		//compositModel.addModel("border", new DialBorderRenderingModel());
		compositModel.addModel("value", new DefaultBoundedModel(-100,100,0));
		
		model.addModel("composit", compositModel);

		this.dialView.setModel(model);
		componentsPane.add(this.dialView);
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(580, 30, this.dialView.getPreferredSize().width, this.dialView.getPreferredSize().height);
	}
	private void setupCompassComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.compass = new Compass();
		componentsPane.add(this.compass);
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(80, 100, this.compass.getPreferredSize().width, this.compass.getPreferredSize().height);
	}
	private void setupHorizonComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.horizon = new Horizon();
		componentsPane.add(this.horizon);
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(70, 280, this.horizon.getPreferredSize().width, this.horizon.getPreferredSize().height);
	}
	private void setupBarPictureComponentsPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.barView = new BarView();
		ModelComposit model = (ModelComposit) this.barView.getModel();
		//model.addModel("colored", new BarColoredRenderingModel());
		model.addModel("picture", new BarPictureRenderingModel("pictures/bar/default_background-mini.png"));
		model.addModel("needle", new BarNeedleRenderingModel());
		model.addModel("label", new BarLabelRenderingModel("Battery", CardinalPosition.NORTH));
		componentsPane.add(this.barView);

		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(530, 390, this.barView.getPreferredSize().width, this.barView.getPreferredSize().height);
	}
	private void setupBarPictureVerticalComponentsPane()
	{
		JPanel componentsPane = new JPanel();
		
		componentsPane.setLayout(new BorderLayout());
		
		this.barVertical1View = new BarView();
		ModelComposit model = (ModelComposit) this.barVertical1View.getModel();
		this.barVertical1View.renderingModel().setOrientation(Orientation.Vertical);
		model.addModel("picture", new BarPictureRenderingModel("pictures/bar/default_background-mini.png"));
		model.addModel("needle", new BarNeedleRenderingModel("pictures/bar/default_needle-mini.png"));
		model.addModel("label", new BarLabelRenderingModel("Altitude", CardinalPosition.SOUTH));
		BarTicksRenderingModel ticks = new BarTicksRenderingModel();
		ticks.setMajorGraduationColor(Color.BLACK);
		ticks.setMinorGraduationColor(Color.GRAY);
		ticks.setMajorTickSize(20);
		ticks.setMinorTickSize(10);
		ticks.setMajorTickSpacing(5);
		ticks.setMinorTickSpacing(1);
		model.addModel("ticks", ticks);
		model.addModel("value", new DefaultBoundedModel(0, 15, 0));
		this.barVertical1View.setModel(model);
		componentsPane.add(this.barVertical1View, BorderLayout.WEST);
		
		componentsPane.setPreferredSize(new Dimension(160,this.barVertical1View.getPreferredSize().height));
		
		this.barVertical2View = new BarView();
		model = (ModelComposit) this.barVertical2View.getModel();
		this.barVertical2View.renderingModel().setOrientation(Orientation.Vertical);
		model.addModel("picture", new BarPictureRenderingModel("pictures/bar/default_background-mini.png"));
		model.addModel("needle", new BarNeedleRenderingModel("pictures/bar/default_needle-mini.png"));
		model.addModel("label", new BarLabelRenderingModel("Trim", CardinalPosition.SOUTH));
		componentsPane.add(this.barVertical2View, BorderLayout.EAST);
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(350, 230, componentsPane.getPreferredSize().width, componentsPane.getPreferredSize().height);
	}
	private void setupDialPartialComponentPane()
	{
		JPanel componentsPane = new JPanel();
		//componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
				
		this.dialPartialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) this.dialPartialView.getModel();
		this.dialPartialView.setRenderer(new DialDefaultRenderer(this.dialPartialView));
		this.dialPartialView.renderingModel().setSense(Rotation.Clockwise);
		DialPartialRenderingModel partialModel = new DialPartialRenderingModel(0,225);
		this.dialPartialView.renderingModel().setTicksStartAngle(partialModel.getEndAngle());
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setMinorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		ColoredRanges colorRanges = new ColoredRanges();
		colorRanges.addRange(new ColoredRange(0, 20, Color.GREEN));
		colorRanges.addRange(new ColoredRange(20, 40, Color.YELLOW));
		colorRanges.addRange(new ColoredRange(40, 50, Color.RED));
		DialColoredRenderingModel colorModel = new DialColoredRenderingModel();
		colorModel.setColorRanges(colorRanges);
		colorModel.setThickness(10);
		DialTicksRenderingModel ticksModel = new DialTicksRenderingModel();
		ticksModel.setMajorGraduationColor(Color.WHITE);
		ticksModel.setMinorGraduationColor(Color.WHITE);
		ticksModel.setMajorTickSize(20);
		ticksModel.setMinorTickSize(10);
		ticksModel.setMajorTickSpacing(10);
		ticksModel.setMinorTickSpacing(2);
		ticksModel.setLabelColor(Color.WHITE);
		ticksModel.setLabelFont(new Font("Monospaced", Font.PLAIN, 12));
		ticksModel.setLabelSpace(3);
		model.addModel("colored", colorModel);
		model.addModel("partial", partialModel);
		model.addModel("picture", new DialPictureRenderingModel("pictures/dial/background-mini.png", "pictures/dial/needle-mini.png"));
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", new DialLabelRenderingModel("Speed", new Point(0, -20)));
		model.addModel("ticks", ticksModel);
		model.addModel("value", new DefaultBoundedModel(0,50,0));
		this.dialPartialView.setModel(model);
		
		componentsPane.add(this.dialPartialView);
		
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(350, 30, this.dialPartialView.getPreferredSize().width, this.dialPartialView.getPreferredSize().height);
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
