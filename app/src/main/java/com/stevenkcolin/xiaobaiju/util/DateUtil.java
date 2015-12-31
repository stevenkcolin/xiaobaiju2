package com.stevenkcolin.xiaobaiju.util;

import android.util.Log;

import java.security.PublicKey;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pengfei on 2015/12/14.
 */
public class DateUtil {
    private static String ISO_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS";
    private static final DateFormat ISO_DATE_MONTH_FORMAT = new SimpleDateFormat("MM-dd");

    public static Date toDate(String strDate){
        DateFormat dateFormat = new SimpleDateFormat(ISO_DATE_FORMAT);
        try {
            return dateFormat.parse(strDate);
        } catch (ParseException pe) {
            Log.e("DateUtil", pe.getMessage());
            return null;
        }
    }

    public static String toLocalString(Date date) {
        return DateFormat.getDateTimeInstance().format(date);
    }

    public static String toLocalStringDateMonth(Date date) {
        return ISO_DATE_MONTH_FORMAT.format(date);
    }
}
