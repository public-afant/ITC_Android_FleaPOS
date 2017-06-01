package com.example.yh.fleamarket_pos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by YH on 15. 12. 2..
 */
public class Grid_ItemDBAdapter extends CursorAdapter {


    public class ViewHolder{
        TextView grid_ItemCount;
        Button CountPlus;
        Button CountMinus;
    }

    //TextView grid_ItemStock;
    static MarketStart marketStart;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    public Grid_ItemDBAdapter(Context context, Cursor c) {
        super(context, c);

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.v("tttt","bindView = bindView");

        marketStart = new MarketStart();

        final ViewHolder viewHolder = (ViewHolder) view.getTag();


        final TextView grid_ItemName= (TextView)view.findViewById(R.id.Grid_ItemName);
        final TextView grid_ItemStock= (TextView)view.findViewById(R.id.Grid_ItemStock);
        final TextView grid_ItemSal= (TextView)view.findViewById(R.id.Grid_ItemSal);
        //final TextView grid_ItemCount = (TextView)view.findViewById(R.id.Grid_ItemCount);
        //final Button CountPlus = (Button)view.findViewById(R.id.count_Plus);
        //final Button CountMinus = (Button)view.findViewById(R.id.count_Minus);

        final int id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id")));

        grid_ItemName.setText(""+cursor.getString(cursor.getColumnIndex("Item_Name")));
        grid_ItemStock.setText("" + cursor.getString(cursor.getColumnIndex("Item_Stock")));
        grid_ItemSal.setText("" + cursor.getString(cursor.getColumnIndex("Item_ItemSal")));

        final int test321 = Integer.parseInt(cursor.getString(cursor.getColumnIndex("Item_ItemSal")));


        viewHolder.CountPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(viewHolder.grid_ItemCount.getText().toString());
                count++;
                viewHolder.grid_ItemCount.setText("" + count);

                Log.v("tttt","Plus_id = " +  id + "  Plus_Count = " + count);
                //--------------------------------
                int old_Sal = Integer.parseInt(MarketStart.item_TotalSal.getText().toString());
                int total = old_Sal + test321;
                MarketStart.item_TotalSal.setText(Integer.toString(total));
                //--------------------------------

                int market_id = id;
                int item_count = count;
                updateDB(id,item_count);

            }
        });
        viewHolder.CountMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(viewHolder.grid_ItemCount.getText().toString());
                if (count > 0) {
                    count--;
                    viewHolder.grid_ItemCount.setText("" + count);
                    //--------------------------------
                    int old_Sal = Integer.parseInt(MarketStart.item_TotalSal.getText().toString());

                    int total = old_Sal - test321;
                    MarketStart.item_TotalSal.setText(Integer.toString(total));
                    //--------------------------------
                    int market_id = id;
                    int item_count = count;
                    updateDB(id, item_count);
                }
            }
        });

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {




        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.grid_itemlist, parent, false);

        ViewHolder viewHolder = new ViewHolder();
        viewHolder.grid_ItemCount = (TextView)v.findViewById(R.id.Grid_ItemCount);
        viewHolder.CountPlus = (Button)v.findViewById(R.id.count_Plus);
        viewHolder.CountMinus = (Button)v.findViewById(R.id.count_Minus);


        v.setTag(viewHolder);


        return v;
    }



    public void updateDB(int id, int item_count){
        dbHelper = new DatabaseHelper(MarketStart.t,"dbpos1.db",null,1);
        db = dbHelper.getWritableDatabase();

        String select_Sql = "select _id from Temp where _id = "+id+";";
        Cursor c = db.rawQuery(select_Sql,null);




        if(c.getCount() == 0){
            String sql = "insert into Temp values("+id+","+item_count+");";
            db.execSQL(sql);
        }else if(c.getCount() == 1){
            Log.v("tttt","id = "+id+"   item_coumt = "+item_count);
            String sql = "update Temp set item_Count = "+item_count+" where _id = "+id+";";
            db.execSQL(sql);
        }
        if(item_count == 0){
            String delete_Sql = "delete from Temp where _id = "+id+";";
            db.execSQL(delete_Sql);
        }


    }







}
