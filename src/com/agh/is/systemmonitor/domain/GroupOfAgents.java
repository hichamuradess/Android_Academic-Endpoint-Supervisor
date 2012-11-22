package com.agh.is.systemmonitor.domain;

import com.agh.is.systemmonitor.adapters.Record;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class GroupOfAgents implements Record{

	private int id;
	private int parent_id;
	private String name;
	
	public GroupOfAgents(int id, int parent_id, String name) {
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

	@Override
	public String toString() {
		return "GroupOfAgents [id=" + id + ", parent_id=" + parent_id
				+ ", name=" + name + "]";
	}

}
