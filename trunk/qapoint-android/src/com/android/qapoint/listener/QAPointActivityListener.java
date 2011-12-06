package com.android.qapoint.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.android.qapoint.activity.LoginActivity;

public class QAPointActivityListener implements OnClickListener{

	private Window window;
	
	public QAPointActivityListener(Window window) {
		this.window = window;
	}
	@Override
	public void onClick(View v) {
		Intent loginActivityIntent = new Intent(window.getContext(),LoginActivity.class);
		window.getContext().startActivity(loginActivityIntent);
	}

}
