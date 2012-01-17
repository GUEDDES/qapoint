package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.qapoint.listener.SignupActivityListener;

public class SignUpActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        Button buttonSignup = (Button) findViewById(R.id.bt_signup_action);
        buttonSignup.setOnClickListener(new SignupActivityListener(this.getWindow()));
        
        Bundle extras = getIntent().getExtras();
    	if(extras != null && extras.get("error") != null && (Boolean)extras.get("error")){
    		TextView error = (TextView)findViewById(R.id.tv_error_signup);
			error.setText("Server Error!");
    	} else if(extras != null && extras.get("error_repassword") != null && (Boolean)extras.get("error_repassword")){
    		TextView error = (TextView)findViewById(R.id.tv_error_signup);
			error.setText("Passwords do not match!");
    	}
    	
    }
}
