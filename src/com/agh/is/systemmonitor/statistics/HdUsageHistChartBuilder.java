package com.agh.is.systemmonitor.statistics;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResponse;

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
			AsyncTaskResponse<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel) {
		super(response, xAxisLabel, yAxisLabel);
	}

	@Override
	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Zużycie dysku");
		for (AgentInformation agentInformation : responseList){
			series.add(new Date(agentInformation.getInsertTime()), (agentInformation.getDiskUsedSpace())/(1024*1024));
		}
		return series;
	}

}
