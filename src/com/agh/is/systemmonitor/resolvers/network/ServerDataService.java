package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentsGroup;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ServerDataService {
	
	private final ServerPathToJSONResolver jsonResolver;
	private final ParametersToPathResolver  paramsPathResolver;
	private final JSONToDataResolver agentDataResolver;

	public ServerDataService(ServerPathToJSONResolver jsonResolver, ParametersToPathResolver paramsPathResolver, JSONToDataResolver agentDataResolver) {
		this.jsonResolver = jsonResolver;
		this.paramsPathResolver = paramsPathResolver;
		this.agentDataResolver = agentDataResolver;
	}
	
	public List<Record> downloadRecords(ServerParametersBuilder parameters) throws ResolvingException {
		List<Record> records = Lists.newLinkedList();
		
		records.addAll(downloadAgentGroups(parameters));
		records.addAll(downloadAgents(parameters));
		
		return records;
	}
	
	public List<AgentsGroup> downloadAgentGroups(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsGroupsDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentGroups(downloadedJson);
	}
	
	public List<Agent> downloadAgents(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgents(downloadedJson);
	}
	
	public List<AgentInformation> downloadAgentsInformation(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsInformationDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentsInformations(downloadedJson);
	}
}
