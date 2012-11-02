package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.google.inject.Inject;

public class ServerDataService {
	
	private final ServerPathToJSONResolver jsonResolver;
	private final ParametersToPathResolver  paramsPathResolver;
	private final JSONToAgentDataResolver agentDataResolver;

	public ServerDataService(ServerPathToJSONResolver jsonResolver, ParametersToPathResolver paramsPathResolver, JSONToAgentDataResolver agentDataResolver) {
		this.jsonResolver = jsonResolver;
		this.paramsPathResolver = paramsPathResolver;
		this.agentDataResolver = agentDataResolver;
	}
	
	public List<Agent> downloadAgents(ServerParameters parameters) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsDownloadPath(parameters);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgents(downloadedJson);
	}
	
	public List<AgentInformation> downloadAgentsInformation(ServerParameters parameters) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsInformationDownloadPath(parameters);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentsInformations(downloadedJson);
	}
}
