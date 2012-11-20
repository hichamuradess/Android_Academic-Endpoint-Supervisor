package com.agh.is.systemmonitor.screens;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectFragment;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentInformationDataSet;
import com.agh.is.systemmonitor.views.AgentInformationFragment;
import com.agh.is.systemmonitor.views.LoginView;

@ContentView(R.layout.agent_information_screen)
public class AgentInformationScreen extends SystemMonitorActivity {

	@InjectFragment(R.id.agent_information_screen_description) AgentInformationFragment infoFragment;
	private DialogWindowsManager dialogWindowManager;


	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = dialogWindowManager.createDialog(this, id);
		return dialog != null ? dialog : super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		dialogWindowManager.prepareDialog(dialog, id);
		super.onPrepareDialog(id, dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();

		if (intent != null) {
			dialogWindowManager = new DialogWindowsManager(this);
			Agent agent = intent.getParcelableExtra("agent");
			AgentInformationDataSet agentInfo = intent.getParcelableExtra("AgentInformationDataSet");
			infoFragment.setAgentData(agent, agentInfo, dialogWindowManager);
		}
	}

}
