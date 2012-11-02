package com.agh.is.systemmonitor.resolvers.network;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentsInformationsGroupedByAgents implements Parcelable{

	private Map<Agent, List<AgentInformation>> groupingMap = Maps.newHashMap();

	public static final Parcelable.Creator<AgentsInformationsGroupedByAgents> CREATOR = new Parcelable.Creator<AgentsInformationsGroupedByAgents>() {
		public AgentsInformationsGroupedByAgents createFromParcel(Parcel in) {
			return new AgentsInformationsGroupedByAgents(in);
		}

		public AgentsInformationsGroupedByAgents[] newArray(int size) {
			return new AgentsInformationsGroupedByAgents[size];
		}
	};

	public AgentsInformationsGroupedByAgents() {

	}

	private AgentsInformationsGroupedByAgents(Parcel in) {
		ClassLoader agentInfoLoader = AgentInformation.class.getClassLoader();

		int amount = in.readInt();
		for(int i = 0; i < amount; ++i) {
			Agent agent = in.readParcelable(Agent.class.getClassLoader());
			groupingMap.put(agent, readAgentsInformationFromPracel(in, agentInfoLoader));
		}
	}

	public void put(Agent agent, List<AgentInformation> agentsInformation) {
		groupingMap.put(agent, agentsInformation);
	}

	public Set<Agent> keySet() {
		return groupingMap.keySet();
	}

	public List<AgentInformation> get(Object key) {
		return groupingMap.get(key);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(groupingMap.size());
		for(Agent agent : groupingMap.keySet()) {
			dest.writeParcelable(agent, flags);
			writeAgentInformationsToParcel(groupingMap.get(agent), dest, flags);
		}
	}	

	private void writeAgentInformationsToParcel(List<AgentInformation> informations, Parcel dest, int flags) {
		dest.writeInt(informations.size());
		for(AgentInformation ai : informations) { 
			dest.writeParcelable(ai, flags);
		}
	}

	private List<AgentInformation> readAgentsInformationFromPracel(Parcel in, ClassLoader agentInfoClassLoader) {
		int size = in.readInt();
		List<AgentInformation> informations = Lists.newArrayListWithCapacity(size);
		for(int i = 0; i < size; ++i) {
			informations.add((AgentInformation)in.readParcelable(agentInfoClassLoader));
		}
		return informations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((groupingMap == null) ? 0 : groupingMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		
		if (!(obj instanceof AgentsInformationsGroupedByAgents)) {
			return false;
		}
		
		AgentsInformationsGroupedByAgents map = (AgentsInformationsGroupedByAgents)obj;
		
		return map.groupingMap.equals(groupingMap);
	}

	

}
