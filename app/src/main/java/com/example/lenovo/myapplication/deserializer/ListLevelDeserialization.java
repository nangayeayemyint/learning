package com.example.lenovo.myapplication.deserializer;

import com.example.lenovo.myapplication.models.SLevel;
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

public class ListLevelDeserialization implements JsonDeserializer<List<SLevel>> {
    List<SLevel> SLevelList = new ArrayList<>();
    GsonBuilder gsonBuilder = new GsonBuilder();
    Gson gson = gsonBuilder.create();
    @Override
    public List<SLevel> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonObject().get("items").getAsJsonArray();
        for(int i=0; i< jsonArray.size(); i++){
            JsonObject jsonObject = jsonArray.get(i).getAsJsonObject();
            SLevel SLevel = gson.fromJson(jsonObject.get("fields").toString(), SLevel.class);

            JsonObject jsonId = (JsonObject) jsonObject.get("sys");
            SLevel.id= jsonId.get("id").getAsString();
            SLevelList.add(SLevel);
        }
        return SLevelList;
    }
}
