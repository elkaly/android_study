package com.example.untitled2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.view.View;

import android.content.Intent;


public class MyActivity extends Activity implements OnClickListener{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button testBtn = (Button)findViewById(R.id.startBtn);


        testBtn.setOnClickListener(this);


        /*
        testBtn.setOnClickListener(new OnClickListener(){
           @Override
            public void onClick(View v){

               Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
           }


        });*/

    }

    @Override
    public void onClick(View v){
        int sel = v.getId();
        switch(sel){
            case R.id.startBtn:

                //화면 띄우기
                //Intent intent = new Intent(MyActivity.this, SecondActivity.class);
                //startActivity(intent);

                //전화걸기
                //Intent intentCall = new Intent();
                //intentCall.setAction(Intent.ACTION_DIAL);
                //intentCall.setData(Uri.parse("tel:01090580695"));
                //startActivity(intentCall);

                //SMS보내기
                Intent intentSms = new Intent();
                intentSms.setAction(Intent.ACTION_VIEW);
                intentSms.setData(Uri.parse("smsto:01090580695"));
                intentSms.putExtra("sms_body", "Hello~!");
                startActivity(intentSms);


                //Toast.makeText(this, "TEs2", Toast.LENGTH_SHORT).show();
            break;


        }
    }

}
