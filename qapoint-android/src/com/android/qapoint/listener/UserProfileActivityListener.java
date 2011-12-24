package com.android.qapoint.listener;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.ColdAnswerListActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.activity.RecommendedQuestionListActivity;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.ColdAnswerList;
import edu.boun.ssw.client.Location;
import edu.boun.ssw.client.Question;


public class UserProfileActivityListener implements OnClickListener {
	Window window;

	public UserProfileActivityListener(Window window) {
		this.window = window;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.bt_askButton) {
			final String questionText = ((EditText) window.findViewById(R.id.ev_questionText)).getText().toString();
			final String username = Session.getInstance().getUsername();
			Question question = new Question(username, questionText);
			final Location location = new Location();
			location.setDistrict("istanbul");// TODO

			new Thread(new Runnable() {

				@Override
				public void run() {
					final String text = questionText.replaceAll(" ", "%20");
					String result = RestClient.connect("http://10.0.2.2:8080/rest/todo/" + text + "/" + username + "/" + location.getDistrict());

					Gson gson = new GsonBuilder().create();
					ColdAnswerList coldAnswerList = gson.fromJson(result, ColdAnswerList.class);
//					Log.i("QAPoint", coldAnswer.getUsername() + " - " + coldAnswer.getAnswer());
					ArrayList<String> coldAnswerUsers = new ArrayList<String>();
					ArrayList<String> coldAnswerAnswers = new ArrayList<String>();
					for (ColdAnswer answer : coldAnswerList.getColdAnswerList()) {
						coldAnswerUsers.add(answer.getUsername());
						coldAnswerAnswers.add(answer.getAnswer());
					}
					Intent coldAnswerListIntent = new Intent(window.getContext(), ColdAnswerListActivity.class);
					Bundle bundle = new Bundle();
					bundle.putStringArrayList("coldAnswers_Users", coldAnswerUsers);
					bundle.putStringArrayList("coldAnswers_Answers", coldAnswerAnswers);
					coldAnswerListIntent.putExtras(bundle);
					window.getContext().startActivity(coldAnswerListIntent);
				}

			}).start();
		} if (v.getId() == R.id.bt_recommendedQuestions) {
			Intent recommendedQuestionsIntent = new Intent(window.getContext(), RecommendedQuestionListActivity.class);
			//TODO getRecommendedQuestions from server
			Bundle bundle = new Bundle();
			ArrayList<String> recommendedQuestions = new ArrayList<String>();
			recommendedQuestions.add("What is the?");
			recommendedQuestions.add("How are you?");
			bundle.putStringArrayList("recommendedQuestions", recommendedQuestions);
			recommendedQuestionsIntent.putExtras(bundle);
			window.getContext().startActivity(recommendedQuestionsIntent);
		}

	}

}
