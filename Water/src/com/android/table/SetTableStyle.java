package com.android.table;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;

public class SetTableStyle extends SimpleCursorAdapter {  
    
    public SetTableStyle(Context context, int layout, Cursor c,  
            String[] columnNames, int[] to) {  
        super(context, layout, c, columnNames,to,0);  
        // TODO Auto-generated constructor stub  
  
    }  
  
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        // TODO Auto-generated method stub  
        // listview每次得到一个item，都要view去绘制，通过getView方法得到view  
        // position为item的序号  
        View view = null;  
        if (convertView != null) {  
            view = convertView;  
            // 使用缓存的view,节约内存  
            // 当listview的item过多时，拖动会遮住一部分item，被遮住的item的view就是convertView保存着。  
            // 当滚动条回到之前被遮住的item时，直接使用convertView，而不必再去new view()  
  
        } else {  
            view = super.getView(position, convertView, parent);  
  
        }  
  
        int[] colors = { Color.rgb(6,227,46), Color.rgb(255, 213, 7),Color.rgb(6, 160, 227)  };//RGB颜色  
  
        view.setBackgroundColor(colors[position % 3]);// 每隔item之间颜色不同  
  
        return super.getView(position, view, parent);  
    }  
  
}  