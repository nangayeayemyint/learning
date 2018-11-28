package com.example.lenovo.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    int counter;
    String name;
    SharedPreferences app_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the app's shared preferences
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);

        // Update the TextView
        final TextView text = (TextView) findViewById(R.id.text);

        final EditText editText = this.findViewById(R.id.edit_text);

        Button saveButton = this.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                savePrefrence();
                text.setText(name);
            }
        });

        Button showButton = this.findViewById(R.id.showButton);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPrefrence();
            }
        });
    }

    public void savePrefrence(){
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("name", name);
        editor.commit(); // Very important
    }

    public void showPrefrence(){
        String preference = app_preferences.getString("name", "");
        Toast.makeText(MainActivity.this,preference+"",Toast.LENGTH_LONG).show();
    }
}
