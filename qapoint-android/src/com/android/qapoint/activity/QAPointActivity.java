package com.android.qapoint.activity;

import com.android.qapoint.listener.QAPointActivityListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

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