package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentInformationDataSet;
import com.agh.is.systemmonitor.domain.AgentService;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.AgentInformationScreen;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.views.AgentInformationFragment;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ShowAgentInformationFromServerTask extends AsyncTask<Agent, Void, AsyncTaskResult<AgentInformationDataSet>> {

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
	protected AsyncTaskResult<AgentInformationDataSet> doInBackground(Agent... params) {
		try {
			dialogsManager.showProgressDialog("Pobieram informacje o agencie : " + agent.getName());
			AgentInformation info = serverDataDownloader.downloadAgentInformation(paramsBuilder);
			List<AgentService> services = serverDataDownloader.downloadAgentInformationDataSet(paramsBuilder);
			return new AsyncTaskResult<AgentInformationDataSet>(new AgentInformationDataSet(info, services));
		} catch (ResolvingException e) {
			return new AsyncTaskResult<AgentInformationDataSet>(e, "Operacja nie powiodła się (problem z nawiązaniem połączenia)");
		} catch (Exception e) {
			return new AsyncTaskResult<AgentInformationDataSet>(e, "Operacja nie powiodła się");
		}
	}

	protected void onPostExecute(AsyncTaskResult<AgentInformationDataSet> response) {
		dialogsManager.hideProgressDialog();
		if (response.getError() != null) {
			dialogsManager.showFailureMessage("Operacja nie powiodła się");
		}
		else {
			if (response.getResult().getAgentInfo() != null) {
				showAgentInformationDataSet(response);
			} else {
				dialogsManager.showInformationalMessage("Brak informacji o agencie");
			}
		}
	};

	private void showAgentInformationDataSet(AsyncTaskResult<AgentInformationDataSet> response) {
		if (infoFragment != null && infoFragment.isInLayout()) {
			infoFragment.initializeAgentData(agent, response.getResult(), dialogsManager);
		} else {
			Intent intent = new Intent(context, AgentInformationScreen.class);
			intent.putExtra("agent", agent);
			intent.putExtra("AgentInformationDataSet",response.getResult()); 
			context.startActivity(intent);
		}
	}

}
