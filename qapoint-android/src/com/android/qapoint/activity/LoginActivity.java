package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.qapoint.listener.LoginActivityListener;

public class LoginActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button buttonSignin = (Button) findViewById(R.id.bt_signin);
        buttonSignin.setOnClickListener(new LoginActivityListener(this.getWindow()));
        
        Button buttonSignup = (Button) findViewById(R.id.bt_signup);
        buttonSignup.setOnClickListener(new LoginActivityListener(this.getWindow()));
        
        Bundle extras = getIntent().getExtras();
    	if(extras != null && extras.get("error") != null && (Boolean)extras.get("error")){
    		TextView error = (TextView)findViewById(R.id.tv_error_login);
			error.setText("Server Error!");
    	}
    }
}
