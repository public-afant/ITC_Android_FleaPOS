package com.example.yh.fleamarket_pos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MarketInfo extends AppCompatActivity {

    Button btnMarket_Start;
    TextView txtTotal;
    Intent intent;
    String market_id;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_info);
        intent = getIntent();
        market_id = intent.getStringExtra("_id");
        int temp = Integer.parseInt(market_id);

        dbHelper = new DatabaseHelper(this,"dbpos1.db", null,1);

        getMarketName(temp);


        txtTotal = (TextView) findViewById(R.id.market_Total);


        getTotalSal();

        //txtTest.setText(name);

        btnMarket_Start = (Button) findViewById(R.id.btnMarket_Start);

        btnMarket_Start.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    intent = new Intent(MarketInfo.this, MarketStart.class);
                    intent.putExtra("market_name", name);
                    startActivity(intent);
                }
        }
        );

    }


    public void getTotalSal(){

        db = dbHelper.getWritableDatabase();

        String query = "select sale_itemSal, sale_Stock from Sale where sale_Stock != 0";
        int sum = Integer.parseInt(txtTotal.getText().toString());

        Cursor cs = db.rawQuery(query, null);

        if(cs.moveToFirst()){
            do{
                int sal=cs.getColumnIndex("sale_itemSal");
                int stock = cs.getColumnIndex("sale_Stock");
                sum = sum + (sal * stock);

            }while (cs.moveToNext());
        }
        txtTotal.setText(""+sum);
    }

    public void getMarketName(int id){
        db = dbHelper.getWritableDatabase();
        String queryMarketName = "SELECT market_Name FROM Market where _id = " + id;
        String market_name="";

        Cursor cs = db.rawQuery(queryMarketName, null);

        if(cs.moveToFirst()){
            do{
                int test=cs.getColumnIndex("market_Name");
                market_name=cs.getString(test);

            }while (cs.moveToNext());
        }

        name = market_name;

        cs.close();

        db.close();

    }



}
