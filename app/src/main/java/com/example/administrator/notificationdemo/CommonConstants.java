package com.example.administrator.notificationdemo;

/**
 *
 * A set of constants used by all of the components in this application. To use these constants
 * the components implement the interface.
 */

public final class CommonConstants {

    private CommonConstants() {
        // don't allow the class to be instantiated
    }
    public static final int DEFAULT_TIMER_DURATION = 3 * 1000;
    public static final String ACTION_SNOOZE = "com.example.administrator.notificationdemo.ACTION_SNOOZE";
    public static final String ACTION_DISMISS = "com.example.administrator.notificationdemo.ACTION_DISMISS";
    public static final String ACTION_NOTIFICATION = "com.example.administrator.notificationdemo.ACTION_PING";
    public static final String EXTRA_MESSAGE= "com.example.administrator.notificationdemo.EXTRA_MESSAGE";
    public static final String EXTRA_TIMER = "com.example.administrator.notificationdemo.EXTRA_TIMER";
    public static final int NOTIFICATION_ID = 001;
    public static final String DEBUG_TAG = "Notification";
}
