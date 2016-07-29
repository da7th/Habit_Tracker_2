package com.example.android.habittracker2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HabitDBHelper dbHelper = new HabitDBHelper(this);
        dbHelper.insertData("1", 1, 1);
        dbHelper.readAllData();
        dbHelper.readData("Habit");
        dbHelper.deleteData("1");
        dbHelper.readAllData();
    }
}
