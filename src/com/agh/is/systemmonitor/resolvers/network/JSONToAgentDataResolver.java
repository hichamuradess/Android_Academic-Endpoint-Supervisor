package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;

public interface JSONToAgentDataResolver{
	
	public List<Agent> resolveAgents(String jsonString);
	public List<AgentInformation> resolveAgentsInformations(String jsonString);
	
}
