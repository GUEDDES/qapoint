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
import com.android.qapoint.restclient.RestClient;

public class SignupActivityListener implements OnClickListener {
	private Window window;
	
	public SignupActivityListener(Window window) {
		this.window = window;
	}
	
	@Override
	public void onClick(View v) {
		final String username = ((EditText)window.findViewById(R.id.ev_username_signup)).getText().toString();
		final String password = ((EditText)window.findViewById(R.id.ev_password_signup)).getText().toString();
		final String repassword = ((EditText)window.findViewById(R.id.ev_repassword_signup)).getText().toString();
		
		if(username == null || password == null || repassword == null || !password.equals(repassword)){
			Intent signUpActivityIntent = new Intent(window.getContext(),SignUpActivity.class);
			signUpActivityIntent.putExtra("error_repassword", true);
			window.getContext().startActivity(signUpActivityIntent);
		} else {

			new Thread(new Runnable() {

				@Override
				public void run() {
					
					try{
						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/signupuser/" + username + "/" + password);
//						RestClient.connect("http://10.0.2.2:8080/rest/qapoint/signupuser/" + username + "/" + password);
						Intent loginActivityIntent = new Intent(window.getContext(),LoginActivity.class);
						window.getContext().startActivity(loginActivityIntent);
					} catch (Exception e){
						e.printStackTrace();
						Intent signUpActivityIntent = new Intent(window.getContext(),SignUpActivity.class);
						signUpActivityIntent.putExtra("error", true);
						window.getContext().startActivity(signUpActivityIntent);
					}
				}
			}).start();
		
		}
	}
}
