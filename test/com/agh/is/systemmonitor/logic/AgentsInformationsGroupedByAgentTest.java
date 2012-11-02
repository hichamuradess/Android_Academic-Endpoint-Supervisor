package com.agh.is.systemmonitor.logic;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import android.content.Intent;

import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.resolvers.network.AgentsInformationsGroupedByAgents;
import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class AgentsInformationsGroupedByAgentTest {
	
	private AgentsInformationsGroupedByAgents underTeest = new AgentsInformationsGroupedByAgents();
	
	@Test
	public void shouldReturnTheSameObjectFromIntent() {
		//given
		Agent agent = new Agent(1, "domain", "somename", 2000);
		AgentInformation agentInfo = new AgentInformation(2, 2, 4000, 30.2f, 50.8f, 10.2f, 20.4f);
		List<AgentInformation> informations = Lists.newArrayList();
		informations.add(agentInfo);
		underTeest.put(agent, informations);
		
		//when 
		Intent intent = new Intent();
		intent.putExtra("EXTRA", underTeest);
		AgentsInformationsGroupedByAgents received = intent.getParcelableExtra("EXTRA");
		
		//then
		assertThat(underTeest).isEqualTo(received);
		assertThat(received.get(agent)).isEqualTo(informations);
	}

}
