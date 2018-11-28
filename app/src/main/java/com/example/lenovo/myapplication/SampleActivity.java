package com.example.lenovo.myapplication;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        final TextView textView1 = this.findViewById(R.id.text1);

        final TextView textView2 = this.findViewById(R.id.text2);

        Button button1 = this.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Object textView = textView2.getText();
                textView2.setText(textView1.getText().toString());
                textView1.setText(textView.toString());

                Application myApplication=getApplication();
                ((MyApplication)myApplication).increasementCount();
                TextView textView3 = SampleActivity.this.findViewById(R.id.text3);
                textView3.setText("Count = "+ ((MyApplication)myApplication).getCount()+"");
            }
        });

        Button button2 = this.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent=new Intent(SampleActivity.this, MainActivity.class);
                SampleActivity.this.startActivity(intent);
            }
        });
    }

}
