package com.agh.is.systemmonitor.resolvers.network;


public class ParametersToPathResolverImpl implements ParametersToPathResolver {

	@Override
	public String resolveAgentsDownloadPath(ServerParameters parameters) {
		return parameters.getHost() + "?do=get_servers" +
				parseToGETRequestParameters("name", parameters.getSortColumn()) +
				parseToGETRequestParameters("order", parameters.getSortOrder()) +
				parseToGETRequestParameters("limit", parameters.getRecordsLimit()) +
				parseToGETRequestParameters("offset", parameters.getSortOffset());
	}

	@Override
	public String resolveAgentsInformationDownloadPath(ServerParameters parameters) {
		return parameters.getHost() + "?do=display_system" +
				parseToGETRequestParameters("id", parameters.getRecordId()) +
				parseToGETRequestParameters("name", parameters.getSortColumn()) +
				parseToGETRequestParameters("order", parameters.getSortOrder()) +
				parseToGETRequestParameters("limit", parameters.getRecordsLimit()) +
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
