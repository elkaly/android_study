package com.example.NotePad;

import android.app.*;

import android.app.Notification.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Calendar;


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


        Button btnNoti = (Button)findViewById(R.id.btnNoti);
        btnNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Notification.Builder notiBuilder = new Notification.Builder(getApplicationContext());
                notiBuilder.setSmallIcon(android.R.drawable.presence_video_busy);
                notiBuilder.setContentTitle("TITLE");
                notiBuilder.setContentText("CONTEXT");

                Notification noti = notiBuilder.build();

                //------------------------------------------------------------------
                // Notify
                NotificationManager notiManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                notiManager.notify(0, noti);
                */

                // 알람 매니저에 등록할 인텐트를 만듬
                Intent intent = new Intent(MyActivity.this, AlarmReceiver.class);
                PendingIntent sender = PendingIntent.getBroadcast(MyActivity.this, 0, intent, 0);

                // 알람을 받을 시간을 5초 뒤로 설정
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND, 5);

                // 알람 매니저에 알람을 등록
                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);

            }
        });


    }




    protected void setListView(){
        final DbAccess access = new DbAccess();

        ArrayList<CustomListData> list = new ArrayList<CustomListData>();
        CustomListData cData;

        //DB OPEN
        access.openDataBase();
        access.makeDataTable();

        //access.insertSomeDbData();

        list = access.userCursor1();
        CustomAdaptor cAdaptor = new CustomAdaptor(MyActivity.this, R.layout.custom_list, list);
        listView = (ListView)findViewById(R.id.listView);

        cAdaptor.notifyDataSetChanged();
        listView.setAdapter(cAdaptor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                access.dbClose();
                CustomListData cData = (CustomListData)parent.getItemAtPosition(position);

                //Toast.makeText(getApplicationContext(), cData.getText(), 1).show();

                Intent intent = new Intent(MyActivity.this, EditPage.class);
                intent.putExtra("seqNo", cData.getSeqNo());
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

       setListView();

    }

}
