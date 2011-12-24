package com.android.qapoint.listener;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.ColdAnswerListActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.Location;
import edu.boun.ssw.client.QAPointServerProxy;
import edu.boun.ssw.client.Question;

//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.UriBuilder;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.ClientConfig;
//import com.sun.jersey.api.client.config.DefaultClientConfig;

public class UserProfileActivityListener implements OnClickListener{
	Window window;
	
	public UserProfileActivityListener(Window window) {
		this.window = window;
	}

	@Override
	public void onClick(View v) {
		String questionText = ((EditText)window.findViewById(R.id.ev_questionText)).getText().toString();
		String username = Session.getInstance().getUsername();
		Question question = new Question(username, questionText);
		Location location = new Location();
		location.setDistrict("istanbul");//TODO

		new Thread(new Runnable() {

			@Override
			public void run() {
				String result = RestClient.connect("http://10.0.2.2:8080/rest/todo");

				Gson gson = new GsonBuilder().create();
				ColdAnswer coldAnswer = gson.fromJson(result, ColdAnswer.class);
				Log.i("QAPoint", coldAnswer.getUsername() + " - " + coldAnswer.getAnswer());
				
				List<ColdAnswer> coldAnswerList = new ArrayList<ColdAnswer>();
				coldAnswerList.add(coldAnswer);
				ArrayList<String> coldAnswerUsers = new ArrayList<String>();
				ArrayList<String> coldAnswerAnswers = new ArrayList<String>();
				for(ColdAnswer answer : coldAnswerList){
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
		
	}


}
