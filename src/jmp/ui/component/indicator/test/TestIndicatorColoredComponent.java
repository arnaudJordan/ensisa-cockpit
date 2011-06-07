package jmp.ui.component.indicator.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.CardinalPosition;
import jmp.ui.component.indicator.IndicatorView;
import jmp.ui.component.indicator.model.IndicatorBorderRenderingModel;
import jmp.ui.component.indicator.model.IndicatorColoredRenderingModel;
import jmp.ui.component.indicator.model.IndicatorLabelRenderingModel;
import jmp.ui.component.indicator.renderer.IndicatorDefaultRenderer;
import jmp.ui.model.ModelComposit;


public class TestIndicatorColoredComponent extends JFrame
{
	private JPanel slidersPane;
	private JCheckBox checkBox;

	private JPanel componentsPane;
	private IndicatorView indicatorView;
	
	public TestIndicatorColoredComponent()
	{
	}

	public void setup()
	{
		setTitle("IndicatorColor");
		this.setupIndicatorColoredComponentPane();
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
		
		this.checkBox = new JCheckBox();
		this.checkBox.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				indicatorView.valueModel().setState(checkBox.isSelected());
			}
		});
		
		this.slidersPane.add(this.checkBox);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupIndicatorColoredComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.indicatorView = new IndicatorView();
		this.indicatorView.setRenderer(new IndicatorDefaultRenderer(this.indicatorView));
		ModelComposit model = (ModelComposit) indicatorView.getModel();
		model.addModel("color", new IndicatorColoredRenderingModel());
		model.addModel("border", new IndicatorBorderRenderingModel());
		model.addModel("label", new IndicatorLabelRenderingModel("LED", CardinalPosition.EAST));

		this.indicatorView.setModel(model);
		this.componentsPane.add(this.indicatorView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestIndicatorColoredComponent app = new TestIndicatorColoredComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
