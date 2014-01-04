package com.example.NotePad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by jeahyukkim on 2014. 1. 4..
 */
public class EditPage extends Activity {
    SQLiteDatabase db;
    TextView txtView;

    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);

        Button backBtn = (Button)findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent rtnIntent = new Intent();

                txtView = (TextView)findViewById(R.id.editText);

                //DB 저장
                try{

                    openDataBase();
                    dropTable();
                    insertSomeDbData();
                    userCursor1();

                    db.close();



                    //rtnIntent.putExtra("memo", txtView.getText().toString());
                    rtnIntent.putExtra("memo", "END");

                    //setResult(RESULT_OK, rtnIntent);

                    //finish();

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
                }

            }
        });
    }

    private void openDataBase(){
        try{
            db = SQLiteDatabase.openDatabase("data/data/com.example.NotePad/TestDB1", null, SQLiteDatabase.CREATE_IF_NECESSARY );
            Toast.makeText(getApplicationContext(), "DB OPEN",1).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }

    }

    private void insertSomeDbData(){
        db.beginTransaction();
        try{

            db.execSQL("create table LocationTable(" + "time integer," + "location text);");
            db.setTransactionSuccessful();

            Toast.makeText(getApplicationContext(), "Table was created!",1).show();

        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
       }
       finally{
        db.endTransaction();
       }

       db.beginTransaction();

        try{
            db.execSQL("insert into LocationTable(time,location)" + "values(1230,'test1');");
            db.execSQL("insert into LocationTable(time,location)" + "values(0200,'test2');");
            db.execSQL("insert into LocationTable(time,location)" + "values(0500,'test3');");

            db.setTransactionSuccessful();
            Toast.makeText(getApplicationContext(), "3 record were inserted!!",1).show();


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
        finally{
            db.endTransaction();

        }
    }

    private void dropTable(){
        try{
            db.execSQL("drop table LocationTable");
            Toast.makeText(getApplicationContext(), "Table dropped",1).show();
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }

    }

    private void userCursor1(){
        try{
            txtView.append("\n");

            String[] columns = {"time", "location"};
            Cursor c = db.query("LocationTable", columns, null, null, null, null, "");


            int theTotal = c.getCount();
            int timecol = c.getColumnIndex("time");
            int locationcol = c.getColumnIndex("location");


            while(c.moveToNext()){
               columns[0] = Integer.toString(c.getInt(timecol));
               columns[1] = c.getString(locationcol);

                txtView.append(columns[0] + "|" + columns[1] + "\n");

            }


        }catch(Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
        }
    }

}