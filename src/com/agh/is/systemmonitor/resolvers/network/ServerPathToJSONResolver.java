package com.agh.is.systemmonitor.resolvers.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class ServerPathToJSONResolver {
	
	public String resolve(String path) throws ResolvingException {
		InputStream contentStream = null;
		try {
			contentStream = getStreamWithServerResponse(new HttpGet(path));
			return CharStreams.toString(new InputStreamReader(contentStream));
		}
		catch (ClientProtocolException e) {
			throw new ResolvingException(e);
		}
		catch (IOException e) {
			throw new ResolvingException(e);
		}
		finally {
			Closeables.closeQuietly(contentStream);
		}
	}

	
	private InputStream getStreamWithServerResponse(HttpUriRequest request) throws ClientProtocolException, IOException {
		HttpResponse response = getServerResponse(request);
		HttpEntity entity = response.getEntity();
		return entity == null ? null : entity.getContent();
	}


	private HttpResponse getServerResponse(HttpUriRequest request) throws ClientProtocolException, IOException {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpContext httpContext = new BasicHttpContext();
		HttpResponse response = httpClient.execute(request, httpContext);
		return response;
	}


}
