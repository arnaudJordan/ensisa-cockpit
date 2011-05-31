package jmp.ui.component.dial.test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.Rotation;
import jmp.ui.component.dial.DialView;
import jmp.ui.component.dial.model.DialBorderRenderingModel;
import jmp.ui.component.dial.model.DialLabelRenderingModel;
import jmp.ui.component.dial.model.DialPictureRenderingModel;
import jmp.ui.component.dial.model.DialTicksRenderingModel;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;


public class TestDialPictureComponent extends JFrame
{
	private JPanel slidersPane;
	private JSlider progressSlider;

	private JPanel componentsPane;
	private DialView dialView;
	
	public TestDialPictureComponent()
	{
	}

	public void setup()
	{
		setTitle("DialPicture");
		this.setupDialPictureComponentPane();
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
		
		this.progressSlider = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSlider.setMajorTickSpacing(50);
		progressSlider.setMinorTickSpacing(10);
		progressSlider.setPaintTicks(true);
		progressSlider.setPaintLabels(true);
		progressSlider.setPaintTrack(true);
		progressSlider.setPaintTicks(true);
		this.progressSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					dialView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.slidersPane.add(this.progressSlider);
		this.getContentPane().add(this.slidersPane, BorderLayout.PAGE_END);
	}

	private void setupDialPictureComponentPane()
	{
		this.componentsPane = new JPanel();
		this.componentsPane.setLayout(new BoxLayout(this.componentsPane, BoxLayout.X_AXIS));
		
		this.dialView = new DialView();
		this.dialView.setRenderer(new DialDefaultRenderer(this.dialView));
		DefaultModelComposit model = (DefaultModelComposit) this.dialView.getModel();
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/background.png", "pictures/dial/needle.png");
		this.dialView.renderingModel().setSense(Rotation.Clockwise);
		this.dialView.renderingModel().setTicksStartAngle(90);
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setPosition(new Point(0, -dialPictureRenderingModel.getBackground().getHeight()/6));
		dialLabelRenderingModel.setColor(Color.WHITE);
		dialLabelRenderingModel.setFont(new Font("Monospaced", Font.PLAIN, 26));
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setMinorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("border", new DialBorderRenderingModel(3));
		model.addModel("ticks", dialTicksRenderingModel);
		model.addModel("label", dialLabelRenderingModel);
		//model.addModel("track", new DialTrackRenderingModel());

		this.dialView.setModel(model);
		this.componentsPane.add(this.dialView);
		this.getContentPane().add(this.componentsPane, BorderLayout.CENTER);
	}
	public static void main(String[] args)
	{
		final TestDialPictureComponent app = new TestDialPictureComponent();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
