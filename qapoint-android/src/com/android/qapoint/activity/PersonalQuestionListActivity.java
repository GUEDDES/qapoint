package com.android.qapoint.activity;

import java.util.ArrayList;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.qapoint.listener.PersonalQuestionListActivityListener;

public class PersonalQuestionListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		ArrayList<String> myQuestions = (ArrayList<String>)extras.get("myQuestion_Texts");		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.personalquestions, android.R.id.empty, myQuestions);
		setListAdapter(adapter);
		setContentView(R.layout.personalquestions);
		
		ListView personalQuestions = (ListView) findViewById(android.R.id.list);
		personalQuestions.setClickable(true);
        PersonalQuestionListActivityListener listener = new PersonalQuestionListActivityListener(this.getWindow());
        personalQuestions.setOnItemClickListener(listener);
	}

}
