package com.example.NotePad;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Logger;


/**
 * Created by jeahyukkim on 2014. 1. 6..
 */
public class DbAccess {
    SQLiteDatabase db;

    protected void openDataBase(){
        try{
            db = SQLiteDatabase.openDatabase("data/data/com.example.NotePad/NoteDB", null, SQLiteDatabase.CREATE_IF_NECESSARY );
            //Toast.makeText(getApplicationContext(), "DB OPEN", 1).show();

        }catch(Exception e){
            
        }

    }


    protected void makeDataTable(){

        db.beginTransaction();
        try{
            db.execSQL("CREATE TABLE IF NOT EXISTS NOTE_MAST(" + "SEQ_NO INTEGER PRIMARY KEY AUTOINCREMENT, time integer," + "location text);");
            db.setTransactionSuccessful();

            //Toast.makeText(getApplicationContext(), "Table was created!",1).show();

        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
        finally{
            db.endTransaction();
        }
    }

    protected void insertSomeDbData(){


        db.beginTransaction();

        try{
            db.execSQL("insert into NOTE_MAST(time,location)" + "values(1230,'test1');");
            db.execSQL("insert into NOTE_MAST(time,location)" + "values(0200,'test2');");
            db.execSQL("insert into NOTE_MAST(time,location)" + "values(0500,'test3');");

            db.setTransactionSuccessful();
            //Toast.makeText(getApplicationContext(), "3 record were inserted!!",1).show();


        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
        finally{
            db.endTransaction();

        }
    }

    protected void dropTable(){
        try{
            db.execSQL("drop table NOTE_MAST");
            //Toast.makeText(getApplicationContext(), "Table dropped",1).show();
        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }

    }

    protected ArrayList<CustomListData> userCursor1(){
        ArrayList<CustomListData> list = new ArrayList<CustomListData>();
        CustomListData cData;

        try{
            String[] columns = {"SEQ_NO", "time", "location"};
            Cursor c = db.query("NOTE_MAST", columns, null, null, null, null, "SEQ_NO");


            int theTotal = c.getCount();
            int seqNo = c.getColumnIndex("SEQ_NO");
            int timecol = c.getColumnIndex("time");
            int locationcol = c.getColumnIndex("location");


            while(c.moveToNext()){

                System.out.println("SEQNO: " + Integer.toString(c.getInt(seqNo)));
                System.out.println("LOCATION: " + c.getString(locationcol));

                cData = new CustomListData(Integer.toString(c.getInt(seqNo)),c.getString(locationcol) );
                list.add(cData);

                //columns[0] = Integer.toString(c.getInt(seqNo));
                //columns[1] = Integer.toString(c.getInt(timecol));
                //columns[2] = c.getString(locationcol);

                //txtView.append(columns[0] + "|" + columns[1] + "|" + columns[2] + "\n");
            }
            System.out.println("LIST SIZE: " + list.size());

        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
        return list;

    }

}
