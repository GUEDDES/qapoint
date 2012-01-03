package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.qapoint.listener.UserProfileActivityListener;
import com.android.qapoint.manager.Session;

public class UserProfileActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userprofile);
    	String username = Session.getInstance().getUsername();
    	TextView welcomeUsername = (TextView) findViewById(R.id.tv_welcomeUsername);
    	welcomeUsername.setText(welcomeUsername.getText() + " " + username);
    	Button askButton = (Button) findViewById(R.id.bt_askButton);
    	askButton.setOnClickListener(new UserProfileActivityListener(this.getWindow()));
    	Button recommendedButton = (Button) findViewById(R.id.bt_recommendedQuestions);
    	recommendedButton.setOnClickListener(new UserProfileActivityListener(this.getWindow()));
    	Button personalButton = (Button) findViewById(R.id.bt_personalQuestions);
    	personalButton.setOnClickListener(new UserProfileActivityListener(this.getWindow()));
    	
    	Bundle extras = getIntent().getExtras();
    	if(extras != null && (Boolean)extras.get("error")){
    		TextView error = (TextView)findViewById(R.id.tv_error1);
			error.setText("Server Error!");
    	}
	}
}
