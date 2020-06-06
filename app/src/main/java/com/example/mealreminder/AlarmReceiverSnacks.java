package com.example.mealreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiverSnacks extends BroadcastReceiver {

    String TAG = "AlarmReceiverSnacks";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalDataSnacks localDataSnacks = new LocalDataSnacks(context);
                NotificationSchedulerSnacks.setReminder(context, AlarmReceiverSnacks.class, localDataSnacks.get_hours(), localDataSnacks.get_mins());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationSchedulerSnacks.showNotificationSnacks(context, remind_pg.class,
                "Snacks Reminder", "it's time to take your Snacks");

    }
}
