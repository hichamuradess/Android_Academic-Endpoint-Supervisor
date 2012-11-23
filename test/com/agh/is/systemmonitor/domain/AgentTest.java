package com.agh.is.systemmonitor.domain;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AgentTest {

	
	@Test
	public void shouldNotAcceptInvalidId() {
		//given
		Agent underTest = null;
		int invalidValue = -1;
		
		//when
		try {
			underTest = new Agent(invalidValue, "", "", 1, "");
			Fail.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e) {
			//then
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidDomain() {
		//given
		Agent underTest = null;
		String invalidValue = null;
		
		//when
		try {
			underTest = new Agent(1, invalidValue, "", 1, "");
			Fail.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e) {
			//then
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidName() {
		//given
		Agent underTest = null;
		String invalidValue = null;
		
		//when
		try {
			underTest = new Agent(1, "", invalidValue, 1, "");
			Fail.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e) {
			//then
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidLastCheck() {
		//given
		Agent underTest = null;
		int invalidValue = -1;
		
		//when
		try {
			underTest = new Agent(1, "", "", invalidValue, "");
			Fail.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e) {
			//then
			assertThat(underTest).isNull();
		}
	}
	
	@Test 
	public void shouldNotAcceptnInvalidGroup() {
		//given
		Agent underTest = null;
		String invalidValue = null;
		
		//when
		try {
			underTest = new Agent(1, "", "", 1, invalidValue);
			Fail.fail("IllegalArgumentException expected");
		}
		catch(IllegalArgumentException e) {
			//then
			assertThat(underTest).isNull();
		}
	}
}
