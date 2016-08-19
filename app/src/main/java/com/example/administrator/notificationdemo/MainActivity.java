package com.example.administrator.notificationdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //点击Button后执行的函数
    public void onPingClick(View v){
        //拿到输入的值
        EditText edit_seconds=(EditText)findViewById(R.id.edit_seconds);
        String time=edit_seconds.getText().toString();
        EditText edit_reminder=(EditText)findViewById(R.id.edit_reminder);
        String reminderText=edit_reminder.getText().toString();

        int seconds=(time == null || time.trim().equals(""))?R.string.seconds_default:Integer.parseInt(time);
        int milliseconds = seconds * 1000;

        //生成一个intent，用来启动Service，在Service中设置通知
        Intent intent=new Intent(this,NotificationService.class);
        intent.putExtra(CommonConstants.EXTRA_MESSAGE,reminderText);
        intent.putExtra(CommonConstants.EXTRA_TIMER, milliseconds);
        intent.setAction(CommonConstants.ACTION_NOTIFICATION);
        startService(intent);
    }
}
