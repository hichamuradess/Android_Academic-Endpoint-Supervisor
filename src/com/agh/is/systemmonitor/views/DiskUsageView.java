package com.agh.is.systemmonitor.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.AgentInformation;

public class DiskUsageView extends LinearLayout {

	private static final int LAYOUT_ID = R.layout.disc_usage_view;

	private TextView discUsageDateField;
	private TextView discUsageHistoryField;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	public DiskUsageView(Context context, AgentInformation agentInfo) {
		super(context);
		initialize(agentInfo);
	}

	private void initialize(AgentInformation agentInfo) {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		inflater.inflate(LAYOUT_ID, this, true);

		discUsageDateField = (TextView)findViewById(R.id.agent_information_disc_usage_date);
		discUsageHistoryField = (TextView)findViewById(R.id.agent_information_disc_usage_history);
		
		discUsageDateField.setText(dateFormat.format(new Date(agentInfo.getInsertTime())));
	}
}
