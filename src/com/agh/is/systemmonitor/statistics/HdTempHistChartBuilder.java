package com.agh.is.systemmonitor.statistics;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.achartengine.model.TimeSeries;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsynsTaskResponse;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class HdTempHistChartBuilder extends AbstractHistChartBuilder {

	public HdTempHistChartBuilder(
			AsynsTaskResponse<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel) {
		super(response, xAxisLabel, yAxisLabel);
	}

	@Override
	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Temperatura dysku");
		for (AgentInformation agentInformation : responseList){
			series.add(new Date(agentInformation.getInsertTime()), agentInformation.getHdTemp());
		}
		return series;
	}

}
