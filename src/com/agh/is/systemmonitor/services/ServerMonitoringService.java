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
import com.google.common.collect.Lists;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServerMonitoringService extends Service {

	private final IBinder binder = new LocalBinder();
	private Timer dataPullerTimer;
	private ServerCommunicationService serverDataDownloader;
	private ServerParametersBuilder agentParametersBuilder = new ServerParametersBuilder().login(SystemMonitorActivity.login).password(SystemMonitorActivity.password).host(SystemMonitorActivity.host);
	private List<Record> records;
	private TimerTask dataPuller;

	@Override
	public void onCreate() {
		super.onCreate();
		serverDataDownloader = new ServerCommunicationService();
	}

	public void startDataPulling() {
		dataPuller = new DataPullingTask();
		dataPullerTimer = new Timer();
		dataPullerTimer.schedule(dataPuller, 0, 10000);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		dataPullerTimer.cancel();
		return super.onUnbind(intent);
	}

	@Override
	public void onDestroy() {
		dataPullerTimer.cancel();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return binder;
	}

	public void setParametersBuilder(ServerParametersBuilder paramsBuilder) {
		agentParametersBuilder = paramsBuilder; 
	}

	public synchronized List<Record> getDownloadedRecords() {
		return records;
	}

	private void sendNotification() {
		Intent intent = new Intent(SystemMonitorActivity.DATA_UPDATE);
		intent.putExtra(SystemMonitorActivity.DATA_UPDATE, "GROUP : " + agentParametersBuilder.build().getGroup());
		sendBroadcast(intent);
	}	

	public class LocalBinder extends Binder {

		public ServerMonitoringService getService() {
			return ServerMonitoringService.this;
		}

	}

	private class DataPullingTask extends TimerTask {

		@Override
		public void run() {
			try {
				Log.i("ServerMonitorinrService", "Downloading from " + agentParametersBuilder.build().toString());
				List<Record> records = serverDataDownloader.downloadRecords(agentParametersBuilder);
				Log.i("ServerMonitorinrService", "Records downloaded " + records.toString());
				
				synchronized(ServerMonitoringService.this) {
					ServerMonitoringService.this.records = Lists.newLinkedList(records);
				}
				
				sendNotification();
			} catch (ResolvingException e) {
				Log.e("ResolvingException", e.getMessage());
			} catch (Exception e) {
				Log.e("Exception", e.getMessage());
			}
		}

	}

}