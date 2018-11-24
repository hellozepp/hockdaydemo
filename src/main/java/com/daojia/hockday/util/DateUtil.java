/**
 * 58.com Inc.
 * Copyright (c) 2005-2012 All Rights Reserved.
 */
package com.daojia.hockday.util;


import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author gaojingjun
 * @version $Id: DateUtil.java, v 0.1 2012-5-29 下午4:47:31 gaojingjun Exp $
 */
public class DateUtil {

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String simple = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String simple2 = "yyyy-MM-dd HH:mm";

    /**
     * 年月日 yyyy-MM-dd
     */
    public static final String dtSimple = "yyyy-MM-dd";

    /**
     * 年月日 yyyyMMdd
     */
    public static final String dtSimple2 = "yyyyMMdd";

    /**
     * 年月日 yyyyMMdd
     */
    public static final String dtSimple3 = "yyyyMMdd_HHmmss";

    /**
     * 年月日 yyyyMMdd
     */
    public static final String dtSimple4 = "yyyyMMddHHmmss";

    public static final int millisecondOfDay = 1000 * 60 * 60 * 24;

    public static final int millisecondOfHour = 1000 * 60 * 60;

    /**
     * 获取格式
     *
     * @param format
     * @return
     */
    public static DateFormat getFormat(String format) {
        return new SimpleDateFormat(format);
    }

    /**
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @return
     */
    public static String simple(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simple).format(date);
    }

    public static String simple2(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(simple2).format(date);
    }

    public static String simple3(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimple3).format(date);
    }

    public static String simple4(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimple4).format(date);
    }

    /**
     * yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String dtSimple(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimple).format(date);
    }

    /**
     * 获取一天的开始时间和结束时间   00:00:00到23:59:59
     *
     * @param date
     * @return
     */
    public static String[] dtSimpleDayOfStartEnd(Date date) {
        if (date == null) {
            return null;
        }
        String[] arr = {getFormat(dtSimple).format(date) + " 00:00:00",
                getFormat(dtSimple).format(date) + " 23:59:59"};
        return arr;
    }

    /**
     * yyyyMMdd
     *
     * @param date
     * @return
     */
    public static String dtSimple2(Date date) {
        if (date == null) {
            return "";
        }
        return getFormat(dtSimple2).format(date);
    }

    /**
     * 当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String simpleFormat() {

        return simple(new Date());
    }

    /**
     * 获取传入时间相差的日期
     *
     * @param dt      传入日期，可以为空
     * @param diffDay 需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
     * @return
     */
    public static Date getDiffDay(Date dt, int diffDay) {
        Calendar ca = Calendar.getInstance();

        if (dt == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(dt);
        }
        ca.add(Calendar.DATE, diffDay);
        return ca.getTime();
    }

    /**
     * 获取输入日期月份的相差日期
     *
     * @param dt
     * @param idiff wfzc055780-
     * @return
     */
    public static Date getDiffMon(Date dt, int diffMon) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MONTH, diffMon);
        return c.getTime();
    }

    /**
     * 获取输入日期月份的相差日期
     *
     * @param dt
     * @param idiff
     * @return
     */
    public static Date getDiffHour(Date dt, int diffHour) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.HOUR, diffHour);
        return c.getTime();
    }

    /**
     * 获取输入日期分钟的相差日期
     *
     * @param dt
     * @param diffMins
     * @return
     */
    public static Date getDiffMins(Date dt, int diffMins) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, diffMins);
        return c.getTime();
    }

    /**
     * 获取输入日期秒的相差日期
     *
     * @param dt
     * @param diffSeconds
     * @return
     */
    public static Date getDiffSeconds(Date dt, int diffSeconds) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.SECOND, diffSeconds);
        return c.getTime();
    }

    /**
     * 返回日期相差天数，进位向上取整数
     * <p><code>startDate < endDate</code>  结果为正</p>
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countDays(Date startDate, Date endDate) {

        if ((startDate == null) || (endDate == null)) {
            return 0;
        }
        double diffMs = endDate.getTime() - startDate.getTime();
        return (int) Math.ceil(diffMs / millisecondOfDay);
    }

    /**
     * 计算时间段包含的天数（包括起始时间当天）
     * <p><code>startDate < endDate</code>  结果为正</p>
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countContainDays(Date startDate, Date endDate) {

        if ((startDate == null) || (endDate == null)) {
            return 0;
        }
        startDate = toDate(startDate);
        endDate = toDate(endDate);
        double diffMs = endDate.getTime() - startDate.getTime();
        return (int) Math.ceil(diffMs / millisecondOfDay) + 1;
    }

    /**
     * 返回日期相差小时，进位向上取整数
     * <p><code>startDate < endDate</code>  结果为正</p>
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int countHours(Date startDate, Date endDate) {

        if ((startDate == null) || (endDate == null)) {
            return 0;
        }
        double diffMs = endDate.getTime() - startDate.getTime();
        return (int) Math.ceil(diffMs / millisecondOfHour);
    }

    public static String countDateHhMmSs(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            Long startMill = countMs(startDate, endDate);
            Date date = toDate(new Date());
            Long total = date.getTime() + startMill.longValue();
            Date result = new Date(total.longValue());
            String ss = simple(result);
            return ss.split(" ")[1];
        } else {
            return "";
        }
    }

    public static Long countMs(Date startDate, Date endDate) {
        if (startDate != null && endDate != null) {
            Long diffMs = endDate.getTime() - startDate.getTime();
            return diffMs;
        } else {
            return 0L;
        }
    }

    /**
     * 将字符串yyyy-MM-dd HH:mm:ss转化为时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static Date toDateTime(String stringDate) throws ParseException {

        if (stringDate == null) {
            return null;
        }

        return getFormat(simple).parse(stringDate);
    }

    /**
     * 将字符串yyyy-MM-dd HH:mm转化为时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static Date toDateTime2(String stringDate) throws ParseException {

        if (stringDate == null) {
            return null;
        }

        return getFormat(simple2).parse(stringDate);
    }

    /**
     * 将字符串yyyy-MM-dd转化为时间
     *
     * @param stringDate
     * @return
     * @throws ParseException
     */
    public static Date toDate(String stringDate) throws ParseException {

        if (stringDate == null) {
            return null;
        }

        return getFormat(dtSimple).parse(stringDate);
    }

    /**
     * 将时间转换为只有日期
     *
     * @param date
     * @return
     */
    public static Date toDate(Date date) {

        Calendar ca = Calendar.getInstance();
        if (date == null) {
            ca.setTime(new Date());
        } else {
            ca.setTime(date);
        }
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.MILLISECOND, 0);
        return ca.getTime();

    }

    /**
     * 获得两个日期之前最大一个
     * <p> 2012-01-02 < 2012-02-02</p>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date getMax(Date date1, Date date2) {

        if (date1 == null || date2 == null) {
            return null;
        }
        return date1.after(date2) ? date1 : date2;
    }

    /**
     * 获得两个日期之前最小一个
     * <p> 2012-01-02 < 2012-02-02</p>
     *
     * @param date1
     * @param date2
     * @return
     */
    public static Date getMin(Date date1, Date date2) {

        if (date1 == null || date2 == null) {
            return null;
        }
        return date1.before(date2) ? date1 : date2;
    }

    /**
     * 判断两个时间是否在同一个月
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {

        String format = "yyyy-MM";
        DateFormat dateFormat = getFormat(format);
        return StringUtils.equals(dateFormat.format(date1), dateFormat.format(date2));
    }

    /**
     * 判断两个时间是否在同一个天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDate(Date date1, Date date2) {

        return StringUtils.equals(dtSimple(date1), dtSimple(date2));
    }

    /**
     * 判断两个时间是否在同一个周
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameWeek(Date date1, Date date2) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int weekNum1 = calendar.get(Calendar.WEEK_OF_YEAR);
        calendar.setTime(date2);
        int weekNum2 = calendar.get(Calendar.WEEK_OF_YEAR);
        return weekNum1 == weekNum2;
    }

    /**
     * 获得年
     *
     * @param date
     * @return
     */
    public static int getYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 获得月
     *
     * @param date
     * @return
     */
    public static int getMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    /**
     * 时间在该年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 时间在该月的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 时间在该年的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfYear(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 时间在该月的第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 时间在该周的第几天(星期天为第一天)
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 将日期类型转换为字符串，获取指定的字符串
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static String[] getDateStrArray(Date beginDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat(dtSimple);
        String beginDateStr = "";
        String endDateStr = "";
        if (beginDate != null) {
            beginDateStr = sdf.format(beginDate) + " 00:00:00";
        }
        if (endDate != null) {
            endDateStr = sdf.format(endDate) + " 23:59:59";
        }
        String[] dateStrArr = {beginDateStr, endDateStr};
        return dateStrArr;
    }

    /**
     * 根据日期获取年月日时分
     *
     * @param date
     * @return
     */
    public static int[] getYMDhm(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int[] arr = {year, month, day, hour, minute};
        return arr;
    }

    /**
     * 将java.sql.Timestamp对象转化为String字符串
     *
     * @param time      要格式的java.sql.Timestamp对象
     * @param strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(java.sql.Timestamp time, String strFormat) {
        DateFormat df = new SimpleDateFormat(strFormat);
        String str = df.format(time);
        return str;
    }

    /**
     * 将java.util.Date对象转化为String字符串
     *
     * @param date      要格式的java.util.Date对象
     * @param strFormat 输出的String字符串格式的限定（如："yyyy-MM-dd HH:mm:ss"）
     * @return 表示日期的字符串
     */
    public static String dateToStr(Date date, String strFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(strFormat);
        String str = sf.format(date);
        return str;
    }

    /**
     * 获取当天和上周当天的时间
     *
     * @return
     */
    public static String[] getStrDate() {
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strToday = sdf.format(today);
        Calendar ca = Calendar.getInstance();
        ca.setTime(today);
        ca.add(Calendar.DATE, -7);
        Date lastweek = ca.getTime();
        String lastWeekStr = sdf.format(lastweek);
        String[] dateArr = {strToday, lastWeekStr};
        return dateArr;
    }

    public static Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, +1);
        date = calendar.getTime();
        return date;
    }

    public static Date getBeforeDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    /**
     * 将String字符串转换为java.util.Date格式日期
     *
     * @param strDate    表示日期的字符串
     * @param dateFormat 传入字符串的日期表示格式（如："yyyy-MM-dd HH:mm:ss"）
     * @return java.util.Date类型日期对象（如果转换失败则返回null）
     */
    public static Date strToUtilDate(String strDate, String dateFormat) {
        SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = sf.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 用于单测
     *
     * @return
     */
    public static Date getNow() {
        return new Date();
    }

    public static void main(String[] args) throws Exception {

        System.out.println(getFormat("yyyyMMdd_HHmmss").format(new Date()));
        ;

        System.out.println(simple4(new Date()));

        /*java.util.Date date = DateUtil.toDate("2014-10-20");

        System.out.println(getDiffDay(date, -21));
        */
        //System.out.println(DateUtil.simple(DateUtil.toDateTime(DateUtil.dtSimple(new Date()))));
        /*        String[] dateArr = getStrDate();
                System.out.println(dateArr[0] + "===>" + dateArr[1]);
                System.out.println(DateUtil.simple(DateUtil.toDate(new Date())));*/
        /*String dateStr = "2014-01-01 00:00:00";
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDateTime(dateStr));

        System.out.println(Calendar.DAY_OF_YEAR);
        cal.add(Calendar.DAY_OF_YEAR, -1);

        System.out.println(simple(cal.getTime()));*/
        /*System.out.println(simple(new Date()));
        System.out.println(simple(getDiffSeconds(new Date(), 10)));*/

        /* Date test = getDiffDay(new Date(), 1);

         System.out.println(simple(test));*/
        //       Date date = toDate("2014-1-1");
        //    System.out.println(getYear(date)+"-"+(getMonth(date)+1)+"-"+getDayOfMonth(date));

    }


    public static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String resultStr = "刚刚";

        if (date != null) {
            long time = date.getTime();
            long currentTime = System.currentTimeMillis();

            long day = (currentTime - time) / (1000 * 60 * 60 * 24);
            long hour = (currentTime - time) / (1000 * 60 * 60);
            long min = (currentTime - time) / (1000 * 60);
            long second = (currentTime - time) / (1000);

            if (day >= 7) {
                resultStr = simpleDateFormat.format(date);
            } else if (day > 0) {
                resultStr = String.valueOf(day + "天前");
            } else if (hour > 0) {
                resultStr = String.valueOf(hour + "小时前");
            } else if (min > 0) {
                resultStr = String.valueOf(min + "分前");
            } else if (second > 0) {
                resultStr = String.valueOf(second + "秒前");
            }
        }
        return resultStr;
    }

}
