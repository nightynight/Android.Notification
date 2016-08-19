package com.example.administrator.notificationdemo;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/8/18.
 */
public class ResultActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        String message = getIntent().getStringExtra(CommonConstants.EXTRA_MESSAGE);
        TextView text = (TextView) findViewById(R.id.result_message);
        text.setText(message);
    }
    public void onSnoozeClick(View v){
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(CommonConstants.ACTION_SNOOZE);
        startService(intent);
    }

    public  void onDismissClick(View v){
        Intent intent = new Intent(getApplicationContext(), NotificationService.class);
        intent.setAction(CommonConstants.ACTION_DISMISS);
        startService(intent);
    }
}
