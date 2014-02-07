package com.example.NotePad;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jeahyukkim on 2014. 1. 11..
 */
public class CustomAdaptor extends ArrayAdapter<CustomListData> {
    private Context context;
    private int layoutResourceId;
    private ArrayList<CustomListData> listData;

    public CustomAdaptor(Context context, int layoutResourceId, ArrayList<CustomListData> listData){
        super(context, layoutResourceId, listData);
        this.context = context;
        this.layoutResourceId = layoutResourceId;
        this.listData = listData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //row는 리스트의 각각의 칸
        View row = convertView;
        if(row == null){
            LayoutInflater inflater  = ((Activity) context).getLayoutInflater();
            //UI레이아웃을 불러 객체화
            row = inflater.inflate(layoutResourceId, parent, false);
        }

        TextView main_str = (TextView) row.findViewById(R.id.txtKey);
        TextView sub_str = (TextView) row.findViewById(R.id.txtContext);

        CustomListData list = listData.get(position);

        if(main_str != null){
          main_str.setText(list.getSeqNo());
        }

        if(sub_str != null){
            sub_str.setText(list.getContext());
        }


        return row;


    }




}
