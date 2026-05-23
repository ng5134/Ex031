package com.example.ex0031;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Expenses.db";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the expenses table
        String createTableQuery = "CREATE TABLE " + Expense.TABLE_NAME;
        createTableQuery += " (" + Expense.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
        createTableQuery += " " + Expense.DESC + " TEXT,";
        createTableQuery += " " + Expense.AMOUNT + " REAL,";
        createTableQuery += " " + Expense.CATEGORY + " TEXT,";
        createTableQuery += " " + Expense.DATE + " TEXT";
        createTableQuery += ");";

        Log.d("DB_Helper", "Executing query: " + createTableQuery); // Log the query for debugging
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Expense.TABLE_NAME);
        onCreate(db);
    }
}