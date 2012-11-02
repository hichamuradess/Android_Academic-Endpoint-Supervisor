/**
 * Copyright (C) 2009, 2010 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agh.is.systemmonitor.statistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;

/**
 * Project status demo chart.
 */
public class StatisticsChartIntentBuilder  {

	private String[] titles;
	private List<int[]> values;
	private List<long[]> dates;
	private int[] colors = new int[] { Color.BLUE, Color.GREEN };
	private PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT };

	private String xAxisTitle;
	private String yAxisTitle;
	private String chartTitle;

	private int axesColor;
	private int labelsColor;


	public StatisticsChartIntentBuilder chartTitle(String title) {
		this.chartTitle = title;
		return this;
	}

	public StatisticsChartIntentBuilder axesColor(int color) {
		this.axesColor = color;
		return this;
	}

	public StatisticsChartIntentBuilder labelsColor(int color) {
		this.labelsColor = color;
		return this;
	}

	public StatisticsChartIntentBuilder titles(String[] titles) {
		this.titles = titles;
		return this;
	}

	public StatisticsChartIntentBuilder values(List<int[]> values) {
		this.values = values;
		return this;
	}

	public StatisticsChartIntentBuilder dates(List<long[]> dates) {
		this.dates = dates;
		return this;
	}

	public StatisticsChartIntentBuilder colors(int[] colors) {
		this.colors = colors;
		return this;
	}

	public StatisticsChartIntentBuilder styles(PointStyle[] styles) {
		this.styles = styles;
		return this;
	}

	public StatisticsChartIntentBuilder xAxisTitle(String title) {
		this.xAxisTitle = title;
		return this;
	}

	public StatisticsChartIntentBuilder yAxisTitle(String title) {
		this.yAxisTitle = title;
		return this;
	}


	public Intent execute(Context context) {
		String[] titles = new String[] { "Air temperature" };
		List<long[]> x = new ArrayList<long[]>();
		for (int i = 0; i < titles.length; i++) {
			long[] xData = new long[1000];
			for (int j = 0; j < xData.length; ++j) {
				xData[j] = j;
			}
			x.add(xData);
		}
		List<int[]> values = new ArrayList<int[]>();
			int[] yData = new int[1000];
			for (int j = 0; j < yData.length; ++j) {
				yData[j] = new Random().nextInt(20);
			}
			values.add(yData);
		int[] colors = new int[] { Color.BLUE, Color.YELLOW };
		PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT };
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer(2);
		setRenderer(renderer);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
			r.setLineWidth(3f);
		}
		setChartSettings(renderer, 0.5, 12.5, 0, 32);
		renderer.setXLabels(200);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);
		renderer.setXLabelsAlign(Align.RIGHT);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomLimits(new double[] { -10, 20, -10, 40 });
		renderer.setZoomRate(1.05f);
		renderer.setLabelsColor(Color.WHITE);
		renderer.setXLabelsColor(Color.GREEN);
		renderer.setYLabelsColor(0, colors[0]);
		renderer.setYLabelsColor(1, colors[1]);

		renderer.setYTitle("Hours", 1);
		renderer.setYAxisAlign(Align.RIGHT, 1);
		renderer.setYLabelsAlign(Align.LEFT, 1);

		renderer.addYTextLabel(20, "Test", 0);
		renderer.addYTextLabel(10, "New Test", 1);

		XYMultipleSeriesDataset dataset = buildDataset(titles, x, values);
		values.clear();
			yData = new int[1000];
			for (int j = 0; j < yData.length; ++j) {
				yData[j] = new Random().nextInt(20);
			}
		values.add(yData);
		addXYSeries(dataset, new String[] { "Sunshine hours" }, x, values, 1);
		Intent intent = ChartFactory.getCubicLineChartIntent(context, dataset, renderer, 0.3f,
				"Average temperature");
		return intent;
	}

	protected XYMultipleSeriesDataset buildDataset(String[] titles, List<long[]> xValues,
			List<int[]> yValues) {
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		addXYSeries(dataset, titles, xValues, yValues, 0);
		return dataset;
	}

	public void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<long[]> xValues,
			List<int[]> yValues, int scale) {
		int length = titles.length;
		for (int i = 0; i < length; i++) {
			XYSeries series = new XYSeries(titles[i], scale);
			long[] xV = xValues.get(i);
			int[] yV = yValues.get(i);
			int seriesLength = xV.length;
			for (int k = 0; k < seriesLength; k++) {
				series.add(xV[k], yV[k]);
			}
			dataset.addSeries(series);
		}
	}

	protected XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		setRenderer(renderer);
		return renderer;
	}

	protected void setRenderer(XYMultipleSeriesRenderer renderer) {
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setPointSize(5f);
		renderer.setMargins(new int[] { 20, 30, 15, 20 });
		generateSeriesRenderers(renderer);
	}
	
	private void generateSeriesRenderers(XYMultipleSeriesRenderer renderer) {
		int length = colors.length;
		for (int i = 0; i < length; i++) {
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			renderer.addSeriesRenderer(r);
		}
	}


	protected void setChartSettings(XYMultipleSeriesRenderer renderer, double xMin, double xMax, double yMin, double yMax) {
		renderer.setChartTitle(chartTitle);
		renderer.setXTitle(xAxisTitle);
		renderer.setYTitle(yAxisTitle);
		renderer.setXAxisMin(xMin);
		renderer.setXAxisMax(xMax);
		renderer.setYAxisMin(yMin);
		renderer.setYAxisMax(yMax);
		renderer.setAxesColor(axesColor);
		renderer.setLabelsColor(labelsColor);
	}

}

