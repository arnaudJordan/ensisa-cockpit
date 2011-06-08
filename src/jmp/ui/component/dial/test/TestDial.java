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
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jmp.ui.component.Rotation;
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
import jmp.ui.component.dial.model.DialTrackRenderingModel;
import jmp.ui.component.dial.renderer.DialCompositRenderer;
import jmp.ui.component.dial.renderer.DialDefaultRenderer;
import jmp.ui.model.DefaultBoundedModel;
import jmp.ui.model.DefaultModelComposit;
import jmp.ui.model.ModelComposit;
import jmp.ui.utilities.ColoredRange;
import jmp.ui.utilities.ColoredRanges;

public class TestDial extends JFrame {
	private JTabbedPane tabbedPane;
	private JPanel horizonPane;
	private JPanel picturePane;
	private JPanel partialPane;
	private JPanel compositPane;
	private JPanel coloredPane;
	private JPanel compassPane;
	
	public TestDial()
	{
	}
	
	public void setup()
	{
		setTitle("Dial");
		this.setupHorizonComponentPane();
		this.setupDialPictureComponentPane();
		this.setupDialPartialComponentPane();
		this.setupDialCompositComponentPane();
		this.setupDialColoredComponentPane();
		this.setupCompassComponentPane();
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
	
	private void setupHorizonComponentPane()
	{
		this.horizonPane = new JPanel();
		this.horizonPane.setLayout(new BoxLayout(this.horizonPane, BoxLayout.X_AXIS));
		
		final Horizon horizon = new Horizon();
		this.horizonPane.add(horizon);

		final JSlider progressSliderPitch = new JSlider(JSlider.HORIZONTAL,-180,180,0);
		progressSliderPitch.setMajorTickSpacing(90);
		progressSliderPitch.setMinorTickSpacing(30);
		progressSliderPitch.setPaintTicks(true);
		progressSliderPitch.setPaintLabels(true);
		progressSliderPitch.setPaintTrack(true);
		progressSliderPitch.setPaintTicks(true);
		progressSliderPitch.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					horizon.setPitch(progressSliderPitch.getValue());
				}
			}
		});
		
		this.horizonPane.add(progressSliderPitch);
		

		final JSlider progressSliderRoll = new JSlider(JSlider.HORIZONTAL,-100,100,0);
		progressSliderRoll.setMajorTickSpacing(50);
		progressSliderRoll.setMinorTickSpacing(10);
		progressSliderRoll.setPaintTicks(true);
		progressSliderRoll.setPaintLabels(true);
		progressSliderRoll.setPaintTrack(true);
		progressSliderRoll.setPaintTicks(true);
		progressSliderRoll.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent changeEvent)
			{
				Object source = changeEvent.getSource();
				JSlider s = (JSlider) source;
				if (!s.getValueIsAdjusting());
				{
					horizon.setRoll(progressSliderRoll.getValue());
				}
			}
		});
		
		this.horizonPane.add(progressSliderRoll);
	}

	private void setupDialPictureComponentPane()
	{
		this.picturePane = new JPanel();
		this.picturePane.setLayout(new BoxLayout(this.picturePane, BoxLayout.X_AXIS));
		
		final DialView dialView = new DialView();
		dialView.setRenderer(new DialDefaultRenderer(dialView));
		DefaultModelComposit model = (DefaultModelComposit) dialView.getModel();
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		dialView.renderingModel().setSense(Rotation.Clockwise);
		dialView.renderingModel().setTicksStartAngle(90);
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
		model.addModel("track", new DialTrackRenderingModel());

		dialView.setModel(model);
		this.picturePane.add(dialView);
		
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
					dialView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.picturePane.add(progressSlider);
	}
	private void setupDialPartialComponentPane()
	{
		this.partialPane = new JPanel();
		this.partialPane.setLayout(new BoxLayout(this.partialPane, BoxLayout.X_AXIS));
				
		final DialView dialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) dialView.getModel();
		dialView.setRenderer(new DialDefaultRenderer(dialView));
		dialView.renderingModel().setSense(Rotation.Anticlockwise);
		DialPartialRenderingModel partialModel = new DialPartialRenderingModel();
		dialView.renderingModel().setTicksStartAngle(partialModel.getStartAngle());
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setMinorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		model.addModel("partial", partialModel);
		model.addModel("picture", new DialPictureRenderingModel());
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", new DialLabelRenderingModel());
		model.addModel("ticks", dialTicksRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		dialView.setModel(model);
		
		this.partialPane.add(dialView);
		
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
					dialView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.partialPane.add(progressSlider);
	}
	
	private void setupDialCompositComponentPane()
	{
		this.compositPane = new JPanel();
		this.compositPane.setLayout(new BoxLayout(this.compositPane, BoxLayout.X_AXIS));
				
		final DialView dialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) dialView.getModel();
		dialView.setRenderer(new DialCompositRenderer(dialView));
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setColor(Color.WHITE);
		dialLabelRenderingModel.setPosition(new Point(0, -dialPictureRenderingModel.getBackground().getHeight()/6));
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("border", new DialBorderRenderingModel());
		model.addModel("label", dialLabelRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		
		DefaultModelComposit compositModel = new DefaultModelComposit();
		DialPictureRenderingModel dialCompositPictureRenderingModel = new DialPictureRenderingModel("pictures/dial/intern_background.png", "pictures/dial/default_intern_needle.png");
		DialLabelRenderingModel dialCompositLabelRenderingModel = new DialLabelRenderingModel("I.D");
		dialCompositLabelRenderingModel.setPosition(new Point(0, -dialCompositPictureRenderingModel.getBackground().getHeight()/6));
		compositModel.addModel("rendering", new DialCompositRenderingModel(new Point(100,-70)));
		compositModel.addModel("picture", dialCompositPictureRenderingModel);
		dialCompositLabelRenderingModel.setColor(Color.BLACK);
		compositModel.addModel("label", dialCompositLabelRenderingModel);
		compositModel.addModel("value", new DefaultBoundedModel(0,100,0));
		
		model.addModel("composit", compositModel);
		
		dialView.setModel(model);
		
		this.compositPane.add(dialView);
		
		final JSlider progressSliderMain = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSliderMain.setMajorTickSpacing(50);
		progressSliderMain.setMinorTickSpacing(10);
		progressSliderMain.setPaintTicks(true);
		progressSliderMain.setPaintLabels(true);
		progressSliderMain.setPaintTrack(true);
		progressSliderMain.setPaintTicks(true);
		progressSliderMain.addChangeListener(new ChangeListener()
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
		
		this.compositPane.add(progressSliderMain);
		

		final JSlider progressSliderIntern = new JSlider(JSlider.HORIZONTAL,0,100,0);
		progressSliderIntern.setMajorTickSpacing(50);
		progressSliderIntern.setMinorTickSpacing(10);
		progressSliderIntern.setPaintTicks(true);
		progressSliderIntern.setPaintLabels(true);
		progressSliderIntern.setPaintTrack(true);
		progressSliderIntern.setPaintTicks(true);
		progressSliderIntern.addChangeListener(new ChangeListener()
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
		
		this.compositPane.add(progressSliderIntern);
	}
	
	private void setupDialColoredComponentPane()
	{
		this.coloredPane = new JPanel();
		this.coloredPane.setLayout(new BoxLayout(this.coloredPane, BoxLayout.X_AXIS));
				
		final DialView dialView = new DialView();
		
		DefaultModelComposit model = (DefaultModelComposit) dialView.getModel();
		
		ColoredRanges colorRanges = new ColoredRanges();
		colorRanges.addRange(new ColoredRange(0, 40, Color.GREEN));
		colorRanges.addRange(new ColoredRange(40, 80, Color.YELLOW));
		colorRanges.addRange(new ColoredRange(80, 100, Color.RED));
		DialColoredRenderingModel colorModel = new DialColoredRenderingModel();
		colorModel.setColorRanges(colorRanges);
		colorModel.setThickness(15);
		DialPartialRenderingModel partialModel = new DialPartialRenderingModel();
		dialView.renderingModel().setSense(Rotation.Anticlockwise);
		dialView.renderingModel().setTicksStartAngle(partialModel.getStartAngle());
		DialPictureRenderingModel dialPictureRenderingModel = new DialPictureRenderingModel();
		DialLabelRenderingModel dialLabelRenderingModel = new DialLabelRenderingModel();
		dialLabelRenderingModel.setPosition(new Point(dialPictureRenderingModel.getBackground().getHeight()/6, dialPictureRenderingModel.getBackground().getHeight()/6));
		model.addModel("colored", colorModel);
		model.addModel("picture", dialPictureRenderingModel);
		model.addModel("value", new DefaultBoundedModel(0,100,0));
		model.addModel("label", dialLabelRenderingModel);
		DialTicksRenderingModel dialTicksRenderingModel = new DialTicksRenderingModel();
		dialTicksRenderingModel.setMajorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setMinorGraduationColor(Color.WHITE);
		dialTicksRenderingModel.setLabelColor(Color.WHITE);
		dialTicksRenderingModel.setMajorTickSize(25);
		
		model.addModel("ticks", dialTicksRenderingModel);
		
		dialView.setModel(model);
		dialView.setRenderer(new DialDefaultRenderer(dialView));
		
		this.coloredPane.add(dialView);
		
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
					dialView.valueModel().setValue(progressSlider.getValue());
				}
			}
		});
		
		this.coloredPane.add(progressSlider);
		
	}
	
	private void setupCompassComponentPane()
	{
		this.compassPane = new JPanel();
		this.compassPane.setLayout(new BoxLayout(this.compassPane, BoxLayout.X_AXIS));
		
		final Compass compass = new Compass();
		this.compassPane.add(compass);
		
		final JSlider progressSlider = new JSlider(JSlider.HORIZONTAL,0,360,0);
		progressSlider.setMajorTickSpacing(90);
		progressSlider.setMinorTickSpacing(30);
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
					compass.setValue(progressSlider.getValue());
				}
			}
		});
		
		this.compassPane.add(progressSlider);
	}
	private void setupTabbedPane()
	{
		this.tabbedPane = new JTabbedPane();
		this.getContentPane().add(this.tabbedPane, BorderLayout.PAGE_START);
		this.tabbedPane.addTab("Horizon", this.horizonPane);
		this.tabbedPane.addTab("Picture", this.picturePane);
		this.tabbedPane.addTab("Partial", this.partialPane);
		this.tabbedPane.addTab("Composit", this.compositPane);
		this.tabbedPane.addTab("Colored", this.coloredPane);
		this.tabbedPane.addTab("Compass", this.compassPane);
	}

	
	public static void main(String[] args)
	{
		final TestDial app = new TestDial();
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				app.setup();
			}
		});
	}
}
