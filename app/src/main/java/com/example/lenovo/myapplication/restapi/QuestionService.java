package com.example.lenovo.myapplication.restapi;

import com.example.lenovo.myapplication.DataStore;
import com.example.lenovo.myapplication.models.Question;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface QuestionService {
    @GET("v2/5bdbd4e9330000760081335c")
    Call<List<Question>> listRepos();
}
