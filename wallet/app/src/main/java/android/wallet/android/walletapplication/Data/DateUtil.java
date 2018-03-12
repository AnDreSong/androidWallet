package android.wallet.android.walletapplication.Data;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Andre
 * @date 2018/3/12  16:00
 * @className DateUtil
 */

public class DateUtil  {

    private  Date date;
    private Calendar calendar;
    public  DateUtil(Date datetime)
    {
        this.date=datetime;
        calendar=Calendar.getInstance();
        calendar.setTime(date);
    }

    public  Calendar getCalendar()
    {
        return calendar;
    }
    public int getYear() {
     return   calendar.get(Calendar.YEAR);
    }

    public void setYear(int year) {
      calendar.set(Calendar.YEAR,year);
    }

    public int getMonth() {
       return  (calendar.get(Calendar.MONTH)+1);
    }

    public void setMonth(int month) {

        calendar.set(Calendar.MONTH,month);
    }

    public int getDay() {
       return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public void setDay(int day) {
      calendar.set(Calendar.DAY_OF_MONTH,day);
    }

    public  Date Create(int year,int month,int day)
    {
        Calendar calendarmonth=Calendar.getInstance();
        calendarmonth.set(Calendar.YEAR,year);
        calendarmonth.set(Calendar.MONTH,month-1);
        calendarmonth.set(Calendar.DAY_OF_MONTH,day);
        return calendarmonth.getTime();
    }

}
