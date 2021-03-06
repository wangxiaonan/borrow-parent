package com.borrow.manage.utils;


import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by wxn on 2018/9/14
 */
public class Utility {

    public static  Date getBrTime(int expect) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(1);
        localDate = localDate.plusMonths(expect);
        ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());

    }
    public static  Date  getDate(String datestr) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
             date = simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static  int getOverdueDay(Date brTime) {

        Calendar caNow = Calendar.getInstance();
        long caNowMill = caNow.getTime().getTime();

        int days = (int) ((caNowMill - brTime.getTime())/(1000*3600*24));
        return days;

    }
    public static LocalDate dateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
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

    public static  String dateStrddHHmmss(Date date){
        return DateFormatUtils.format(date,"yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {
       Date date =  Utility.getBrTime(8);
        System.out.println(dateStrddHHmmss(date));
//        System.out.println(dateStrddHHmmss(new Date()));
//        System.out.println(PasswordHelper.encryptPassword("123456"));
//        for (int i = 0; i< 7 ;i++) {
//            System.out.println(UUIDProvider.uuid());
//        }
//
//        System.out.println(DateFormatUtils.format(new Date(),"yyyyMMddHHmmssSSS"));
//        LocalDateTime localDateTime = LocalDateTime.now();
//        StringBuffer str = new StringBuffer();
//        int year = localDateTime.getYear();
//        int month = localDateTime.getMonthValue();
//        int day = localDateTime.getDayOfMonth();
//        int hour = localDateTime.getHour();
//        int minute = localDateTime.getMinute();
//        int second = localDateTime.getSecond();
//        int nano = localDateTime.getNano();

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
