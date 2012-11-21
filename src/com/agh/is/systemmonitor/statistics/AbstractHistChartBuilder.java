package com.agh.is.systemmonitor.statistics;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.google.common.collect.Lists;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;

public abstract class AbstractHistChartBuilder implements HistChartBuilder{
	protected List<AgentInformation> responseList;
	
	@Override
	public Intent getIntent(Context context) {
		return ChartFactory.getLineChartIntent(context, createMultipleDataset(createSeries()), createRenderer()); 
	}
	
	private XYMultipleSeriesDataset createMultipleDataset(TimeSeries series)
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(series);
		return dataset;
	}
	
	private XYMultipleSeriesRenderer createRenderer()
	{
		XYSeriesRenderer renderer = new XYSeriesRenderer();
		renderer.setColor(Color.WHITE);
		renderer.setPointStyle(PointStyle.DIAMOND);
		renderer.setFillPoints(true);
		XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
		multipleSeriesRenderer.addSeriesRenderer(renderer);
		return multipleSeriesRenderer;
	}
	
	public AbstractHistChartBuilder(AsyncTaskResult<List<AgentInformation>> response)
	{
		List<AgentInformation> list = Lists.newArrayList(response.getResult());
		Collections.sort(list);
		this.responseList = list;
	}
	
	protected abstract TimeSeries createSeries();
}
