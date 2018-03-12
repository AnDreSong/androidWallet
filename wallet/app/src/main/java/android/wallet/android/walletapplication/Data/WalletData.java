package android.wallet.android.walletapplication.Data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Andre
 * @date 2018/3/5  8:44
 * @className WalletData
 */

public class WalletData {

    private int todayRandomMoney;
    private  int dayInyear;
    private Date todayTime;
    public int getDayInyear()
    {
        Date date=getTodayTime();
        Calendar ca=Calendar.getInstance();
        ca.setTime(date);
        dayInyear=ca.get(Calendar.DAY_OF_YEAR);

        return dayInyear;


    }
    public int getTodayRandomMoney() {
        return todayRandomMoney;
    }
    public void setTodayRandomMoney(int todayRandomMoney)
    {
        this.todayRandomMoney = todayRandomMoney;
    }
    public Date getTodayTime() {
        return todayTime;
    }

    public void setTodayTime(Date todayTime) {
        this.todayTime = todayTime;
    }




}
