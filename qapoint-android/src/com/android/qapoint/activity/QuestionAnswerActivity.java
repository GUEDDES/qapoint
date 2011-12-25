package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.android.qapoint.listener.QuestionAnswerActivityListener;

public class QuestionAnswerActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answerquestion);
		Bundle extras = getIntent().getExtras();
    	String selectedQuestion = extras.getString("selectedQuestion");
    	TextView question = (TextView) findViewById(R.id.tv_selectedQuestion);
    	question.setText(selectedQuestion);
    	
    	Button button = (Button) findViewById(R.id.bt_answerquestion);
    	button.setOnClickListener(new QuestionAnswerActivityListener(this.getWindow()));
	}
}
