package com.example.yh.fleamarket_pos;

import android.content.ClipData;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ItemAdd extends AppCompatActivity {
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    String ItemName,ItemSal,ItemCost,ItemStock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
        Intent intent = getIntent();
        final String et_market_Name = intent.getStringExtra("et_market_Name");



        Button btnItemCancel = (Button) findViewById(R.id.btnItemCancel);
        Button btnItemInsert = (Button) findViewById(R.id.btnItemInsert);



        dbHelper = new DatabaseHelper(this,"dbpos1.db", null,1);


        btnItemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnItemInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtItemName = (EditText) findViewById(R.id.edtItemName);
                EditText edtItemSal = (EditText) findViewById(R.id.edtItemSal);
                EditText edtItemCost = (EditText) findViewById(R.id.edtItemCost);
                EditText edtItemStock = (EditText) findViewById(R.id.edtItemStock);
                ItemName = edtItemName.getText().toString();
                ItemSal = edtItemSal.getText().toString();
                ItemCost = edtItemCost.getText().toString();
                ItemStock = edtItemStock.getText().toString();
                String sql = "insert into Item values(null,'" + et_market_Name + "','"+ItemName+"',"+ItemSal+","+ItemCost+","+ItemStock+")";
                String sql1 = "insert into Sale values(null,'" + et_market_Name + "','"+ItemName+"',"+ItemSal+","+ItemCost+",0)";
                db = dbHelper.getWritableDatabase();
                db.execSQL(sql);
                db.execSQL(sql1);


                finish();
            }
        });





    }
}
