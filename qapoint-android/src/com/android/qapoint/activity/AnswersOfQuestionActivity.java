package com.android.qapoint.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AnswersOfQuestionActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.answersofquestion);
		Bundle extras = getIntent().getExtras();
    	String selectedPersonalQuestion = extras.getString("selectedPersonalQuestion");
    	TextView question = (TextView) findViewById(R.id.tv_selectedPersonalQuestion);
    	question.setText(selectedPersonalQuestion);
    	
    	ArrayList<String> usernames = (ArrayList<String>)extras.get("warmAnswers_Users");
		ArrayList<String> answers = (ArrayList<String>)extras.get("warmAnswers_Answers");
		
		TextView warmAnswerUser1 = (TextView) findViewById(R.id.tv_warmAnswerUser1);
		warmAnswerUser1.setText(usernames.get(0) + " says...");
		TextView warmAnswerText1 = (TextView) findViewById(R.id.tv_warmAnswerText1);
		warmAnswerText1.setText("- " + answers.get(0));

		
	}

}
