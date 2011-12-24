package com.android.qapoint.listener;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;

import com.android.qapoint.activity.ColdAnswerListActivity;
import com.android.qapoint.activity.R;
import com.android.qapoint.manager.Session;
import com.android.qapoint.restclient.RestClient;

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.Location;
import edu.boun.ssw.client.QAPointServerProxy;
import edu.boun.ssw.client.Question;

import java.net.URI;

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
				RestClient.connect("http://ws.audioscrobbler.com/2.0/?method=album.gettags&artist=cher&album=believe&api_key=b25b959554ed76058ac220b7b2e0a026&format=json");
			}
			 
		 }).start();
//		askQuestion(question, location);
		
		
		
		QAPointServerProxy qaPointServerProxy = new QAPointServerProxy();
		List<ColdAnswer> coldAnswerList = qaPointServerProxy.askQuestion(question, location);
		ArrayList<String> coldAnswerUsers = new ArrayList<String>();
		ArrayList<String> coldAnswerAnswers = new ArrayList<String>();
		for(ColdAnswer coldAnswer : coldAnswerList){
			coldAnswerUsers.add(coldAnswer.getUsername());
			coldAnswerAnswers.add(coldAnswer.getAnswer());
		}
		Intent coldAnswerListIntent = new Intent(window.getContext(), ColdAnswerListActivity.class);
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("coldAnswers_Users", coldAnswerUsers);
		bundle.putStringArrayList("coldAnswers_Answers", coldAnswerAnswers);
		coldAnswerListIntent.putExtras(bundle);
		window.getContext().startActivity(coldAnswerListIntent);
	}
	
//	public void askQuestion(Question question, Location location){
//		ClientConfig config = new DefaultClientConfig();
//		Client client = Client.create(config);
//		WebResource service = client.resource(getBaseURI());
//		// Fluent interfaces
//		System.out.println(service.path("rest").path("hello").accept(
//				MediaType.TEXT_PLAIN).get(ClientResponse.class).toString());
//		// Get plain text
//		System.out.println(service.path("rest").path("hello").accept(
//				MediaType.TEXT_PLAIN).get(String.class));
//		// Get XML
//		System.out.println(service.path("rest").path("hello").accept(
//				MediaType.TEXT_XML).get(String.class));
//		// The HTML
//		System.out.println(service.path("rest").path("hello").accept(
//				MediaType.TEXT_HTML).get(String.class));
//	}
//	
//	private static URI getBaseURI() {
//		return UriBuilder.fromUri(
//				"http://localhost:8080").build();
//	}

}
