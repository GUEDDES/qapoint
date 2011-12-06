package com.android.qapoint.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;

import com.android.qapoint.adapter.ColdAnswerArrayAdapter;

import edu.boun.ssw.client.ColdAnswer;

public class ColdAnswerListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		ArrayList<String> usernames = (ArrayList<String>)extras.get("coldAnswers_Users");
		ArrayList<String> answers = (ArrayList<String>)extras.get("coldAnswers_Answers");
		List<ColdAnswer> coldAnswerList = new ArrayList<ColdAnswer>();
		for(int i=0; i<usernames.size(); i++){
			ColdAnswer coldAnswer = new ColdAnswer();
			coldAnswer.setUsername(usernames.get(i));
			coldAnswer.setAnswer(answers.get(i));
			coldAnswerList.add(coldAnswer);
		}
		ColdAnswerArrayAdapter adapter = new ColdAnswerArrayAdapter(this, R.layout.coldanswerlist, answers, coldAnswerList);
		setListAdapter(adapter);
	}
}
