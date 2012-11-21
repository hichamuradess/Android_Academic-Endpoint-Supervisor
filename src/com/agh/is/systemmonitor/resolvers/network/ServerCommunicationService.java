package com.agh.is.systemmonitor.resolvers.network;

import java.util.List;

import com.agh.is.systemmonitor.adapters.Record;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.AgentService;
import com.agh.is.systemmonitor.domain.GroupOfAgents;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

public class ServerCommunicationService {
	
	private final ServerPathToJSONResolver jsonResolver;
	private final ParametersToPathResolver  paramsPathResolver;
	private final JSONToDataResolver agentDataResolver;
	
	
	public ServerCommunicationService() {
		this(new ServerPathToJSONResolver(), new ParametersToPathResolver(), new JSONToDataResolver());
	}

	public ServerCommunicationService(ServerPathToJSONResolver jsonResolver, ParametersToPathResolver paramsPathResolver, JSONToDataResolver agentDataResolver) {
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
	
	public String loginUserOnServer(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String loginPath = paramsPathResolver.resolveAgentsGroupsDownloadPath(parametersBuilder);
		String serverResponseJson = jsonResolver.resolve(loginPath);
		return serverResponseJson;
	}
	
	public List<GroupOfAgents> downloadAgentGroups(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsGroupsDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentGroups(downloadedJson);
	}
	
	public List<Agent> downloadAgents(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgents(downloadedJson);
	}
	
	public List<AgentInformation> downloadAllInformationsAboutAgent(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAllInformationsAboutAgentPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAllAvailableInformationsAboutAgent(downloadedJson);
	}
	
	public AgentInformation downloadAgentInformation(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsInformationDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentInformation(downloadedJson);
	}
	
	public List<AgentService> downloadAgentInformationDataSet(ServerParametersBuilder parametersBuilder) throws ResolvingException {
		String downloadPath = paramsPathResolver.resolveAgentsServicesInformationDownloadPath(parametersBuilder);
		String downloadedJson = jsonResolver.resolve(downloadPath);
		return agentDataResolver.resolveAgentServicesInformation(downloadedJson);
	}
}
