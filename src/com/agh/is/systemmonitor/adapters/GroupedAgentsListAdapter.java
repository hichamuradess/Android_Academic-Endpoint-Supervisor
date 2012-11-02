package com.agh.is.systemmonitor.adapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.views.AgentRowView;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GroupedAgentsListAdapter extends BaseExpandableListAdapter {

    @Override
    public boolean areAllItemsEnabled()
    {
        return true;
    }

    private Context context;

    private ArrayList<String> groups = Lists.newArrayList();
    private ArrayList<ArrayList<Agent>> children = Lists.newArrayList();
    private AgentClickListener listener;

    public GroupedAgentsListAdapter(Context context, List<Agent> agents, AgentClickListener listener) {
    	Map<String, ArrayList<Agent>> groupedAgents = Maps.newHashMap();
    	for(Agent a : agents) {
    		ArrayList<Agent> value = groupedAgents.get(a.getGroup());
    		if (value == null) {
    			value = Lists.newArrayList();
    		}
    		value.add(a);
			groupedAgents.put(a.getGroup(), value);
    	}
    	
    	this.context = context;
    	this.listener = listener;
    	List<String> sortedGroups = Lists.newArrayList();
    	for(String group : groupedAgents.keySet()) {
    		sortedGroups.add(group);
    	}
    	Collections.sort(sortedGroups);
    	
    	for(String group : sortedGroups) {
    		groups.add(group);
    		children.add(groupedAgents.get(group));
    	}
    }
    
    public void addItem(Agent agent, String groupName) {
        if (!groups.contains(groupName)) {
            groups.add(groupName);
        }
        int index = groups.indexOf(groupName);
        if (children.size() < index + 1) {
            children.add(new ArrayList<Agent>());
        }
        children.get(index).add(agent);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return children.get(groupPosition).get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
    
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
            View convertView, ViewGroup parent) {
        Agent agent = (Agent) getChild(groupPosition, childPosition);
        if (convertView == null) {
            return new AgentRowView(context, agent, listener);
        }
        else {
	        AgentRowView rowView = (AgentRowView)convertView;
	        rowView.setAgent(agent);
	        return rowView;
        }
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return children.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    // Return a group view. You can load your custom layout here.
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
            ViewGroup parent) {
        String group = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.agent_group_view, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.agent_group_view_group_name);
        tv.setText(group);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }

}