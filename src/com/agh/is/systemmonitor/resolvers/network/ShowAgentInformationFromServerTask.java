package com.agh.is.systemmonitor.resolvers.network;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.AgentInformationScreen;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.views.AgentInformationFragment;

public class ShowAgentInformationFromServerTask extends AsyncTask<Agent, Void, AsyncTaskResult<AgentInformation>> {

	private Agent agent; 
	private DialogWindowsManager dialogsManager;
	private ServerCommunicationService serverDataDownloader = new ServerCommunicationService();
	private ServerParametersBuilder paramsBuilder;
	private AgentInformationFragment infoFragment;
	private Context context;

	public ShowAgentInformationFromServerTask(Context context, AgentInformationFragment infoFragment, DialogWindowsManager dialogsManager, Agent agent, ServerParametersBuilder paramsBuilder) {
		this.dialogsManager = dialogsManager;
		this.paramsBuilder = paramsBuilder;
		this.agent = agent;
		this.infoFragment = infoFragment;
		this.context = context;
	}

	@Override
	protected AsyncTaskResult<AgentInformation> doInBackground(Agent... params) {
		try {
			dialogsManager.showProgressDialog("Pobieram informacje o agencie : " + agent.getName());
			AgentInformation info = serverDataDownloader.downloadAgentInformation(paramsBuilder);
			return new AsyncTaskResult<AgentInformation>(info);
		} catch (ResolvingException e) {
			return new AsyncTaskResult<AgentInformation>(e, "Operacja nie powiodła się (problem z nawiązaniem połączenia)");
		}
	}

	protected void onPostExecute(AsyncTaskResult<AgentInformation> response) {
		dialogsManager.hideProgressDialog();
		if (response.getError() != null) {
			dialogsManager.showFailureMessage("Operacja nie powiodła się");
		}
		else {
			if (response.getResult() != null) {
				showAgentInformation(response);
			} else {
				dialogsManager.showFailureMessage("Brak informacji o agencie");
			}
		}
	};

	private void showAgentInformation(AsyncTaskResult<AgentInformation> response) {
		if (infoFragment != null && infoFragment.isInLayout()) {
			infoFragment.setAgentData(agent, response.getResult(), dialogsManager);
		} else {
			Intent intent = new Intent(context, AgentInformationScreen.class);
			intent.putExtra("agent", agent);
			intent.putExtra("agentInformation",response.getResult()); 
			context.startActivity(intent);
		}

	}
}
