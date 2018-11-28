package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class LevelFinishedActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_execises);

        Bundle bd = getIntent().getExtras();
        if (bd != null) {
            String totalScore = bd.getString("score");
            TextView textView = this.findViewById(R.id.score);
            textView.setText("Your total score are " + totalScore + ".");
        }
    }
}
