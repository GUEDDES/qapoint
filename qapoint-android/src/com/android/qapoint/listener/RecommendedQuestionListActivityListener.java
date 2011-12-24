package com.android.qapoint.listener;

import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.qapoint.activity.QuestionAnswerActivity;

public class RecommendedQuestionListActivityListener implements OnItemClickListener {
	Window window;
	
	public RecommendedQuestionListActivityListener(Window window) {
		this.window = window;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Object o = arg0.getAdapter().getItem(arg2);
        String selectedQuestion = o.toString();
        Intent answerQuestionIntent = new Intent(window.getContext(), QuestionAnswerActivity.class);
		answerQuestionIntent.putExtra("selectedQuestion", selectedQuestion);
		window.getContext().startActivity(answerQuestionIntent);
	}

}
