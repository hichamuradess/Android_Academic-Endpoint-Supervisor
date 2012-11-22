package com.agh.is.systemmonitor.resolvers.network;

import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;


/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ParametersToPathResolver {

	public String resolveLoginPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.build();
		return parameters.getHost() + resolveCredentials(parameters) ;
	}
	
	public String resolveAgentsDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.build();
		return parameters.getHost() + resolveCredentials(parameters) + "&do=display_servers" + resolveCommonParameters(parameters) 
				+ parseToGETRequestParameters("group", parameters.getGroup());
	}

	public String resolveAgentsGroupsDownloadPath(ServerParametersBuilder parametersBuilder) {
		
		ServerParameters parameters =parametersBuilder.build();
		return parameters.getHost() + resolveCredentials(parameters)+ "&do=display_groups" + resolveCommonParameters(parameters) 
				+ parseToGETRequestParameters("parent_id", parameters.getGroup());
	}
	
	public String resolveAgentInformationForStatisticsDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.recordsLimit("50").build();
		return parameters.getHost() +  resolveCredentials(parameters) + "&do=display_system" + resolveCommonParameters(parameters);
	}

	public String resolveAgentsInformationDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.column("all").recordsLimit("1").build();
		return parameters.getHost() +  resolveCredentials(parameters) + "&do=display_system" + resolveCommonParameters(parameters);
	}

	public String resolveAgentsServicesInformationDownloadPath(ServerParametersBuilder paramsBuilder) {
		ServerParameters parameters = paramsBuilder.build();
		return parameters.getHost() +  resolveCredentials(parameters) + "&do=display_current_ping" + resolveCommonParameters(parameters);
	}
	
	private String resolveCredentials(ServerParameters parameters) {
		return "?login="+parameters.getLogin()+ "&pass="+parameters.getPassword();
	}
	
	private String resolveCommonParameters(ServerParameters parameters) {
		return parseToGETRequestParameters("id", parameters.getRecordId()) +
				parseToGETRequestParameters("name", parameters.getSortColumn()) +
				parseToGETRequestParameters("column", parameters.getColumn()) +
				parseToGETRequestParameters("order", parameters.getSortOrder()) +
				parseToGETRequestParameters("limit", parameters.getRecordsLimit()) +
//				parseToGETRequestParameters("parent_id", parameters.getParentID()) +
				parseToGETRequestParameters("offset", parameters.getSortOffset());
	}

	private String parseToGETRequestParameters(String parameterName, String value) {
		if (value != null) {
			return "&" + parameterName + "=" + value;
		}
		else {
			return "";
		}
	}

	public String resolveAllInformationsAboutAgentPath(
			ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.column("all").recordsLimit("12").build();
		return parameters.getHost() +  resolveCredentials(parameters) + "&do=display_system" + resolveCommonParameters(parameters);
	}

}
