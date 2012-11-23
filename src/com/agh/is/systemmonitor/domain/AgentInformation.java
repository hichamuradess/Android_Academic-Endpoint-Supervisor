package com.agh.is.systemmonitor.domain;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Ints;

import android.os.Parcel;
import android.os.Parcelable;

public class AgentInformation implements Parcelable, Comparable<AgentInformation>{

	private final int id;
	private final int server_id;
	private final long insert_time;
	private final float cpu_temp;
	private final float hd_temp;
	private final float disk_free;
	private final float disk_full;
	
	public static final Parcelable.Creator<AgentInformation> CREATOR = new Parcelable.Creator<AgentInformation>() {
		public AgentInformation createFromParcel(Parcel in) {
			return new AgentInformation(in.readInt(), in.readInt(), in.readLong(), 
					in.readFloat(), in.readFloat(), in.readFloat(), in.readFloat());
		}

		public AgentInformation[] newArray(int size) {
			return new AgentInformation[size];
		}
	};
	
	public AgentInformation(int id, int serverID, long insertTime, float cpuTemp,
			float hdTemp, float diskFreeSpace, float diskUsedSpace) {
		Preconditions.checkArgument(id >= 0);
		Preconditions.checkArgument(serverID >= 0);
		Preconditions.checkArgument(insertTime >= 0);
		Preconditions.checkArgument(cpuTemp >= 0);
		Preconditions.checkArgument(hdTemp >= 0);
		Preconditions.checkArgument(diskFreeSpace >= 0);
		Preconditions.checkArgument(diskUsedSpace >= 0);
		
		this.id = id;
		this.server_id = serverID;
		this.insert_time = insertTime;
		this.cpu_temp = cpuTemp;
		this.hd_temp = hdTemp;
		this.disk_free = diskFreeSpace;
		this.disk_full = diskUsedSpace;
	}
	
	
	public int getId() {
		return id;
	}


	public int getServerID() {
		return server_id;
	}


	public long getInsertTime() {
		return insert_time;
	}


	public float getCpuTemp() {
		return cpu_temp;
	}


	public float getHdTemp() {
		return hd_temp;
	}


	public float getDiskFreeSpace() {
		return disk_free;
	}


	public float getDiskUsedSpace() {
		return disk_full;
	}


	@Override
	public int describeContents() {
		return 0;
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(server_id);
		dest.writeLong(insert_time);
		dest.writeFloat(cpu_temp);
		dest.writeFloat(hd_temp);
		dest.writeFloat(disk_free);
		dest.writeFloat(disk_full);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(cpu_temp);
		result = prime * result + Float.floatToIntBits(disk_free);
		result = prime * result + Float.floatToIntBits(disk_full);
		result = prime * result + Float.floatToIntBits(hd_temp);
		result = prime * result + id;
		result = prime * result + (int) (insert_time ^ (insert_time >>> 32));
		result = prime * result + server_id;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgentInformation other = (AgentInformation) obj;
		if (Float.floatToIntBits(cpu_temp) != Float
				.floatToIntBits(other.cpu_temp))
			return false;
		if (Float.floatToIntBits(disk_free) != Float
				.floatToIntBits(other.disk_free))
			return false;
		if (Float.floatToIntBits(disk_full) != Float
				.floatToIntBits(other.disk_full))
			return false;
		if (Float.floatToIntBits(hd_temp) != Float
				.floatToIntBits(other.hd_temp))
			return false;
		if (id != other.id)
			return false;
		if (insert_time != other.insert_time)
			return false;
		if (server_id != other.server_id)
			return false;
		return true;
	}

	@Override
	public int compareTo(AgentInformation another) {
		long anothersTime = another.getInsertTime();
		return Ints.checkedCast(insert_time-anothersTime);
	}
}
