package com.webrookie.weathernoticeapp.models.utils;

import cn.hutool.core.date.ChineseDate;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author WebRookie
 * @date 2023/6/9 21:22
 **/
class DateUtilsTest {
    /**
     * 获取今天离目标的距离天数
     * @param year 目标年
     * @param month  目标月
     * @param day 目标天
     * @return 天数
     */
    public static String passDay(int year,int month,int day){
        LocalDate start = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        return passDay(start,now);
    }

    /**
     *
     * @param chineseYear 农历年
     * @param chineseMonth 农历月
     * @param chineseDay 农历日
     * @return 距离今天的天数
     */
    public static String passDayChinese(int chineseYear,int chineseMonth,int chineseDay){
        ChineseDate chineseDate = new ChineseDate(chineseYear,chineseMonth,chineseDay);
        return passDayChinese(chineseDate);
    }

    public static String passDayChinese(ChineseDate chineseDate){
        int gregorianYear = chineseDate.getGregorianYear();
        int gregorianMonthBase1 = chineseDate.getGregorianMonthBase1();
        int gregorianDay = chineseDate.getGregorianDay();
        LocalDate start = LocalDate.of(gregorianYear, gregorianMonthBase1, gregorianDay);
        LocalDate now = LocalDate.now();
        return passDay(start,now);
    }

    public static String passDayOfNow(LocalDate source){
        int year = source.getYear();
        int month = source.getMonthValue();
        int day = source.getDayOfMonth();
        LocalDate start = LocalDate.of(year, month, day);
        LocalDate now = LocalDate.now();
        return passDay(start,now);
    }

    /**
     *  获取资源离目标的距离天数
     * @param source 资源日期
     * @param target 目标容器
     * @return 天数
     */
    public static String passDay(LocalDate source,LocalDate target){
        return source.toEpochDay() - target.toEpochDay() + "";
    }


    /**
     *
     * 获取距离阳历生日的天数
     * @param month 阳历生日的月份
     * @param day 阳历生日的日期
     * @return 天数
     */
    public static String getNextBirthDay(int month,int day){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int monthValue = now.getMonthValue();
        int dayOfMonth = now.getDayOfMonth();

        //生日已经过去
        if(monthValue > month || (monthValue == month && dayOfMonth > day)){
            year++;
        }
        return passDay(LocalDate.of(year, month, day),now);
    }

    public static String getNextBirthDay(LocalDate birthday){
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        return getNextBirthDay(month,day);
    }

    public static String getNextChineseBirthDay(LocalDate birthday){
        int month = birthday.getMonthValue();
        int day = birthday.getDayOfMonth();
        return getNextChineseBirthDay(month,day);
    }

    /**
     * 获取距离农历生日的天数
     * @param chineseMonth 农历生日的月份
     * @param chineseDay 农历生日的日期
     * @return 天数
     */
    public static String getNextChineseBirthDay(int chineseMonth,int chineseDay){
        //现在时间
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        //非闰月生日时间
        ChineseDate chineseBirthDayNoLeap = new ChineseDate(year,chineseMonth,chineseDay,false);
        int greMonth = chineseBirthDayNoLeap.getGregorianMonthBase1();
        int greDay = chineseBirthDayNoLeap.getGregorianDay();
        int gregorianYear = chineseBirthDayNoLeap.getGregorianYear();

        LocalDate birthDayOfNoLeap = LocalDate.of(gregorianYear, greMonth, greDay);

        //如果非闰月没有过去
        if(birthDayOfNoLeap.isAfter(now)){
            return passDayOfNow(birthDayOfNoLeap);
        }

        //闰月生日
        ChineseDate chineseBirthDayLeap = new ChineseDate(year,chineseMonth,chineseDay);
        int greMonthLeap = chineseBirthDayLeap.getGregorianMonthBase1();
        int greDayLeap = chineseBirthDayLeap.getGregorianDay();
        int gregorianYearLeap = chineseBirthDayLeap.getGregorianYear();
        LocalDate birthDayOfLeap = LocalDate.of(gregorianYearLeap, greMonthLeap, greDayLeap);

        //如果闰月没有过去
        if(birthDayOfLeap.isAfter(now)){
            return passDayOfNow(birthDayOfLeap);
        }


        //生日已经过去
        ChineseDate chineseBirthdayNext = new ChineseDate(++year,chineseMonth,chineseDay,false);
        return passDayChinese(chineseBirthdayNext);

    }

    @Test
    void getNextChineseBirthDay() {
        int chineseMonth = 5;
        int chineseDay = 20;
        //现在时间
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        //非闰月生日时间
        ChineseDate chineseBirthDayNoLeap = new ChineseDate(year,chineseMonth,chineseDay,false);
        int greMonth = chineseBirthDayNoLeap.getGregorianMonthBase1();
        int greDay = chineseBirthDayNoLeap.getGregorianDay();
        int gregorianYear = chineseBirthDayNoLeap.getGregorianYear();

        LocalDate birthDayOfNoLeap = LocalDate.of(gregorianYear, greMonth, greDay);

        //如果非闰月没有过去
        if(birthDayOfNoLeap.isAfter(now)){
            System.out.println(passDayOfNow(birthDayOfNoLeap));
        }

        //闰月生日
        ChineseDate chineseBirthDayLeap = new ChineseDate(year,chineseMonth,chineseDay);
        int greMonthLeap = chineseBirthDayLeap.getGregorianMonthBase1();
        int greDayLeap = chineseBirthDayLeap.getGregorianDay();
        int gregorianYearLeap = chineseBirthDayLeap.getGregorianYear();
        LocalDate birthDayOfLeap = LocalDate.of(gregorianYearLeap, greMonthLeap, greDayLeap);

        //如果闰月没有过去
        if(birthDayOfLeap.isAfter(now)){
            System.out.println(passDayOfNow(birthDayOfLeap));
        }


        //生日已经过去
        ChineseDate chineseBirthdayNext = new ChineseDate(++year,chineseMonth,chineseDay,false);
        System.out.println(passDayChinese(chineseBirthdayNext));
    }
}