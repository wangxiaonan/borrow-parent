package com.borrow.manage.utils;


import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wxn on 2018/9/14
 */
public class Utility {

    public static  Date getBrTime(int expect) {

        LocalDateTime lastDateTime =LocalDateTime.now();
        int day = lastDateTime.getDayOfMonth();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, expect);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        String nextMonthDay = format.format(c.getTime());
        int nextLastDay = Integer.valueOf(nextMonthDay.split("-")[2]);
        if (day >nextLastDay ) {
            day = nextLastDay;
        }
        c.set(Calendar.DAY_OF_MONTH,day);
        return c.getTime();

    }
    public static  int getOverdueDay(Date brTime) {

        Calendar caNow = Calendar.getInstance();
        long caNowMill = caNow.getTime().getTime();

        int days = (int) ((caNowMill - brTime.getTime())/(1000*3600*24));
        return days;

    }

    public static  long getUnixTime() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dayTime = format.format(new Date());
        long time = 0;
        try {
            time = format.parse(dayTime).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static  String dateStr(){
        return DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS");
    }
    public static  String dateStr(Date date){
        return DateFormatUtils.format(date,"yyyyMMddHHmmssSSS");
    }

    public static void main(String[] args) {

        System.out.println(DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS"));
        LocalDateTime localDateTime = LocalDateTime.now();
        StringBuffer str = new StringBuffer();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        int hour = localDateTime.getHour();
        int minute = localDateTime.getMinute();
        int second = localDateTime.getSecond();
        int nano = localDateTime.getNano();

//        System.out.println(getBrTime(5));
//        Date brTime = null;
//        try {
//             brTime = DateUtils.parseDate("2018-09-27",new String[]{"yyyy-MM-dd"} );
//            System.out.println(brTime.getTime()/1000);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println(getOverdueDay(brTime));
//        System.out.println(getUnixTime());
    }




}
