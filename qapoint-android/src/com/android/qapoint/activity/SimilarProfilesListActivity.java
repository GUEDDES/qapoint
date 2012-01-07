package com.android.qapoint.activity;

import java.util.ArrayList;

import com.android.qapoint.listener.PersonalQuestionListActivityListener;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimilarProfilesListActivity extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		ArrayList<String> similarProfiles = (ArrayList<String>)extras.get("similarProfiles");		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.similarprofiles, android.R.id.empty, similarProfiles);
		setListAdapter(adapter);
		setContentView(R.layout.personalquestions);
	}

}
