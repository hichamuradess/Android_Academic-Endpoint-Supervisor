package com.agh.is.systemmonitor.views;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.agh.is.systemmonitor.R;

public class AgentsListFragment extends RoboFragment{

	@InjectView(R.id.agents_list_agents_list) private ExpandableListView agentsList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (View)inflater.inflate(R.layout.agents_list,
		        container, false);
	}
	
	public void setListAdapter(ExpandableListAdapter adapter) {
		agentsList.setAdapter(adapter);
	}

}
