package com.android.qapoint.activity;

import com.android.qapoint.listener.UserProfileActivityListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
		Bundle extras = getIntent().getExtras();
    	String username = extras.getString("username");
    	TextView welcomeUsername = (TextView) findViewById(R.id.tv_welcomeUsername);
    	welcomeUsername.setText(welcomeUsername.getText() + " " + username);
    	Button button = (Button) findViewById(R.id.bt_askButton);
    	button.setOnClickListener(new UserProfileActivityListener(this.getWindow()));
	}
}
