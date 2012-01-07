package com.android.qapoint.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.LinearLayout;
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
		
//		TextView warmAnswerUser1 = (TextView) findViewById(R.id.tv_warmAnswerUser1);
//		warmAnswerUser1.setText(usernames.get(0) + " says...");
//		TextView warmAnswerText1 = (TextView) findViewById(R.id.tv_warmAnswerText1);
//		warmAnswerText1.setText("- " + answers.get(0));
		
		////////////////
		View absLayout =  findViewById(R.id.ly_answersofQuestion);
        
        float x = 17;
        float y = 118;
		for (int i = 0; i < usernames.size(); i++) {
			if(usernames.get(i) == null || usernames.get(i).equals("")) {
				continue;
			}
			TextView userTV = new TextView(this);
	        TextView answerTV = new TextView(this);
			userTV.setText("- " + usernames.get(i) + " says...");
			userTV.setX(x);
			userTV.setY(y);
			userTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			((AbsoluteLayout) absLayout).addView(userTV);
			
			answerTV.setText(answers.get(i));
			answerTV.setX(x);
			answerTV.setY(y + 40);
			answerTV.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			((AbsoluteLayout) absLayout).addView(answerTV);
			
			y += 80;
		}

		
	}

}
