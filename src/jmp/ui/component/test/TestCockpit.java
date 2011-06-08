package jmp.ui.component.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.Rotation;
import jmp.ui.component.bar.BarView;
import jmp.ui.component.bar.model.BarBorderRenderingModel;
import jmp.ui.component.bar.model.BarColoredRangeRenderingModel;
import jmp.ui.component.bar.model.BarColoredRenderingModel;
import jmp.ui.component.bar.model.BarLabelRenderingModel;
import jmp.ui.component.bar.model.BarNeedleRenderingModel;
import jmp.ui.component.bar.model.BarPictureRenderingModel;
import jmp.ui.component.bar.model.BarTicksRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.aircraft.Horizon;
import jmp.ui.component.dial.aircraft.drone.Compass;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialCompositRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPartialRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.renderer.DialCompositRenderer;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorBlinkRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorBlinkMultiRenderer;
import jmp.ui.component.indicator.renderer.IndicatorBlinkRenderer;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultBoundedModels;
import jmp.ui.model.DefaultModelComposit;
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
		setSize(800, 600);
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
		sliderPane.setPreferredSize(new Dimension(800,200));
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
					barVertical1View.valueModel().setValue(progressSlider.getValue());
					barVertical2View.valueModel().setValue(progressSlider.getValue());
					compass.valueModel().setValue(progressSlider.getValue());
					horizon.setPitch(progressSlider.getValue());
					dialView.valueModel().setValue(progressSlider.getValue());
					dialPartialView.valueModel().setValue(progressSlider.getValue());
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(0, progressSlider.getValue());
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(1, progressSlider.getValue());
					((DefaultBoundedModel)((ModelComposit) ((ModelComposit) dialView.getModel()).getModel("composit")).getModel("value")).setValue(progressSlider.getValue());
				}
			}
		});
		sliderPane.add(progressSlider);
		
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
		
		sliderPane.add(new JLabel("Compass"));
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
		
		sliderPane.add(new JLabel("Pitch"));
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
		
		sliderPane.add(new JLabel("Roll"));
		sliderPane.add(rollSlider);
		
		final JSlider speedSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
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
		
		sliderPane.add(new JLabel("Speed"));
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
		
		sliderPane.add(new JLabel("Battery"));
		sliderPane.add(batterySlider);
		
		final JSlider altitudeSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		altitudeSlider.setToolTipText("Altitude");
		altitudeSlider.setMajorTickSpacing(25);
		altitudeSlider.setMinorTickSpacing(10);
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
		
		sliderPane.add(new JLabel("Altitude"));
		sliderPane.add(altitudeSlider);
		this.getContentPane().add(sliderPane);
		
		final JSlider temperatureSlider1 = new JSlider(JSlider.HORIZONTAL,0,100,0);
		temperatureSlider1.setToolTipText("Temperature");
		temperatureSlider1.setMajorTickSpacing(25);
		temperatureSlider1.setMinorTickSpacing(10);
		temperatureSlider1.setPaintTicks(true);
		temperatureSlider1.setPaintLabels(true);
		temperatureSlider1.setPaintTrack(true);
		temperatureSlider1.setPaintTicks(true);
		temperatureSlider1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					barVertical2View.valueModel().setValue(temperatureSlider1.getValue());
				}
			}
		});
		
		sliderPane.add(new JLabel("Temperature"));
		sliderPane.add(temperatureSlider1);
		this.getContentPane().add(sliderPane);
		sliderPane.setBounds(0, 450, sliderPane.getPreferredSize().width, sliderPane.getPreferredSize().height);
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
			imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
			imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
			imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
			
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
		blinkMultiPane.setBounds(500, 180, 60, 200);
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
		
		
		
		
		
		
		
		/*this.dialView.setRenderer(new DialDefaultRenderer(this.dialView));
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/background-mini.png", "pictures/dial/needle-mini.png");
		this.dialView.renderingModel().setSense(Rotation.Clockwise);
		this.dialView.renderingModel().setTicksStartAngle(90);
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setPosition(new Point(0, -dialPictureRenderingModel.getBackground().getHeight()/6));
		dialLabelRenderingModel.setColor(Color.WHITE);
		dialLabelRenderingModel.setFont(new Font("Monospaced", Font.PLAIN, 18));
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.BLACK);
		dialTicksRenderingModel.setMinorGraduationColor(Color.BLACK);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("border", new DialBorderRenderingModel(3));
		model.addModel("label", dialLabelRenderingModel);
*/
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
		componentsPane.add(new JSeparator(SwingConstants.VERTICAL));
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
		componentsPane.setBounds(480, 405, 200, 50);
	}
	private void setupBarPictureVerticalComponentsPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
		
		this.barVertical1View = new BarView();
		ModelComposit model = (ModelComposit) this.barVertical1View.getModel();
		this.barVertical1View.renderingModel().setOrientation(Orientation.Vertical);
		model.addModel("picture", new BarPictureRenderingModel("pictures/bar/default_background-mini.png"));
		model.addModel("needle", new BarNeedleRenderingModel("pictures/bar/default_needle-mini.png"));
		model.addModel("label", new BarLabelRenderingModel("Altitude", CardinalPosition.SOUTH));
		model.addModel("value", new DefaultBoundedModel(0, 100, 0));
		componentsPane.add(this.barVertical1View);
		
		this.barVertical2View = new BarView();
		model = (ModelComposit) this.barVertical2View.getModel();
		this.barVertical2View.renderingModel().setOrientation(Orientation.Vertical);
		model.addModel("picture", new BarPictureRenderingModel("pictures/bar/default_background-mini.png"));
		model.addModel("needle", new BarNeedleRenderingModel("pictures/bar/default_needle-mini.png"));
		//model.addModel("label", new BarLabelRenderingModel("BAR", CardinalPosition.NORTH));
		componentsPane.add(this.barVertical2View);
		this.getContentPane().add(componentsPane);
		componentsPane.setBounds(380, 230, 80, 220);
	}
	private void setupDialPartialComponentPane()
	{
		JPanel componentsPane = new JPanel();
		componentsPane.setLayout(new BoxLayout(componentsPane, BoxLayout.X_AXIS));
				
		this.dialPartialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) this.dialPartialView.getModel();
		this.dialPartialView.setRenderer(new DialDefaultRenderer(this.dialPartialView));
		this.dialPartialView.renderingModel().setSense(Rotation.Clockwise);
		DialPartialRenderingModel partialModel = new DialPartialRenderingModel(0,-135);
		this.dialPartialView.renderingModel().setTicksStartAngle(partialModel.getEndAngle());
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setMinorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		model.addModel("partial", partialModel);
		model.addModel("picture", new DialPictureRenderingModel("pictures/dial/background-mini.png", "pictures/dial/needle-mini.png"));
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", new DialLabelRenderingModel("Speed", new Point(0, -30)));
		model.addModel("value", new DefaultBoundedModel(0,100,0));
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
