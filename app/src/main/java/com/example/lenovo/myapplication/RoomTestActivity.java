package com.example.lenovo.myapplication;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.lenovo.myapplication.database.AppDatabase;
import com.example.lenovo.myapplication.database.User;
import com.example.lenovo.myapplication.database.UserDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RoomTestActivity extends AppCompatActivity {
    List<User> userList = new ArrayList<>();
    User user =new User();
    UserDao userDao;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "users.db").build();

        Button addBtn = this.findViewById(R.id.add);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InsertTesk insertTesk = new InsertTesk(db){
                    @Override
                    protected void onPostExecute(Boolean aboolean) {
                        super.onPostExecute(aboolean);
                        Toast.makeText(RoomTestActivity.this, "Success :" + aboolean, Toast.LENGTH_SHORT).show();
                    }
                };
                insertTesk.execute();

            }
        });

        Button selectBtn = this.findViewById(R.id.select);
        selectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrintTesk printTesk = new PrintTesk(db){
                    @Override
                    protected void onPostExecute(List<User> userList) {
                        super.onPostExecute(userList);
                        Toast.makeText(RoomTestActivity.this, "Success :" + userList.size(), Toast.LENGTH_SHORT).show();
                    }
                };
                printTesk.execute();
            }
        });

    }

    class InsertTesk extends AsyncTask<Void, Void, Boolean>{
        private AppDatabase db;

        public InsertTesk(AppDatabase db){
            this.db = db;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            User user = new User();
            user.firstName = "Hello";
            user.lastName = "World";
            db.userDao().insert(user);

            return true;
        }
    }

    class PrintTesk extends AsyncTask<Void, Void, List<User>>{
        private AppDatabase db;

        public PrintTesk(AppDatabase db){
            this.db = db;
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            return db.userDao().getAll();
        }
    }
}
