package com.agh.is.systemmonitor.screens;

import roboguice.activity.RoboFragmentActivity;
import android.app.Dialog;
import android.content.Context;
import android.view.View.OnClickListener;

import com.agh.is.systemmonitor.views.dialogs.OperationResultDialog;
import com.agh.is.systemmonitor.views.dialogs.ProgressDialog;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
abstract public class SystemMonitorActivity extends RoboFragmentActivity {

	public static final String DATA_UPDATE = "com.agh.is.systemmonitor.DATA_UPDATE";	
	public static String password;
	public static String login;
	public static String host;
	
}
