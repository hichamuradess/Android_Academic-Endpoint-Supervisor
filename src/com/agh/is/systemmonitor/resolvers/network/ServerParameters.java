package com.agh.is.systemmonitor.resolvers.network;

import com.google.common.base.Preconditions;

public class ServerParameters {
	
	private final String sortColumn;
	private final String sortOrder;
	private final String recordsLimit;
	private final String sortOffset;
	private final String recordId;
	private final String host;
	
	public static  class ServerParametersBuilder {
		
		private String sortColumn;
		private String sortOrder;
		private String recordsLimit;
		private String sortOffset;
		private String recordId;
		private String host;
		
		public ServerParametersBuilder host(String host) {
			Preconditions.checkNotNull(host);
			this.host = host;
			return this;
		}
		
		public ServerParametersBuilder recordId(String recordID) {
			Preconditions.checkNotNull(recordID);
			this.recordId = recordID;
			return this;
		}
		
		public ServerParametersBuilder sortOrder(String sortOrder) {
			Preconditions.checkNotNull(sortOrder);
			this.sortOrder = sortOrder;
			return this;
		}
		
		public ServerParametersBuilder sortColumn(String sortColumn) {
			Preconditions.checkNotNull(sortColumn);
			this.sortColumn = sortColumn;
			return this;
		}
		
		public ServerParametersBuilder recordsLimit(String recordsLimit) {
			Preconditions.checkNotNull(recordsLimit);
			this.recordsLimit = recordsLimit;
			return this;
		}
		
		public ServerParametersBuilder sortOffset(String sortOffset) {
			Preconditions.checkNotNull(sortOffset);
			this.sortOffset = sortOffset;
			return this;
		}
		
		public ServerParameters build() {
			return new ServerParameters(host, sortColumn, sortOrder, recordsLimit, sortOffset, recordId);
		}
	}
	
	private ServerParameters(String host, String sortColumn, String sortOrder,
			String recordsLimit, String sortOffset, String recordId) {
		super();
		
		
		this.host = host;
		this.sortColumn = sortColumn;
		this.sortOrder = sortOrder;
		this.recordsLimit = recordsLimit;
		this.sortOffset = sortOffset;
		this.recordId = recordId;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getSortColumn() {
		return sortColumn;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public String getRecordsLimit() {
		return recordsLimit;
	}

	public String getSortOffset() {
		return sortOffset;
	}

	public String getRecordId() {
		return recordId;
	}

}
