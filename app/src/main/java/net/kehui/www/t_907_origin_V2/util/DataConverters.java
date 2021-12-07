package net.kehui.www.t_907_origin_V2.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

/**
 * @author li.md
 * @date 2019/7/6
 */
public class DataConverters {

    @TypeConverter
    public final String  dataToString(int[] a) {
        Gson gson = new Gson();
        return gson.toJson(a);
    }

    @TypeConverter
    public final int[] stringToData(String str) {
        Gson gson = new Gson();
        return gson.fromJson(str, int[].class);
    }


}
