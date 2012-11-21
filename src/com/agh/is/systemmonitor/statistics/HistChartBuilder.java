package com.agh.is.systemmonitor.statistics;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResult;

public interface HistChartBuilder {
	public Intent getIntent(Context context);
}
