package jmp.ui.component.indicator.model;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jmp.ui.component.CardinalPosition;

public class IndicatorLabelMultiRenderingModel extends IndicatorRenderingModel {

	private List<IndicatorLabelRenderingModel> labels;

	public IndicatorLabelMultiRenderingModel() {
		this(new ArrayList<IndicatorLabelRenderingModel>());
	}
	
	public IndicatorLabelMultiRenderingModel(List<IndicatorLabelRenderingModel> labels) {
		this.labels = labels;
	}
	public void addLabel(IndicatorLabelRenderingModel label)
	{
		this.labels.add(label);
	}
	public IndicatorLabelRenderingModel getLabel(int n)
	{
		return this.labels.get(n);
	}
	public Iterator<IndicatorLabelRenderingModel> getIterator()
	{
		return this.labels.iterator();
	}
}
