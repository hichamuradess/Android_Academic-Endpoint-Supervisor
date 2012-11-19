package com.agh.is.systemmonitor.networking;

import static org.fest.assertions.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.agh.is.systemmonitor.AESTestRunner;
import com.agh.is.systemmonitor.domain.Agent;
import com.agh.is.systemmonitor.domain.AgentInformation;
import com.agh.is.systemmonitor.domain.GroupOfAgents;
import com.agh.is.systemmonitor.resolvers.network.JSONToDataResolver;

@RunWith(AESTestRunner.class)
public class JSONToDataResolverTest {
	
	private JSONToDataResolver underTest = new JSONToDataResolver();

	@Test
	public void shouldResolveAgentsWhenDataIsCorrect() {
		//given
		String json = "[{\"id\":\"1\",\"domain\":\"domena\",\"name\":\"nazwa\",\"last_check\":\"1\",\"group\":\"grupa\"}]";
		
		//when
		List<Agent> agents = underTest.resolveAgents(json);
		
		//then
		assertThat(agents).contains(new Agent(1, "domena", "nazwa",1,"grupa"));
	}
	
	@Test
	public void shouldResolveAgentInformationWhenDataIsCorrect() {
		//given
		String json = "[{\"id\":\"1\",\"server_id\":\"1\",\"insert_time\":\"1\",\"cpu_temp\":\"1.1\",\"hd_temp\":\"1.2\",\"disk_free\":\"0\",\"disk_full\":\"100\"}]";
		
		//when
		List<AgentInformation> agentsInfo = underTest.resolveAgentsInformations(json);
		
		//then
		assertThat(agentsInfo).contains(new AgentInformation(1, 1, 1, 1.1f, 1.2f, 0, 100));
	}
	
	@Test
	public void shouldResolveGroupOfAgentsWhenDataIsCorrect() {
		//given
		String json = "[{\"id\":\"1\",\"parent_id\":\"0\",\"name\":\"nazwa\"}]";
		
		//when
		List<GroupOfAgents> groupOfAgents = underTest.resolveAgentGroups(json);
		
		//then
		assertThat(groupOfAgents).contains(new GroupOfAgents(1, 0, "nazwa"));
	}
}
