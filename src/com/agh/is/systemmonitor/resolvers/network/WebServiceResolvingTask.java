package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.view.View;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.services.AsynsTaskResponse;

public abstract class WebServiceResolvingTask<T, K, V> extends AsyncTask<T, K, AsynsTaskResponse<V>> {

	private DialogWindowsManager dialogsManager;
	private String operationStartedMessage;
	private String connnectionProblemMessage;
	private String operationFailedMessage;
	private String successfullMessage;

	public WebServiceResolvingTask(DialogWindowsManager dialogsManager, String operationStartedMessage, 
			String connectionProblemMessage, String operationFailedMessage, String successMessage) {
		this.dialogsManager = dialogsManager;
		this.operationStartedMessage = operationStartedMessage;
		this.operationFailedMessage = operationFailedMessage;
		this.successfullMessage = successMessage;
	}

	@Override
	protected AsynsTaskResponse<V> doInBackground(T... params) {
		try {
			dialogsManager.showProgressDialog(operationStartedMessage);
			return new AsynsTaskResponse<V>(resolve());
		} catch (ResolvingException e) {
			return new AsynsTaskResponse<V>(e, connnectionProblemMessage);
		} catch (Exception e) {
			return new AsynsTaskResponse<V>(e, operationFailedMessage);
		}
	}

	@Override
	protected void onPostExecute(AsynsTaskResponse<V> response) {
		dialogsManager.hideProgressDialog();
		if (response.getError() != null) {
			dialogsManager.showFailureMessage(response.errorMessage());
			handleError(response.getError());
		}
		else {
			dialogsManager.showSuccessfulMessage(successfullMessage, getSuccessDialogWindowButtonClickListener(response.getResult()));
			handleSuccess(response);
		}
	}

	protected abstract V resolve() throws ResolvingException;
	protected abstract void handleError(Exception error);
	protected abstract void handleSuccess(AsynsTaskResponse<V> result);
	protected abstract android.view.View.OnClickListener getSuccessDialogWindowButtonClickListener(V result);



}
