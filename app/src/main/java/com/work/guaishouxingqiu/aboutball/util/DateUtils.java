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
                        switch (week){
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
                        break;
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return resultWeek;
        }
        LogUtils.w("getWeek--",resultWeek);
        return resultWeek;
    }
}
