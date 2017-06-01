package com.example.yh.fleamarket_pos;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


public class MarketStart extends AppCompatActivity {

    SQLiteDatabase db;
    DatabaseHelper dbHelper;
    String market_name;
    Intent intent;
    ListView grid_ItemList;
    String sql;
    Grid_ItemDBAdapter grid_itemDBAdapter;
    Button market_Start_Back;
    Button market_Start_Reset;
    Button market_Start_Buy;
    static TextView item_TotalSal;
    static TextView textView;
    static Context t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_start);
        intent = getIntent();
        market_name = intent.getStringExtra("market_name");
        dbHelper = new DatabaseHelper(this,"dbpos1.db", null,1);


t = this;


        item_TotalSal = (TextView) findViewById(R.id.Item_TotalSal);
        grid_ItemList = (ListView) findViewById(R.id.ItemList);
        market_Start_Back = (Button) findViewById(R.id.Market_Start_Back);
        market_Start_Reset = (Button) findViewById(R.id.Market_Start_Reset);
        market_Start_Buy = (Button) findViewById(R.id.Market_Start_Buy);

        market_Start_Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item_TotalSal.setText("0");

                delete_Count();
                selectDB1();
            }
        });
        market_Start_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_Count();
                finish();
            }
        });

        market_Start_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDBUpdate();
            }
        });



    }

    public void onResume(){
        super.onResume();
        delete_Count();
        selectDB1();
    }

    public void selectDB1(){
        db = dbHelper.getWritableDatabase();
        sql = "SELECT * FROM Item where market_Name = '"+market_name+"';";
        Cursor c = db.rawQuery(sql, null);
        if(c.getCount() >= 0){
            //startManagingCursor(cursor);
            grid_itemDBAdapter = new Grid_ItemDBAdapter(this,c);
            grid_ItemList.setAdapter(grid_itemDBAdapter);
        }
    }
    public void delete_Count(){
        db = dbHelper.getWritableDatabase();
        db.execSQL("delete from Temp");
    }

    public void setDBUpdate(){
        db = dbHelper.getWritableDatabase();
        String query = "select * from Temp";
        Cursor cs = db.rawQuery(query, null);


        if(cs.moveToFirst()){
            do{
                int id=cs.getColumnIndex("_id");
                int count=cs.getColumnIndex("item_Count");

                String update_sale_query = "update Sale set sale_Stock = sale_Stock + "+ count +" where _id = "+ id;
                String update_item_query = "update Item set item_Stock = sale_Stock - "+ count +" where _id = "+ id;
                db.execSQL(update_sale_query);
                db.execSQL(update_item_query);

            }while (cs.moveToNext());
        }

        cs.close();
        db.close();
    }

}
