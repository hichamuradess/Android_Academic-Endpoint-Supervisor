package com.agh.is.systemmonitor.screens;

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

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.resolvers.network.LoginUserOnServerTask;
import com.agh.is.systemmonitor.resolvers.network.ServerParameters;
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.views.LoginButtonOnClickListener;
import com.agh.is.systemmonitor.views.LoginView;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.gson.Gson;

public class LoginScreen extends SystemMonitorActivity  {

	private LoginView loginView; 
	private View logoView;
	private View loginSection;
	private Animation logoViewAnimation; 
	private Animation loginSectionAnimation;
	private DialogWindowsManager dialogWindowManager;
	private boolean isAnimationOn = true;

	public void turnOnAnimation() {
		isAnimationOn = true;
	}
	
	public void turnOffAnimation() {
		isAnimationOn = false;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		initialize();
	}

	private void initialize() {
		loginView = (LoginView)findViewById(R.id.login_screen_login_view);
		logoView = (View)findViewById(R.id.login_view_logo);
		loginSection = (View)findViewById(R.id.login_view_login_section);
		logoViewAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_from_left);
		loginSectionAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate_from_left);

		dialogWindowManager = new DialogWindowsManager(this);
		loginSection.setVisibility(View.INVISIBLE);
		if (isAnimationOn) {
			logoViewAnimation.setAnimationListener(new LogoViewAnimationListener());
			logoView.startAnimation(logoViewAnimation);
		}
		loginView.setLoginClickListener(loginListener);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = dialogWindowManager.createDialog(this, id);
		return dialog != null ? dialog : super.onCreateDialog(id);
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		dialogWindowManager.prepareDialog(dialog, id);
		super.onPrepareDialog(id, dialog);
	}

	private class LogoViewAnimationListener implements AnimationListener {

		@Override
		public void onAnimationStart(Animation animation) {
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
		}

		@Override
		public void onAnimationEnd(Animation animation) {
			loginSection.startAnimation(loginSectionAnimation);
			loginSectionAnimation.setAnimationListener(new AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
				}

				@Override
				public void onAnimationRepeat(Animation animation) {				
				}

				@Override
				public void onAnimationEnd(Animation animation) {
					loginSection.setVisibility(View.VISIBLE);
				}
			});
		}
	}

	private LoginButtonOnClickListener loginListener = new LoginButtonOnClickListener() {
		@Override
		public void onClick(String login, String password, String host) {
			LoginScreen.login = login;
			LoginScreen.password = password;
			LoginScreen.host = host;
			new LoginUserOnServerTask(LoginScreen.this, dialogWindowManager, 
					new ServerParameters.ServerParametersBuilder().login(login).password(password).host(host)).execute(new Void[]{});
		}
	};
}
