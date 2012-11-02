package com.agh.is.systemmonitor.screens;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;
import android.content.Intent;
import android.os.Bundle;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.views.AgentInformationFragment;

@ContentView(R.layout.agent_information_screen)
public class AgentInformationScreen extends RoboFragmentActivity {
	
	@InjectFragment(R.id.agent_information_screen_description) AgentInformationFragment infoFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		
		if (intent != null) {
			Agent agent = intent.getParcelableExtra("agent");
			AgentInformation agentInfo = intent.getParcelableExtra("agentInformation");
			infoFragment.setAgentData(agent, agentInfo);
		}
	}
	
}
