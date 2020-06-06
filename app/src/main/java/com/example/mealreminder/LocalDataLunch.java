package com.example.mealreminder;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalDataLunch {
    private static final String APP_SHARED_PREFS_LUNCH = "RemindMep";

    private SharedPreferences appSharedPrefsLunch;
    private SharedPreferences.Editor prefsEditorL;

    private static final String reminderStatL="reminderStatL";
    private static final String hour="hourl";
    private static final String min="minl";

    public LocalDataLunch(Context context)
    {
        this.appSharedPrefsLunch = context.getSharedPreferences(APP_SHARED_PREFS_LUNCH, Context.MODE_PRIVATE);
        this.prefsEditorL = appSharedPrefsLunch.edit();
    }

    // Settings Page Set Reminder

    public boolean getReminderStatus()
    {
        return appSharedPrefsLunch.getBoolean(reminderStatL, false);
    }

    public void setReminderStatus(boolean status)
    {
        prefsEditorL.putBoolean(reminderStatL, status);
        prefsEditorL.commit();
    }

    // Settings Page Reminder Time (Hour)

    public int get_hourl()
    {
        return appSharedPrefsLunch.getInt(hour, 12);
    }

    public void set_hourl(int hl)
    {
        prefsEditorL.putInt(hour, hl);
        prefsEditorL.commit();
    }

    // Settings Page Reminder Time (Minutes)

    public int get_minl()
    {
        return appSharedPrefsLunch.getInt(min, 45);
    }

    public void set_minl(int ml)
    {
        prefsEditorL.putInt(min, ml);
        prefsEditorL.commit();
    }

    public void reset()
    {
        prefsEditorL.clear();
        prefsEditorL.commit();

    }
}
