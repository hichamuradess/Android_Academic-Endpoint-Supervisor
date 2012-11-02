package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.io.StringReader;
import java.lang.reflect.Type;

public class JSONToAgentDataResolverImpl implements JSONToAgentDataResolver{

	private Gson gson = new Gson();
	private Type agentsType = new TypeToken<List<Agent>>(){}.getType();
	private Type agentsInformationsType = new TypeToken<List<AgentInformation>>(){}.getType();
	
	@Override
	public List<Agent> resolveAgents(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), agentsType) ;
	}

	@Override
	public List<AgentInformation> resolveAgentsInformations(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), agentsInformationsType);
	}

}
