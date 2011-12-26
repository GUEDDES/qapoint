package com.android.qapoint.listener;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.qapoint.activity.AnswersOfQuestionActivity;
import com.android.qapoint.activity.PersonalQuestionListActivity;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.boun.ssw.client.Question;
import edu.boun.ssw.client.WarmAnswer;
import edu.boun.ssw.client.WarmAnswerList;

public class PersonalQuestionListActivityListener implements OnItemClickListener {
	Window window;
	
	public PersonalQuestionListActivityListener(Window window) {
		this.window = window;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		final String username = Session.getInstance().getUsername();
		Object o = arg0.getAdapter().getItem(arg2);
        final String selectedQuestion = o.toString();
        Intent answersOfQuestionIntent = new Intent(window.getContext(), AnswersOfQuestionActivity.class);
        answersOfQuestionIntent.putExtra("selectedPersonalQuestion", selectedQuestion);
        Bundle bundle = new Bundle();
        bundle.putString("selectedPersonalQuestion", selectedQuestion);
        ArrayList<String> warmAnswerUsers = new ArrayList<String>();
        warmAnswerUsers.add("tugce");
        ArrayList<String> warmAnswerTexts = new ArrayList<String>();
        warmAnswerTexts.add("I think you should try....");
		bundle.putStringArrayList("warmAnswers_Users", warmAnswerUsers);
		bundle.putStringArrayList("warmAnswers_Answers", warmAnswerTexts);
		answersOfQuestionIntent.putExtras(bundle);
		window.getContext().startActivity(answersOfQuestionIntent);
		
		
//		//TODO getPersonalQuestions from server
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				final String text = selectedQuestion.replaceAll(" ", "%20");
//				String result = RestClient.connect("http://10.0.2.2:8080/qapoint/warmanswers/" + username + "/" + text);
//				Gson gson = new GsonBuilder().create();
//				WarmAnswerList warmAnswerList = gson.fromJson(result, WarmAnswerList.class);
//				
//				ArrayList<String> warmAnswerUsers = new ArrayList<String>();
//				ArrayList<String> warmAnswerTexts = new ArrayList<String>();
//				for (WarmAnswer answer : warmAnswerList.getWarmAnswerList()) {
//					warmAnswerUsers.add(answer.getUsername());
//					warmAnswerTexts.add(answer.getAnswer());
//				}
//				Intent answersOfQuestionIntent = new Intent(window.getContext(), AnswersOfQuestionActivity.class);
//		        answersOfQuestionIntent.putExtra("selectedPersonalQuestion", selectedQuestion);
//		        Bundle bundle = new Bundle();
//		        bundle.putString("selectedPersonalQuestion", selectedQuestion);
//				bundle.putStringArrayList("warmAnswers_Users", warmAnswerUsers);
//				bundle.putStringArrayList("warmAnswers_Answers", warmAnswerTexts);
//				answersOfQuestionIntent.putExtras(bundle);
//				window.getContext().startActivity(answersOfQuestionIntent);
//			}
//		}).start();
	}
	
}
