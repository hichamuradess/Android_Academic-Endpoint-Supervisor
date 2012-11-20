package com.agh.is.systemmonitor.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentService implements Parcelable{
	
	private int id; 
	private int server_id;
	private String port;
	private long time;
	private long when;
	
	public static final Parcelable.Creator<AgentService> CREATOR = new Parcelable.Creator<AgentService>() {
		public AgentService createFromParcel(Parcel in) {
			return new AgentService(in);
		}

		public AgentService[] newArray(int size) {
			return new AgentService[size];
		}
	};
	
	private AgentService(Parcel in) {
		id = in.readInt();
		server_id = in.readInt();
		port = in.readString();
		time = in.readLong();
		when = in.readLong();
	}
	
	public int getId() {
		return id;
	}
	
	public int getServer_id() {
		return server_id;
	}
	
	public String getPort() {
		return port;
	}
	
	public long getTime() {
		return time;
	}
	
	public long getWhen() {
		return when;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(server_id);
		dest.writeString(port);
		dest.writeLong(time);
		dest.writeLong(when);
	}
}
