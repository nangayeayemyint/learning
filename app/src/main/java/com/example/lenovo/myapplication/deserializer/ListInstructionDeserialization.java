package com.example.lenovo.myapplication.deserializer;

import com.example.lenovo.myapplication.models.SInstruction;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListInstructionDeserialization implements JsonDeserializer<List<SInstruction>> {
    @Override
    public List<SInstruction> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<SInstruction> SInstructionList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        JsonArray jsonArray = json.getAsJsonObject().get("items").getAsJsonArray();
        for(int i=0; i< jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            SInstruction SInstruction = gson.fromJson(jsonObject.get("fields").toString(), SInstruction.class);
            SInstructionList.add(SInstruction);
        }
        return SInstructionList;

    }
}
