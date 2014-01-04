package com.example.NotePad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        Button newBtn = (Button)findViewById(R.id.newBtn);

        newBtn.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v){

               //화면 띄우기
               Intent intent = new Intent(MyActivity.this, EditPage.class);
               startActivityForResult(intent, 100);
           }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){ //신규 입력 후 메인 복귀 시
            String rtnMemo = data.getStringExtra("memo");

            System.out.println("RTN VALUE: " + rtnMemo + "=========================");
            Toast.makeText(getApplicationContext(), rtnMemo, resultCode).show();

       }

    }
}
