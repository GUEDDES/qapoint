package com.android.qapoint.activity;

import com.android.qapoint.listener.LoginActivityListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button button = (Button) findViewById(R.id.bt_signin);
        button.setOnClickListener(new LoginActivityListener(this.getWindow()));
    }
}
