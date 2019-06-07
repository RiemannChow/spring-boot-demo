package com.riemann.springbootdemo.util.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author riemann
 * @date 2019/05/28 22:07
 */
public class CommonUtil {
    public static void main(String[] args) {
        /*Calendar calendar = Calendar.getInstance();
        System.out.println("目前时间: " + calendar.getTime());
        System.out.println("Calendar时区: " + calendar.getTimeZone().getID());
        System.out.println("user.timezone: " + System.getProperty("user.timezone"));
        System.out.println("user.country: " + System.getProperty("user.country"));
        System.out.println("默认时区: " + TimeZone.getDefault().getID());*/

        getSystemTime();
    }

    public static String getSystemTime() {

        Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
        return calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);

        /*long start1 = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        System.out.println(sdf.format(new Date()));// new Date()为获取当前系统时间
        long end1 = System.currentTimeMillis();
        System.out.println((end1 - start1) + "ms");*/

        /*long start2 = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();//可以对每个时间域单独修改
        System.out.println(calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DATE) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND));
        long end2 = System.currentTimeMillis();
        System.out.println((end2 - start2) + "ms");*/

        /*long start3 = System.currentTimeMillis();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        long end3 = System.currentTimeMillis();
        System.out.println(zero);
        System.out.println((end3 - start3) + "ms");*/

        /*long start4 = System.currentTimeMillis();
        long current = System.currentTimeMillis();
        long zero = current/(1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
        System.out.println(zero);
        long end4 = System.currentTimeMillis();
        System.out.println((end4 - start4) + "ms");*/
    }

}
