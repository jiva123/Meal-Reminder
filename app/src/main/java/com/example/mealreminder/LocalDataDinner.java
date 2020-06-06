package com.example.mealreminder;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDataDinner {
    private static final String APP_SHARED_PREFS_DINNER = "RemindPrefDnr";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private static final String reminderStatus="reminderStatus";
    private static final String hour="hourd";
    private static final String min="mind";

    public LocalDataDinner(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS_DINNER, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    // Settings Page Set Reminder

    public boolean getReminderStatus()
    {
        return appSharedPrefs.getBoolean(reminderStatus, true);
    }

    public void setReminderStatus(boolean status)
    {
        prefsEditor.putBoolean(reminderStatus, status);
        prefsEditor.commit();
    }

    // Settings Page Reminder Time (Hour)

    public int get_hourd()
    {
        return appSharedPrefs.getInt(hour, 22);
    }

    public void set_hourd(int hd)
    {
        prefsEditor.putInt(hour, hd);
        prefsEditor.commit();
    }

    // Settings Page Reminder Time (Minutes)

    public int get_mind()
    {
        return appSharedPrefs.getInt(min, 0);
    }

    public void set_mind(int md)
    {
        prefsEditor.putInt(min, md);
        prefsEditor.commit();
    }

    public void reset()
    {
        prefsEditor.clear();
        prefsEditor.commit();

    }

}
