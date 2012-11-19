package com.agh.is.systemmonitor.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.AgentInformation;

public class CpuUsageView extends LinearLayout {

	private static final int LAYOUT_ID = R.layout.cpu_usage;

	private TextView cpuUsageDateField;
	private TextView cpuUsageValueField;
	private TextView cpuUsageHistoryField;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

	public CpuUsageView(Context context, AgentInformation agentInfo) {
		super(context);
		initialize(agentInfo);
	}

	private void initialize(AgentInformation agentInfo) {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		inflater.inflate(LAYOUT_ID, this, true);

		cpuUsageDateField = (TextView)findViewById(R.id.agent_information_cpu_usage_date);
		cpuUsageValueField = (TextView)findViewById(R.id.agent_information_cpu_usage_value);
		cpuUsageHistoryField = (TextView)findViewById(R.id.agent_information_cpu_usage_value);
		
		cpuUsageDateField.setText(dateFormat.format(new Date(agentInfo.getInsertTime())));
		cpuUsageValueField.setText(agentInfo.getCpuTemp() + "%");
		cpuUsageHistoryField.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
}
