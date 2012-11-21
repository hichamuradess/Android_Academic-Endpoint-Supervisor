package com.agh.is.systemmonitor.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.resolvers.network.ResolvingException;
import com.agh.is.systemmonitor.resolvers.network.ServerCommunicationService;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.SystemMonitorActivity;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServerMonitoringService extends Service {

	private final IBinder binder = new LocalBinder();
	private Timer updatingTimer;
	private ServerCommunicationService serverDataDownloader;
	private ServerParametersBuilder agentParametersBuilder = new ServerParametersBuilder().login(SystemMonitorActivity.login).password(SystemMonitorActivity.password).host(SystemMonitorActivity.host);
	private List<Record> records;

	private TimerTask notify = new TimerTask() {

		@Override
		public void run() {
			try {
				downloadDataFromServer();
			} catch (ResolvingException e) {
				Log.e("ResolvingException", e.getMessage());
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
			}
		}
	};


	@Override
	public void onCreate() {
		super.onCreate();
		serverDataDownloader = new ServerCommunicationService();
		updatingTimer = new Timer();
		updatingTimer.schedule(notify, 0, 10000);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		updatingTimer.cancel();
		super.onDestroy();
	}

	public class LocalBinder extends Binder {

		public ServerMonitoringService getService() {
			return ServerMonitoringService.this;
		}

	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}
	
	public void setHost(String host) {
		synchronized(this) {
			agentParametersBuilder = new ServerParametersBuilder().host(host);
		}
	}
	
	public synchronized List<Record> getDownloadedRecords() {
		return records;
	}

	private void downloadDataFromServer() throws ResolvingException {
		synchronized (this) {
			records = serverDataDownloader.downloadRecords(agentParametersBuilder);
		}
		sendNotification();
	}


	private void sendNotification() {
		Intent intent = new Intent(SystemMonitorActivity.DATA_UPDATE);
		intent.putExtra(SystemMonitorActivity.DATA_UPDATE, "updated");
		sendBroadcast(intent);
	}	

}