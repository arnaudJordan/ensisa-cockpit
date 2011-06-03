package jmp.ui.component.indicator.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.Orientation;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelMultiRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.model.IndicatorOrientationRenderingModel;
import jmp.ui.component.indicator.model.IndicatorPictureRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorMultiRenderer;
import jmp.ui.model.BooleanModel;
import jmp.ui.model.BooleanModels;
import jmp.ui.model.DefaultBooleanModel;
import jmp.ui.model.DefaultBooleanModels;
import jmp.ui.model.ModelComposit;


public class TestIndicatorPictureMultiComponent extends JFrame
{
	private JPanel slidersPane;
	private JCheckBox checkBox1;
	private JCheckBox checkBox2;
	private JCheckBox checkBox3;
	private JCheckBox checkBox4;
	

	private JPanel componentsPane;
	private IndicatorView indicatorView;
	
	public TestIndicatorPictureMultiComponent()
	{
	}

	public void setup()
	{
		setTitle("IndicatorPictureMulti");
		this.setupIndicatorPictureComponentPane();
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
		
		this.checkBox1 = new JCheckBox();
		this.checkBox1.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(0, checkBox1.isSelected());
			}
		});
		
		this.slidersPane.add(this.checkBox1);
		this.checkBox2 = new JCheckBox();
		this.checkBox2.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(1, checkBox2.isSelected());
			}
		});
		
		this.slidersPane.add(this.checkBox2);
		this.checkBox3 = new JCheckBox();
		this.checkBox3.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(2, checkBox3.isSelected());
			}
		});
		
		this.slidersPane.add(this.checkBox3);
		this.checkBox4 = new JCheckBox();
		this.checkBox4.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				((BooleanModels) ((ModelComposit) indicatorView.getModel()).getModel("value")).setState(3, checkBox4.isSelected());
			}
		});
		
		this.slidersPane.add(this.checkBox4);
		
		this.getContentPane().add(this.slidersPane, BorderLayout.AFTER_LINE_ENDS);
	}

	private void setupIndicatorPictureComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		this.indicatorView.setRenderer(new IndicatorMultiRenderer(this.indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		DefaultBooleanModels valueModel = new DefaultBooleanModels();
		List<BooleanModel> list = new ArrayList<BooleanModel>();
		list.add(new DefaultBooleanModel());
		list.add(new DefaultBooleanModel());
		list.add(new DefaultBooleanModel());
		valueModel.setModels(list);
		model.addModel("value", valueModel);
		model.addModel("picture", new IndicatorPictureRenderingModel());
		//model.addModel("color", new IndicatorColoredRenderingModel());
		
		List<IndicatorLabelRenderingModel> labels = new ArrayList<IndicatorLabelRenderingModel>();
		labels.add(new IndicatorLabelRenderingModel("LED 1", CardinalPosition.WEST));
		labels.add(new IndicatorLabelRenderingModel("LED 2", CardinalPosition.SOUTH));
		labels.add(new IndicatorLabelRenderingModel("LED3", CardinalPosition.WEST));
		
		model.addModel("labels", new IndicatorLabelMultiRenderingModel(labels));
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("orientation", new IndicatorOrientationRenderingModel(Orientation.Horizontal));
		
		this.indicatorView.setModel(model);
		this.componentsPane.add(this.indicatorView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestIndicatorPictureMultiComponent app = new TestIndicatorPictureMultiComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
