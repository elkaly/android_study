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
            System.out.println("**********************  openDataBase Error **************************");
        }
    }


    protected void makeDataTable(){
        db.beginTransaction();
        try{
            String query = "";
            query += " CREATE TABLE IF NOT EXISTS TO_DO_MAST( ";
            query += " SEQ_NO INTEGER PRIMARY KEY AUTOINCREMENT, ";
            query += " TITLE TEXT, ";
            query += " DUE_DATE TEXT, ";
            query += " DUE_TIME TEXT, ";
            query += " CONTEXT TEXT, ";
            query += " CONFIRM TEXT );";
            //db.execSQL("CREATE TABLE IF NOT EXISTS TO_DO_MAST( SEQ_NO INTEGER PRIMARY KEY AUTOINCREMENT, time integer, location text);"   );
            db.execSQL(query);
            db.setTransactionSuccessful();

            //Toast.makeText(getApplicationContext(), "Table was created!",1).show();

        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();

            System.out.println("**********************  makeDataTable Error **************************");

        }
        finally{
            db.endTransaction();
        }
    }

    protected void insertSomeDbData(){


        db.beginTransaction();

        try{
            db.execSQL("insert into TO_DO_MAST(TITLE,DUE_DATE, DUE_TIME, CONTEXT, CONFIRM)" + "values('title1','20140210', '1730', 'CONTEXT1', 'N');");
            db.execSQL("insert into TO_DO_MAST(TITLE,DUE_DATE, DUE_TIME, CONTEXT, CONFIRM)" + "values('title2','20140211', '1830', 'CONTEXT2', 'N');");
            db.execSQL("insert into TO_DO_MAST(TITLE,DUE_DATE, DUE_TIME, CONTEXT, CONFIRM)" + "values('title3','20140212', '1930', 'CONTEXT3', 'N');");

            db.setTransactionSuccessful();
            //Toast.makeText(getApplicationContext(), "3 record were inserted!!",1).show();


        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
        finally{
            db.endTransaction();

        }
    }

    protected void insertData(String key, String title, String date, String time, String context, String confirm){
        String sql = "";
        db.beginTransaction();

        if(key.isEmpty() || "".equals(key)  ){
            //Insert
            sql = "INSERT INTO TO_DO_MAST(TITLE, DUE_DATE, DUE_TIME, CONTEXT, CONFIRM) ";
            sql += "VALUES('" + title + "', '"+date +"', '" + time + "', '" + context + "', '"+ confirm +"' );";
        }else{
            //Update
            sql = "UPDATE TO_DO_MAST SET TITLE = '" + title +"' , DUE_DATE = '" +date + "' , DUE_TIME = '" + time + "' ,  CONTEXT = '" + context + "', CONFIRM = '" + confirm + "' ";
            sql += "WHERE SEQ_NO = " + key + ";";
        }

        System.out.println("SQL : =============== " + sql);

        try{
            db.execSQL(sql);
            db.setTransactionSuccessful();

        }catch(Exception e){

        }finally{
            db.endTransaction();

        }


    }

    protected void dropTable(){
        try{
            db.execSQL("drop table TO_DO_MAST");
            //Toast.makeText(getApplicationContext(), "Table dropped",1).show();
        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }

    }

    protected ArrayList<CustomListData> userCursor1(){
        ArrayList<CustomListData> list = new ArrayList<CustomListData>();
        CustomListData cData;



        try{
            String[] columns = {"SEQ_NO", "TITLE", "DUE_DATE", "DUE_TIME", "CONTEXT", "CONFIRM"};
            Cursor c = db.query("TO_DO_MAST", columns, null, null, null, null, "SEQ_NO");


            int theTotal = c.getCount();
            int seqNoCol = c.getColumnIndex("SEQ_NO");
            int titleCol = c.getColumnIndex("TITLE");
            int dueDateCol = c.getColumnIndex("DUE_DATE");
            int dueTimeCol = c.getColumnIndex("DUE_TIME");
            int contextCol = c.getColumnIndex("CONTEXT");
            int confirmCol = c.getColumnIndex("CONFIRM");


            while(c.moveToNext()){

                System.out.println("SEQ NO : " + Integer.toString(c.getInt(seqNoCol)));
                System.out.println("CONTEXT : " + c.getString(contextCol));

                cData = new CustomListData(Integer.toString(c.getInt(seqNoCol)),
                                           c.getString(titleCol),
                                           c.getString(dueDateCol),
                                           c.getString(dueTimeCol),
                                           c.getString(contextCol),
                                           c.getString(confirmCol)
                                          );
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

    protected void dbClose(){
        db.close();
    }


    protected String[] getContextBySeqNo(String pSeqNo){
        String[] returnValue = new String[10];

        System.out.println("SEQ_NO: " + pSeqNo);

        try{
            String[] columns = {"CONTEXT"};
            String[] seqNo = {pSeqNo};

            //Cursor c = db.query("NOTE_MAST", columns, "SEQ_NO=?", seqNo, null, null, null);

            Cursor c = db.rawQuery("SELECT TITLE, DUE_DATE, DUE_TIME, CONTEXT, CONFIRM FROM TO_DO_MAST WHERE SEQ_NO=" + pSeqNo, null);


            int titleCol = c.getColumnIndex("TITLE");
            int dueDateCol = c.getColumnIndex("DUE_DATE");
            int dueTimeCol = c.getColumnIndex("DUE_TIME");
            int contextCol = c.getColumnIndex("CONTEXT");
            int confirmCol = c.getColumnIndex("CONFIRM");


            while(c.moveToNext()){
                returnValue[0] = c.getString(titleCol);
                returnValue[1] = c.getString(dueDateCol);
                returnValue[2] = c.getString(dueTimeCol);
                returnValue[3] = c.getString(contextCol);
                returnValue[4] = c.getString(confirmCol);
                System.out.println("RETURN VALUE : " + returnValue[0] + " " + returnValue[1] + " " + returnValue[2] + " ");
            }

        }catch(Exception e){
            //Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }

        return returnValue;

    }


}
