package jmp.ui.component.dial;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Stroke;

public class DialTicksRenderingModel extends DialRenderingModel {
	private static final Color GRADUATION_COLOR = Color.RED;
	
	private static final double MAJOR_GRADUTION_RATIO = 0.25;
	private static final int LABEL_SPACE = 2;

	private static final double MINOR_GRADUTION_RATIO = MAJOR_GRADUTION_RATIO * 1.5;
	private static final Stroke MINOR_GRADUTION_STROKE = new BasicStroke(3f);
	private static final Stroke MAJOR_GRADUTION_STROKE = new BasicStroke(4f);
	private static final int MAJOR_TICK_SPACING = 20;
	private static final int MINOR_TICK_SPACING = 5;
	private static final int MINOR_TICK_SIZE = 20;
	private static final int MAJOR_TICK_SIZE = 2 * MINOR_TICK_SIZE;
	
	private Color graduationColor;
	private int LabelSpace;
	private double MajorGradutionRatio;
	private double MinorGradutionRatio;
	private Stroke MajorGradutionStroke;
	private Stroke MinorGradutionStroke;
	private double MajorTickSpacing;
	private double MinorTickSpacing;
	private double MinorTickSize;
	private double MajorTickSize;
	
	public DialTicksRenderingModel()
	{
		this.setGraduationColor(GRADUATION_COLOR);
		this.setLabelSpace(LABEL_SPACE);
		this.setMajorGradutionRatio(MAJOR_GRADUTION_RATIO);
		this.setMinorGradutionRatio(MINOR_GRADUTION_RATIO);
		this.setMajorGradutionStroke(MAJOR_GRADUTION_STROKE);
		this.setMinorGradutionStroke(MINOR_GRADUTION_STROKE);
		this.setMajorTickSpacing(MAJOR_TICK_SPACING);
		this.setMinorTickSpacing(MINOR_TICK_SPACING);
		this.setMinorTickSize(MINOR_TICK_SIZE);
		this.setMajorTickSize(MAJOR_TICK_SIZE);
	}

	public Color getGraduationColor() {
		return graduationColor;
	}

	public void setGraduationColor(Color graduationColor) {
		this.graduationColor = graduationColor;
	}

	public int getLabelSpace() {
		return LabelSpace;
	}

	public void setLabelSpace(int labelSpace) {
		LabelSpace = labelSpace;
	}

	public double getMajorGradutionRatio() {
		return MajorGradutionRatio;
	}

	public void setMajorGradutionRatio(double majorGradutionRatio) {
		MajorGradutionRatio = majorGradutionRatio;
	}

	public double getMinorGradutionRatio() {
		return MinorGradutionRatio;
	}

	public void setMinorGradutionRatio(double minorGradutionRatio) {
		MinorGradutionRatio = minorGradutionRatio;
	}

	public Stroke getMajorGradutionStroke() {
		return MajorGradutionStroke;
	}

	public void setMajorGradutionStroke(Stroke majorGradutionStroke) {
		MajorGradutionStroke = majorGradutionStroke;
	}

	public Stroke getMinorGradutionStroke() {
		return MinorGradutionStroke;
	}

	public void setMinorGradutionStroke(Stroke minorGradutionStroke) {
		MinorGradutionStroke = minorGradutionStroke;
	}

	public double getMajorTickSpacing() {
		return MajorTickSpacing;
	}

	public void setMajorTickSpacing(double majorTickSpacing) {
		MajorTickSpacing = majorTickSpacing;
	}

	public double getMinorTickSpacing() {
		return MinorTickSpacing;
	}

	public void setMinorTickSpacing(double minorTickSpacing) {
		MinorTickSpacing = minorTickSpacing;
	}

	public double getMinorTickSize() {
		return MinorTickSize;
	}

	public void setMinorTickSize(double minorTickSize) {
		MinorTickSize = minorTickSize;
	}

	public double getMajorTickSize() {
		return MajorTickSize;
	}

	public void setMajorTickSize(double majorTickSize) {
		MajorTickSize = majorTickSize;
	}

}
