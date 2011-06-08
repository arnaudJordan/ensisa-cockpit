package jmp.ui.component.dial.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Stroke;

public class DialTicksRenderingModel extends DialRenderingModel {
	private static final Color MINOR_GRADUATION_COLOR = Color.BLACK;
	private static final Color MAJOR_GRADUATION_COLOR = Color.RED;
	private static final Color LABEL_COLOR = Color.BLACK;
	final static Font LABEL_FONT = new Font("Monospaced", Font.PLAIN, 18);
	private static final int LABEL_SPACE = 10;
	private static final int MARGIN = 0;
	
	private static final double MAJOR_GRADUTION_RATIO = 0.25;	

	private static final double MINOR_GRADUTION_RATIO = MAJOR_GRADUTION_RATIO * 1.5;
	private static final float MINOR_GRADUTION_WIDTH = 2f;
	private static final float MAJOR_GRADUTION_WIDTH = 2f;
	//private static final Stroke MINOR_GRADUTION_STROKE = new BasicStroke(MINOR_GRADUTION_WIDTH,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	//private static final Stroke MAJOR_GRADUTION_STROKE = new BasicStroke(MAJOR_GRADUTION_WIDTH,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL);
	private static final int MAJOR_TICK_SPACING = 20;
	private static final int MINOR_TICK_SPACING = 5;
	private static final int MINOR_TICK_SIZE = 20;
	private static final int MAJOR_TICK_SIZE = 2 * MINOR_TICK_SIZE;
	
	private Color MinorGraduationColor;
	private Color MajorGraduationColor;
	private Color LabelColor;
	private Font LabelFont;
	private int LabelSpace;
	private int Margin;
	private float MinorGraduationWidth;
	private float MajorGraduationWidth;
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
		this.setMinorGraduationColor(MINOR_GRADUATION_COLOR);
		this.setMajorGraduationColor(MAJOR_GRADUATION_COLOR);
		this.setLabelColor(LABEL_COLOR);
		this.setLabelFont(LABEL_FONT);
		this.setLabelSpace(LABEL_SPACE);
		this.setMargin(MARGIN);
		this.setMinorGraduationWidth(MINOR_GRADUTION_WIDTH);
		this.setMajorGraduationWidth(MAJOR_GRADUTION_WIDTH);
		this.setMajorGradutionRatio(MAJOR_GRADUTION_RATIO);
		this.setMinorGradutionRatio(MINOR_GRADUTION_RATIO);
		this.setMajorTickSpacing(MAJOR_TICK_SPACING);
		this.setMinorTickSpacing(MINOR_TICK_SPACING);
		this.setMinorTickSize(MINOR_TICK_SIZE);
		this.setMajorTickSize(MAJOR_TICK_SIZE);
	}

	public Color getMinorGraduationColor() {
		return MinorGraduationColor;
	}

	public void setMinorGraduationColor(Color minorGraduationColor) {
		if(this.MinorGraduationColor==minorGraduationColor) return;
		MinorGraduationColor = minorGraduationColor;
		this.modelChange();
	}

	public Color getMajorGraduationColor() {
		return MajorGraduationColor;
	}

	public void setMajorGraduationColor(Color majorGraduationColor) {
		if(this.MajorGraduationColor == majorGraduationColor) return;
		MajorGraduationColor = majorGraduationColor;
		this.modelChange();
	}

	public Color getLabelColor() {
		return LabelColor;
	}

	public void setLabelColor(Color labelColor) {
		if(this.LabelColor == labelColor) return;
		LabelColor = labelColor;
		this.modelChange();
	}

	public void setLabelFont(Font labelFont) {
		if(this.LabelFont == labelFont) return;
		LabelFont = labelFont;
		this.modelChange();
	}

	public Font getLabelFont() {
		return LabelFont;
	}

	public int getLabelSpace() {
		return LabelSpace;
	}

	public void setLabelSpace(int labelSpace) {
		if(this.LabelSpace == labelSpace) return;
		LabelSpace = labelSpace;
		this.modelChange();
	}

	public float getMinorGraduationWidth() {
		return MinorGraduationWidth;
	}

	public void setMinorGraduationWidth(float minorGraduationWidth) {
		if(this.MinorGraduationWidth == minorGraduationWidth) return;
		MinorGraduationWidth = minorGraduationWidth;
		setMinorGradutionStroke(new BasicStroke(minorGraduationWidth,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		this.modelChange();
	}

	public float getMajorGraduationWidth() {
		return MajorGraduationWidth;
	}

	public void setMajorGraduationWidth(float majorGraduationWidth) {
		if(this.MajorGraduationWidth == majorGraduationWidth) return;
		MajorGraduationWidth = majorGraduationWidth;
		setMajorGradutionStroke(new BasicStroke(majorGraduationWidth,BasicStroke.CAP_BUTT,BasicStroke.JOIN_BEVEL));
		this.modelChange();
	}

	public double getMajorGradutionRatio() {
		return MajorGradutionRatio;
	}

	public void setMajorGradutionRatio(double majorGraduationRatio) {
		if(this.MajorGradutionRatio == majorGraduationRatio) return;
		MajorGradutionRatio = majorGraduationRatio;
		this.modelChange();
	}

	public double getMinorGradutionRatio() {
		return MinorGradutionRatio;
	}

	public void setMinorGradutionRatio(double minorGraduationRatio) {
		if(this.MinorGradutionRatio == minorGraduationRatio) return;
		MinorGradutionRatio = minorGraduationRatio;
	}

	public Stroke getMajorGradutionStroke() {
		return MajorGradutionStroke;
	}

	public void setMajorGradutionStroke(Stroke majorGraduationStroke) {
		if(this.MajorGradutionStroke == majorGraduationStroke) return;
		MajorGradutionStroke = majorGraduationStroke;
		this.modelChange();
	}

	public Stroke getMinorGradutionStroke() {
		return MinorGradutionStroke;
	}

	public void setMinorGradutionStroke(Stroke minorGraduationStroke) {
		if(this.MinorGradutionStroke == minorGraduationStroke) return;
		MinorGradutionStroke = minorGraduationStroke;
		this.modelChange();
	}

	public double getMajorTickSpacing() {
		return MajorTickSpacing;
	}

	public void setMajorTickSpacing(double majorTickSpacing) {
		if(this.MajorTickSpacing == majorTickSpacing) return;
		MajorTickSpacing = majorTickSpacing;
		this.modelChange();
	}

	public double getMinorTickSpacing() {
		return MinorTickSpacing;
	}

	public void setMinorTickSpacing(double minorTickSpacing) {
		if(this.MinorTickSpacing == minorTickSpacing) return;
		MinorTickSpacing = minorTickSpacing;
		this.modelChange();
	}

	public double getMinorTickSize() {
		return MinorTickSize;
	}

	public void setMinorTickSize(double minorTickSize) {
		if(this.MinorTickSize == minorTickSize) return;
		MinorTickSize = minorTickSize;
		this.modelChange();
	}

	public double getMajorTickSize() {
		return MajorTickSize;
	}

	public void setMajorTickSize(double majorTickSize) {
		if(this.MajorTickSize == majorTickSize) return;
		MajorTickSize = majorTickSize;
		this.modelChange();
	}

	public int getMargin() {
		return Margin;
	}
	
	public void setMargin(int margin) {
		if(this.Margin == margin) return;
		this.Margin = margin;
		this.modelChange();
	}
}
