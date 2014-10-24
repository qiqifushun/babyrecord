package com.qiqi.babyrecord.util;

import android.content.Context;
import android.preference.PreferenceManager;

public class SharedPreferenceUtils {

    public static long getCheckedBabyInfoId(Context context) {
        long babyId = PreferenceManager.getDefaultSharedPreferences(context)
                .getLong("babyId", -1L);
        return babyId;
    }

    public static void setCheckedBabyInfoId(Context context, long babyId) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putLong("babyId", babyId).commit();
    }
}
