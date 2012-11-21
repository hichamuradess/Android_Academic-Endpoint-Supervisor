package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.statistics.CpuUsageHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HdTempHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HdUsageHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HistChartBuilder;


public class ShowStatisticsTask extends AsyncTask<Void, Void, AsyncTaskResult<List<AgentInformation>>> {

	private Agent agent; 
	private DialogWindowsManager dialogsManager;
	private ServerCommunicationService serverDataDownloader = new ServerCommunicationService();
	private ServerParametersBuilder paramsBuilder;
	private Activity context;

	public ShowStatisticsTask(Activity activity, DialogWindowsManager dialogsManager, Agent agent, ServerParametersBuilder paramsBuilder) {
		this.dialogsManager = dialogsManager;
		this.paramsBuilder = paramsBuilder;
		this.agent = agent;
		this.context = activity;
	}

	@Override
	protected AsyncTaskResult<List<AgentInformation>> doInBackground(Void... params) {
		try {
			dialogsManager.showProgressDialog("Pobieram historię dla agenta : " + agent.getName());
			List<AgentInformation> info = serverDataDownloader.downloadStatisticsInformations(paramsBuilder);
			return new AsyncTaskResult<List<AgentInformation>>(info);
		} catch (ResolvingException e) {
			return new AsyncTaskResult<List<AgentInformation>>(e, "Operacja nie powiodła się (problem z nawiązaniem połączenia)");
		}
	}

	@Override
	protected void onPostExecute(final AsyncTaskResult<List<AgentInformation>> response) {
		dialogsManager.hideProgressDialog();
		if (response.getError() != null) {
			dialogsManager.showFailureMessage("Operacja nie powiodła się");
		}
		else {
			dialogsManager.showSuccessfulMessage("Pobrano historię", new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					ServerParameters params = paramsBuilder.build();
					String column = params.getColumn();
					
					HistChartBuilder chartBuilder = null;
					if (column.equals("hd_temp")){
						chartBuilder = new HdTempHistChartBuilder(response);
					}
					else if (column.equals("cpu_usage")){
						chartBuilder = new CpuUsageHistChartBuilder(response);
					}
					else if (column.equals("disk_usage")){
						chartBuilder = new HdUsageHistChartBuilder(response);
					}
					final HistChartBuilder finalBUilder = chartBuilder;
					if (chartBuilder != null)
					{ 
						context.runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
						context.startActivity(finalBUilder.getIntent(context));
								
							}
						});
					}
				 	
				}
			});
		}
	};

}
