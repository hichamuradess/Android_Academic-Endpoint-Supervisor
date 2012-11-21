package com.agh.is.systemmonitor.resolvers.network;

import com.google.common.base.Preconditions;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServerParameters{

	private final String sortColumn;
	private final String sortOrder;
	private final String recordsLimit;
	private final String sortOffset;
	private final String recordId;
	private final String parent_id;
	private final String host;
	private final String login;
	private final String password;
	private final String column;

	public static  class ServerParametersBuilder {

		private String sortColumn;
		private String sortOrder;
		private String recordsLimit;
		private String sortOffset;
		private String recordId;
		private String host;
		private String parent_id = "0";
		private String login;
		private String password;
		private String column;

		public ServerParametersBuilder column(String column) {
			Preconditions.checkNotNull(column);
			this.column = column;
			return this;
		}

		public ServerParametersBuilder login(String login) {
			Preconditions.checkNotNull(login);
			this.login = login;
			return this;
		}

		public ServerParametersBuilder password(String password) {
			Preconditions.checkNotNull(password);
			this.password = password;
			return this;
		}

		public ServerParametersBuilder host(String host) {
			Preconditions.checkNotNull(host);
			this.host = host;
			return this;
		}

		public ServerParametersBuilder parentId(String parentID) {
			Preconditions.checkNotNull(parentID);
			this.parent_id = parentID;
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
			return new ServerParameters(login, password, parent_id, host, sortColumn, sortOrder, recordsLimit, sortOffset, recordId, column);
		}
	}

	private ServerParameters(String login, String password, String parentID, String host, String sortColumn, String sortOrder,
			String recordsLimit, String sortOffset, String recordId, String column) {
		super();

		this.column = column;
		this.login = login;
		this.password = password;
		this.parent_id =parentID;
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

	public String getParentID() {
		return parent_id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}
	
	public String getColumn() {
		return column;
	}

}
