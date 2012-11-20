package com.agh.is.systemmonitor.screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

import com.agh.is.systemmonitor.views.dialogs.OperationResultDialog;
import com.agh.is.systemmonitor.views.dialogs.ProgressDialog;

public class DialogWindowsManager {
	
	private Activity activity; 
	
	private static final int PROGRESS_DIALOG_ID = 0; 
	private static final int OPERATION_SUCCESSFUL_DIALOG_ID = 1;
	private static final int OPERATION_FIALED_DIALOG_ID = 2;

	private ProgressDialog progressDialog;
	private String progressDialogText;

	private OperationResultDialog operationResultDialog;
	private OnClickListener okButtonListener;
	private String operationResultDialogText;	

	DialogWindowsManager(Activity activty) {
		this.activity = activty;
	}
	
	Dialog createDialog(Context context, int id) {
		switch(id) {
			case PROGRESS_DIALOG_ID: {
				return progressDialog = new ProgressDialog(context); 
			}
			case OPERATION_FIALED_DIALOG_ID : {
				return operationResultDialog = new OperationResultDialog(context);
			}
			case OPERATION_SUCCESSFUL_DIALOG_ID : {
				return operationResultDialog = new OperationResultDialog(context);
			}
		}
		throw new IllegalArgumentException("Wrong dialog id");
	}

	void prepareDialog(Dialog dialog, int id) {
		switch(id) {
			case PROGRESS_DIALOG_ID: {
				((ProgressDialog)dialog).setText(progressDialogText);
				break;
			}
			case OPERATION_FIALED_DIALOG_ID: {
				((OperationResultDialog)dialog).showFailureMessage(operationResultDialogText, okButtonListener);
				break;
			}
			case OPERATION_SUCCESSFUL_DIALOG_ID: {
				((OperationResultDialog)dialog).showSuccessfulMessage(operationResultDialogText, okButtonListener);
				break;
			}
		}
	}
	
	public void showProgressDialog(String title) {
		this.progressDialogText = title;
		showDialogOnUiThread(PROGRESS_DIALOG_ID);
	}

	public void hideProgressDialog() {
		dismissDialogOnUiThread(progressDialog);
	}

	 void showSuccessfulMessage(String message) {
		this.okButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideOperationResultDialog();
			}
		};
		this.operationResultDialogText = message;
		showDialogOnUiThread(OPERATION_SUCCESSFUL_DIALOG_ID);
	}
	
	public void showSuccessfulMessage(String message, OnClickListener okButtonListener) {
		this.okButtonListener = okButtonListener;
		this.operationResultDialogText = message;
		showDialogOnUiThread(OPERATION_SUCCESSFUL_DIALOG_ID);
	}

	public void showFailureMessage(String message) {
		this.okButtonListener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				hideOperationResultDialog();
			}
		};
		this.operationResultDialogText = message;
		showDialogOnUiThread(OPERATION_FIALED_DIALOG_ID);
	}
	
	void showFailureMessage(String message, OnClickListener okButtonListener) {
		this.okButtonListener = okButtonListener;
		this.operationResultDialogText = message;
		showDialogOnUiThread(OPERATION_FIALED_DIALOG_ID);
	}

	void hideOperationResultDialog() {
		dismissDialogOnUiThread(operationResultDialog);
	}

	private void showDialogOnUiThread(final int dialogID) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				activity.showDialog(dialogID);
			}
		});
	}

	private void dismissDialogOnUiThread(final Dialog dialog) {
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
			}
		});
	}	
}
