package com.example.untitled2;

import android.app.Activity;
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

                Intent intent = new Intent(MyActivity.this, SecondActivity.class);
                startActivity(intent);
                //Toast.makeText(this, "TEs2", Toast.LENGTH_SHORT).show();
            break;


        }
    }

}
