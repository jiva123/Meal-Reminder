package com.example.mealreminder;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDataSnacks {
    private static final String APP_SHARED_PREFS_SNACKS = "RemindMeprefS";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private static final String reminderStatus="reminderStatS";
    private static final String hour="hours";
    private static final String min="mins";

    public LocalDataSnacks(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS_SNACKS, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    // Settings Page Set Reminder

    public boolean getReminderStatus()
    {
        return appSharedPrefs.getBoolean(reminderStatus, false);
    }

    public void setReminderStatus(boolean status)
    {
        prefsEditor.putBoolean(reminderStatus, status);
        prefsEditor.commit();
    }

    // Settings Page Reminder Time (Hour)

    public int get_hours()
    {
        return appSharedPrefs.getInt(hour, 17);
    }

    public void set_hours(int hs)
    {
        prefsEditor.putInt(hour, hs);
        prefsEditor.commit();
    }

    // Settings Page Reminder Time (Minutes)

    public int get_mins()
    {
        return appSharedPrefs.getInt(min, 0);
    }

    public void set_mins(int ms)
    {
        prefsEditor.putInt(min, ms);
        prefsEditor.commit();
    }

    public void reset()
    {
        prefsEditor.clear();
        prefsEditor.commit();

    }
}
