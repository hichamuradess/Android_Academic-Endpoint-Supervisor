package com.agh.is.systemmonitor.screens;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.views.ThermometerView;

import android.app.Activity;
import android.os.Bundle;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ThermometerActivity extends RoboActivity {

	@InjectView(R.id.thermometer) ThermometerView thermometer;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thermometer_test);
        thermometer.setTemperature(100);
    }
}