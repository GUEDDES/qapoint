package com.android.qapoint.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginErrorActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginerror);
		
		Button button = (Button)findViewById(R.id.bt_returnback);
    	button.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Intent loginIntent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(loginIntent);
			}
		});
	}
}
