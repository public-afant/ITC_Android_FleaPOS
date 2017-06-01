package com.example.yh.fleamarket_pos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by YH on 15. 11. 18..
 */
public class DBAdapter extends CursorAdapter {



    public DBAdapter(Context context, Cursor c) {
        super(context, c);

    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        final TextView market_Name = (TextView)view.findViewById(R.id.market_Name);
        final TextView market_TableSal = (TextView)view.findViewById(R.id.market_TableSal);

        market_Name.setText(""+cursor.getString(cursor.getColumnIndex("market_Name")));
        market_TableSal.setText("" + cursor.getString(cursor.getColumnIndex("market_TableSal")));

    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.listitem, parent, false);
        return v;
    }











}
