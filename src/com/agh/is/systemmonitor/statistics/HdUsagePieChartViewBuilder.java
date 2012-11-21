package com.agh.is.systemmonitor.statistics;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

public class HdUsagePieChartViewBuilder {

	public View createPieChartView(Context context, int a, int b){
		CategorySeries series = new CategorySeries("Zużycie dysku");
		series.add("Zajęte", a);
		series.add("Wolne", b);
		
		int[] colors = new int[] { Color.RED, Color.GREEN};

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitle("Zużycie dysku");
		renderer.setChartTitleTextSize(7);
		renderer.setZoomButtonsVisible(true);

		View view = ChartFactory.getPieChartView(context, series, renderer);
		return view;
	}
}
