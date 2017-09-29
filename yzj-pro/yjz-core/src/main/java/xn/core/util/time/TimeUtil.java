package xn.core.util.time;

import static java.util.Calendar.DATE;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import xn.core.util.data.StringUtil;

/**
 * @Description: 时间工具类
 * @author Zhangjc
 * @date 2016年4月15日 下午3:52:04
 */
public final class TimeUtil {

    private static final int[] MONTH_DAYS = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final ThreadLocal<Map<String, SimpleDateFormat>> sdfMap = new ThreadLocal<Map<String, SimpleDateFormat>>() {
        @Override
        protected Map<String, SimpleDateFormat> initialValue() {
            return new ConcurrentHashMap<>();
        };
    };

    private static final ThreadLocal<Calendar> calendarLocal = new ThreadLocal<Calendar>() {
        @Override
        protected Calendar initialValue() {
            return Calendar.getInstance();
        };
    };

    private static final String[] FORMAT_LEN = {
            // 0-7
            null, null, null, null, "yyyy", null, "yyyyMM", "yyyy-MM",
            // 8-15
            "yyyyMMdd", null, "yyyy-MM-dd", null, null, "yyyy-MM-dd HH", "yyyyMMddHHmmss", "yyyyMMddHHmmssS",
            // 16-21
            "yyyy-MM-dd HH:mm", null, null, "yyyy-MM-dd HH:mm:ss", null, "yyyy-MM-dd HH:mm:ss.S" };

    public static final String getSysDate(String format) {
        return format(new Date(currentTimeMillis()), format);
    }

    public static final Timestamp getSysTimestamp() {
        return new Timestamp(currentTimeMillis());
    }

    public static final Calendar getSysCalendar() {
        Calendar cal = calendarLocal.get();
        cal.setTimeInMillis(currentTimeMillis());
        return cal;
    }

    public static final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public static final int getDayOfMonth() {
        return getSysCalendar().get(Calendar.DAY_OF_MONTH);
    }

    public static final int getYYYY() {
        return getSysCalendar().get(Calendar.YEAR);
    }
    
    public static final int getMM() {
        return getSysCalendar().get(Calendar.MONTH) + 1;
    }

    public static final int getYYYYMM() {
        Calendar cal = getSysCalendar();
        return cal.get(Calendar.YEAR) * 100 + cal.get(Calendar.MONTH) + 1;
    }

    public static final int getYYYYMMDD() {
        Calendar cal = getSysCalendar();
        return cal.get(Calendar.YEAR) * 10000 + (cal.get(Calendar.MONTH) + 1) * 100 + cal.get(Calendar.DAY_OF_MONTH);
    }

    public static final long getYYYYMMDDHHMMSS() {
        Calendar cal = getSysCalendar();
        return cal.get(Calendar.YEAR) * 10000000000L + (cal.get(Calendar.MONTH) + 1) * 100000000L + cal.get(Calendar.DAY_OF_MONTH) * 1000000L + cal
                .get(Calendar.HOUR_OF_DAY) * 10000L + cal.get(Calendar.MINUTE) * 100L + cal.get(Calendar.SECOND);
    }

    public static final String getSysDate() {
        return getSysDate(DATE_FORMAT);
    }

    protected static final String getSysTime() {
        return getSysDate(TIME_FORMAT);
    }

    public static final String getTimestampFormat(String value) throws Exception {
        String format = FORMAT_LEN[value.length()];
        if (format == null)
            throw new Exception("无法解析正确的日期格式[" + value + "]");
        return format;
    }

    public static final java.sql.Date parseToSqlDate(Date time) throws Exception {

        if (time == null)
            return null;
        return new java.sql.Date(time.getTime());
    }

    public static final java.sql.Date parseToSqlDate(Date time, String format) throws Exception {
        if (time == null)
            return null;
        if (StringUtil.isBlank(format)) {
            format = TimeUtil.DATE_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String timeStr = sdf.format(time.getTime());
        return new java.sql.Date(sdf.parse(timeStr).getTime());
    }

    public static final Date parseDate(String time) throws Exception {

        return parseDate(time, null);
    }

    public static final Date parseDate(String time, String format) throws Exception {

        if (time == null)
            return null;
        if (StringUtil.isBlank(format)) {
            format = TimeUtil.DATE_FORMAT;
        }
        DateFormat sdf = getSimpleDateFormat(format);
        return new Date(sdf.parse(time).getTime());
    }

    public static final Date parseDate(Date time) throws Exception {
        if (time == null)
            return null;

        DateFormat sdf = new SimpleDateFormat();
        String timeStr = sdf.format(time);
        return new Date(sdf.parse(timeStr).getTime());
    }

    public static final Date parseDate(Date time, String format) throws Exception {
        if (time == null)
            return null;
        if (StringUtil.isBlank(format)) {
            format = TimeUtil.DATE_FORMAT;
        }
        DateFormat sdf = getSimpleDateFormat(format);
        String timeStr = sdf.format(time);
        return new Date(sdf.parse(timeStr).getTime());
    }

    public static final Timestamp parse(String timeStr) throws Exception {
        return parse(timeStr, null);
    }

    public static final Timestamp parse(String timeStr, String format) throws Exception {
        if (StringUtil.isBlank(timeStr))
            return null;
        if (StringUtil.isBlank(format)) {
            format = getTimestampFormat(timeStr);
        }
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        try {
            Date date = sdf.parse(timeStr);
            return new Timestamp(date.getTime());
        }
        catch (ParseException e) {
            throw e;
        }
    }

    public static final String format(Date time) {
        return format(time, TIME_FORMAT);
    }

    public static final String format(Date time, String format) {
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        return sdf.format(time);
    }

    public static final String format(String time, String format) throws Exception {
        if (StringUtil.isBlank(time))
            return time;
        if (time != null && format != null && time.length() == format.length())
            return time;
        return format(parse(time), format);
    }

    public static final SimpleDateFormat getSimpleDateFormat(final String format) {
        return new SimpleDateFormat(format);
    }

    public static final int daysBetween(String dateStr1, String dateStr2) throws Exception {
        Date d1 = parse(dateStr1);
        Date d2 = parse(dateStr2);
        return daysBetween(d1, d2);
    }

    public static final int daysBetween(Date date1, Date date2) {
        return (int) compareDate(date1, date2, DATE);
    }

    public static final int daysBetween(Calendar cal1, Calendar cal2) {
        return (int) compareCalendar(cal1, cal2, DATE);
    }

    public static long compareDate(String date1, String date2, int field) throws Exception {
        return compareDate(parse(date1), parse(date2), field);
    }

    public static long compareDate(Date date1, Date date2, int field) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date1);

        Calendar c2 = Calendar.getInstance();
        c2.setTime(date2);

        return compareCalendar(c1, c2, field);
    }

    public static long compareCalendar(Calendar c1, Calendar c2, int field) {
        long t1 = c1.getTimeInMillis();
        long t2 = c2.getTimeInMillis();

        switch (field) {
        case SECOND:
            return t1 / 1000 - t2 / 1000;
        case MINUTE:
            return t1 / 60000 - t2 / 60000;
        case HOUR:
            return t1 / 3600000 - t2 / 3600000;
        case DATE:
            int rawOffset = c1.getTimeZone().getRawOffset();
            return (t1 + rawOffset) / 86400000 - (t2 + rawOffset) / 86400000;
        case MONTH:
            return c1.get(YEAR) * 12 - c2.get(YEAR) * 12 + c1.get(MONTH) - c2.get(MONTH);
        case YEAR:
            return c1.get(YEAR) - c2.get(YEAR);
        default:
            return t1 - t2;
        }
    }

    /**
     * @Description: 获取指定日期years年后的日期 日期格式要求为: yyyy-MM-dd
     * @author Zhangjc
     * @param date
     * @param years
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddYear(String date, int years) throws Exception {
        return dateAddAmount(date, YEAR, years);
    }

    /**
     * @Description: 获取指定日期months月后的日期 日期格式要求为: yyyy-MM-dd
     * @author Zhangjc
     * @param date
     * @param months
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddMonth(String date, int months) throws Exception {
        return dateAddAmount(date, MONTH, months);
    }

    /**
     * @Description: 获取指定日期days天后的日期 日期格式要求为: yyyy-MM-dd
     * @author Zhangjc
     * @param date
     * @param days
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddDay(String date, int days) throws Exception {
        return dateAddAmount(date, DATE, days);
    }

    /**
     * @Description: 获取指定时间hours小时后的日期 日期格式要求为: yyyy-MM-dd hh:mm:ss
     * @author Zhangjc
     * @param date
     * @param hours
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddHour(String date, int hours) throws Exception {
        return dateAddAmount(date, HOUR, hours);
    }

    /**
     * @Description: 获取指定时间minutes分钟后的日期 日期格式要求为: yyyy-MM-dd hh:mm:ss
     * @author Zhangjc
     * @param date
     * @param minutes
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddMinute(String date, int minutes) throws Exception {
        return dateAddAmount(date, MINUTE, minutes);
    }

    /**
     * @Description: 获取指定时间seconds秒后的日期 日期格式要求为: yyyy-MM-dd hh:mm:ss
     * @author Zhangjc
     * @param date
     * @param seconds
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddSecond(String date, int seconds) throws Exception {
        return dateAddAmount(date, SECOND, seconds);
    }

    /**
     * @Description: 日期字符串
     * @author Zhangjc
     * @param dateStr
     * @param field
     * @param amount
     * @return
     * @throws Exception
     * @
     */
    public static final String dateAddAmount(String dateStr, int field, int amount) throws Exception {
        return dateAddAmount(dateStr, field, amount, getTimestampFormat(dateStr));
    }

    public static final String dateAddAmount(String dateStr, int field, int amount, String format) throws Exception {
        String inFormat = getTimestampFormat(dateStr);// 根据传入的日期字符串，得到其日期格式
        Date date = parse(dateStr, inFormat);
        Date retDate = dateAddAmount(date, field, amount);
        return format(retDate, format);
    }

    /**
     * @Description: 获取指定日期months月后的日期
     * @author Zhangjc
     * @param date
     * @param months
     * @return
     */
    public static final Date dateAddMonth(Date date, int months) {
        return dateAddAmount(date, MONTH, months);
    }

    /**
     * @Description: 获取指定日期days天后的日期
     * @author Zhangjc
     * @param date
     * @param days
     * @return
     */
    public static final Date dateAddDay(Date date, int days) {
        return dateAddAmount(date, DATE, days);
    }

    /**
     * @Description: 获取指定日期hours小时后的日期
     * @author Zhangjc
     * @param date
     * @param hours
     * @return
     */
    public static final Date dateAddHour(Date date, int hours) {
        return dateAddAmount(date, HOUR, hours);
    }

    /**
     * @Description: 获取指定日期minutes分钟后的日期
     * @author Zhangjc
     * @param date
     * @param minutes
     * @return
     */
    public static final Date dateAddMinute(Date date, int minutes) {
        return dateAddAmount(date, MINUTE, minutes);
    }

    /**
     * @Description: 获取指定时间seconds秒后的日期
     * @author Zhangjc
     * @param date
     * @param seconds
     * @return
     * @
     */
    public static final Date dateAddSecond(Date date, int seconds) {
        return dateAddAmount(date, SECOND, seconds);
    }

    /**
     * @Description: 获取指定日期指定的类型之后的日期
     * @author Zhangjc
     * @param date
     * @param field
     * @param amount
     * @return
     */
    public static final Date dateAddAmount(Date date, int field, int amount) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        cd.add(field, amount);
        return cd.getTime();
    }

    /**
     * @Description: 获取系统最大日期1900-01-01 00:00:00
     * @author Zhangjc
     * @return
     */
    public static final String getTheFirstDateTime() {
        return "1900-01-01 00:00:00";
    }

    /**
     * @Description: 获取系统最大日期2050-12-31 23:59:59
     * @author Zhangjc
     * @return
     */
    public static final String getTheLastDateTime() {
        return "2050-12-31 23:59:59";
    }

    /**
     * @Description: 获取系统最大月份205012
     * @author Zhangjc
     * @return
     */
    public static final String getTheLastDate205012() {
        return "205012";
    }

    /**
     * @Description: 获取系统最大日期20501231
     * @author Zhangjc
     * @return
     */
    public static final String getTheLastDate20501231() {
        return "20501231";
    }

    /**
     * @Description: 获取系统最大日期2050-12-31
     * @author Zhangjc
     * @return
     */
    public static final String getTheLastDate() {
        return "2050-12-31";
    }

    /**
     * @Description: 获取每天最大时间 23:59:59
     * @author Zhangjc
     * @return
     */
    public static final String getTheLastTime235959() {
        return " 23:59:59";
    }

    /**
     * @Description: 获取每天的初始时间 00:00:00
     * @author Zhangjc
     * @return
     */
    public static final String getTheFirstTime000000() {
        return " 00:00:00";
    }

    /**
     * @Description: 获取某个日期当月的最后一天的日期
     * @author Zhangjc
     * @param dateStr
     * @return
     */
    public static final String getLastDateOfMonth(String dateStr) {
        return getTheDateOfMonth(dateStr, 31);
    }

    /**
     * @Description: 获取某个日期当月的第一天的日期
     * @author Zhangjc
     * @param dateStr
     * @return
     */
    public static final String getFirstDateOfMonth(String dateStr) {
        return getTheDateOfMonth(dateStr, 1);
    }

    /**
     * @Description: 获取某个日期当月的最后一天的日期
     * @author Zhangjc
     * @param dateStr
     * @param day
     * @return
     */
    private static final String getTheDateOfMonth(String dateStr, int day) {
        int yyyy = Integer.parseInt(dateStr.substring(0, 4));

        boolean hasSep = dateStr.indexOf('-') > 0;
        int mmIndex = hasSep ? 5 : 4;
        int mm = Integer.parseInt(dateStr.substring(mmIndex, mmIndex + 2));

        int maxDay = getLastDay(yyyy * 100 + mm);
        if (day > maxDay)
            day = maxDay;
        return yyyy + (hasSep ? "-" : "") + (mm > 9 ? mm : "0" + mm) + (hasSep ? '-' : "") + (day > 9 ? day : "0" + day);
    }

    /**
     * @Description: 获取某月的天数
     * @author Zhangjc
     * @param yyyyMM
     * @return
     */
    public static final int getLastDay(int yyyyMM) {
        int yyyy = yyyyMM / 100;
        int mm = yyyyMM % 100;
        if (mm != 2)
            return MONTH_DAYS[mm - 1];
        if (yyyy % 400 == 0 || (yyyy % 100 != 0 && yyyy % 4 == 0))
            return 29;
        return 28;
    }

    /**
     * @Description:得到两个日期之间相差的月数
     * @author Zhangjc
     * @param yyyymm1
     * @param yyyymm2
     * @return
     */
    public static int diffMonths(int yyyymm1, int yyyymm2) {
        return (yyyymm1 / 100 - yyyymm2 / 100) * 12 + (yyyymm1 % 100 - yyyymm2 % 100);
    }

    /**
     * @Description: 当前年第一天
     * @author zhangjs
     * @return
     */
    public static String getCurrYearFirst() {
        return getYearFirst(getSysCalendar().get(Calendar.YEAR), DATE_FORMAT);
    }

    /**
     * @Description: 取年的第一天
     * @author zhangjs
     * @param year
     * @param format
     * @return
     */
    public static String getYearFirst(int year, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        Date currYearFirst = calendar.getTime();
        return format(currYearFirst, format);
    }

    /**
     * @Description: 取年的第一天
     * @author zhangjs
     * @param date
     * @return
     * @throws Exception
     */
    public static String getYearFirst(String date) throws Exception {
        return getYearFirst(date, DATE_FORMAT);
    }

    /**
     * @Description: 取年的第一天
     * @author zhangjs
     * @param date
     * @param format
     * @return
     * @throws Exception
     */
    public static String getYearFirst(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(TimeUtil.parseDate(date));
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }

    /**
     * 获取某年最后一天日期
     * 
     * @param year 年份
     * @return Date
     */
    public static String getYearLast(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarDate = Calendar.getInstance();
        calendar.clear();
        calendarDate.setTime(TimeUtil.parseDate(date));
        calendar.set(Calendar.YEAR, calendarDate.get(YEAR));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        Date dateTime = calendar.getTime();

        return format(dateTime, format);
    }

    /**
     * 取得当前日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static String getFirstDayOfWeek(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(TimeUtil.parseDate(date));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }

    /**
     * 取得当前日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static String getLastDayOfWeek(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.setTime(TimeUtil.parseDate(date));
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }

    public static String getFirstDayOfQuarter(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(TimeUtil.parseDate(date));
        int curMonth = calendar.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            calendar.set(Calendar.MONTH, Calendar.APRIL);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, Calendar.JULY);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }

    public static String getLastDayOfQuarter(String date, String format) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(TimeUtil.parseDate(date));
        int curMonth = calendar.get(Calendar.MONTH);
        if (curMonth >= Calendar.JANUARY && curMonth <= Calendar.MARCH) {
            calendar.set(Calendar.MONTH, Calendar.MARCH);
        }
        if (curMonth >= Calendar.APRIL && curMonth <= Calendar.JUNE) {
            calendar.set(Calendar.MONTH, Calendar.JUNE);
        }
        if (curMonth >= Calendar.JULY && curMonth <= Calendar.SEPTEMBER) {
            calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        }
        if (curMonth >= Calendar.OCTOBER && curMonth <= Calendar.DECEMBER) {
            calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date dateTime = calendar.getTime();
        return format(dateTime, format);

        
    }

    /**
     * @Description: 根据日期获取月份
     * @author yangy
     * @param Date
     * @return
     */
    public static final int getMonthByDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    /**
     * @Description: 根据日期获取上个月份最后一天
     * @author yangy
     * @param Date
     * @return
     */
    public static final Date getLastMothByCurrent(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        Date dateTime = calendar.getTime();
        return dateTime;
    }

    /**
     * @Description: 两个日期相减获取天数
     * @author yangy
     * @param Date
     * @return
     */
    public static final int getday(Date date1, Date date2) {

        return (int) ((date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * @Description: 根据日期获取上个月的这天
     * @author Administrator
     * @param args
     */
    public static final Date getLastMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();
    }

    /**
     * @Description: 根据日期获取上一年的这天
     * @author Administrator
     * @param args
     */
    public static final Date getLastYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * @Description: 根据日期获取上一周的这天
     * @author Administrator
     * @param args
     */
    public static final Date getLastWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        return calendar.getTime();
    }
    
    /**
     * @Description: 根据日期获取上个月份25号日期
     * @author yangy
     * @param Date
     * @return
     */
    public static final String getLastMoth25(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        calendar.set(Calendar.DATE,26);
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }
    
    /**
     * @Description: 根据日期获取上个月份25号日期
     * @author yangy
     * @param Date
     * @return
     */
    public static final String getCurrentMoth26(Date date, String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 0);
        calendar.add(Calendar.DATE, 25);
        Date dateTime = calendar.getTime();
        return format(dateTime, format);
    }
}
