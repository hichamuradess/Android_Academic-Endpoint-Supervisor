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
import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordClickListener;
import com.agh.is.systemmonitor.adapters.RecordsAdapter;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentsGroup;
import com.agh.is.systemmonitor.resolvers.network.JSONToDataResolver;
import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolver;
import com.agh.is.systemmonitor.resolvers.network.ResolvingException;
import com.agh.is.systemmonitor.resolvers.network.ServerDataService;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ServerPathToJSONResolver;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.services.ServerMonitoringService;
import com.agh.is.systemmonitor.services.ServerMonitoringService.LocalBinder;
import com.agh.is.systemmonitor.views.AgentInformationFragment;
import com.agh.is.systemmonitor.views.AgentsListFragment;

@ContentView(R.layout.main_screen)
public class MainScreen extends SystemMonitorActivity implements RecordClickListener{

	private ServerMonitoringService serverMonitoringService; 
	private boolean serverMonitoringServiceIsBound;
	private UpdatedDataReceiver updatedDataReceiver;
	@InjectFragment(R.id.main_screen_list) private AgentsListFragment listView;
	private RecordsAdapter listAdapter;
	private DialogWindowsManager dialogsManager;
	private ServerParametersBuilder paramsBuilder = new ServerParametersBuilder().host(ServerMonitoringService.DOWNLOAD_LINK);
	private ServerDataService serverDataDownloader; 
	private AgentInformationFragment infoFragment ;
	public String parentID;

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
		serverDataDownloader = new ServerDataService(new ServerPathToJSONResolver(), new ParametersToPathResolver(), new JSONToDataResolver());
		parentID = getIntent().getStringExtra("parentID");
		new RecordsDownloadTask(paramsBuilder.login(login).password(password).parentId(parentID)).execute(new Void[]{});
	}

	protected void onStart() {
		super.onStart();
		registerReceiver();
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

		@Override
		public void handleReceive(Context context, Intent intent) {
			String json = intent.getStringExtra(SystemMonitorActivity.DATA_UPDATE);
			if (listAdapter != null) {
				listAdapter.notifyDataSetChanged();
			}
		}

	}

	@Override
	public void onClick(Record record) {
		if (record instanceof AgentsGroup) {
			String parentID = String.valueOf(((AgentsGroup) record).getId());
			Intent intent = new Intent(this, MainScreen.class);
			intent.putExtra("parentID", parentID);
			startActivity(intent);
		} else {
			showAgentInformation((Agent) record);
		}
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
				return serverDataDownloader.downloadAgentsInformation(
						paramsBuilder.login(login).password(password).recordId(Integer.toString(agent.getId()))).get(0);
				
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

	private class RecordsDownloadTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Record>>> {

		private ServerParametersBuilder paramsBuilder = null;
		
		public RecordsDownloadTask(ServerParametersBuilder paramsBuilder) {
			this.paramsBuilder = paramsBuilder;
		}
		
		@Override
		protected AsyncTaskResult<List<Record>> doInBackground(Void... voidParams) {
			try {
				dialogsManager.showProgressDialog("Pobieram dane");
				List<Record> agents = serverDataDownloader.downloadRecords(paramsBuilder);
				return new AsyncTaskResult<List<Record>>(agents);
			} catch (ResolvingException e) {
				return new AsyncTaskResult<List<Record>>(e, "Pobieranie nie powiodło się (problem z połączeniem)");
			}
		}

		@Override
		protected void onPostExecute(AsyncTaskResult<List<Record>> result) {
			dialogsManager.hideProgressDialog();
			if (result.getError() != null) {
				dialogsManager.showFailureMessage(result.errorMessage());

			} else {
				if (result.getResult().contains("error")) {
					String errMsg = result.errorMessage();
					dialogsManager.showFailureMessage("Pobieranie nie powiodło się : " + errMsg); 
				}
				else {
					Intent intent = new Intent(getBaseContext(), ServerMonitoringService.class);
					listView.setListAdapter(listAdapter = new RecordsAdapter(getBaseContext(), result.getResult(), MainScreen.this));
					bindService(intent, conn, Context.BIND_AUTO_CREATE);
				}
			}
			super.onPostExecute(result);
		}

	}


} 