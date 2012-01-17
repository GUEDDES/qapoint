package com.android.qapoint.listener;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.android.qapoint.activity.R;
import com.android.qapoint.activity.UserProfileActivity;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;

public class QuestionAnswerActivityListener implements OnClickListener {
	Window window;

	public QuestionAnswerActivityListener(Window window) {
		this.window = window;
	}


	@Override
	public void onClick(View arg0) {
		final String username = Session.getInstance().getUsername();
		final String questionText = ((TextView)window.findViewById(R.id.tv_selectedQuestion)).getText().toString();
		final String answerText = ((EditText)window.findViewById(R.id.et_selectedQuestionAnswer)).getText().toString();
		new Thread(new Runnable() {

			@Override
			public void run() {
				final String qText = questionText.replaceAll(" ", "%20");
				final String aText = answerText.replaceAll(" ", "%20");		
//				RestClient.connect("http://174.143.253.161:8080/rest/qapoint/addwarmanswer/" + qText + "/" + aText + "/" + username);
				RestClient.connect("http://10.0.2.2:8080/rest/qapoint/addwarmanswer/" + qText + "/" + aText + "/" + username);
				
				Intent userProfileActivityIntent = new Intent(window.getContext(), UserProfileActivity.class);
				window.getContext().startActivity(userProfileActivityIntent);
			}
		}).start();
		
	}

}
