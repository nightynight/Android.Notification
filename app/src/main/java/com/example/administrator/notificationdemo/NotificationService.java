package com.example.administrator.notificationdemo;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Administrator on 2016/8/18.
 */
public class NotificationService extends IntentService {
    private NotificationManager mNotificationManager;
    private String mMessage;
    private int mMillis;
    private NotificationCompat.Builder builder;

    public NotificationService() {
        super("com.example.administrator.notificationdemo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //从intent中获取消息内容
        mMessage = intent.getStringExtra(CommonConstants.EXTRA_MESSAGE);
        //从intent中获取时间，默认为CommonConstants.DEFAULT_TIMER_DURATION
        mMillis = intent.getIntExtra(CommonConstants.EXTRA_TIMER, CommonConstants.DEFAULT_TIMER_DURATION);

        mNotificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        //Intent可能是从MainActivity或ResultActivity传过来的，
        // MainActivity传过来的Action是ACTION_NOTIFICATION，
        // ResultActivity传过来的Action是ACTION_SNOOZE或ACTION_DISMISS。
        switch (intent.getAction()){
            case CommonConstants.ACTION_NOTIFICATION:   notification(mMillis, mMessage);    break;
            case CommonConstants.ACTION_SNOOZE:   snooze();    break;
            case CommonConstants.ACTION_DISMISS:   dismiss();    break;
        }
    }

    private void notification(int mMillis,String message){
        //NotificationCompat.Builder给许多各种不同的平台提供notification支持
        //创建Builder对象，
        /*
        一个Builder至少包含以下内容：
            1.一个小的icon，用setSmallIcon())方法设置
            2.一个标题，用setContentTitle())方法设置
            3.详细的文本，用setContentText())方法设置
         */
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_stat_notification)  //设置了之后没效果，还是显示应用图标？
                .setContentTitle(getString(R.string.notification))
                .setContentText(getString(R.string.set_reminder))
                .setDefaults(Notification.DEFAULT_ALL); // requires VIBRATE permission

        Intent resultIntent = new Intent(this, ResultActivity.class);
        resultIntent.putExtra(CommonConstants.EXTRA_MESSAGE, message);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //通过调用setFlags())方法并设置标识FLAG_ACTIVITY_NEW_TASK 与 FLAG_ACTIVITY_CLEAR_TASK，来设置Activity在一个新的任务中启动

        //设置点击了通知栏后要做什么事
        // 这里是打开ResultActivity
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,   //	Context: The Context in which this PendingIntent should start the activity.
                        0,  //int: Private request code for the sender
                        resultIntent,   //Intent: Intent of the activities to be launched.
                        PendingIntent.FLAG_UPDATE_CURRENT   //int: May be FLAG_ONE_SHOT, FLAG_NO_CREATE, FLAG_CANCEL_CURRENT, FLAG_UPDATE_CURRENT, or any of the flags as supported by Intent.fillIn() to control which unspecified parts of the intent that can be supplied when the actual send happens.
                );
        builder.setContentIntent(resultPendingIntent);
        startTimer(mMillis);
    }

    private void snooze(){
        if (true){
            //TODO 如何判断通知有没有被cancel
            notification(CommonConstants.DEFAULT_TIMER_DURATION, getString(R.string.done_snoozing));
        }
    }

    private void dismiss(){
        mNotificationManager.cancel(CommonConstants.NOTIFICATION_ID);
    }

    private void startTimer(int millis) {
        try {
            Thread.sleep(millis);

        } catch (InterruptedException e) {
            Log.d(CommonConstants.DEBUG_TAG, getString(R.string.sleep_error));
        }
        // Including the notification ID allows you to update the notification later on.
        //通知消息栏
        mNotificationManager.notify(CommonConstants.NOTIFICATION_ID, builder.build());
    }

    private void downPicture(){
        //下面的线程展示了如何在Notification中放入ProgressBar
        /*
        Notifications可以包含一个展示用户正在进行的操作状态的动画进度指示器。
        如果你可以在任何时候估算这个操作得花多少时间以及当前已经完成多少，你可以用“determinate（确定的，下同）”形式的指示器（一个进度条）。
        如果你不能估算这个操作的长度，使用“indeterminate（不确定，下同）”形式的指示器（一个活动的指示器）。

        为了展示一个确定长度的进度条，调用 setProgress(max, progress, false))方法将进度条添加进notification，然后发布这个notification，
        第三个参数是个boolean类型，决定进度条是 indeterminate (true) 还是 determinate (false)。
        */
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        int id = 1;
                        NotificationManager mNotifyManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext());
                        mBuilder.setContentTitle("Picture Download")
                                .setContentText("Download in progress")
                                .setSmallIcon(R.drawable.ic_stat_notification);
                        int incr;
                        for (incr = 0; incr <= 100;) {
                            try {
                                Thread.sleep(500);  //每0.5秒更新一次ProgressBar
                            } catch (InterruptedException e) {
                                Log.d("TAG", "sleep failure");
                            }
                            mBuilder.setProgress(100, incr, false);
                            // Displays the progress bar for the first time.
                            mNotifyManager.notify(id, mBuilder.build());
                            incr+=5;    //实际情况是获取下载的进度
                        }
                        // When the loop is finished, updates the notification
                        mBuilder.setContentText("Download complete")
                                // Removes the progress bar
                                .setProgress(0,0,false);
                        mNotifyManager.notify(id, mBuilder.build());
                    }
                }
        ).start();
    }
}
