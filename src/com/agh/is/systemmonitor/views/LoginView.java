package com.agh.is.systemmonitor.views;

import com.agh.is.systemmonitor.R;
import com.agh.is.systemmonitor.R.id;
import com.agh.is.systemmonitor.R.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * Copyright (c) 2012
 * @author Kremski Adrian, Kulpa Marcin, Mirek Krzysztof, Olkuski Aleksander, Osika Jakub, Skrabalak Wojciech, Srebrny Tomasz, Szurek Kacper
 * All rights reserved
 */
public class LoginView extends LinearLayout{
	
	private static final int LAYOUT_ID = R.layout.login_view;
	private EditText hostField = null;
	private EditText emailField = null;
	private EditText passwordField = null;
	private Button loginButton = null;
	
	public LoginView(Context context) {
		super(context);
		initialize();
	}
	
	public LoginView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize();
	}
	
	public void setLoginClickListener(final LoginButtonOnClickListener listener) {
		loginButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				listener.onClick(emailField.getText().toString(), passwordField.getText().toString(), hostField.getText().toString());
			}
		});
	}
	
	private void initialize() {
		String service = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(service);	
		inflater.inflate(LAYOUT_ID, this, true);
		
		emailField = (EditText)findViewById(R.id.login_view_email_field);
		passwordField = (EditText)findViewById(R.id.login_view_password_field);
		hostField = (EditText)findViewById(R.id.login_view_host_field);
		loginButton = (Button)findViewById(R.id.login_view_login_button);
	}
}
