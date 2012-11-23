package com.agh.is.systemmonitor.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentService;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServiceRowView extends RelativeLayout{

	private static final int LAYOUT_ID = R.layout.service_row_view;
	
	private TextView serviceName;
	private TextView serviceDateOfCheck;
	private ImageView serviceStatusImage;
	private static final SimpleDateFormat dateFormatted = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss");
	
	public ServiceRowView(Context context, AgentService agentService) {
		super(context);
		initializeViews(agentService);
	}
	
	public void setService(AgentService agentService) {
		serviceName.setText("Protokół : " + agentService.getPort());
		serviceDateOfCheck.setText("Data ostatniego pomiaru : " + dateFormatted.format(new Date(agentService.getWhen())));
		if (agentService.getTime() == -1) {
			serviceStatusImage.setBackgroundResource(R.drawable.operation_result_dialog_error);
		} else {
			serviceStatusImage.setBackgroundResource(R.drawable.operation_result_dialog_success_icon);
		}
	}
	
	private void initializeViews(AgentService agentService) {
		ViewGroup layout = getLayout();
		serviceName = (TextView)layout.findViewById(R.id.service_row_view_name);
		serviceDateOfCheck = (TextView)layout.findViewById(R.id.service_row_view_when);
		serviceStatusImage = (ImageView)layout.findViewById(R.id.service_row_view_status_photo);
		setService(agentService);
	}
	
	private ViewGroup getLayout() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		return (ViewGroup)inflater.inflate(LAYOUT_ID, this, true);
	}	
}
