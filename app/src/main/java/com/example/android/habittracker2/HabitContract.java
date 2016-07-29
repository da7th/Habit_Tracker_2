package com.example.android.habittracker2;

import android.provider.BaseColumns;

/**
 * Created by da7th on 7/29/2016.
 */
public class HabitContract {

    public static abstract class SQLHabitContract implements BaseColumns {

        //table name and column names
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_EXPECTED = "expected";

    }
}
