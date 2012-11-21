package com.agh.is.systemmonitor.statistics;

import java.util.List;

import org.achartengine.model.TimeSeries;

import android.content.Context;
import android.content.Intent;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;

public class HdTempHistChartBuilder extends AbstractHistChartBuilder {

	public HdTempHistChartBuilder(
			AsyncTaskResult<List<AgentInformation>> response, String xAxisLabel, String yAxisLabel) {
		super(response, xAxisLabel, yAxisLabel);
	}

	@Override
	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Temperatura dysku");
		for (AgentInformation agentInformation : responseList){
			series.add(agentInformation.getInsertTime(), agentInformation.getHdTemp());
		}
		return series;
	}

}
