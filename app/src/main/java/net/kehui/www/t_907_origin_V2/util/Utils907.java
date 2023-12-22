package net.kehui.www.t_907_origin_V2.util;

import android.content.Context;
import android.provider.Settings;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils907 {

    /**
     * @param context   运行环境、场景
     * @return  获取设备序列号
     */
    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * @return  当前时间
     */
    public static String formatTimeStamp(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    /**
     * @param timeString    字符串
     * @return  转时间戳
     */
    public static long getTime(String timeString) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        long l = 0;
        try {
            d = sdf.parse(timeString);
            l = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }
}
