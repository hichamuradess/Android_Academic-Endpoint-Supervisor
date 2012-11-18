package com.agh.is.systemmonitor.resolvers.network;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentsGroup;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class JSONToDataResolver {

	private Gson gson = new Gson();
	private Type agentsType = new TypeToken<List<Agent>>(){}.getType();
	private Type agentsInformationsType = new TypeToken<List<AgentInformation>>(){}.getType();
	private Type groupOfAgentsType = new TypeToken<List<AgentsGroup>>(){}.getType();

	public List<Agent> resolveAgents(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), agentsType) ;
	}

	public List<AgentsGroup> resolveAgentGroups(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), groupOfAgentsType) ;
	}
	
	public List<AgentInformation> resolveAgentsInformations(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), agentsInformationsType);
	}



}
