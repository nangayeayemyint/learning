package com.example.lenovo.myapplication;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lenovo.myapplication.database.LevelDatabase;
import com.example.lenovo.myapplication.database.models.LevelRecord;
import com.example.lenovo.myapplication.deserializer.ListLevelDeserialization;
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


public class LevelListActivity extends AppCompatActivity {
    LevelDatabase db;
    SharedPreferences preferences;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exercises_page) {
            Toast.makeText(this,""+id , Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LevelActivity.class);
            this.startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final String SPACE_ID = "xtnomdz5cbr8";
        final String ENVIRONMENT_ID = "master";
        final String CONTENT_TYPE = "level";
        final String ACCESS_TOKEN = "8963b88da92f0fec2136bce1ceb438f1dfa13f120cd5c139d68687a1ae0a4af8";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list);
        db = Room.databaseBuilder(getApplicationContext(),LevelDatabase.class, "levelRecord.db").build();
        Toast.makeText(LevelListActivity.this, "Success",Toast.LENGTH_LONG );
        Type levelListType = new TypeToken<List<SLevel>>(){}.getType();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(levelListType, new ListLevelDeserialization());

        Gson gson = gsonBuilder.create();
        GsonConverterFactory converterFactory = GsonConverterFactory.create(gson);

        if(preferences.getBoolean(PrefrenceKey.isDownloaded,false)==false){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://cdn.contentful.com/")
                    .addConverterFactory(converterFactory)
                    .build();

            LearnerService service = retrofit.create(LearnerService.class);
            Call<List<SLevel>> call = service.getLevelList(SPACE_ID, ENVIRONMENT_ID, CONTENT_TYPE, ACCESS_TOKEN);

            call.enqueue(new Callback<List<SLevel>>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(Call<List<SLevel>> call, Response<List<SLevel>> response) {
                    if (response.isSuccessful()) {
                        List<SLevel> levelList = response.body();
                        updateLevel(levelList);
                    } else {
                        Toast.makeText(LevelListActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<SLevel>> call, Throwable t) {
                    Toast.makeText(LevelListActivity.this, "error:" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }else{
            showTesk();
        }
    }

    public void updateLevel(final List<SLevel> levelList){
        InsertTesk insertTesk = new InsertTesk(db, levelList){
            @Override
            protected void onPostExecute(List<LevelRecord> levelRecordList) {
                super.onPostExecute(levelRecordList);
                showLevel(levelRecordList);
                savePrefrence();
            }
        };
        insertTesk.execute();
    }

    class ShowTesk extends AsyncTask<Void, Void, List<LevelRecord>> {
        private LevelDatabase db;

        public ShowTesk(LevelDatabase db){
            this.db = db;

        }
        @Override
        protected List<LevelRecord> doInBackground(Void... voids) {
            return db.levelDao().getAll();
        }
    }

    public void showTesk(){

        ShowTesk showTesk = new ShowTesk(db){
            @Override
            protected void onPostExecute(List<LevelRecord> levelRecordList) {
                super.onPostExecute(levelRecordList);
               showLevel(levelRecordList);

            }
        };
        showTesk.execute();
    }

    class InsertTesk extends AsyncTask<Void, Void, List<LevelRecord>> {
        private LevelDatabase db;
        private List<SLevel> sLevelList;

        public InsertTesk(LevelDatabase db, List<SLevel> sLevelList){
            this.db = db;
            this.sLevelList = sLevelList;
        }

        @Override
        protected List<LevelRecord> doInBackground(Void... voids) {

                for (int i = 0; i < sLevelList.size(); i++) {
                    LevelRecord levelRecord = new LevelRecord();
                    levelRecord.levelId = sLevelList.get(i).id;
                    levelRecord.name = sLevelList.get(i).name;
                    levelRecord.description = sLevelList.get(i).description;
                    levelRecord.order = sLevelList.get(i).order;
                    db.levelDao().insert(levelRecord);
                }
            return db.levelDao().getAll();
        }
    }

    public void showLevel(final List<LevelRecord> levelRecordList){
        final ListView listView = (ListView) findViewById(R.id.listId);
        ArrayAdapter<LevelRecord> adapter = new LevelArrayAdapter(LevelListActivity.this, R.layout.item_level, levelRecordList);
        listView.setAdapter(adapter);
        listView.setDivider(null);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String levelId=levelRecordList.get(position).levelId;
                Toast.makeText(LevelListActivity.this, " "+position, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(LevelListActivity.this, LevelActivity.class);
                intent.putExtra(LevelActivity.LEVEL_POSITION_KEY, position);
                intent.putExtra(LevelActivity.LEVEL_ID_KEY,levelId);
                startActivity(intent);
            }
        });
    }

    public void savePrefrence(){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PrefrenceKey.isDownloaded, true);
        editor.commit();// Very important
    }
}
