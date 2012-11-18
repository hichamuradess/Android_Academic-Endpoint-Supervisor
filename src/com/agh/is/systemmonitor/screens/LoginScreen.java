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
import com.agh.is.systemmonitor.services.AsyncTaskResult;
import com.agh.is.systemmonitor.views.LoginButtonOnClickListener;
import com.agh.is.systemmonitor.views.LoginView;
import com.google.common.io.CharStreams;
import com.google.common.io.Closeables;
import com.google.gson.Gson;

public class LoginScreen extends Activity  {

	private LoginView loginView; 
	private View logoView;
	private View loginSection;
	private Animation logoViewAnimation; 
	private Animation loginSectionAnimation;
	private DialogWindowsManager dialogWindowManager;

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
		logoViewAnimation.setAnimationListener(new LogoViewAnimationListener());
		logoView.startAnimation(logoViewAnimation);
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
		public void onClick(String email, String password) {
			dialogWindowManager.showProgressDialog("Loguję użytkownika");
			new LoginUserOnServerTask(email, password).execute(new Void[]{});
		}
	};

	private class LoginUserOnServerTask extends AsyncTask<Void, Void, AsyncTaskResult<String>> {

		private String login;
		private String pass;

		public LoginUserOnServerTask(String login, String password) {
			this.login = login;
			this.pass = password;
		}

		@Override
		protected AsyncTaskResult<String> doInBackground(Void... params) {
			try {
				String response = loginUser("http://aes.srebrny.pl/?login="+login+"&pass="+pass);
				return new AsyncTaskResult<String>(response);
			} catch (IOException e) {
				return new AsyncTaskResult<String>(e, "Logowanie nie powiodło się (problem z połączeniem)");
			}
		}

		@Override
		protected void onPostExecute(AsyncTaskResult<String> result) {
			dialogWindowManager.hideProgressDialog();
			if (result.getError() != null) {
				dialogWindowManager.showFailureMessage(result.errorMessage());

			} else {
				if (result.getResult().contains("error")) {
					String response = result.getResult();
					Gson gson = new Gson();
					ServerError err = (ServerError)gson.fromJson(response, ServerError.class);
					dialogWindowManager.showFailureMessage("Logowanie nie powiodło się : " + err.error); 
				}
				else {
					dialogWindowManager.showSuccessfulMessage("Logowanie powiodło się", new OnClickListener() {

						@Override
						public void onClick(View v) {
							Intent i = new Intent(getBaseContext(), MainScreen.class);
							MainScreen.login = login;
							MainScreen.password = pass;
							i.putExtra("parentID", "0");
							startActivity(i);
						}
					});
				}
			}
			super.onPostExecute(result);
		}

		public String loginUser(String path) throws IOException  {
			InputStream contentStream = null;
			try {
				contentStream = getStreamWithServerResponse(new HttpGet(path));
				return CharStreams.toString(new InputStreamReader(contentStream));
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

	class ServerError {
		String error;
	}
}
