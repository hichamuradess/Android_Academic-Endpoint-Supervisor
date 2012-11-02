package com.agh.is.systemmonitor.resolvers.network;

interface ServerPathToJSONResolver {
	
	String resolve(String path) throws ResolvingException;

}
