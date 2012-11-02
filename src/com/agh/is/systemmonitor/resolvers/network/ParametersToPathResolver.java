package com.agh.is.systemmonitor.resolvers.network;

public interface ParametersToPathResolver {
	
	public String resolveAgentsDownloadPath(ServerParameters parameters);
	public String resolveAgentsInformationDownloadPath(ServerParameters parameters);
	
}
