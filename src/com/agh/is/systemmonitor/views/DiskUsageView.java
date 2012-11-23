package com.agh.is.systemmonitor.views;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Inflater;

import org.achartengine.chart.PieChart;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.ShowStatisticsTask;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;
import com.agh.is.systemmonitor.screens.SystemMonitorActivity;
import com.agh.is.systemmonitor.statistics.HdUsagePieChartViewBuilder;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class DiskUsageView extends LinearLayout {

	private static final int LAYOUT_ID = R.layout.disc_usage_view;

	private TextView discUsageDateField;
	private TextView discUsageHistoryField;
	private TextView discUsageValueField;
	private View pieChartView;
	private TextView hdTempDateField;
	private ThermometerView hdTempValueField;
	private TextView hdTempHistoryField;
	private TextView discUsageValueVield;
	private LinearLayout pieChartPlaceholder;

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
	private Activity activity;

	public DiskUsageView(Activity activity, DialogWindowsManager dialogManager, Agent agent, AgentInformation agentInfo) {
		super(activity);
		this.activity = activity;
		initialize(agentInfo, dialogManager, agent);
	}
	
	private void initialize(AgentInformation agentInfo, final DialogWindowsManager dialogManager, final Agent agent) {
		
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);
		inflater.inflate(LAYOUT_ID, this, true);
		
		
		discUsageValueVield = (TextView)findViewById(R.id.agent_information_disc_usage_value);
		discUsageDateField = (TextView)findViewById(R.id.agent_information_disc_usage_date);
		discUsageHistoryField = (TextView)findViewById(R.id.agent_information_disc_usage_history);
		
		HdUsagePieChartViewBuilder builder = new HdUsagePieChartViewBuilder();
		pieChartView = builder.createPieChartView(activity.getBaseContext(), agentInfo.getDiskUsedSpace(), agentInfo.getDiskFreeSpace());
		pieChartPlaceholder = (LinearLayout)findViewById(R.id.agent_information_disc_usage_piechart);
		pieChartPlaceholder.addView(pieChartView);
		
		discUsageDateField.setText(dateFormat.format(new Date(agentInfo.getInsertTime())));
		discUsageValueVield.setText(String.valueOf((100*agentInfo.getDiskUsedSpace()/(agentInfo.getDiskFreeSpace()+agentInfo.getDiskUsedSpace())))+"%");
		
		
		discUsageHistoryField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new ShowStatisticsTask(activity, dialogManager, agent, 
								new ServerParametersBuilder().login(SystemMonitorActivity.login)
								.password(SystemMonitorActivity.password).column("disk_full")
								.host(SystemMonitorActivity.host).recordId(String.valueOf(agent.getId()))).execute(new Void[]{});
					}
				});
			}
		});
		
		hdTempDateField = (TextView)findViewById(R.id.agent_information_hd_temp_date);
		hdTempValueField = (ThermometerView)findViewById(R.id.agent_information_hd_temp_value);
		hdTempHistoryField = (TextView)findViewById(R.id.agent_information_hd_temp_history);
		
		hdTempDateField.setText(dateFormat.format(new Date(agentInfo.getInsertTime())));
		hdTempValueField.setTemperature(agentInfo.getCpuTemp());
		
		hdTempHistoryField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						new ShowStatisticsTask(activity, dialogManager, agent, 
								new ServerParametersBuilder().login(SystemMonitorActivity.login)
								.password(SystemMonitorActivity.password).column("hd_temp")
								.host(SystemMonitorActivity.host).recordId(String.valueOf(agent.getId()))).execute(new Void[]{});
					}
				});
			}
		});
	}
}
