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

        final DbAccess access = new DbAccess();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_page);

        Intent intent = getIntent();
        String pSeqNo = intent.getStringExtra("seqNo");


        if(!"".equals(pSeqNo)){
            try{
                access.openDataBase();
                access.makeDataTable();

                String pContext = access.getContextBySeqNo(pSeqNo);
                txtView = (TextView)findViewById(R.id.editText);
                txtView.setText(pContext);

                Toast.makeText(getApplicationContext(), pContext, 1).show();
            }catch(Exception e){
                Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
            }finally{
                access.dbClose();
            }
        }

        Button backBtn = (Button)findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                Intent rtnIntent = new Intent();
                txtView = (TextView)findViewById(R.id.editText);

                //DB 저장
                try{
                    access.openDataBase();
                    access.insertData( intent.getStringExtra("seqNo") ,txtView.getText().toString());

                }catch(Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(),1).show();
                }finally{
                    access.dbClose();

                    rtnIntent.putExtra("memo", "END");
                    setResult(RESULT_OK, rtnIntent);
                    finish();

                }
            }
        });
    }


}