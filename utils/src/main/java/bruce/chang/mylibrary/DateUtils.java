package bruce.chang.mylibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by: BruceChang
 * Date on : 2017/4/10.
 * Time on: 17:12
 * Progect_Name:MyCommonUtils
 * Source Github：
 * Description:日期工具类
 */

public class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");

    /**
     * 格式化日期时间
     *
     * @param date
     * @return 年月日时分秒yyyy-MM-dd HH:mm:ss
     */
    public static String formatDataTime_1(long date) {
        return DATE_FORMAT_DATETIME.format(new Date(date));
    }

    /**
     * 格式化日期时间
     *
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String formatDataTime_2(Date date) {
        return DATE_FORMAT_DATETIME.format(date);
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String formatDate(long date) {
        return DATE_FORMAT_DATE.format(new Date(date));
    }

    /**
     * 格式化日期
     *
     * @param date
     * @return yyyy-MM-dd
     */
    public static String formatDate(Date date) {
        return DATE_FORMAT_DATE.format(date);
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String formatTime_1(long date) {
        return DATE_FORMAT_TIME.format(new Date(date));
    }

    /**
     * 格式化时间
     *
     * @param date
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String formatTime_2(Date date) {
        return DATE_FORMAT_TIME.format(date);
    }


    /**
     * 自定义格式的格式化日期时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date formatDateCustom_2(String date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 自定义格式的格式化日期时间
     *
     * @param beginDate
     * @param format
     * @return
     */
    public static String formatDateCustom_1(Date beginDate, String format) {
        String result = "";
        if (beginDate != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(beginDate);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 将时间字符串转换成Date
     *
     * @param s
     * @param style
     * @return
     */
    public static Date string2Date(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 6) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取系统时间
     *
     * @return
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * 获取系统日期
     *
     * @return
     */
    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
    }

    /**
     * 获取系统日期时间
     *
     * @return
     */
    public static String getDateTime_1() {
        return DATE_FORMAT_DATETIME.format(System.currentTimeMillis());
    }

    /**
     * 获取系统日期时间
     *
     * @param format
     * @return
     */
    public static String getDateTime_2(String format) {
        return new SimpleDateFormat(format).format(System.currentTimeMillis());
    }


    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate_1(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 得到几天后的时间
     *
     * @param d
     * @param day
     * @return
     */
    public static Date addDate_2(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param dateStart Date 日期
     * @param dateEnd   Date 日期
     * @return 返回相减后的日期
     */
    public static long subtractDate_1(Date dateStart, Date dateEnd) {
        return dateEnd.getTime() - dateStart.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param dateStart Date 日期
     * @param dateEnd   Date 日期
     * @return 返回相减后的日期
     */
    public static int subtractDate_2(Date dateStart, Date dateEnd) {
        return (int) ((getMillis(dateStart) - getMillis(dateEnd)) / (24 * 3600 * 1000));
    }


    /**
     * 获取某天是星期几
     *
     * @param date
     * @return
     */
    public static String getMonthDayWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);    //获取年
        int month = c.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
        int day = c.get(Calendar.DAY_OF_MONTH);    //获取当前天数
        int week = c.get(Calendar.DAY_OF_WEEK);

        String weekStr = null;

        switch (week) {

            case Calendar.SUNDAY:
                weekStr = "周日";
                break;

            case Calendar.MONDAY:
                weekStr = "周一";
                break;

            case Calendar.TUESDAY:
                weekStr = "周二";
                break;

            case Calendar.WEDNESDAY:
                weekStr = "周三";
                break;

            case Calendar.THURSDAY:
                weekStr = "周四";
                break;

            case Calendar.FRIDAY:
                weekStr = "周五";
                break;

            case Calendar.SATURDAY:
                weekStr = "周六";
                break;
        }

        return month + "月" + day + "日" + "(" + weekStr + ")";
    }


    /**
     * 获得口头时间字符串，如今天，昨天等
     *
     * @param d 时间格式为yyyy-MM-dd HH:mm:ss
     * @return 口头时间字符串
     */
    public static String getTimeInterval(String d) {
        Date date = formatDateCustom_2(d, "yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowYear = now.get(Calendar.YEAR);
        int nowMonth = now.get(Calendar.MONTH);
        int nowWeek = now.get(Calendar.WEEK_OF_MONTH);
        int nowDay = now.get(Calendar.DAY_OF_WEEK);
        int nowHour = now.get(Calendar.HOUR_OF_DAY);
        int nowMinute = now.get(Calendar.MINUTE);

        Calendar ca = Calendar.getInstance();
        if (date != null)
            ca.setTime(date);
        else
            ca.setTime(new Date());
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int week = ca.get(Calendar.WEEK_OF_MONTH);
        int day = ca.get(Calendar.DAY_OF_WEEK);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        int minute = ca.get(Calendar.MINUTE);
        if (year != nowYear) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //不同年份
            return sdf.format(date);
        } else {
            if (month != nowMonth) {
                //不同月份
                SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                return sdf.format(date);
            } else {
                if (week != nowWeek) {
                    //不同周
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else if (day != nowDay) {
                    if (day + 1 == nowDay) {
                        return "昨天" + formatDateCustom_1(date, "HH:mm");
                    }
                    if (day + 2 == nowDay) {
                        return "前天" + formatDateCustom_1(date, "HH:mm");
                    }
                    //不同天
                    SimpleDateFormat sdf = new SimpleDateFormat("M月dd日");
                    return sdf.format(date);
                } else {
                    //同一天
                    int hourGap = nowHour - hour;
                    if (hourGap == 0)//1小时内
                    {
                        if (nowMinute - minute < 1) {
                            return "刚刚";
                        } else {
                            return (nowMinute - minute) + "分钟前";
                        }
                    } else if (hourGap >= 1 && hourGap <= 12) {
                        return hourGap + "小时前";
                    } else {
                        SimpleDateFormat sdf = new SimpleDateFormat("今天 HH:mm");
                        return sdf.format(date);
                    }
                }
            }
        }
    }

    /**
     * 获取当前时间为本月的第几周
     *
     * @return
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    /**
     * 获取当前时间为本周的第几天
     *
     * @return
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }
}
