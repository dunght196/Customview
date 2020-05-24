package com.example.customview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class StateViewActivity extends AppCompatActivity {

    StateView stateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state_view);
        stateView = findViewById(R.id.state_view);
        stateView.setViewState(StateView.LOADING);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stateView.setViewState(StateView.NETWORK);
            }
        }, 5000);

        findViewById(R.id.btn_click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateView.setViewState(StateView.CONTENT);
            }
        });
    }
}
