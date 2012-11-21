package com.agh.is.systemmonitor.statistics;

import java.sql.Date;
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

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public abstract class AbstractHistChartBuilder implements HistChartBuilder{
	protected List<AgentInformation> responseList;
	private String xAxisLabel;
	private String yAxisLabel;
	
	@Override
	public Intent getIntent(Context context) {
		return ChartFactory.getTimeChartIntent(context, createMultipleDataset(createSeries()), createRenderer(), "h:mm a"); 
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
		renderer.setColor(Color.YELLOW);
		renderer.setPointStyle(PointStyle.DIAMOND);
		renderer.setFillPoints(true);
		XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
		multipleSeriesRenderer.addSeriesRenderer(renderer);
		multipleSeriesRenderer.setYAxisMax(100);
		multipleSeriesRenderer.setYAxisMin(0);
		multipleSeriesRenderer.setXTitle(xAxisLabel);
		multipleSeriesRenderer.setYTitle(yAxisLabel);
		multipleSeriesRenderer.setZoomEnabled(true);
		return multipleSeriesRenderer;
	}
	
	public AbstractHistChartBuilder(AsyncTaskResult<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel)
	{
		List<AgentInformation> list = Lists.newArrayList(response.getResult());
		Collections.sort(list);
		this.responseList = list;
		this.xAxisLabel = xAxisLabel;
		this.yAxisLabel = yAxisLabel;
	}
	
	protected Date parseTimestamp(long timeStamp){
		Date time=new Date(timeStamp*1000);
		return time;
	}
	
	protected abstract TimeSeries createSeries();
}
