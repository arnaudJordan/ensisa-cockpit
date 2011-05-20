package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.DialBorderRenderingModel;
import jmp.ui.component.dial.DialCompositRenderer;
import jmp.ui.component.dial.DialCompositRenderingModel;
import jmp.ui.component.dial.DialDefaultRenderer;
import jmp.ui.component.dial.DialLabelRenderingModel;
import jmp.ui.component.dial.DialPartialRenderingModel;
import jmp.ui.component.dial.DialPictureRenderingModel;
import jmp.ui.component.dial.DialView;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestDialCompositComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSliderMain;
	private JSlider progressSliderIntern;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialCompositComponent()
	{
	}

	public void setup()
	{
		this.setupDialPartialComponentPane();
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
		
		
		
		this.progressSliderMain = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSliderMain.setMajorTickSpacing(90);
		progressSliderMain.setMinorTickSpacing(30);
		progressSliderMain.setPaintTicks(true);
		progressSliderMain.setPaintLabels(true);
		progressSliderMain.setPaintTrack(true);
		progressSliderMain.setPaintTicks(true);
		this.progressSliderMain.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("value")).setValue(progressSliderMain.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderMain);
		this.getContentPane().add(this.slidersPane, BorderLayout.AFTER_LAST_LINE);
		

		this.progressSliderIntern = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSliderIntern.setMajorTickSpacing(50);
		progressSliderIntern.setMinorTickSpacing(10);
		progressSliderIntern.setPaintTicks(true);
		progressSliderIntern.setPaintLabels(true);
		progressSliderIntern.setPaintTrack(true);
		progressSliderIntern.setPaintTicks(true);
		this.progressSliderIntern.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					((DefaultBoundedModel) ((DefaultModelComposit) dialView.getModel()).getModel("internValue")).setValue(progressSliderIntern.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSliderIntern);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialPartialComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
				
		this.dialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		this.dialView.setRenderer(new DialCompositRenderer(this.dialView));
		model.addModel("composit", new DialCompositRenderingModel(new Point(-130,-25)));
		model.addModel("picture", new DialPictureRenderingModel());
		
		DialPictureRenderingModel internPicture = new DialPictureRenderingModel("pictures/dial/default_intern_background.png", "pictures/dial/default_intern_background.png");
		model.addModel("internPicture", internPicture);
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", new DialLabelRenderingModel());
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("internValue", new DefaultBoundedModel(0,100,0));
		this.dialView.setModel(model);
		
		this.componentsPane.add(this.dialView);
		
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialCompositComponent app = new TestDialCompositComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}