package com.android.qapoint.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.qapoint.activity.R;

import edu.boun.ssw.client.ColdAnswer;

public class ColdAnswerArrayAdapter extends ArrayAdapter<String>{

	private final Context context;
	private final List<ColdAnswer> coldAnswerList;
	public ColdAnswerArrayAdapter(Context context, int textViewResourceId, List<String> answers, List<ColdAnswer> list) {
		super(context, textViewResourceId, answers);
		this.coldAnswerList = list;
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.coldanswerlist, parent, false);
		TextView coldAnswerUserName = (TextView) rowView.findViewById(R.id.tv_coldAnswerUsername);
		TextView coldAnswer = (TextView) rowView.findViewById(R.id.tv_coldAnswer);
		coldAnswerUserName.setText(coldAnswerList.get(position).getUsername());
		coldAnswer.setText(coldAnswerList.get(position).getAnswer());
		return rowView;
	}


	

}
