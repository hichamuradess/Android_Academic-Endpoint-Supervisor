package com.agh.is.systemmonitor.screens;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.views.LoginButtonOnClickListener;
import com.agh.is.systemmonitor.views.LoginView;

@ContentView(R.layout.login_screen)
public class LoginScreen extends RoboActivity  {

	@InjectView(R.id.login_screen_login_view) private LoginView loginView; 
	@InjectView(R.id.login_view_logo_image) private View logoView;
    @InjectView(R.id.login_view_login_section) private View loginSection;
    @InjectResource(R.anim.rotate_from_left) private Animation logoViewAnimation; 
	@InjectResource(R.anim.rotate_from_left) private Animation loginSectionAnimation;
	private DialogWindowsManager dialogWindowManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		public void onClick(String email, String passwrod) {
			dialogWindowManager.showSuccessfulMessage("Coś się powiodło", new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(getBaseContext(), MainScreen.class));
				}
			});
		}
	};
}
