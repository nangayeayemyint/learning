package com.example.lenovo.myapplication;

import com.example.lenovo.myapplication.deserializer.ListInstructionDeserialization;
import com.example.lenovo.myapplication.deserializer.ListLevelDeserialization;
import com.example.lenovo.myapplication.models.SInstruction;
import com.example.lenovo.myapplication.models.SLevel;
import com.example.lenovo.myapplication.restapi.LearnerService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestContentful {

    public static void main(String args[]) throws IOException {

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
        Response<List<SLevel>> response = call.execute();
        List<SLevel> SLevelList = response.body();
        String levelId = SLevelList.get(0).id;
        System.out.println(levelId);
      /*  String result1=null+ "\n";
        for(SLevel level : SLevelList){
            result1+=level.name + " ";
            result1+=level.description + "\n";
        }*/
       // System.out.println(result1);

        //levelId = levelId.replace("\"","");

        Call<List<SInstruction>> call1 = service.getInstructionList("xtnomdz5cbr8", "master","5skmWFBzDqWaeqm28EEqw8" , "8963b88da92f0fec2136bce1ceb438f1dfa13f120cd5c139d68687a1ae0a4af8");
        Call<List<SInstruction>> call2 = service.getInstructionList("xtnomdz5cbr8", "master",levelId , "8963b88da92f0fec2136bce1ceb438f1dfa13f120cd5c139d68687a1ae0a4af8");


        System.out.println(call1.request().url());
        System.out.println(call2.request().url());
        System.out.println(call1.request().url().toString().equals(call2.request().url().toString()));

        Response<List<SInstruction>> response1 = call1.execute();
        List<SInstruction> SInstructionList = response1.body();

        String result=null + "\n";
        for(SInstruction SInstruction : SInstructionList){
            result+= SInstruction.description + " ";
            result+= SInstruction.imageUrl + "\n";
        }
        System.out.println(result);
    }
}
