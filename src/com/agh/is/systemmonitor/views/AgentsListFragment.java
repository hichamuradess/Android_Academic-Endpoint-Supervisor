package com.agh.is.systemmonitor.views;

import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.agh.is.systemmonitor.R;

public class AgentsListFragment extends RoboFragment{

	@InjectView(R.id.agents_list_agents_list) private ListView recordsList;
	@InjectResource(R.drawable.group_arrow) private Drawable groupIndicatorArrow;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return (View)inflater.inflate(R.layout.agents_list,
		        container, false);
	}
	
	public void setListAdapter(ListAdapter adapter) {
		recordsList.setAdapter(adapter);
	}

}
