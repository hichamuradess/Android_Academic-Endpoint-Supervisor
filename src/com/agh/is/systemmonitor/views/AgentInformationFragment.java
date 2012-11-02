package com.agh.is.systemmonitor.views;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;

public class AgentInformationFragment extends RoboFragment{
	
	@InjectView(R.id.agent_information_temp_view) private TextView tempView;
	@InjectView(R.id.agent_information_cpu_thermometer_view) private ThermometerView cputTempView;
	@InjectView(R.id.agent_information_hd_thermometer_view) private ThermometerView hdTempView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (View)inflater.inflate(R.layout.agent_information,
		        container, false);
	}
	
	public void setAgentData(Agent agent, AgentInformation agentInfo) {
		cputTempView.setTemperature(agentInfo.getCpuTemp());
		hdTempView.setTemperature(agentInfo.getHdTemp());
	}
	
}
