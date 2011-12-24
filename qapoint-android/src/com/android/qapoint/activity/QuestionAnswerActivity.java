package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class QuestionAnswerActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerquestion);
		Bundle extras = getIntent().getExtras();
    	String selectedQuestion = extras.getString("selectedQuestion");
    	TextView question = (TextView) findViewById(R.id.tv_selectedQuestion);
    	question.setText(selectedQuestion);
    	
	}
}
