package com.agh.is.systemmonitor.screens;

import java.util.List;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;
import roboguice.receiver.RoboBroadcastReceiver;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.AgentClickListener;
import com.agh.is.systemmonitor.adapters.GroupedAgentsListAdapter;
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
import com.agh.is.systemmonitor.services.ServerMonitoringService;
import com.agh.is.systemmonitor.services.ServerMonitoringService.LocalBinder;
import com.agh.is.systemmonitor.views.AgentInformationFragment;
import com.agh.is.systemmonitor.views.AgentsListFragment;
import com.google.common.collect.Lists;

@ContentView(R.layout.main_screen)
public class MainScreen extends RoboFragmentActivity implements AgentClickListener{

	private ServerMonitoringService serverMonitoringService; 
	private boolean serverMonitoringServiceIsBound;
	private UpdatedDataReceiver updatedDataReceiver;
	@InjectFragment(R.id.main_screen_list) private AgentsListFragment listView;
	private GroupedAgentsListAdapter listAdapter;
	private DialogWindowsManager dialogsManager;
	private ServerParametersBuilder paramsBuilder = new ServerParameters.ServerParametersBuilder().host("http://aes.srebrny.pl/");
	private ServerDataService serverDataDownloader = new ServerDataService(
				new ServerPathToJSONResolverImpl(), 
				new ParametersToPathResolverImpl(), 
				new JSONToAgentDataResolverImpl());
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
		dialogsManager = new DialogWindowsManager(this);
		listView.setListAdapter(listAdapter = new GroupedAgentsListAdapter(this, createMockAgents(), this));
	}

	protected void onStart() {
		super.onStart();
		registerReceiver();
		Intent intent = new Intent(this, ServerMonitoringService.class);
//		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	};

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = dialogsManager.createDialog(this, id);
		return dialog != null ? dialog : super.onCreateDialog(id);
	}
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		dialogsManager.prepareDialog(dialog, id);
		super.onPrepareDialog(id, dialog);
	}
	

	@Override
	protected void onStop() {
		super.onStop();
		unregisterReceiver(updatedDataReceiver);
		if (serverMonitoringServiceIsBound) {
			unbindService(conn);
			serverMonitoringServiceIsBound = false;
		}
	}

	private void registerReceiver() {
		updatedDataReceiver = new UpdatedDataReceiver();
		IntentFilter filter = new IntentFilter(SystemMonitorActivity.DATA_UPDATE)	;
		registerReceiver(updatedDataReceiver, filter);
	}

	public class UpdatedDataReceiver extends RoboBroadcastReceiver {

		private int name=0;

		@Override
		public void handleReceive(Context context, Intent intent) {
			AgentsInformationsGroupedByAgents informationsGroupedByAgent = (AgentsInformationsGroupedByAgents)intent.getParcelableExtra(SystemMonitorActivity.DATA_UPDATE);
			for(Agent a : informationsGroupedByAgent.keySet()) {
				listAdapter.addItem(a, Integer.toString(name));
			}
			listAdapter.notifyDataSetChanged();
			name++;
		}

	}

	private List<Agent> createMockAgents() {
		return Lists.newArrayList(
				new Agent(1, "domain", "name", 0, "1.1"),
				new Agent(1, "domain", "name", 0, "2.1"),
				new Agent(1, "domain", "name", 0, "2.2"),
				new Agent(1, "domain", "name", 0, "3.1"),
				new Agent(1, "domain", "name", 0, "3.2"),
				new Agent(1, "domain", "name", 0, "3.3"),
				new Agent(1, "domain", "name", 0, "4.1"),
				new Agent(1, "domain", "name", 0, "4.2"),
				new Agent(1, "domain", "name", 0, "4.3"),
				new Agent(1, "domain", "name", 0, "4.4"),
				new Agent(1, "domain", "name", 0, "5.1"),
				new Agent(1, "domain", "name", 0, "5.2"),
				new Agent(1, "domain", "name", 0, "5.3"),
				new Agent(1, "domain", "name", 0, "5.4"),
				new Agent(1, "domain", "name", 0, "5.5")
			);
	}

	@Override
	public void onClick(Agent agent) {
		showAgentInformation(agent);
	}
	
	private void showAgentInformation(Agent agent) {
		infoFragment = (AgentInformationFragment)getSupportFragmentManager().findFragmentById(R.id.main_screen_description);
		new DownloadAndShowAgentInfoTask().execute(agent);
	}
	
	private class DownloadAndShowAgentInfoTask extends AsyncTask<Agent, Void, AgentInformation> {

		private ResolvingException exception; 
		private Agent agent; 
		
		@Override
		protected AgentInformation doInBackground(Agent... params) {
			this.agent = params[0];
			try {
				dialogsManager.showProgressDialog("Pobieram informacje o agencie : " + agent.getName());
				return serverDataDownloader.downloadAgentsInformation(paramsBuilder.recordId(Integer.toString(agent.getId())).build()).get(0);
			} catch (ResolvingException e) {
				exception = e;
				return null;
			}
		}
		
		protected void onPostExecute(AgentInformation result) {
			dialogsManager.hideProgressDialog();
			
			if (exception != null) {
				dialogsManager.showFailureMessage("Operacja nie powiodła się");
			}
			else {
				showAgentInformation(result);
			}
		};
		
		private void showAgentInformation(AgentInformation agentInfo) {
			if (infoFragment != null && infoFragment.isInLayout()) {
				infoFragment.setAgentData(agent, agentInfo);
			} else {
				Intent intent = new Intent(MainScreen.this, AgentInformationScreen.class);
				intent.putExtra("agent", agent);
				intent.putExtra("agentInformation",agentInfo); 
				startActivity(intent);
			}
			
		}
	}
} 