package com.example.NotePad;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import java.text.DateFormat;
import java.util.Calendar;


/**
 * Created by jeahyukkim on 2014. 1. 4..
 */
public class EditPage extends Activity {
    SQLiteDatabase db;
    TextView txtContext;
    TextView txtTitle;

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

                String pContent[] = access.getContextBySeqNo(pSeqNo);

                txtTitle = (TextView)findViewById(R.id.editTitle);
                txtTitle.setText(pContent[0]);

                Button btnDate = (Button)findViewById(R.id.btnDate);
                btnDate.setText(pContent[1].substring(0,4) + "/" + pContent[1].substring(4,6) + "/" + pContent[1].substring(6,8));

                Button btnTime = (Button)findViewById(R.id.btnTime);
                btnTime.setText(pContent[2].substring(0,2) + ":" + pContent[2].substring(2,4));

                txtContext = (TextView)findViewById(R.id.editText);
                txtContext.setText(pContent[3]);


                ToggleButton btnConfirm = (ToggleButton)findViewById(R.id.toggleButton);
                if("Y".equals(pContent[4])){
                    btnConfirm.setChecked(true);
                }else{
                    btnConfirm.setChecked(false);
                }

                Toast.makeText(getApplicationContext(), pContent[2], 1).show();
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
                txtContext = (TextView)findViewById(R.id.editText);
                txtTitle = (TextView)findViewById(R.id.editTitle);

                Button btnDate = (Button)findViewById(R.id.btnDate);
                String day = btnDate.getText().toString().replace("/","");
                Button btnTime = (Button)findViewById(R.id.btnTime);
                String time = btnTime.getText().toString().replace(":","");

                ToggleButton btnConfirm = (ToggleButton)findViewById(R.id.toggleButton);
                String confirm = btnConfirm.isChecked()?"Y":"N";

                //DB 저장
                try{
                    access.openDataBase();
                    access.makeDataTable();
                    access.insertData(intent.getStringExtra("seqNo"),
                                      txtTitle.getText().toString(),
                                      day,
                                      time,
                                      txtContext.getText().toString(),
                                      confirm);

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

        Button btnDate = (Button)findViewById(R.id.btnDate);
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDateDialog();
            }
        });

        Button btnTime = (Button)findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTimeDialog();
            }
        });


    }

    public boolean isNum(String value){
        boolean flag = true;

        try{
            Integer.parseInt(value);
            flag = true;
        }catch(Exception e){
            flag = false;
        }

        return flag;
    }

    public void getDateDialog(){
        Dialog dia = null;
        Calendar c = Calendar.getInstance();

        Button btnDate = (Button)findViewById(R.id.btnDate);
        String day = btnDate.getText().toString().replace("/","");
        System.out.println("DAY: " + day);

        if(isNum(day) && day.length() == 8){

            int year = Integer.parseInt(day.substring(0, 4));
            int month = Integer.parseInt(day.substring(4, 6));
            int date = Integer.parseInt(day.substring(6, 8));

            System.out.println("YEAR: " + year +" " + "MONTH: " +month + " " + "DATE: " + date );

            dia = new DatePickerDialog (this, mDateSetListener, year, month-1, date);
        }else{
            dia = new DatePickerDialog (this, mDateSetListener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        }

        dia.show();
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Button btnDate = (Button)findViewById(R.id.btnDate);
                    btnDate.setText(year+"/"+leftPad(monthOfYear + 1)+"/"+leftPad(dayOfMonth));
                }
            };

    public void getTimeDialog(){
        Dialog dia = null;
        Calendar c = Calendar.getInstance();


        Button btnTime = (Button)findViewById(R.id.btnTime);
        String time = btnTime.getText().toString().replace(":","");
        System.out.println("TIME: " + time);

        if(isNum(time) && time.length() == 4){

            int hour = Integer.parseInt(time.substring(0, 2));
            int minute = Integer.parseInt(time.substring(2, 4));

            System.out.println("HOUR: " + hour +" " + "MINUTE: " + minute );

            dia = new TimePickerDialog(this, mTimeSetListener, hour, minute, false);
        }else{
            dia = new TimePickerDialog(this, mTimeSetListener, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);
        }


        dia.show();
    }

    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener(){
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    Button btnTime = (Button)findViewById(R.id.btnTime);
                    btnTime.setText(leftPad(hourOfDay)+":"+leftPad(minute));
                }
            };


    public String leftPad(int num) {
        String rtnVal = null;
        if (num < 10){
            rtnVal =    ObjectUtils.toString(num);
            rtnVal = StringUtils.leftPad(rtnVal, 2, '0').trim();
        } else {
            rtnVal = ObjectUtils.toString(num).trim();
        }
        return rtnVal;
    }


}