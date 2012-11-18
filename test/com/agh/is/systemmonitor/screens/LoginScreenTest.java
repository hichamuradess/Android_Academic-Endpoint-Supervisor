package com.agh.is.systemmonitor.screens;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.Button;
import android.widget.TextView;

import com.agh.is.systemmonitor.AESTestRunner;
import com.agh.is.systemmonitor.R;

@RunWith(AESTestRunner.class)
public class LoginScreenTest {
	
	private LoginScreen loginScreen;
	
	@Before 
	public void setUp() {
		loginScreen = new LoginScreen();
		loginScreen.onCreate(null);
	}
	
	@Test
	public void shouldHaveTitleLabel() {
		//given
		String title = loginScreen.getResources().getString(R.string.login_screen_title_logo);
		
		//when
		TextView titleLabel = (TextView)loginScreen.findViewById(R.id.login_view_logo);
		
		//then
		assertThat(titleLabel.getText().toString()).isEqualTo(title);
	}
	
	@Test
	public void shoudlHaveEmailLabel() {
		//given
		String title = loginScreen.getResources().getString(R.string.login_view_email_label);
		
		//when
		TextView emailLabel = (TextView)loginScreen.findViewById(R.id.login_view_email_label);
		
		//then
		assertThat(emailLabel.getText().toString()).isEqualTo(title);
	}
	
	@Test
	public void shouldHavePasswordLabel() {
		//given
		String title = loginScreen.getResources().getString(R.string.login_view_password_label);
		
		//when
		TextView passLabel = (TextView)loginScreen.findViewById(R.id.login_view_password_label);
		
		//then
		assertThat(passLabel.getText().toString()).isEqualTo(title);
	}
	
	@Test
	public void shoudlHaveLoginButton() {
		//given
		String title = loginScreen.getResources().getString(R.string.login_view_login_button);
		
		//when
		Button btnLabel = (Button)loginScreen.findViewById(R.id.login_view_login_button);
		
		//then
		assertThat(btnLabel.getText().toString()).isEqualTo(title);
	}
	
	@Test
	public void shouldHaveHostLabel() {
		//given
		String title = loginScreen.getResources().getString(R.string.login_view_host_label);
		
		//when
		TextView hostLabel = (Button)loginScreen.findViewById(R.id.login_view_host_label);
		
		//then
		assertThat(hostLabel.getText().toString()).isEqualTo(title);
	}



}
