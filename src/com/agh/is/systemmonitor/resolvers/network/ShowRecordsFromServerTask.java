package com.agh.is.systemmonitor.resolvers.network;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordsAdapter;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.screens.MainScreen;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.services.ServerMonitoringService;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ShowRecordsFromServerTask extends AsyncTask<Void, Void, AsyncTaskResult<List<Record>>> {

	private ServerParametersBuilder paramsBuilder = null;
	private DialogWindowsManager dialogsManager;
	private ServerCommunicationService serverService = new ServerCommunicationService();
	private MainScreen mainScrnActivity;

	public ShowRecordsFromServerTask(MainScreen mainScrnActivity, DialogWindowsManager dialogsManager, ServerParametersBuilder paramsBuilder) {
		this.paramsBuilder = paramsBuilder;
		this.dialogsManager = dialogsManager;
		this.mainScrnActivity = mainScrnActivity;
	}

	@Override
	protected AsyncTaskResult<List<Record>> doInBackground(Void... voidParams) {
		try {
			dialogsManager.showProgressDialog("Pobieram dane");
			List<Record> agents = serverService.downloadRecords(paramsBuilder);
			return new AsyncTaskResult<List<Record>>(agents);
		} catch (ResolvingException e) {
			return new AsyncTaskResult<List<Record>>(e, "Pobieranie nie powiodło się (problem z połączeniem)");
		}
	}

	@Override
	protected void onPostExecute(AsyncTaskResult<List<Record>> response) {
		dialogsManager.hideProgressDialog();
		handleServerResponse(response);
		super.onPostExecute(response);
	}

	private void handleServerResponse(AsyncTaskResult<List<Record>> response) {
		if (response.getError() != null) {
			dialogsManager.showFailureMessage(response.errorMessage());

		} else {
			if (response.getResult().contains("error")) {
				String errMsg = response.errorMessage();
				dialogsManager.showFailureMessage("Pobieranie nie powiodło się : " + errMsg); 
			}
			else {
				Intent intent = new Intent(mainScrnActivity, ServerMonitoringService.class);
				mainScrnActivity.setListAdapter(new RecordsAdapter(mainScrnActivity, response.getResult(), mainScrnActivity));
				mainScrnActivity.bindService(intent);
			}
		}
		
	}
}
