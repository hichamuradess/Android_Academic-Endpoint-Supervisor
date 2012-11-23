package com.agh.is.systemmonitor.domain;

import static org.fest.assertions.Assertions.assertThat;

import org.fest.assertions.Fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Intent;

@RunWith(MockitoJUnitRunner.class)
public class AgentInformationTest {

	@Test
	public void shouldNotAcceptInvalidID() {
		//given
		int invalidID = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(invalidID, 1, 1, 1, 1, 1, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}


	@Test
	public void shouldNotAcceptInvalidServerID() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, invalidValue, 1, 1, 1, 1, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidInsertTime() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, 1, invalidValue, 1, 1, 1, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidCpuTemp() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, 1, 1, invalidValue, 1, 1, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidHdTemp() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, 1, 1, 1, invalidValue, 1, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidDiscFreeSpace() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, 1, 1, 1, 1, invalidValue, 1);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldNotAcceptInvalidDiscUsedSpace() {
		//given
		int invalidValue = -1;

		//when
		AgentInformation underTest = null; 
		try {
			underTest = new AgentInformation(1, 1, 1, 1, 1, 1, invalidValue);
			Fail.fail("IllegalArgumentException expected");
		} catch (IllegalArgumentException e) {
			//then;
			assertThat(underTest).isNull();
		}
	}
	
	@Test
	public void shouldReturnTheSameObjectAfterParceling() {
		//given
//		AgentInformation underTest = new AgentInformation(1, 2, 3, 4, 5, 6, 7);
//		Intent intent = new Intent();
//		
//		//when
//		intent.putExtra("underTest", underTest);
//		AgentInformation result = intent.getParcelableExtra("underTest");
//		
//		//then
//		assertThat(underTest).isEqualTo(result);
	}
}
