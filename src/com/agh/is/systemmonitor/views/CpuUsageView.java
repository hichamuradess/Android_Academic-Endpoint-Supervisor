package com.agh.is.systemmonitor.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ShowStatisticsTask;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.screens.SystemMonitorActivity;

public class CpuUsageView extends LinearLayout {

	private static final int LAYOUT_ID = R.layout.cpu_usage;

	private TextView cpuUsageDateField;
	private TextView cpuUsageValueField;
	private TextView cpuUsageHistoryField;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	public CpuUsageView(final Activity activity, final DialogWindowsManager dialogManager, final Agent agent, final AgentInformation agentInfo) {
		super(activity);
		initialize(activity, dialogManager, agent, agentInfo);
	}

	private void initialize(final Activity activity, final DialogWindowsManager dialogManager, final Agent agent, final AgentInformation agentInfo) {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		inflater.inflate(LAYOUT_ID, this, true);

		cpuUsageDateField = (TextView)findViewById(R.id.agent_information_cpu_usage_date);
		cpuUsageValueField = (TextView)findViewById(R.id.agent_information_cpu_usage_value);
		cpuUsageHistoryField = (TextView)findViewById(R.id.agent_information_cpu_usage_history);
		
		cpuUsageDateField.setText(dateFormat.format(new Date(agentInfo.getInsertTime())));
		cpuUsageValueField.setText(agentInfo.getCpuTemp() + "%");
		cpuUsageHistoryField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new ShowStatisticsTask(activity, dialogManager, agent, 
								new ServerParametersBuilder().login(SystemMonitorActivity.login)
								.password(SystemMonitorActivity.password).column("cpu_usage")
								.host(SystemMonitorActivity.host).recordId(String.valueOf(agent.getId()))).execute(new Void[]{});
					}
				});
			}
		});
	}
}
