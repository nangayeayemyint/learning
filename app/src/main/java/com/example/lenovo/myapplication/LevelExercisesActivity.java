package com.example.lenovo.myapplication;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.myapplication.models.Answer;
import com.example.lenovo.myapplication.models.Question;
import com.example.lenovo.myapplication.restapi.QuestionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LevelExercisesActivity extends AppCompatActivity {
    private int count = 0;
    private int totalScore = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        Bundle bd = getIntent().getExtras();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(converterFactory)
                .build();

        QuestionService service = retrofit.create(QuestionService.class);
        Call<List<Question>> call = service.listRepos();


        call.enqueue(new Callback<List<Question>>() {
            @RequiresApi(api=Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if(response.isSuccessful()) {
                    List<Question> questionList = response.body();
                    playExercise(questionList);
                }
                else {
                    Toast.makeText(LevelExercisesActivity.this, "error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
                Toast.makeText(LevelExercisesActivity.this, "error:"+t.getMessage(), Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

        final Integer intex = (Integer) bd.get("index");
        if (bd != null) {
            setTitle("TestContentful - " + intex);
            Toast.makeText(LevelExercisesActivity.this, "Downloading the exercises", Toast.LENGTH_SHORT).show();
        }

    }

    public void playExercise(final List<Question> questionList){
        LinearLayout loadingLayout = this.findViewById(R.id.loading_layout);
        loadingLayout.setVisibility(View.GONE);
        LinearLayout questionAndanswerLayout = this.findViewById(R.id.question_and_answer_layout);
        questionAndanswerLayout.setVisibility(View.VISIBLE);

        count = 0;
        totalScore = 0;
        Question currentQuestion = getCurrentQuestion(questionList);
        showQuestion(currentQuestion);

        Button button = findViewById(R.id.ok);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Question currentQuestion = getCurrentQuestion(questionList);
                int selectedIndex = getSelectedIndexFromRadioButtons();
                boolean isCorrect = isCorrectAnswerSelected(selectedIndex, currentQuestion);
                if(isCorrect) {
                    totalScore++;
                    mediaPlayer = MediaPlayer.create(LevelExercisesActivity.this, R.raw.sound_win);
                    mediaPlayer.start();
                }else{
                    mediaPlayer = MediaPlayer.create(LevelExercisesActivity.this, R.raw.sound_loss);
                    mediaPlayer.start();
                }
                Question nextQuestion = incrementToNextQuestionIfExists(questionList);
                if(nextQuestion != null) {
                    showQuestion(nextQuestion);
                } else {
                   Intent intent = new Intent(LevelExercisesActivity.this, LevelFinishedActivity.class);
                   intent.putExtra("score", totalScore +"");
                   startActivity(intent);
                }

            }
        });

    }

    public Question getCurrentQuestion(List<Question> questionList) {
        return questionList.get(count);
    }

    public Question incrementToNextQuestionIfExists(List<Question> questionList) {
        count++;
        if(count < questionList.size()) {
            return getCurrentQuestion(questionList);
        } else {
            return null;
        }
    }

    public int getCorrectQuestionIndex(Question question) {
        for(int i =0 ; i < question.answerList.size(); i++) {
            Answer answer = question.answerList.get(i);
            if(answer.isCorrect) {
                return i;
            }
        }
        return 0;
    }

    public void showQuestion(Question question) {
        final RadioButton radioButton1 = findViewById(R.id.radio_item_one);
        final RadioButton radioButton2 = findViewById(R.id.radio_item_two);
        final RadioButton radioButton3 = findViewById(R.id.radio_item_three);
        final ImageView imageView = findViewById(R.id.image);

        Picasso.get().load(question.imageUrl).into(imageView);
        radioButton1.setText(question.answerList.get(0).text);
        radioButton2.setText(question.answerList.get(1).text);
        radioButton3.setText(question.answerList.get(2).text);
    }

    public boolean isCorrectAnswerSelected(int selectedIndex, Question question) {
        return selectedIndex == getCorrectQuestionIndex(question);
    }

    public int getSelectedIndexFromRadioButtons() {
        final RadioButton radioButton1 = findViewById(R.id.radio_item_one);
        final RadioButton radioButton2 = findViewById(R.id.radio_item_two);
        final RadioButton radioButton3 = findViewById(R.id.radio_item_three);

        if(radioButton1.isChecked()) {
            return 0;
        } else if(radioButton2.isChecked()) {
            return 1;
        } else if(radioButton3.isChecked()) {
            return 2;
        }
        return -1;
    }
}
