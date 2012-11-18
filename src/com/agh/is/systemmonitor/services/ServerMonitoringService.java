package com.agh.is.systemmonitor.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.resolvers.network.JSONToDataResolver;
import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolver;
import com.agh.is.systemmonitor.resolvers.network.ResolvingException;
import com.agh.is.systemmonitor.resolvers.network.ServerDataService;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ServerPathToJSONResolver;
import com.agh.is.systemmonitor.screens.MainScreen;
import com.agh.is.systemmonitor.screens.SystemMonitorActivity;

public class ServerMonitoringService extends Service {

	private final IBinder binder = new LocalBinder();
	private Timer updatingTimer;
	private ServerDataService serverDataDownloader;
	public static final String DOWNLOAD_LINK = "http://aes.srebrny.pl/";
	private ServerParametersBuilder agentParametersBuilder = new ServerParametersBuilder().login(SystemMonitorActivity.login).password(SystemMonitorActivity.password).host(DOWNLOAD_LINK);
	private List<Record> records;

	private TimerTask notify = new TimerTask() {

		@Override
		public void run() {
			try {
				downloadDataFromServer();
			} catch (ResolvingException e) {
				e.printStackTrace();
			}
		}
	};


	@Override
	public void onCreate() {
		super.onCreate();
		serverDataDownloader = new ServerDataService(new ServerPathToJSONResolver(), new ParametersToPathResolver(), new JSONToDataResolver());
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