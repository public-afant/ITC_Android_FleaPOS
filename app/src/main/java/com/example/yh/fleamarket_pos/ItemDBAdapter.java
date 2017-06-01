package com.example.yh.fleamarket_pos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by YH on 15. 11. 18..
 */
public class ItemDBAdapter extends CursorAdapter {



        public ItemDBAdapter(Context context, Cursor c) {
            super(context, c);
        }
        @Override
        public void bindView(View view, Context context, Cursor cursor) {


            final TextView item_name = (TextView)view.findViewById(R.id.item_name);
            final TextView item_stock = (TextView)view.findViewById(R.id.item_stock);

            item_name.setText(""+cursor.getString(cursor.getColumnIndex("Item_Name")));
            item_stock.setText(""+cursor.getString(cursor.getColumnIndex("Item_Stock")));

        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v = inflater.inflate(R.layout.item_listitem, parent, false);
            return v;
        }
}
