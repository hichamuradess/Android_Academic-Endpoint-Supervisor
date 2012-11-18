package com.agh.is.systemmonitor.domain;

import com.agh.is.systemmonitor.adapters.Record;

public class AgentsGroup implements Record{

	private int id;
	private int parent_id;
	private String name;
	
	public AgentsGroup(int id, int parent_id, String name) {
		super();
		this.id = id;
		this.parent_id = parent_id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public int getParentID() {
		return parent_id;
	}
	
	public String getName() {
		return name;
	}

}
