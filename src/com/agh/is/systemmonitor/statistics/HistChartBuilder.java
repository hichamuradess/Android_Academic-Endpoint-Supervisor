package com.agh.is.systemmonitor.statistics;

import java.util.List;

import android.content.Context;
import android.content.Intent;

import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.services.AsyncTaskResponse;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public interface HistChartBuilder {
	public Intent getIntent(Context context);
}
