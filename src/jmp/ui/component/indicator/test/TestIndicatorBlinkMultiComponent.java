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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBlinkMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorBlinkMultiRenderer;
import jmp.ui.model.BoundedModel;
import jmp.ui.model.BoundedModels;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultBoundedModels;
import jmp.ui.model.ModelComposit;
import jmp.ui.utilities.ImageList;
import jmp.ui.utilities.ImageListRange;
import jmp.ui.utilities.ImageListRanges;


public class TestIndicatorBlinkMultiComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider1;
	private JSlider progressSlider2;
	private JSlider progressSlider3;
	private JSlider progressSlider4;

	private JPanel componentsPane;
	private IndicatorView indicatorView;
	
	public TestIndicatorBlinkMultiComponent()
	{
	}

	public void setup()
	{
		setTitle("IndicatorBlinkMulti");
		this.setupIndicatorBlinkComponentPane();
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
		
		this.progressSlider1 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider1.setMajorTickSpacing(50);
		progressSlider1.setMinorTickSpacing(10);
		progressSlider1.setPaintTicks(true);
		progressSlider1.setPaintLabels(true);
		progressSlider1.setPaintTrack(true);
		progressSlider1.setPaintTicks(true);
		this.progressSlider1.addChangeListener(new ChangeListener()
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
		
		this.slidersPane.add(this.progressSlider1);
		this.progressSlider2 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider2.setMajorTickSpacing(50);
		progressSlider2.setMinorTickSpacing(10);
		progressSlider2.setPaintTicks(true);
		progressSlider2.setPaintLabels(true);
		progressSlider2.setPaintTrack(true);
		progressSlider2.setPaintTicks(true);
		this.progressSlider2.addChangeListener(new ChangeListener()
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
		
		this.slidersPane.add(this.progressSlider2);
		this.progressSlider3 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider3.setMajorTickSpacing(50);
		progressSlider3.setMinorTickSpacing(10);
		progressSlider3.setPaintTicks(true);
		progressSlider3.setPaintLabels(true);
		progressSlider3.setPaintTrack(true);
		progressSlider3.setPaintTicks(true);
		this.progressSlider3.addChangeListener(new ChangeListener()
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
		
		this.slidersPane.add(this.progressSlider3);
		this.progressSlider4 = new JSlider(JSlider.VERTICAL,0,100,0);
		progressSlider4.setMajorTickSpacing(50);
		progressSlider4.setMinorTickSpacing(10);
		progressSlider4.setPaintTicks(true);
		progressSlider4.setPaintLabels(true);
		progressSlider4.setPaintTrack(true);
		progressSlider4.setPaintTicks(true);
		this.progressSlider4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((BoundedModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setValue(3, progressSlider4.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider4);
		this.getContentPane().add(this.slidersPane, BorderLayout.LINE_END);
	}

	private void setupIndicatorBlinkComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		this.indicatorView.setRenderer(new IndicatorBlinkMultiRenderer(this.indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		DefaultBoundedModels valueModel = new DefaultBoundedModels();
		List<BoundedModel> list = new ArrayList<BoundedModel>();
		list.add(new DefaultBoundedModel(0,100,0));
		list.add(new DefaultBoundedModel(0,100,0));
		list.add(new DefaultBoundedModel(0,100,0));
		//list.add(new DefaultBoundedModel(0,100,0));
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
			
//			imageListRanges = new ImageListRanges();
//			imageListRanges.addRange(new ImageListRange(0, 25, imageList0));
//			imageListRanges.addRange(new ImageListRange(25, 75, imageList1));
//			imageListRanges.addRange(new ImageListRange(75, 100, imageList2));
//			imageListRangesList.add(imageListRanges);
			
			List<IndicatorLabelRenderingModel> labels = new ArrayList<IndicatorLabelRenderingModel>();
			labels.add(new IndicatorLabelRenderingModel("LED 1", CardinalPosition.WEST));
			labels.add(new IndicatorLabelRenderingModel("LED 2", CardinalPosition.SOUTH));
			labels.add(new IndicatorLabelRenderingModel("LED3", CardinalPosition.WEST));
			model.addModel("orientation", new IndicatorOrientationRenderingModel(Orientation.Horizontal));
			model.addModel("labels", new IndicatorLabelMultiRenderingModel(labels));
			model.addModel("blinkMulti", new IndicatorBlinkMultiRenderingModel(imageListRangesList));
			//model.addModel("border", new IndicatorBorderRenderingModel());
		} catch (IOException e) {
			e.printStackTrace();
			
		}

		this.indicatorView.setModel(model);
		this.componentsPane.add(this.indicatorView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestIndicatorBlinkMultiComponent app = new TestIndicatorBlinkMultiComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
