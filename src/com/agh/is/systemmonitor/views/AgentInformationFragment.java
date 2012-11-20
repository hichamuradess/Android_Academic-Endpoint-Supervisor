package com.agh.is.systemmonitor.views;

import roboguice.fragment.RoboFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.screens.DialogWindowsManager;

public class AgentInformationFragment extends RoboFragment{

	//	@InjectView(R.id.agent_information_temp_view) private TextView tempView;
	//	@InjectView(R.id.agent_information_cpu_thermometer_view) private ThermometerView cputTempView;
	private ThermometerView hdTempView;
	private TabHost tabHost;
	private TabSpec cpuUsageTab;
	private TabSpec hdTempTab;
	private TabSpec discUsageTab;
	private TextView titleLabel;
	private DialogWindowsManager dialogManager;
	private Agent agent;
	private AgentInformation agentInfo;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = (View)inflater.inflate(R.layout.agent_information,
				container, false);
		tabHost = (TabHost)layout.findViewById(android.R.id.tabhost);
		titleLabel = (TextView)layout.findViewById(R.id.agent_information_title_label);
		return layout;
	}

	public void setAgentData(Agent agent, AgentInformation agentInfo, DialogWindowsManager dialogManager) {
		this.agent = agent;
		this.agentInfo = agentInfo;
		this.dialogManager= dialogManager;
		titleLabel.setText(agent.getName());
		tabHost.setup();
		addTabWithAgentCpuUsage(tabHost);
		addTabWithAgentHdTemp(tabHost);
		addTabWithAgentDiskUsage(tabHost);
		setUpTabWidget();
    }
	
	private void addTabWithAgentCpuUsage(TabHost tabHost) {
        cpuUsageTab = tabHost.newTabSpec("cpu usage");
        cpuUsageTab.setIndicator("Zużycie procesora");
        cpuUsageTab.setContent(new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return new CpuUsageView(getActivity(), dialogManager, agent, agentInfo);
			}
		});
        tabHost.addTab(cpuUsageTab);
	}
 
	private void addTabWithAgentHdTemp(TabHost tabHost) {
        hdTempTab = tabHost.newTabSpec("hd temp");
        hdTempTab.setIndicator("Temperatura dysku");
        hdTempTab.setContent(new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return new HdTempView(getActivity(), dialogManager, agent, agentInfo);
			}
		});
        tabHost.addTab(hdTempTab);
	}
	
	private void addTabWithAgentDiskUsage(TabHost tabHost) {
        discUsageTab = tabHost.newTabSpec("disk usage");
        discUsageTab.setIndicator("Zużycie dysku?");
        discUsageTab.setContent(new TabContentFactory() {
			@Override
			public View createTabContent(String tag) {
				return new DiskUsageView(getActivity(), dialogManager, agent, agentInfo);
			}
		});
        tabHost.addTab(discUsageTab);
	}
	
	private void setUpTabWidget() {
        tabHost.getTabWidget().setCurrentTab(1);
        setUpTab(0);
        setUpTab(1);
        setUpTab(2);
	}
	
	private void setUpTab(int index) {
        View tabView = tabHost.getTabWidget().getChildAt(index);
        tabView.getLayoutParams().height = 80;
        tabView.setPadding(10, 10, 10, 10);
        tabView.setBackgroundColor(Color.BLACK);
	}
}
