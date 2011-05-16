package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class DialHorizonRenderingModel extends DialRenderingModel 
{
	private static final Color SKY_COLOR = new Color(0, 204, 255);
	private static final Color GROUND_COLOR = new Color(153, 102, 51);
	private static final Color GRADUATION_COLOR = Color.WHITE;
	private static final Color BORDER_COLOR = Color.GRAY;
	private static final Dimension SIZE = new Dimension(150, 150);
	
	private static final double MAJOR_GRADUTION_RATIO = 0.25;
	private static final int LABEL_SPACE = 2;
	private static final int BORDER_SIZE = 2;

	private static final double MINOR_GRADUTION_RATIO = MAJOR_GRADUTION_RATIO * 1.5;
	private static final Stroke MINOR_GRADUTION_STROKE = new BasicStroke(0.5f);
	private static final Stroke MAJOR_GRADUTION_STROKE = new BasicStroke(2f);
	private static final int MAJOR_TICK_SPACING = 10;
	private static final int MINOR_TICK_SPACING = 5;
	private static final int PITCH_INTERVAL = 60;
	private final static String PLANE_IMAGE_PATH = "pictures/dial/horizon_plane.png";
	
	private static final int BACKGROUND_MULTIPLIER = 4;
	
	private Color skyColor;
	private Color groundColor;
	private Color graduationColor;
	private Dimension Size;
	private int LabelSpace;
	private int BorderSize;
	private double MajorGradutionRatio;
	private double MinorGradutionRatio;
	private Stroke MajorGradutionStroke;
	private Stroke MinorGradutionStroke;
	private double MajorTickSpacing;
	private double MinorTickSpacing;
	private int pitchInterval; 
	private BufferedImage horizonImage;
	private BufferedImage planeImage;
	private int backgroundMultiplier;
	private Color borderColor;

	public DialHorizonRenderingModel()
	{
		this.setSkyColor(SKY_COLOR);
		this.setGroundColor(GROUND_COLOR);
		this.setGraduationColor(GRADUATION_COLOR);
		this.setSize(SIZE);
		this.setLabelSpace(LABEL_SPACE);
		this.setBorderSize(BORDER_SIZE);
		this.setMajorGradutionRatio(MAJOR_GRADUTION_RATIO);
		this.setMinorGradutionRatio(MINOR_GRADUTION_RATIO);
		this.setMajorGradutionStroke(MAJOR_GRADUTION_STROKE);
		this.setMinorGradutionStroke(MINOR_GRADUTION_STROKE);
		this.setMajorTickSpacing(MAJOR_TICK_SPACING);
		this.setMinorTickSpacing(MINOR_TICK_SPACING);
		this.setPitchInterval(PITCH_INTERVAL);
		this.setBackgroundMultiplier(BACKGROUND_MULTIPLIER);
		this.setBorderColor(BORDER_COLOR);
		this.setHorizonImage(new BufferedImage((int) Size.getWidth() * getBackgroundMultiplier(), (int) Size.getHeight() * getBackgroundMultiplier(), BufferedImage.TYPE_INT_RGB));
		try
		{
			this.setPlaneImage(ImageIO.read(new File(System.getProperty("java.class.path") + "/../" + PLANE_IMAGE_PATH)));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}


	public void setSkyColor(Color skyColor) 
	{
		this.skyColor = skyColor;
	}

	public Color getSkyColor() 
	{
		return skyColor;
	}

	public void setGroundColor(Color groundColor) 
	{
		this.groundColor = groundColor;
	}

	public Color getGroundColor() 
	{
		return groundColor;
	}

	public void setGraduationColor(Color graduationColor) 
	{
		this.graduationColor = graduationColor;
	}

	public Color getGraduationColor() 
	{
		return graduationColor;
	}

	public void setSize(Dimension size) 
	{
		Size = size;
	}
	public Dimension getSize() 
	{
		return Size;
	}

	public void setLabelSpace(int labelSpace) {
		LabelSpace = labelSpace;
	}

	public int getLabelSpace() {
		return LabelSpace;
	}

	public void setBorderSize(int borderSize) {
		BorderSize = borderSize;
	}

	public int getBorderSize() {
		return BorderSize;
	}

	public void setMajorGradutionRatio(double majorGradutionRatio) {
		MajorGradutionRatio = majorGradutionRatio;
	}

	public double getMajorGradutionRatio() {
		return MajorGradutionRatio;
	}

	public void setMinorGradutionRatio(double minorGradutionRatio) {
		MinorGradutionRatio = minorGradutionRatio;
	}

	public double getMinorGradutionRatio() {
		return MinorGradutionRatio;
	}

	public void setMajorGradutionStroke(Stroke majorGradutionStroke) {
		MajorGradutionStroke = majorGradutionStroke;
	}

	public Stroke getMajorGradutionStroke() {
		return MajorGradutionStroke;
	}

	public void setMinorGradutionStroke(Stroke minorGradutionStroke) {
		MinorGradutionStroke = minorGradutionStroke;
	}

	public Stroke getMinorGradutionStroke() {
		return MinorGradutionStroke;
	}

	public void setMajorTickSpacing(double majorGradutionSpacing) {
		MajorTickSpacing = majorGradutionSpacing;
	}

	public double getMajorTickSpacing() {
		return MajorTickSpacing;
	}

	public void setMinorTickSpacing(double minorGradutionSpacing) {
		MinorTickSpacing = minorGradutionSpacing;
	}

	public double getMinorTickSpacing() {
		return MinorTickSpacing;
	}

	public void setPitchInterval(int pitchInterval) {
		this.pitchInterval = pitchInterval;
	}


	public int getPitchInterval() {
		return pitchInterval;
	}


	public void setHorizonImage(BufferedImage horizonImage) {
		this.horizonImage = horizonImage;
	}

	public BufferedImage getHorizonImage() {
		return horizonImage;
	}

	public void setPlaneImage(BufferedImage planeImage) {
		this.planeImage = planeImage;
	}

	public BufferedImage getPlaneImage() {
		return planeImage;
	}

	public void setBackgroundMultiplier(int backgroundMultiplier) {
		this.backgroundMultiplier = backgroundMultiplier;
	}

	public int getBackgroundMultiplier() {
		return backgroundMultiplier;
	}

	public Color getBorderColor() {
		return borderColor;
	}
	private void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}
}
