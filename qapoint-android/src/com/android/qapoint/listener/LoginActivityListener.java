package com.android.qapoint.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.LoginActivity;
import com.android.qapoint.activity.LoginErrorActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.activity.SignUpActivity;
import com.android.qapoint.activity.UserProfileActivity;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.boun.ssw.client.LoginResponse;

public class LoginActivityListener implements OnClickListener {
	private Window window;
	
	public LoginActivityListener(Window window) {
		this.window = window;
	}
	
	@Override
	public void onClick(View v) {
		final String username = ((EditText)window.findViewById(R.id.ev_username)).getText().toString();
		final String password = ((EditText)window.findViewById(R.id.ev_password)).getText().toString();
		
		if(v.getId() == R.id.bt_signin){
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					
					try{
//						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/signinuser/" + username + "/" + password);
						String result = RestClient.connect("http://10.0.2.2:8080/rest/qapoint/loginuser/" + username + "/" + password);
						Gson gson = new GsonBuilder().create();
						LoginResponse resp = gson.fromJson(result, LoginResponse.class);
						if(resp.getValidUser() == 1){
							Session.getInstance().setUsername(username);
							Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
							window.getContext().startActivity(userProfileActivityIntent);
						} else {
							Intent loginErrorActivityIntent = new Intent(window.getContext(),LoginErrorActivity.class);
							window.getContext().startActivity(loginErrorActivityIntent);
						}
					} catch (Exception e){
						e.printStackTrace();
						Intent loginActivityIntent = new Intent(window.getContext(),LoginActivity.class);
						loginActivityIntent.putExtra("error", true);
						window.getContext().startActivity(loginActivityIntent);
					}
				}
			}).start();
		
		} else if (v.getId() == R.id.bt_signup){
			Intent signUpActivityIntent = new Intent(window.getContext(),SignUpActivity.class);
			window.getContext().startActivity(signUpActivityIntent);
		}
	}

}
