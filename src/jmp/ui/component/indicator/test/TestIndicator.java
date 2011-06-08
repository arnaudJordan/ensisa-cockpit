package jmp.ui.component.indicator.test;

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



public class TestIndicator extends JFrame{
	
	private JTabbedPane tabbedPane;
	private JPanel blinkPane;
	private JPanel blinkMultiPane;
	private JPanel coloredPane;
	private JPanel colorRangePane;
	private JPanel picturePane;
	private JPanel pictureMultiPane;
	
	public TestIndicator()
	{
	}
	
	public void setup()
	{
		setTitle("Indicator");
		this.setupIndicatorBlinkComponentPane();
		this.setupIndicatorBlinkMultiComponentPane();
		this.setupIndicatorColoredComponentPane();
		this.setupIndicatorColoredRangeComponentPane();
		this.setupIndicatorPictureMultiComponentPane();
		this.setupIndicatorPictureComponentPane();
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
	
	private void setupIndicatorPictureComponentPane()
	{
		this.picturePane = new JPanel();
		this.picturePane.setLayout(new BoxLayout(this.picturePane, BoxLayout.X_AXIS));
		
		final IndicatorView indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorDefaultRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		model.addModel("color", new IndicatorColoredRenderingModel());
		model.addModel("picture", new IndicatorPictureRenderingModel());
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.NORTH));

		indicatorView.setModel(model);
		this.picturePane.add(indicatorView);
		
		final JCheckBox checkBox = new JCheckBox();
		checkBox.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				indicatorView.valueModel().setState(checkBox.isSelected());
			}
		});
		
		this.picturePane.add(checkBox);
	}
	private void setupTabbedPane()
	{
		this.tabbedPane = new JTabbedPane();
		this.getContentPane().add(this.tabbedPane, BorderLayout.PAGE_START);
		this.tabbedPane.addTab("Blink", this.blinkPane);
		this.tabbedPane.addTab("BlinkMulti", this.blinkMultiPane);
		this.tabbedPane.addTab("Colored", this.coloredPane);
		this.tabbedPane.addTab("ColoredRange", this.colorRangePane);
		this.tabbedPane.addTab("PictureMulti", this.pictureMultiPane);
		this.tabbedPane.addTab("Picture", this.picturePane);
	}
	private void setupIndicatorPictureMultiComponentPane()
	{
		this.pictureMultiPane = new JPanel();
		this.pictureMultiPane.setLayout(new BoxLayout(this.pictureMultiPane, BoxLayout.X_AXIS));
		
		final IndicatorView indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorMultiRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		DefaultBooleanModels valueModel = new DefaultBooleanModels();
		List<BooleanModel> list = new ArrayList<BooleanModel>();
		list.add(new DefaultBooleanModel());
		list.add(new DefaultBooleanModel());
		list.add(new DefaultBooleanModel());
		valueModel.setModels(list);
		model.addModel("value", valueModel);
		//model.addModel("picture", new IndicatorPictureRenderingModel());
		model.addModel("color", new IndicatorColoredRenderingModel());
		
		List<IndicatorLabelRenderingModel> labels = new ArrayList<IndicatorLabelRenderingModel>();
		labels.add(new IndicatorLabelRenderingModel("LED 1", CardinalPosition.WEST));
		labels.add(new IndicatorLabelRenderingModel("LED 2", CardinalPosition.SOUTH));
		labels.add(new IndicatorLabelRenderingModel("LED 3", CardinalPosition.WEST));
		
		model.addModel("labels", new IndicatorLabelMultiRenderingModel(labels));
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("orientation", new IndicatorOrientationRenderingModel(Orientation.Horizontal));
		
		indicatorView.setModel(model);
		this.pictureMultiPane.add(indicatorView);
		
		final JCheckBox checkBox1 = new JCheckBox();
		checkBox1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(0, checkBox1.isSelected());
			}
		});
		
		this.pictureMultiPane.add(checkBox1);
		final JCheckBox checkBox2 = new JCheckBox();
		checkBox2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(1, checkBox2.isSelected());
			}
		});
		
		this.pictureMultiPane.add(checkBox2);
		final JCheckBox checkBox3 = new JCheckBox();
		checkBox3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(2, checkBox3.isSelected());
			}
		});
		
		this.pictureMultiPane.add(checkBox3);
		
	}
	private void setupIndicatorColoredRangeComponentPane()
	{
		this.colorRangePane = new JPanel();
		this.colorRangePane.setLayout(new BoxLayout(this.colorRangePane, BoxLayout.X_AXIS));
		final IndicatorView coloredRangeView = new IndicatorView();
		coloredRangeView.setRenderer(new IndicatorColoredRangeRenderer(coloredRangeView));
		ModelComposit model = (ModelComposit) coloredRangeView.getModel();
		model.addModel("colorRange", new IndicatorColoredRangeRenderingModel());
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.EAST));

		coloredRangeView.setModel(model);
		this.colorRangePane.add(coloredRangeView);
		
		final JSlider progressSlider = new JSlider(JSlider.VERTICAL,0,100,0);
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
					((BoundedModel) ((ModelComposit) coloredRangeView.getModel()).getModel("value")).setValue(progressSlider.getValue());
				}
			}
		});
		
		this.colorRangePane.add(progressSlider);
	}
	private void setupIndicatorBlinkMultiComponentPane()
	{
		this.blinkMultiPane = new JPanel();
		this.blinkMultiPane.setLayout(new BoxLayout(this.blinkMultiPane, BoxLayout.X_AXIS));
		
		final IndicatorView indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorBlinkMultiRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		DefaultBoundedModels valueModel = new DefaultBoundedModels();
		List<BoundedModel> list = new ArrayList<BoundedModel>();
		list.add(new DefaultBoundedModel(0,100,0));
		list.add(new DefaultBoundedModel(0,100,0));
		list.add(new DefaultBoundedModel(0,100,0));
		valueModel.setModels(list);
		model.addModel("value", valueModel);
		
		BufferedImage image1;
		BufferedImage image2;
		try {
			image1 = ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + "pictures/indicator/default_on.png"));
			image2 = ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + "pictures/indicator/default_off.png"));
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
			imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
			imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
			imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
			imageListRangesList.add(imageListRanges);
			
			imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			imageList2 = new ImageList();
			imageList2.add(image2);
			
			imageList0 = new ImageList();
			imageList0.add(image1);
			
			imageListRanges = new ImageListRanges();
			imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
			imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
			imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
			imageListRangesList.add(imageListRanges);
			
			imageList1 = new ImageList();
			imageList1.add(image1);
			imageList1.add(image2);
			
			imageList2 = new ImageList();
			imageList2.add(image2);
			
			imageList0 = new ImageList();
			imageList0.add(image1);
			
			
			List<IndicatorLabelRenderingModel> labels = new ArrayList<IndicatorLabelRenderingModel>();
			labels.add(new IndicatorLabelRenderingModel("LED 1", CardinalPosition.WEST));
			labels.add(new IndicatorLabelRenderingModel("LED 2", CardinalPosition.SOUTH));
			labels.add(new IndicatorLabelRenderingModel("LED3", CardinalPosition.WEST));
			model.addModel("orientation", new IndicatorOrientationRenderingModel(Orientation.Vertical));
			model.addModel("labels", new IndicatorLabelMultiRenderingModel(labels));
			model.addModel("blinkMulti", new IndicatorBlinkMultiRenderingModel(imageListRangesList));
		} catch (IOException e) {
			e.printStackTrace();
			
		}

		indicatorView.setModel(model);
		blinkMultiPane.add(indicatorView);
		
		final JSlider progressSlider1 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider1.setMajorTickSpacing(50);
		progressSlider1.setMinorTickSpacing(10);
		progressSlider1.setPaintTicks(true);
		progressSlider1.setPaintLabels(true);
		progressSlider1.setPaintTrack(true);
		progressSlider1.setPaintTicks(true);
		progressSlider1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(0, progressSlider1.getValue());
				}
			}
		});
		
		blinkMultiPane.add(progressSlider1);
		final JSlider progressSlider2 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider2.setMajorTickSpacing(50);
		progressSlider2.setMinorTickSpacing(10);
		progressSlider2.setPaintTicks(true);
		progressSlider2.setPaintLabels(true);
		progressSlider2.setPaintTrack(true);
		progressSlider2.setPaintTicks(true);
		progressSlider2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(1, progressSlider2.getValue());
				}
			}
		});
		
		this.blinkMultiPane.add(progressSlider2);
		final JSlider progressSlider3 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider3.setMajorTickSpacing(50);
		progressSlider3.setMinorTickSpacing(10);
		progressSlider3.setPaintTicks(true);
		progressSlider3.setPaintLabels(true);
		progressSlider3.setPaintTrack(true);
		progressSlider3.setPaintTicks(true);
		progressSlider3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(2, progressSlider3.getValue());
				}
			}
		});
		
		this.blinkMultiPane.add(progressSlider3);
	}
	private void setupIndicatorBlinkComponentPane()
	{
		this.blinkPane = new JPanel();
		this.blinkPane.setLayout(new BoxLayout(this.blinkPane, BoxLayout.X_AXIS));
		
		final IndicatorView indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorBlinkRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		
		BufferedImage image1;
		BufferedImage image2;
		try {
			image1 = ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + "pictures/indicator/default_on.png"));
			image2 = ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + "pictures/indicator/default_off.png"));
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
		this.blinkPane.add(indicatorView);
		
		
		JPanel slidersPane = new JPanel();
		slidersPane.setLayout(new BoxLayout(slidersPane, BoxLayout.Y_AXIS));
		
		final JSlider progressSlider = new JSlider(JSlider.VERTICAL,0,100,0);
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
					((BoundedModel) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(progressSlider.getValue());
				}
			}
		});
		
		this.blinkPane.add(progressSlider);		
	}
	private void setupIndicatorColoredComponentPane()
	{
		this.coloredPane = new JPanel();
		this.coloredPane.setLayout(new BoxLayout(this.coloredPane, BoxLayout.X_AXIS));
		
		final IndicatorView indicatorView = new IndicatorView();
		indicatorView.setRenderer(new IndicatorDefaultRenderer(indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		model.addModel("color", new IndicatorColoredRenderingModel());
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.EAST));

		indicatorView.setModel(model);
		this.coloredPane.add(indicatorView);
		
		final JCheckBox checkBox = new JCheckBox();
		checkBox.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				indicatorView.valueModel().setState(checkBox.isSelected());
			}
		});
		
		coloredPane.add(checkBox);
	}
	
	public static void main(String[] args)
	{
		final TestIndicator app = new TestIndicator();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
