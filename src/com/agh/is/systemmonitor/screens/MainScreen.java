package com.agh.is.systemmonitor.screens;

import roboguice.receiver.RoboBroadcastReceiver;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordClickListener;
import com.agh.is.systemmonitor.adapters.RecordsAdapter;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.GroupOfAgents;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ShowAgentInformationFromServerTask;
import com.agh.is.systemmonitor.resolvers.network.ShowRecordsFromServerTask;
import com.agh.is.systemmonitor.services.ServerMonitoringService;
import com.agh.is.systemmonitor.services.ServerMonitoringService.LocalBinder;
import com.agh.is.systemmonitor.views.AgentInformationFragment;
import com.agh.is.systemmonitor.views.AgentsListFragment;
import com.google.common.annotations.VisibleForTesting;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class MainScreen extends SystemMonitorActivity implements RecordClickListener{

	private ServerMonitoringService serverMonitoringService; 
	private boolean serverMonitoringServiceIsBound;
	private UpdatedDataReceiver updatedDataReceiver;
	private AgentsListFragment listView;
	private RecordsAdapter listAdapter;
	private DialogWindowsManager dialogsManager;
	private ServerParametersBuilder paramsBuilder = new ServerParametersBuilder().host(ServerMonitoringService.DOWNLOAD_LINK);
	private AgentInformationFragment infoFragment ;

	private ServiceConnection conn = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			LocalBinder binder = (LocalBinder) service;
			serverMonitoringService = binder.getService();
			serverMonitoringServiceIsBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			serverMonitoringServiceIsBound = false;
		}
	};	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_screen);
		initialize();
	}

	public void onStart() {
		super.onStart();
		registerReceiver();
	};

	@Override
	public Dialog onCreateDialog(int id) {
		Dialog dialog = dialogsManager.createDialog(this, id);
		return dialog != null ? dialog : super.onCreateDialog(id);
	}

	@Override
	public void onPrepareDialog(int id, Dialog dialog) {
		dialogsManager.prepareDialog(dialog, id);
		super.onPrepareDialog(id, dialog);
	}

	public void bindService(Intent intent) {
		bindService(intent, conn, Service.BIND_AUTO_CREATE);
	}

	public void setListAdapter(RecordsAdapter listAdapter) {
		this.listAdapter = listAdapter;
		listView.setListAdapter(listAdapter);
	}

	@Override
	public void onClick(Record record) {
		if (record instanceof GroupOfAgents) {
			showGroupOfAgents((GroupOfAgents)record);
		} else {
			showAgentInformation((Agent) record);
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		unregisterReceiver(updatedDataReceiver);
		if (serverMonitoringServiceIsBound) {
			unbindService(conn);
			serverMonitoringServiceIsBound = false;
		}
	}

	@VisibleForTesting
	void initialize() {
		dialogsManager = new DialogWindowsManager(this);
		String parentID = getIntent().getStringExtra("parentID");
		listView = (AgentsListFragment)getSupportFragmentManager().findFragmentById(R.id.main_screen_list);
		new ShowRecordsFromServerTask(this, dialogsManager, paramsBuilder.login(login).password(password).parentId(parentID)).execute(new Void[]{});
	}

	private void registerReceiver() {
		updatedDataReceiver = new UpdatedDataReceiver();
		IntentFilter filter = new IntentFilter(SystemMonitorActivity.DATA_UPDATE)	;
		registerReceiver(updatedDataReceiver, filter);
	}

	private void showGroupOfAgents(GroupOfAgents groupOfAgents) {
		String parentID = String.valueOf(groupOfAgents.getId());
		Intent intent = new Intent(this, MainScreen.class);
		intent.putExtra("parentID", parentID);
		startActivity(intent);
	}

	private void showAgentInformation(Agent agent) {
		infoFragment = (AgentInformationFragment)getSupportFragmentManager().findFragmentById(R.id.main_screen_description);
		new ShowAgentInformationFromServerTask(this, infoFragment, dialogsManager, agent,
				new ServerParametersBuilder().login(login).password(password).host(host).recordId(String.valueOf(agent.getId()))).execute(agent);
	}

	private class UpdatedDataReceiver extends RoboBroadcastReceiver {

		@Override
		public void handleReceive(Context context, Intent intent) {
			String json = intent.getStringExtra(SystemMonitorActivity.DATA_UPDATE);
			if (listAdapter != null) {
				listAdapter.notifyDataSetChanged();
			}
		}

	}
} 