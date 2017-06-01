package com.example.yh.fleamarket_pos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import java.util.ArrayList;


/**
 * Created by YH on 15. 11. 18..
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name,factory,version);
    }

    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table Market" + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "market_Name text, "
                + "market_Date text,"
                + "market_TableSal integer);");

        db.execSQL("create table Sale" + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "market_Name text, "
                + "sale_Name text,"
                + "sale_ItemSal integer,"
                + "sale_ItemCost integer,"
                + "sale_Stock integer);");

        db.execSQL("create table Item" + "("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "market_Name text, "
                + "Item_Name text,"
                + "Item_ItemSal integer,"
                + "Item_ItemCost integer,"
                + "Item_Stock integer);");

        db.execSQL("create table Temp" + "("
                + "_id INTEGER PRIMARY KEY,"
                + "item_Count integer);");

    }
    public void onOpen(SQLiteDatabase db) {

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ");
        onCreate(db);
    }




}