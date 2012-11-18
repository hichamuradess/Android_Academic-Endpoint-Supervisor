package com.agh.is.systemmonitor.resolvers.network;

import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;



public class ParametersToPathResolver {

	public String resolveAgentsDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.build();
		return parameters.getHost() + resolveCredentials(parameters) + "&do=display_servers" + resolveCommonParameters(parameters);
	}

	public String resolveAgentsGroupsDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.build();
		return parameters.getHost() + resolveCredentials(parameters)+ "&do=display_groups" + resolveCommonParameters(parameters);
	}

	public String resolveAgentsInformationDownloadPath(ServerParametersBuilder parametersBuilder) {
		ServerParameters parameters =parametersBuilder.column("all").build();
		return parameters.getHost() +  resolveCredentials(parameters) + "&do=display_system" + resolveCommonParameters(parameters);
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
				parseToGETRequestParameters("parent_id", parameters.getParentID()) +
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

}
