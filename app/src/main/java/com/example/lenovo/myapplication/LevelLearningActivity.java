package com.example.lenovo.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.deserializer.ListInstructionDeserialization;
import com.example.lenovo.myapplication.models.SInstruction;
import com.example.lenovo.myapplication.restapi.LearnerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LevelLearningActivity extends AppCompatActivity {
    private DataStore dataStore = new DataStore();
    private int count =0;
    private MediaPlayer mediaPlayer;
    private MediaPlayer.OnCompletionListener onCompletionListener;
    private String levelId = "";
    private String spaceId="xtnomdz5cbr8";
    public int getCount(){
        return count;
    }

    public void getDecreaseCount(){
        if(count<=0){
            count=0;
        }
        else {
            count--;
        }
    }

    public void getIncreaseCount(){
        if(count>=dataStore.SInstructionList.size()-1){
            count = dataStore.SInstructionList.size()-1;
        }
        else {
            count++;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        mediaPlayer = MediaPlayer.create(this, dataStore.getInstruction(count).uri);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        final Integer intex = (Integer) bd.get("intex");
        if (bd != null) {
            levelId=bd.getString("levelId");
        }

        Type instructionListType = new TypeToken<List<SInstruction>>(){}.getType();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(instructionListType, new ListInstructionDeserialization());
        Gson gson = gsonBuilder.create();
        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://cdn.contentful.com/")
                .addConverterFactory(converterFactory)
                .build();

        LearnerService service = retrofit.create(LearnerService.class);
        Call<List<SInstruction>> call = service.getInstructionList(spaceId, "master",levelId, "8963b88da92f0fec2136bce1ceb438f1dfa13f120cd5c139d68687a1ae0a4af8");

        call.enqueue(new Callback<List<SInstruction>>() {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<SInstruction>> call, Response<List<SInstruction>> response) {
                if(response.isSuccessful()) {
                    List<SInstruction> SInstructionList = response.body();
                    showLearningEachLevel(SInstructionList);
                    Toast.makeText(LevelLearningActivity.this, "success", Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(LevelLearningActivity.this, "error"+levelId, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<SInstruction>> call, Throwable t) {
                Toast.makeText(LevelLearningActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public void showLearningEachLevel(final List<SInstruction> SInstructionList){

        displayWordAndPlaySound(getCount(), SInstructionList);

        ImageView leftArrow = this.findViewById(R.id.left_arrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDecreaseCount();
                int count = getCount();
                displayWordAndPlaySound(count, SInstructionList);
            }
        });

        ImageView rightArrow = this.findViewById(R.id.right_arrow);
        rightArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getIncreaseCount();
                displayWordAndPlaySound(getCount(), SInstructionList);
            }
        });

        ImageView repeatArrow = this.findViewById(R.id.repeat_arrow);
        repeatArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int count = getCount();
                displayWordAndPlaySound(count, SInstructionList);
            }
        });

        ImageView continuousArrow = this.findViewById(R.id.auto_next_arrow);

        onCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.release();
                mediaPlayer = null;
                count++;
                if ( count < dataStore.SInstructionList.size() ) {
                    displayWordAndPlaySound(count, SInstructionList);
                    mediaPlayer.setOnCompletionListener(onCompletionListener);
                }
            }
        };

        continuousArrow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                displayWordAndPlaySound(count, SInstructionList);
                mediaPlayer.setOnCompletionListener(onCompletionListener);
            }
        });
    }

    public void displayWordAndPlaySound(int intex, List<SInstruction> SInstructionList){
        TextView textView1 = this.findViewById(R.id.item_level_name);
        //TextView textView2 = this.findViewById(R.id.item_statement);
        ImageView imageView = this.findViewById(R.id.image);
        //mediaPlayer =MediaPlayer.create(this, dataStore.getInstruction(count).uri);

        textView1.setText(SInstructionList.get(intex).description);
        //textView2.setText(dataStore.getInstruction(intex).statement);
        Picasso.get().load(SInstructionList.get(intex).imageUrl).into(imageView);
        //mediaPlayer.start();
    }

}
