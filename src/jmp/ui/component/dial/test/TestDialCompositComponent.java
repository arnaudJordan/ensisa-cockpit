package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialCompositRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.renderer.DialCompositRenderer;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;


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
		setTitle("DialComposit");
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
		progressSliderMain.setMajorTickSpacing(50);
		progressSliderMain.setMinorTickSpacing(10);
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
					((DefaultBoundedModel)((ModelComposit) ((ModelComposit) dialView.getModel()).getModel("composit")).getModel("value")).setValue(progressSliderIntern.getValue());
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
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setPosition(new Point(0, -dialPictureRenderingModel.getBackground().getHeight()/6));
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", dialLabelRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		
		DefaultModelComposit compositModel = new DefaultModelComposit();
		DialPictureRenderingModel dialCompositPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/default_intern_background.png", "pictures/dial/default_intern_needle.png");
		DialLabelRenderingModel dialCompositLabelRenderingModel = new DialLabelRenderingModel("I.D");
		dialCompositLabelRenderingModel.setPosition(new Point(0, -dialCompositPictureRenderingModel.getBackground().getHeight()/6));
		compositModel.addModel("rendering", new DialCompositRenderingModel(new Point(75,0)));
		compositModel.addModel("picture", dialCompositPictureRenderingModel);
		compositModel.addModel("border", new DialBorderRenderingModel());
		compositModel.addModel("label", dialCompositLabelRenderingModel);
		compositModel.addModel("value", new DefaultBoundedModel(0,100,0));
		
		model.addModel("composit", compositModel);
		
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
