package com.android.qapoint.listener;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.ColdAnswerListActivity;
import com.android.qapoint.activity.PersonalQuestionListActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.activity.RecommendedQuestionListActivity;
import com.android.qapoint.activity.SimilarProfilesListActivity;
import com.android.qapoint.activity.UserProfileActivity;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.ColdAnswerList;
import edu.boun.ssw.client.Question;
import edu.boun.ssw.client.QuestionList;
import edu.boun.ssw.client.User;
import edu.boun.ssw.client.UserList;


public class UserProfileActivityListener implements OnClickListener {
	Window window;

	public UserProfileActivityListener(Window window) {
		this.window = window;
	}

	@Override
	public void onClick(View v) {
		final String username = Session.getInstance().getUsername();
		if (v.getId() == R.id.bt_askButton) {
			final String questionText = ((EditText) window.findViewById(R.id.et_questionText)).getText().toString();			
			Question question = new Question(username, questionText);
//			final Location location = new Location();
//			location.setDistrict("istanbul");// TODO

			new Thread(new Runnable() {

				@Override
				public void run() {

					try{
						final String text = questionText.replaceAll(" ", "%20");
						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/askquestion/" + text + "/" + username + "/" + "41.077022:29.026051");
//						String result = RestClient.connect("http://10.0.2.2:8080/rest/qapoint/askquestion/" + text + "/" + username + "/" + "41.077022:29.026051");
						
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
					} catch (Exception e){
						e.printStackTrace();
						Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
						userProfileActivityIntent.putExtra("error", true);
						window.getContext().startActivity(userProfileActivityIntent);
					}
				}

			}).start();
		} if (v.getId() == R.id.bt_recommendedQuestions) {

			//TODO getRecommendedQuestions from server
			new Thread(new Runnable() {

				@Override
				public void run() {
					try{
						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/getrecommendedquestions/" + username);
//						String result = RestClient.connect("http://10.0.2.2:8080/rest/qapoint/getrecommendedquestions/" + username);
						Gson gson = new GsonBuilder().create();
						QuestionList recommendedQuestionList = gson.fromJson(result, QuestionList.class);
						
						ArrayList<String> recommendedQuestionUsers = new ArrayList<String>();
						ArrayList<String> recommendedQuestionTexts = new ArrayList<String>();
						for (Question question : recommendedQuestionList.getQuestionList()) {
							recommendedQuestionUsers.add(question.getUsername());
							recommendedQuestionTexts.add(question.getQuestionText());
						}
						Intent recommendedQuestionsIntent = new Intent(window.getContext(), RecommendedQuestionListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putStringArrayList("recommendedQuestion_Users", recommendedQuestionUsers);
						bundle.putStringArrayList("recommendedQuestion_Texts", recommendedQuestionTexts);
						recommendedQuestionsIntent.putExtras(bundle);
						window.getContext().startActivity(recommendedQuestionsIntent);
					} catch (Exception e){
						e.printStackTrace();
						Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
						userProfileActivityIntent.putExtra("error", true);
						window.getContext().startActivity(userProfileActivityIntent);
					}
				}
			}).start();
			
//			Intent recommendedQuestionsIntent = new Intent(window.getContext(), RecommendedQuestionListActivity.class);
//			Bundle bundle = new Bundle();
//			ArrayList<String> recommendedQuestions = new ArrayList<String>();
//			recommendedQuestions.add("What is the?");
//			recommendedQuestions.add("How are you?");
//			bundle.putStringArrayList("recommendedQuestion_Texts", recommendedQuestions);
//			recommendedQuestionsIntent.putExtras(bundle);
//			window.getContext().startActivity(recommendedQuestionsIntent);
		} else if (v.getId() == R.id.bt_personalQuestions) {
			
			//TODO getPersonalQuestions from server
			new Thread(new Runnable() {

				@Override
				public void run() {
					
					try{
						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/getquestions/" + username);
//						String result = RestClient.connect("http://10.0.2.2:8080/rest/qapoint/getquestions/" + username);
						Gson gson = new GsonBuilder().create();
						QuestionList personalQuestionList = gson.fromJson(result, QuestionList.class);
						
						ArrayList<String> personalQuestionUsers = new ArrayList<String>();
						ArrayList<String> personalQuestionTexts = new ArrayList<String>();
						for (Question question : personalQuestionList.getQuestionList()) {
							personalQuestionUsers.add(question.getUsername());
							personalQuestionTexts.add(question.getQuestionText());
						}
						Intent personalQuestionsIntent = new Intent(window.getContext(), PersonalQuestionListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putStringArrayList("myQuestion_Users", personalQuestionUsers);
						bundle.putStringArrayList("myQuestion_Texts", personalQuestionTexts);
						personalQuestionsIntent.putExtras(bundle);
						window.getContext().startActivity(personalQuestionsIntent);
					} catch (Exception e){
						e.printStackTrace();
						Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
						userProfileActivityIntent.putExtra("error", true);
						window.getContext().startActivity(userProfileActivityIntent);
					}
				}
			}).start();
			
			
			
//			Intent myQuestionsIntent = new Intent(window.getContext(), PersonalQuestionListActivity.class);
//			Bundle bundle = new Bundle();
//			ArrayList<String> personalQuestions = new ArrayList<String>();
//			personalQuestions.add("What is my first Question?");
//			personalQuestions.add("What is my second Question?");
//			bundle.putStringArrayList("myQuestion_Texts", personalQuestions);
//			myQuestionsIntent.putExtras(bundle);
//			window.getContext().startActivity(myQuestionsIntent);
		}  else if (v.getId() == R.id.bt_similarProfiles) {
			//TODO getPersonalQuestions from server
			new Thread(new Runnable() {

				@Override
				public void run() {
					
					try{
						String result = RestClient.connect("http://174.143.253.161:8080/rest/qapoint/getsimilarusers/" + username);
//						String result = RestClient.connect("http://10.0.2.2:8080/rest/qapoint/getsimilarusers/" + username);
						Gson gson = new GsonBuilder().create();
						UserList similarProfilesList = gson.fromJson(result, UserList.class);
						
						ArrayList<String> similarProfiles = new ArrayList<String>();
						for (User user : similarProfilesList.getUserList()) {
							similarProfiles.add(user.getUsername());
						}
						Intent similarProfilesIntent = new Intent(window.getContext(), SimilarProfilesListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putStringArrayList("similarProfiles", similarProfiles);
						similarProfilesIntent.putExtras(bundle);
						window.getContext().startActivity(similarProfilesIntent);
					} catch (Exception e){
						e.printStackTrace();
						Intent userProfileActivityIntent = new Intent(window.getContext(),UserProfileActivity.class);
						userProfileActivityIntent.putExtra("error", true);
						window.getContext().startActivity(userProfileActivityIntent);
					}
				}
			}).start();
		}

	}

}
