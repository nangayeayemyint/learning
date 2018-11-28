package com.example.lenovo.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.deserializer.ListInstructionDeserialization;
import com.example.lenovo.myapplication.deserializer.ListLevelDeserialization;
import com.example.lenovo.myapplication.models.SInstruction;
import com.example.lenovo.myapplication.models.SLevel;
import com.example.lenovo.myapplication.restapi.LearnerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LevelActivity extends AppCompatActivity {
    private String levelId="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level);

        Type levelListType = new TypeToken<List<SLevel>>(){}.getType();
        Type instructionListType = new TypeToken<List<SInstruction>>(){}.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(levelListType, new ListLevelDeserialization());
        gsonBuilder.registerTypeAdapter(instructionListType, new ListInstructionDeserialization());
        Gson gson = gsonBuilder.create();
        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://cdn.contentful.com/")
                .addConverterFactory(converterFactory)
                .build();

        LearnerService service = retrofit.create(LearnerService.class);
        Call<List<SLevel>> call = service.getLevelList("xtnomdz5cbr8", "master", "level", "8963b88da92f0fec2136bce1ceb438f1dfa13f120cd5c139d68687a1ae0a4af8");

        call.enqueue(new Callback<List<SLevel>>() {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<SLevel>> call, Response<List<SLevel>> response) {
                if(response.isSuccessful()) {
                    List<SLevel> SLevelList = response.body();
                    showAboutTitleLearningAndExercise(SLevelList);
                    Toast.makeText(LevelActivity.this, "success", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(LevelActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<SLevel>> call, Throwable t) {
                Toast.makeText(LevelActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void showAboutTitleLearningAndExercise(List<SLevel> SLevelList){
        DataStore dataStore = new DataStore();

        TextView textView = this.findViewById(R.id.item_level_name);
        TextView expertnessRate = this.findViewById(R.id.expertness_rate);

        Intent intent = getIntent();
        final Bundle bd = intent.getExtras();


        if(bd != null)
        {
            Integer position = (Integer) bd.get("position");
            levelId=  bd.getString("levelId");

            setTitle(SLevelList.get(position).name);
            textView.setText(SLevelList.get(position).description);
            expertnessRate.setText(dataStore.getLevel(position).expertness + "%");
        }

        Button learningButton = this.findViewById(R.id.button_learning);
        learningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent=new Intent(LevelActivity.this, LevelLearningActivity.class);
                intent.putExtra("intex",(Integer)bd.get("position"));
                intent.putExtra("levelId",levelId);
                startActivity(intent);
            }
        });

        Button exercisesButton = this.findViewById(R.id.button_exercises);
        exercisesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                Intent intent=new Intent(LevelActivity.this, LevelExercisesActivity.class);
                intent.putExtra("index",(Integer)bd.get("position"));
                startActivity(intent);

            }
        });
    }
}
