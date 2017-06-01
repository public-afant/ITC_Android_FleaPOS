package com.example.yh.fleamarket_pos;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MarketAdd extends AppCompatActivity {


    String test;
    ListView lvItemList;
    String sql;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    private TextView txtDate;
    private Button btnDateAdd;
    private int mYear;
    private int mMonth;
    private int mDay;
    String et_market_Name;
    static final int DATE_DIALOG_ID = 0;
    Toast clsToast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_add);
        Intent intent = getIntent();
        et_market_Name = intent.getStringExtra("et_market_Name");   //부스명 인텐트


        final Button btnItemAdd = (Button) findViewById(R.id.btnItemAdd);
        Button btnMarketCancel = (Button) findViewById(R.id.btnMarketCancel);
        Button btnMarketInsert = (Button) findViewById(R.id.btnMarketInsert);
        lvItemList = (ListView) findViewById(R.id.lvItemList);

        clsToast = Toast.makeText( this, "부스가 생성되었습니다.", Toast.LENGTH_LONG );

        txtDate = (TextView)findViewById(R.id.txtDate);
        btnDateAdd = (Button)findViewById(R.id.btnDateAdd);

        btnDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance();

        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();


        dbHelper = new DatabaseHelper(this,"dbpos1.db", null,1);

        //selectDB1();




        btnItemAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MarketAdd.this, ItemAdd.class);
                intent.putExtra("et_market_Name", et_market_Name);
                startActivity(intent);
            }
        });

        btnMarketCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnMarketInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtMarketSal = (EditText) findViewById(R.id.edtMarketSal);

                String edtSal = edtMarketSal.getText().toString();
                //int intEdtSal = Integer.parseInt(test);
                String sql = "insert into Market values(null,'" + et_market_Name + "','"+ test +"',"+ edtSal +")";

                db = dbHelper.getWritableDatabase();
                db.execSQL(sql);

                db.close();

                clsToast.show( );

                finish();
            }
        });







    }

    @Override
    public void onResume(){
        super.onResume();
        selectDB1();
    }



    private void updateDisplay()
    {
        txtDate.setText(new StringBuilder()
                .append(mYear).append("-")
                .append(mMonth + 1).append("-")
                .append(mDay).append(" "));
        test = txtDate.getText().toString();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    @Override
    protected Dialog onCreateDialog(int id)
    {
        switch(id)
        {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        }
        return null;
    }


    public void selectDB1(){
        db = dbHelper.getWritableDatabase();
        sql = "SELECT * FROM Item where market_Name = '"+et_market_Name+"';";

        Cursor cursor = db.rawQuery(sql, null);
        if(cursor.getCount() > 0){
            //startManagingCursor(cursor);
            ItemDBAdapter itemDBAdapter = new ItemDBAdapter(this,cursor);
            lvItemList.setAdapter(itemDBAdapter);
        }
    }
}
