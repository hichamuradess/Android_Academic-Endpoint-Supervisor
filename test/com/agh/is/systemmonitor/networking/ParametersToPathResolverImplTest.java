package com.agh.is.systemmonitor.networking;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.agh.is.systemmonitor.resolvers.network.JSONToAgentDataResolverImpl;
import com.agh.is.systemmonitor.resolvers.network.ResolvingException;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters;
import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolverImpl;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolver;
import com.agh.is.systemmonitor.resolvers.network.ServerPathToJSONResolverImpl;

@RunWith(MockitoJUnitRunner.class)
public class ParametersToPathResolverImplTest {

	private ServerParametersBuilder builder = new ServerParametersBuilder();
	private ParametersToPathResolver underTest = new ParametersToPathResolverImpl();
	
	@Test
	public void shouldResolveAgentsInformationDownloadPath() {
		//given
		ServerParameters parameters = builder.host("http://aes.srebrny.pl/").
				recordsLimit("0").sortColumn("id").sortOffset("1").sortOrder("asc").build();
		
		//when 
		String path = underTest.resolveAgentsDownloadPath(parameters);
		
		//then
		String correctPath =  "http://aes.srebrny.pl/?do=get_servers&name=id&order=asc&limit=0&offset=1";
		assertThat(path).isEqualTo(correctPath);
	}
	
	@Test
	public void shouldResolveAgentsDownloadPath() {
		//given
		ServerParameters parameters = builder.host("http://aes.srebrny.pl/").
				recordsLimit("0").sortColumn("id").sortOffset("1").sortOrder("asc").recordId("1").build();
		
		//when 
		String path = underTest.resolveAgentsInformationDownloadPath(parameters);
		
		//then
		String correctPath = "http://aes.srebrny.pl/?do=display_system&id=1&name=id&order=asc&limit=0&offset=1";
				try {
			String json = new ServerPathToJSONResolverImpl().resolve(correctPath);
			System.out.println(new JSONToAgentDataResolverImpl().resolveAgentsInformations(json).get(0).getId());
		} catch (ResolvingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertThat(path).isEqualTo(correctPath);
	}
	
}
