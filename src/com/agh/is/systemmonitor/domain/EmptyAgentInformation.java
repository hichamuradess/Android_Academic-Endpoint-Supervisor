package com.agh.is.systemmonitor.domain;

public class EmptyAgentInformation extends AgentInformation{

	public EmptyAgentInformation(int id, int serverID, long insertTime,
			float cpuTemp, float hdTemp, float diskFreeSpace,
			float diskUsedSpace) {
		super(id, serverID, insertTime, cpuTemp, hdTemp, diskFreeSpace, diskUsedSpace);
	}

}
