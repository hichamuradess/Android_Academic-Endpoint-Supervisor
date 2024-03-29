//package com.agh.is.systemmonitor.networking;
//
//import static org.fest.assertions.Assertions.assertThat;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import com.agh.is.systemmonitor.AESTestRunner;
//import com.agh.is.systemmonitor.resolvers.network.ParametersToPathResolver;
//import com.agh.is.systemmonitor.resolvers.network.ServerParameters;
//import com.agh.is.systemmonitor.resolvers.network.ServerParameters.ServerParametersBuilder;
//
//@RunWith(AESTestRunner.class)
//public class ParametersToPathResolverImplTest {
//
//	private ServerParametersBuilder builder = new ServerParametersBuilder();
//	private ParametersToPathResolver underTest = new ParametersToPathResolver();
//	
//	@Test
//	public void shouldResolveAgentsInformationDownloadPath() {
//		//given
//		ServerParameters parameters = builder.host("http://aes.srebrny.pl/").
//				recordsLimit("0").sortColumn("id").sortOffset("1").sortOrder("asc").build();
//		
//		//when 
//		String path = underTest.resolveAgentsDownloadPath(parameters);
//		
//		//then
//		String correctPath =  "http://aes.srebrny.pl/?do=get_servers&name=id&order=asc&limit=0&offset=1";
//		assertThat(path).isEqualTo(correctPath);
//	}
//	
//	@Test
//	public void shouldResolveAgentsDownloadPath() {
//		//given
//		ServerParameters parameters = builder.host("http://aes.srebrny.pl/").
//				recordsLimit("0").sortColumn("id").sortOffset("1").sortOrder("asc").recordId("1").build();
//		
//		//when 
//		String path = underTest.resolveAgentsInformationDownloadPath(parameters);
//		
//		//then
//		String correctPath = "http://aes.srebrny.pl/?do=display_system&id=1&name=id&order=asc&limit=0&offset=1";
//		assertThat(path).isEqualTo(correctPath);
//	}
//	
//}
