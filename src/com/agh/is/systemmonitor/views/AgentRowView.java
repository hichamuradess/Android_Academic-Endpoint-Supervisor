package com.agh.is.systemmonitor.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.adapters.AgentClickListener;
import com.agh.is.systemmonitor.domain.Agent;


public class AgentRowView extends RelativeLayout{

	private static final int LAYOUT_ID = R.layout.agent_row_view;
	
	private Agent agent = null;
	private TextView agentNameLabel = null;
	private TextView agentDomainLabel = null;
	
	public AgentRowView(Context context, Agent agent, final AgentClickListener listener) {
		super(context);
		this.agent = agent;
		setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				listener.onClick(getAgent());
			}
		});
		initializeViews();
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
		agentNameLabel.setText(agent.getName());
		agentDomainLabel.setText(agent.getDomain());
	}
	
	private void initializeViews() {
		ViewGroup layout = getLayout();
		agentNameLabel = (TextView)layout.findViewById(R.id.agent_row_view_agent_name);
		agentNameLabel.setText(agent.getName());
		agentDomainLabel = (TextView)layout.findViewById(R.id.agent_row_view_agent_domain);
		agentDomainLabel.setText(agent.getDomain());
	}
	
	private ViewGroup getLayout() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		return (ViewGroup)inflater.inflate(LAYOUT_ID, this, true);
	}	
}
