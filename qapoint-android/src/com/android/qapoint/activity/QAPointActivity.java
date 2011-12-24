package com.android.qapoint.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

import com.android.qapoint.listener.QAPointActivityListener;

public class QAPointActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button button = (Button) findViewById(R.id.bt_comein);
        button.setOnClickListener(new QAPointActivityListener(this.getWindow()));
       
    }
}