package com.agh.is.systemmonitor.resolvers.network;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.screens.MainScreen;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.google.gson.Gson;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class LoginUserOnServerTask extends AsyncTask<Void, Void, AsyncTaskResult<String>> {


	private DialogWindowsManager dialogWindowManager;
	private ServerParametersBuilder paramsBuilder = new ServerParametersBuilder();
	private ServerCommunicationService serverDataService = new ServerCommunicationService();
	private Context context;

	public LoginUserOnServerTask(Context context, DialogWindowsManager dialogWindowsManager, ServerParametersBuilder paramsBuilder) {
		this.dialogWindowManager = dialogWindowsManager;
		this.paramsBuilder = paramsBuilder;
		this.context = context;
	}

	@Override
	protected AsyncTaskResult<String> doInBackground(Void... params) {
		try {
			dialogWindowManager.showProgressDialog("Loguję użytkownika");
			String response = serverDataService.loginUserOnServer(paramsBuilder);
			return new AsyncTaskResult<String>(response);
		} catch (ResolvingException e) {
			return new AsyncTaskResult<String>(e, "Logowanie nie powiodło się (problem z połączeniem)");
		}
	}

	@Override
	protected void onPostExecute(AsyncTaskResult<String> result) {
		dialogWindowManager.hideProgressDialog();
		handleServerResponse(result);
		super.onPostExecute(result);
	}

	private void handleServerResponse(AsyncTaskResult<String> result) {
		if (result.getError() != null) {
			dialogWindowManager.showFailureMessage(result.errorMessage());

		} else {
			if (result.getResult().contains("error")) {
				handleLoginFailure(result);
			}
			else {
				handleLoginSuccess();
			}
		}

	}

	private void handleLoginFailure(AsyncTaskResult<String> result) {
		String response = result.getResult();
		Gson gson = new Gson();
		ServerError err = (ServerError)gson.fromJson(response, ServerError.class);
		dialogWindowManager.showFailureMessage("Logowanie nie powiodło się : " + err.error); 
	}

	private void handleLoginSuccess() {
		dialogWindowManager.showSuccessfulMessage("Logowanie powiodło się", new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(context, MainScreen.class);
				i.putExtra("parentID", "0");
				context.startActivity(i);
			}
		});
	}

	private class ServerError {
		String error;
	}


}

