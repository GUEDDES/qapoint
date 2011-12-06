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

import edu.boun.ssw.client.ColdAnswer;
import edu.boun.ssw.client.Location;
import edu.boun.ssw.client.QAPointServerProxy;
import edu.boun.ssw.client.Question;

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

}
