package com.agh.is.systemmonitor.views;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.ServicesAdapter;
import com.agh.is.systemmonitor.domain.AgentService;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServicesView extends LinearLayout {

	private static final int LAYOUT_ID = R.layout.services_list_view;

	public ServicesView(Context context, List<AgentService> agentServices) {
		super(context);
		initialize(agentServices);
	}

	private void initialize(List<AgentService> services) {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		View layout = inflater.inflate(LAYOUT_ID, this, true);
		ListView servicesList = (ListView)layout.findViewById(R.id.services_list_list);
		Collections.sort(services, new Comparator<AgentService>() {
			@Override
			public int compare(AgentService lhs, AgentService rhs) {
				return lhs.getPort().compareTo(rhs.getPort());
			}
		});
		servicesList.setAdapter(new ServicesAdapter(getContext(), services));
	}
}
