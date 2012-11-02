package com.agh.is.systemmonitor.services;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.AgentsInformationsGroupedByAgents;
import com.agh.is.systemmonitor.resolvers.network.JSONToAgentDataResolverImpl;
import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolverImpl;
import com.agh.is.systemmonitor.resolvers.network.ResolvingException;
import com.agh.is.systemmonitor.resolvers.network.ServerDataService;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ServerPathToJSONResolverImpl;
import com.agh.is.systemmonitor.screens.SystemMonitorActivity;

public class ServerMonitoringService extends Service {

	private final IBinder binder = new LocalBinder();
	private int counter = 0;
	private Timer updatingTimer;
	private ServerDataService serverDataDownloader;
	private ServerParameters agentParameters = new ServerParameters.ServerParametersBuilder().host("http://aes.srebrny.pl/").build();
	private ServerParametersBuilder serverParametersBuilder = new ServerParametersBuilder().host("http://aes.srebrny.pl/")	;
	
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
		serverDataDownloader = new ServerDataService(
				new ServerPathToJSONResolverImpl(), 
				new ParametersToPathResolverImpl(), 
				new JSONToAgentDataResolverImpl());
		
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

	private void downloadDataFromServer() throws ResolvingException {
		AgentsInformationsGroupedByAgents agentsToInformationsMap = new AgentsInformationsGroupedByAgents();
		
		List<Agent> agents = serverDataDownloader.downloadAgents(agentParameters);
		for(Agent a : agents) {
			ServerParameters parameters = serverParametersBuilder.recordId(Integer.toString(a.getId())).build();
			List<AgentInformation> informations = serverDataDownloader.downloadAgentsInformation(parameters);
			agentsToInformationsMap.put(a, informations);
		}
		
		sendData(agentsToInformationsMap);
	}
	
	
	private void sendData(AgentsInformationsGroupedByAgents map) {
		Intent intent = new Intent(SystemMonitorActivity.DATA_UPDATE);
		intent.putExtra(SystemMonitorActivity.DATA_UPDATE, map);
		sendBroadcast(intent);
	}	

}