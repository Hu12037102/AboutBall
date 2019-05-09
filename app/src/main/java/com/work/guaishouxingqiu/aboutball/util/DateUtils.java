package com.work.guaishouxingqiu.aboutball.util;

import android.support.v7.widget.CardView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 作者: 胡庆岭
 * 创建时间: 2019/4/17 15:15
 * 更新时间: 2019/4/17 15:15
 * 描述:时间相关的工具类
 */
public class DateUtils {
    /**
     * 根据当前时间返回今天、明天、后天、星期几
     *
     * @param date 时间 yyyy-MM-dd
     * @return
     */
    public static String getWeek(String date) {
        String resultWeek = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date time = simpleDateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(time);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.clear();
            calendar.setTime(new Date(System.currentTimeMillis()));
            int newYear = calendar.get(Calendar.YEAR);
            int newMonth = calendar.get(Calendar.MONTH);
            int newDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (year == newYear && month == newMonth) {
                switch (day - newDay) {
                    case 0:
                        resultWeek = "今天";
                        break;
                    case 1:
                        resultWeek = "明天";
                        break;
                    case 2:
                        resultWeek = "后天";
                        break;
                    default:
                        resultWeek = getMonthDayToWeek(week);
                        break;
                }
            } else {
                resultWeek = getMonthDayToWeek(week);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return resultWeek;
        }
        LogUtils.w("getWeek--", resultWeek);
        return resultWeek;
    }

    private static String getMonthDayToWeek(int day) {
        String resultWeek = "";
        switch (day) {
            case 1:
                resultWeek = "星期日";
                break;
            case 2:
                resultWeek = "星期一";
                break;
            case 3:
                resultWeek = "星期二";
                break;
            case 4:
                resultWeek = "星期三";
                break;
            case 5:
                resultWeek = "星期四";
                break;
            case 6:
                resultWeek = "星期五";
                break;
            case 7:
                resultWeek = "星期六";
                break;
            default:
                break;
        }
        return resultWeek;
    }

    /**
     * 将时分秒拆分成时分
     *
     * @param time
     * @return
     */
    public static String getHourMinute(String time) {
        String[] timeArray = time.split(":");
        if (timeArray.length == 3) {
            return timeArray[0] + ":" + timeArray[1];
        }
        return null;
    }

    /**
     * 根据年月日时分秒获取下一天的时间
     *
     * @param time yyyy-MM-dd hh:mm:ss
     * @return 下一天的时间
     */
    public static String getNextDayTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String newTime = "";
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, day + 1);
            newTime = sdf.format(calendar.getTime());
            LogUtils.w("getNextDayTime--", sdf.format(calendar.getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newTime;
    }

    /**
     * 根据年月日时分秒，显示今天明天后天+日期
     *
     * @param time yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String getDate(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTime = "";
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.clear();
            calendar.setTime(new Date(System.currentTimeMillis()));
            int newYear = calendar.get(Calendar.YEAR);
            int newMonth = calendar.get(Calendar.MONTH);
            int newDay = calendar.get(Calendar.DAY_OF_MONTH);
            if (newYear == year && newMonth == month) {
                switch (day - newDay) {
                    case 0:
                        dateTime = dateTime.concat("今天 ").concat(getMonthAndDay(month, day));
                        break;
                    case 1:
                        dateTime = dateTime.concat("明天 ").concat(getMonthAndDay(month, day));
                        break;
                    case 2:
                        dateTime = dateTime.concat("后天 ").concat(getMonthAndDay(month, day));
                        break;
                    case -1:
                        dateTime = dateTime.concat("昨天 ").concat(getMonthAndDay(month, day));
                        break;
                    case -2:
                        dateTime = dateTime.concat("前天 ").concat(getMonthAndDay(month, day));
                        break;
                    default:
                        dateTime = getMonthAndDay(month, day);
                        break;
                }
            } else {
                dateTime = getMonthAndDay(month, day);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }


    public static String getMonthAndDay(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTime = "";
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            dateTime = year + "-" + (month + 1) + "-" + day;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String getMonthAndDay(int month, int day) {
        return (month + 1) + "月" + day + "日";
    }

    /**
     * 根据年月日时分秒显示24小时时间
     *
     * @param time yyyy-MM-dd hh:mm:ss
     * @return
     */
    public static String getHourMinutes(String time) {
        String resultHour = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            resultHour = hour + ":" + minute;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultHour;
    }
}
