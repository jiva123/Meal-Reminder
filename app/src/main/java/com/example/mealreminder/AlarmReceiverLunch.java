package com.example.mealreminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiverLunch extends BroadcastReceiver {

    String TAG = "AlarmReceiverLunch";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                LocalDataLunch localDataLunch = new LocalDataLunch(context);
                NotificationSchedulerLunch.setReminder(context, AlarmReceiverLunch.class, localDataLunch.get_hourl(), localDataLunch.get_minl());
                return;
            }
        }

        Log.d(TAG, "onReceive: ");

        //Trigger the notification
        NotificationSchedulerLunch.showNotificationLunch(context, remind_pg.class,
                "Lunch Reminder", "it's time to take your Lunch");

    }
}
