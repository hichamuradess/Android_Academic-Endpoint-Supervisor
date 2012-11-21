package com.agh.is.systemmonitor.statistics;

import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;

import android.R;
import android.content.Context;
import android.content.Intent;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class HdUsageHistChartBuilder extends AbstractHistChartBuilder{

	public HdUsageHistChartBuilder(
			AsyncTaskResult<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel) {
		super(response, xAxisLabel, yAxisLabel);
	}

	@Override
	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Zużycie dysku");
		for (AgentInformation agentInformation : responseList){
			series.add(agentInformation.getInsertTime(), agentInformation.getDiskUsedSpace());
		}
		return series;
	}

}
