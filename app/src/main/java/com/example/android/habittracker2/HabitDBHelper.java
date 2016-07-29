package com.example.android.habittracker2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by da7th on 7/29/2016.
 */
public class HabitDBHelper extends SQLiteOpenHelper{

    //tag used to find errors in this particular file
    private String DB_LOG_TAG = "DATABASE_HELPER";

    public HabitDBHelper(Context context) {
        super(context, SQLHabitContract.TABLE_NAME, null, 1);
    }

    //oncreate will create the table with the given columns and value datatypes to be in each column
    //the _ID will be the primary key parameter and will auto increment
    @Override
    public void onCreate(SQLiteDatabase habitDb) {

        habitDb.execSQL("create table " + SQLHabitContract.TABLE_NAME + " (_ID INTEGER PRIMARY KEY "
                + "AUTOINCREMENT, TITLE TEXT, COMPLETED INTEGER, EXPECTED INTEGER)");
        Log.v(DB_LOG_TAG, "the onCreate method has been called the table has been created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase habitDb, int i, int i1) {
        //upon the need to upgrade, the entire table is deleted
        deleteAllData(habitDb);
        //upon the need to upgrade, the entire table is dropped if it still exists
        habitDb.execSQL("DROP TABLE IF EXISTS " + SQLHabitContract.TABLE_NAME);
        //and here it is recreated by calling the onCreate method again
        onCreate(habitDb);
        Log.v(DB_LOG_TAG, "the onUpgrade method has been called and the table has been dropped " +
                "and recreated");
    }

    public boolean insertData(String title, Integer completed, Integer expected){

        //create a writable instance of the database
        SQLiteDatabase db = this.getWritableDatabase();
        Log.v(DB_LOG_TAG, "database is set to get Writable");

        //we use a variable of content values to take in the values to be set
        ContentValues contentValues = new ContentValues();
        contentValues.put(SQLHabitContract.COLUMN_NAME_TITLE,title);
        contentValues.put(SQLHabitContract.COLUMN_NAME_COMPLETED,completed);
        contentValues.put(SQLHabitContract.COLUMN_NAME_EXPECTED, expected);

        //the insert function takes the table name and the content values set and sets it to the
        //table, in order to check that the insert funcation was successful it must not return -1
        //by placing the result in a long variable we can check for the -1 using an if statement
        long result = db.insert(SQLHabitContract.TABLE_NAME,null,contentValues);
        return result != -1;
    }

    public Cursor readAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //a cursor object allows for random read and write, this code allows us to store all of the
        // table data into the cursor res and return it to where the method was called to be shown
        Cursor res = db.rawQuery("select * from " + SQLHabitContract.TABLE_NAME, null);
        return res;
    }

    public Boolean updateData(String id, String title, Integer completed, Integer expected){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(SQLHabitContract._ID,id);
        contentValues.put(SQLHabitContract.COLUMN_NAME_TITLE,title);
        contentValues.put(SQLHabitContract.COLUMN_NAME_COMPLETED,completed);
        contentValues.put(SQLHabitContract.COLUMN_NAME_EXPECTED, expected);

        //the following method call will replace the row based on the unique identifier which in
        // this case is the id
        db.update(SQLHabitContract.TABLE_NAME, contentValues, "_ID = ?",new String[]{id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        //the following deleted a row of data according to the given id
        return db.delete(SQLHabitContract.TABLE_NAME, "_ID = ?", new String[]{id});
    }

    public Integer deleteAllData(SQLiteDatabase db){

        //the following will delete all the data in the table
        return db.delete(SQLHabitContract.TABLE_NAME, "1", null);    // will return the count of deleted rows
    }

    public Cursor readData(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        //a cursor object allows for random read and write, this code allows us to store all of the
        // table data into the cursor res and return it to where the method was called to be shown
        Cursor res = db.query(SQLHabitContract.TABLE_NAME, new String[]{title}, "_ID = ?", new String[]{"TITLE = ?"}, null, null, null);
        return res;
    }

    //the contract class by which to setup the table
    //basecolumn will setup two extra columns for _ID and _count
    public static abstract class SQLHabitContract implements BaseColumns {

        //table name and column names
        public static final String TABLE_NAME = "habits";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_COMPLETED = "completed";
        public static final String COLUMN_NAME_EXPECTED = "expected";

    }
}
