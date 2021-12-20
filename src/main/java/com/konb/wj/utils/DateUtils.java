package com.konb.wj.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author konb
 */
public class DateUtils {

    public static Date stringToDate(String date, String format) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(date);
    }

    public static int getTwoDayDifference(Date oneDate, Date twoDate) {
        long difference = twoDate.getTime() - oneDate.getTime();
        int dayTimeZone = 1000 * 60 * 60 * 24;
        return (int) (difference / dayTimeZone);
    }

}
