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
import com.agh.is.systemmonitor.services.AsyncTaskResponse;
import com.agh.is.systemmonitor.statistics.CpuUsageHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HdTempHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HdUsageHistChartBuilder;
import com.agh.is.systemmonitor.statistics.HistChartBuilder;


public class ShowStatisticsTask extends AsyncTask<Void, Void, AsyncTaskResponse<List<AgentInformation>>> {

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
	protected AsyncTaskResponse<List<AgentInformation>> doInBackground(Void... params) {
		try {
			dialogsManager.showProgressDialog("Pobieram historię dla agenta : " + agent.getName());
			List<AgentInformation> info = serverDataDownloader.downloadStatisticsInformations(paramsBuilder);
			return new AsyncTaskResponse<List<AgentInformation>>(info);
		} catch (ResolvingException e) {
			return new AsyncTaskResponse<List<AgentInformation>>(e, "Operacja nie powiodła się (problem z nawiązaniem połączenia)");
		} catch (Exception e) {
			return new AsyncTaskResponse<List<AgentInformation>>(e, "Operacja nie powiodła się");
		}
	}

	@Override
	protected void onPostExecute(final AsyncTaskResponse<List<AgentInformation>> response) {
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
						chartBuilder = new HdTempHistChartBuilder(response, "czas", "temp.[°C ]");
					}
					else if (column.equals("cpu_temp")){
						chartBuilder = new CpuUsageHistChartBuilder(response, "czas", "%");
					}
					else if (column.equals("disk_full")){
						chartBuilder = new HdUsageHistChartBuilder(response, "czas", "GB");
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
