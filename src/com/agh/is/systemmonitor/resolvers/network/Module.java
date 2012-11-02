package com.agh.is.systemmonitor.resolvers.network;

import com.google.inject.AbstractModule;

public class Module extends AbstractModule {

	@Override
	protected void configure() {
		bind(JSONToAgentDataResolver.class).to(JSONToAgentDataResolverImpl.class);
		bind(ParametersToPathResolver.class).to(ParametersToPathResolverImpl.class);
		bind(ServerPathToJSONResolver.class).to(ServerPathToJSONResolverImpl.class);
	}
	

}
