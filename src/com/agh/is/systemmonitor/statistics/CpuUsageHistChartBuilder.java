package com.agh.is.systemmonitor.statistics;

import java.util.List;

import org.achartengine.model.TimeSeries;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class CpuUsageHistChartBuilder extends AbstractHistChartBuilder {
	
	public CpuUsageHistChartBuilder(
			AsyncTaskResult<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel) {
		super(response, xAxisLabel, yAxisLabel);
	}

	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Zużycie CPU");
		for (AgentInformation agentInformation : responseList){
			series.add(parseTimestamp(agentInformation.getInsertTime()), agentInformation.getCpuTemp());
		}
		return series;
	}
}
