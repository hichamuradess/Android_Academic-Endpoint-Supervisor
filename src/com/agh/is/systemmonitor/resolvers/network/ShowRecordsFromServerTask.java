package com.agh.is.systemmonitor.resolvers.network;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.adapters.RecordsAdapter;
import com.agh.is.systemmonitor.domain.AgentInformation;
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
public class ShowRecordsFromServerTask extends WebServiceResolvingTask<Void, Void, AsyncTaskResult<List<Record>>>{

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
			List<Record> agents = serverService.downloadRecords(paramsBuilder);
			return new AsyncTaskResult<List<Record>>(agents);
		} catch (ResolvingException e) {
			return new AsyncTaskResult<List<Record>>(e, "Pobieranie nie powiodło się (problem z połączeniem)");
		}catch (Exception e) {
			return new AsyncTaskResult<List<Record>>(e, "Operacja nie powiodła się");
		}
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
			}
		}

	}

	@Override
	protected AsyncTaskResult<List<Record>> resolve() throws ResolvingException {
		return new AsyncTaskResult<List<Record>>(serverService.downloadRecords(paramsBuilder));
	}

	@Override
	protected void handleError(Exception error) {
		Log.e("ShowRecordsFromServerTask", error.getMessage());
	}

	@Override
	protected void handleSuccess(
			AsyncTaskResult<AsyncTaskResult<List<Record>>> result) {
		Log.e("ShowRecordsFromServerTask", "Downloaded records : " + result.toString());
		
	}

	@Override
	protected OnClickListener getSuccessDialogWindowButtonClickListener(final AsyncTaskResult<List<Record>> result) {
		return new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mainScrnActivity, ServerMonitoringService.class);
				intent.putExtra("group", paramsBuilder.build().getGroup());
				mainScrnActivity.setListAdapter(new RecordsAdapter(mainScrnActivity, result.getResult(), mainScrnActivity));
				mainScrnActivity.bindService(intent);
			}
		};
	}
}
