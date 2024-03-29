package com.agh.is.systemmonitor.resolvers.network;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.List;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentService;
import com.agh.is.systemmonitor.domain.GroupOfAgents;
import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class JSONToDataResolver {

	private Gson gson = new Gson();
	private Type agentsType = new TypeToken<List<Agent>>(){}.getType();
	private Type allInformationsAboutAgentType = new TypeToken<List<AgentInformation>>(){}.getType();
	private Type agentInformationType = new TypeToken<AgentInformation>(){}.getType();
	private Type groupOfAgentsType = new TypeToken<List<GroupOfAgents>>(){}.getType();
	private Type agentServicesInformationType = new TypeToken<List<AgentService>>(){}.getType();

	public List<Agent> resolveAgents(String jsonString) {
		return gson.fromJson(new StringReader(jsonString), agentsType) ;
	}

	public List<GroupOfAgents> resolveAgentGroups(String jsonString) {
		if (!jsonString.equals("[]")) { 
			return gson.fromJson(new StringReader(jsonString), groupOfAgentsType) ;
		} else {
			return Lists.newLinkedList();
		}
	}
	
	public List<AgentInformation> resolveAllAvailableInformationsAboutAgent(String jsonString) {
		if (!jsonString.equals("[]")) { 
			return gson.fromJson(new StringReader(jsonString), allInformationsAboutAgentType);
		} else {
			return Lists.newLinkedList();
		}
	}

	public AgentInformation resolveAgentInformation(String jsonString) {
		if (!jsonString.equals("[]")) { 
			List<AgentInformation> info = gson.fromJson(new StringReader(jsonString), allInformationsAboutAgentType);
			return info.get(0);
		} else {
			return null;
		}
	}
	
	public List<AgentService> resolveAgentServicesInformation(String jsonString) {
		if (!jsonString.equals("[]")) { 
			return gson.fromJson(new StringReader(jsonString), agentServicesInformationType);
		} else {
			return Lists.newLinkedList();
		}
	}
}
