package com.agh.is.systemmonitor.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.agh.is.systemmonitor.domain.AgentService;
import com.agh.is.systemmonitor.views.ServiceRowView;

public class ServicesAdapter extends BaseAdapter {


	private List<AgentService> services;

	public ServicesAdapter(Context context, List<AgentService> services) {
		this.services = services;
	}

	@Override
	public int getCount() {
		return services.size();
	}

	@Override
	public Object getItem(int position) {
		return services.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ServiceRowView rowView;
		if (convertView == null) {
			rowView = new ServiceRowView(parent.getContext(), services.get(position));
		} else {
			rowView = (ServiceRowView)convertView;
			rowView.setService(services.get(position));
		}
		return rowView;
	}

}