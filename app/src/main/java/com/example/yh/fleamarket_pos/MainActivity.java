package com.example.yh.fleamarket_pos;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {


    DatabaseHelper dbHelper;
    ListView lvMarketList;
    SQLiteDatabase db;
    String sql;
    Intent intent;
    DBAdapter dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        Button btnMarketAdd = (Button) findViewById(R.id.btnMarketAdd);
        lvMarketList = (ListView) findViewById(R.id.lvMarketList);


        dbHelper = new DatabaseHelper(this,"dbpos1.db",null,1);
        lvMarketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(MainActivity.this, MarketInfo.class);
                intent.putExtra("_id", Long.toString(id));
                startActivity(intent);
            }
        });

        lvMarketList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, final long id) {

                AlertDialog diaBox = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("부스 삭제")
                        .setMessage("선택한 부스를 삭제하시겠습니?")
                        .setPositiveButton("예", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                deleteDB(Integer.parseInt(Long.toString(id)));
                                selectDB();
                            }
                        })
                        .setNegativeButton("아니오", null)
                        .create();
                diaBox.show();
                return false;
            }
        });



        btnMarketAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout linear = (LinearLayout) View.inflate(MainActivity.this, R.layout.custom_box, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("부스명을 입력하세요.");
                builder.setView(linear);
                builder.setNegativeButton("추가", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText et_market_Name = (EditText) linear.findViewById(R.id.et_market_Name);
                        intent = new Intent(MainActivity.this, MarketAdd.class);
                        intent.putExtra("et_market_Name", et_market_Name.getText().toString());
                        startActivity(intent);
                    }
                });
                builder.setPositiveButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        selectDB();
    }



    private void selectDB(){
        db = dbHelper.getWritableDatabase();
        sql = "SELECT * FROM Market;";

        Cursor c = db.rawQuery(sql, null);
        if(c.getCount() >= 0){
            //startManagingCursor(c);
            dbAdapter = new DBAdapter(this, c);
            lvMarketList.setAdapter(dbAdapter);
        }
        //c.close();


    }

    public void deleteDB(int id){

        getMarketName(id);
        db=dbHelper.getWritableDatabase();
        String queryMarket="DELETE FROM Market WHERE _id= " + id;
        db.execSQL(queryMarket);
        db.close();

    }

    public void getMarketName(int id){
        Log.v("알림","id = " + id);
        db = dbHelper.getWritableDatabase();
        String queryMarketName = "SELECT market_Name FROM Market where _id = " + id;
        String name="";
        Cursor cs = db.rawQuery(queryMarketName, null);

        if(cs.moveToFirst()){
            do{
            int test=cs.getColumnIndex("market_Name");
            name=cs.getString(test);

            }while (cs.moveToNext());
        }
        cs.close();

        String queryItem = "DELETE FROM Item WHERE market_Name= '" + name + "';";
        String querySale = "DELETE FROM Sale WHERE market_Name= '" + name + "';";
        db.execSQL(queryItem);
        db.execSQL(querySale);

        db.close();
    }


}
