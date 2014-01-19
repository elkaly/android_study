package com.example.NotePad;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MyActivity extends Activity {

    private ArrayList<String> list;
    private ArrayAdapter<String> arrayAdapter;
    private ListView listView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setListView();

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


    protected void setListView(){
        final DbAccess access = new DbAccess();

        ArrayList<CustomListData> list = new ArrayList<CustomListData>();
        CustomListData cData;

        //DB OPEN
        access.openDataBase();

        list = access.userCursor1();
        CustomAdaptor cAdaptor = new CustomAdaptor(MyActivity.this, R.layout.custom_list, list);
        listView = (ListView)findViewById(R.id.listView);

        cAdaptor.notifyDataSetChanged();
        listView.setAdapter(cAdaptor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CustomListData cData = (CustomListData)parent.getItemAtPosition(position);

                //Toast.makeText(getApplicationContext(), cData.getText(), 1).show();

                Intent intent = new Intent(MyActivity.this, EditPage.class);
                intent.putExtra("seqNo", cData.getKey());
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
