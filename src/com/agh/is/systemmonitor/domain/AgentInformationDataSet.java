package com.agh.is.systemmonitor.domain;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.common.collect.Lists;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class AgentInformationDataSet implements Parcelable {
	
	private AgentInformation agentInfo;
	private List<AgentService> agentServices;

	public static final Parcelable.Creator<AgentInformationDataSet> CREATOR = new Parcelable.Creator<AgentInformationDataSet>() {
		public AgentInformationDataSet createFromParcel(Parcel in) {
			return new AgentInformationDataSet(in);
		}

		public AgentInformationDataSet[] newArray(int size) {
			return new AgentInformationDataSet[size];
		}
	};
	
	private AgentInformationDataSet(Parcel in) {
		agentInfo = (AgentInformation)in.readParcelable(AgentInformation.class.getClassLoader());
		int agentServicesAmount = in.readInt();	
		agentServices = Lists.newLinkedList();
		for (int i = 0; i < agentServicesAmount	; ++i) {
			agentServices.add((AgentService)in.readParcelable(AgentService.class.getClassLoader()));
		}
	}
	
	public AgentInformationDataSet(AgentInformation agentInfo,
			List<AgentService> agentServices) {
		super();
		this.agentInfo = agentInfo;
		this.agentServices = agentServices;
	}

	public AgentInformation getAgentInfo() {
		return agentInfo;
	}
	public List<AgentService> getAgentServices() {
		return agentServices;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(agentInfo, PARCELABLE_WRITE_RETURN_VALUE);
		dest.writeInt(agentServices.size());
		for(AgentService service : agentServices) {
			dest.writeParcelable(service, PARCELABLE_WRITE_RETURN_VALUE);
		}
	}
}
