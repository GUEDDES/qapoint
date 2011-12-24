package com.android.qapoint.activity;

import java.util.ArrayList;

import com.android.qapoint.listener.RecommendedQuestionListActivityListener;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class RecommendedQuestionListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		ArrayList<String> questions = (ArrayList<String>)extras.get("recommendedQuestions");		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.recommendedquestions, android.R.id.empty, questions);
		setListAdapter(adapter);
		setContentView(R.layout.recommendedquestions);

    	ListView recommendedQuestions = (ListView) findViewById(android.R.id.list);
    	recommendedQuestions.setClickable(true);
        RecommendedQuestionListActivityListener listener = new RecommendedQuestionListActivityListener(this.getWindow());
        recommendedQuestions.setOnItemClickListener(listener);
	}

}
