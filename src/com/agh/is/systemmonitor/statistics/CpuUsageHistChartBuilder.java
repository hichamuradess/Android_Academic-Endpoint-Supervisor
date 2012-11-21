package com.agh.is.systemmonitor.statistics;

import java.util.List;

import org.achartengine.model.TimeSeries;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;

public class CpuUsageHistChartBuilder extends AbstractHistChartBuilder {
	
	public CpuUsageHistChartBuilder(
			AsyncTaskResult<List<AgentInformation>> response) {
		super(response);
	}

	protected TimeSeries createSeries() {
		TimeSeries series = new TimeSeries("Zużycie CPU");
		for (AgentInformation agentInformation : responseList){
			series.add(agentInformation.getInsertTime(), agentInformation.getCpuTemp());
		}
		return series;
	}
}
