package com.android.qapoint.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.LoginErrorActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.activity.UserProfileActivity;
import com.android.qapoint.manager.Session;

public class LoginActivityListener implements OnClickListener {
	private Window window;
	
	public LoginActivityListener(Window window) {
		this.window = window;
	}
	
	@Override
	public void onClick(View v) {
		String username = ((EditText)window.findViewById(R.id.ev_username)).getText().toString();
		String password = ((EditText)window.findViewById(R.id.ev_password)).getText().toString();
		if(username != null && (username.equals("serkan") || username.equals("gozde") || username.equals("tugce")) && password != null && password.equals("1111")){
			Session.getInstance().setUsername(username);
			Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
			window.getContext().startActivity(userProfileActivityIntent);
		} else {
			Intent loginErrorActivityIntent = new Intent(window.getContext(),LoginErrorActivity.class);
			window.getContext().startActivity(loginErrorActivityIntent);
		}
	}

}
