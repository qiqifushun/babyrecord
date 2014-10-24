package com.qiqi.babyrecord.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.qiqi.babyrecord.R;

public class BabyInfoUtils {

    public static final long DAY = 24 * 60 * 60 * 1000L;

    public static String getAge(long selectedDate, long birthday) {

        if (selectedDate - birthday <= 0) {
            return "时间错误";
        }

        // 100天之内都按照天算
        Calendar calendar1 = Calendar.getInstance(Locale.getDefault());
        calendar1.setTimeInMillis(selectedDate);
        calendar1.set(Calendar.HOUR_OF_DAY, 0);
        calendar1.set(Calendar.MINUTE, 0);
        calendar1.set(Calendar.SECOND, 0);
        calendar1.set(Calendar.MILLISECOND, 0);
        Calendar calendar2 = Calendar.getInstance(Locale.getDefault());
        calendar2.setTimeInMillis(birthday);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);
        int days = Math.round((float) (calendar1.getTimeInMillis() - calendar2
                .getTimeInMillis()) / DAY);
        if (days == 0) {
            return "出生日";
        }
        if (days <= 100) {
            return days + "天";
        }

        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy",
                Locale.getDefault());
        SimpleDateFormat monthFormat = new SimpleDateFormat("M",
                Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("d",
                Locale.getDefault());

        Date now = new Date();
        Date birth = new Date(birthday);

        int year = Integer.valueOf(yearFormat.format(now))
                - Integer.valueOf(yearFormat.format(birth));
        int month = Integer.valueOf(monthFormat.format(now))
                - Integer.valueOf(monthFormat.format(birth));
        int day = Integer.valueOf(dayFormat.format(now))
                - Integer.valueOf(dayFormat.format(birth));

        StringBuffer stringBuffer = new StringBuffer();
        int length = 0;
        // 几岁
        if (year > 0) {
            if (month < 0 || month == 0 && day < 0) {
                year--;
            }
            if (year > 0) {
                stringBuffer.append(year);
                stringBuffer.append("岁");
                length++;
            }
        }

        // 几个月
        if (month <= 0) {
            month = 12 + month;
        }
        if (day < 0) {
            month--;
        }
        if (month > 0) {
            stringBuffer.append(month);
            stringBuffer.append("月");
            length++;
        }

        // 几天
        if (day < 0) {
            Calendar dayLastMonth = Calendar.getInstance(Locale.getDefault());
            dayLastMonth.roll(Calendar.MONTH, -1);
            Calendar dayBirth = Calendar.getInstance(Locale.getDefault());
            dayBirth.setTimeInMillis(birthday);
            dayLastMonth.set(Calendar.DAY_OF_MONTH,
                    dayBirth.get(Calendar.DAY_OF_MONTH));
            if (dayLastMonth.get(Calendar.MONTH) == Calendar.DECEMBER) {
                dayLastMonth.roll(Calendar.YEAR, -1);
            }
            day = Math.round((float) (selectedDate - dayLastMonth
                    .getTimeInMillis()) / DAY);
        }
        if (length < 2 && day > 0) {
            stringBuffer.append(day);
            stringBuffer.append("天");
            length++;
        }

        return stringBuffer.toString();
    }

    /**
     * @param
     * @return
     * @author leidiqiu 2014-10-14 上午10:53:44
     */
    public static String getWeight(int weight) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(weight);
        stringBuffer.append("克");
        stringBuffer.append("（");
        int jin = weight * 2 / 1000;
        stringBuffer.append(jin);
        stringBuffer.append("斤");
        int liang = Math.round((float) (weight * 2 % 1000) / 100);
        if (liang > 0) {
            stringBuffer.append(liang);
            stringBuffer.append("两");
        }
        stringBuffer.append("）");
        return stringBuffer.toString();
    }

    public static String getBirthday(long birthday) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                .format(new Date(birthday));
    }

    public static Bitmap getBabyImage(Context context, String path) {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_launcher);
        }
        return bitmap;
    }
}
