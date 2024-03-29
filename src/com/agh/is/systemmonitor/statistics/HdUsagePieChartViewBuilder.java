package com.agh.is.systemmonitor.statistics;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class HdUsagePieChartViewBuilder {

	public View createPieChartView(Context context, float f, float g){
		CategorySeries series = new CategorySeries("Zużycie dysku");
		series.add("Zajęte", f);
		series.add("Wolne", g);
		
		int[] colors = new int[] { Color.RED, Color.GREEN};

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitleTextSize(9);
		renderer.setZoomEnabled(false);
		renderer.setPanEnabled(false);
	    renderer.setMargins(new int[] { 100, 100, 10, 10 });
	    renderer.setExternalZoomEnabled(false);
		renderer.setInScroll(true);
	    
		View view = ChartFactory.getPieChartView(context, series, renderer);
		view.setMinimumHeight(200);
		view.setMinimumWidth(200);
		view.setPadding(0,100,0,0);
		return view;
	}
}
