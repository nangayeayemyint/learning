package com.example.lenovo.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.lenovo.myapplication.models.Question;
import com.example.lenovo.myapplication.restapi.QuestionService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Test extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise1);

        ImageView imageView = this.findViewById(R.id.image);
        //Picasso.get().load("https://i.redd.it/sznxx5pufym01.jpg").into(imageView);

    }

    public static void main(String args[]) throws IOException {

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.mocky.io/")
                .addConverterFactory(converterFactory)
                .build();

        QuestionService service = retrofit.create(QuestionService.class);

        Call<List<Question>> call = service.listRepos();
        Response<List<Question>> response = call.execute();
        List<Question> questionList = response.body();
        System.out.println(questionList.size());

        String result = null;

            result += questionList.get(0).text;
            //result += question.imageUrl;
            result += questionList.get(0).answerList.get(0).text;

        System.out.println(result);
    }
}
