package com.agh.is.systemmonitor.domain;

import com.agh.is.systemmonitor.adapters.Record;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class Agent implements Parcelable, Record{
	
	private final int id;
	private final String domain;
	private final String name;
	private final int last_check;
	private String group;

	public static final Parcelable.Creator<Agent> CREATOR = new Parcelable.Creator<Agent>() {
		public Agent createFromParcel(Parcel in) {
			return new Agent(in.readInt(), in.readString(), in.readString(), in.readInt(), in.readString());
		}

		public Agent[] newArray(int size) {
			return new Agent[size];
		}
	};
	
	public Agent(int id, String domain, String name, int lastCheck, String group) {
		this.id = id;
		this.domain = domain;
		this.name = name;
		this.last_check = lastCheck;
		this.group = group;
	}

	public void setGroup(String group) {
		this.group = group;
	}
		
	public String getGroup() {
		return group;
	}
	
	public int getId() {
		return id;
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public int getLastCheck() {
		return last_check;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(domain);
		dest.writeString(name);
		dest.writeInt(last_check);
		dest.writeString(group);
	}
	
	
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof Agent)) {
			return false;
		}
		
		Agent agent = (Agent)o;
		
		return agent.getId() == id;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	
	@Override
	public String toString() {
		return "Agent [id=" + id + ", domain=" + domain + ", name=" + name
				+ ", last_check=" + last_check + ", group=" + group + "]";
	}
}
