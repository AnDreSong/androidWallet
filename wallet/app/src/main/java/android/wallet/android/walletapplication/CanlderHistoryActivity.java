package android.wallet.android.walletapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.wallet.android.walletapplication.Coom.JsonUtil;
import android.wallet.android.walletapplication.Coom.ReadUtil;
import android.wallet.android.walletapplication.Data.WalletData;
import android.wallet.android.walletapplication.Data.WalletDataList;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class CanlderHistoryActivity extends AppCompatActivity {
    CalendarView calendarView;
    TextView txtShowInfo;
    WalletDataList WalletDatas;
    String fileName="WalletDataList";
    Calendar c=Calendar.getInstance();
    Calendar cmin=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canlder_history);
        getSupportActionBar().hide();
        fileName+=c.get(Calendar.YEAR);
        InitControl();
        readList();
        cmin.set(c.get(Calendar.YEAR),0,1);
        calendarView.setMaxDate(c.getTimeInMillis());
        calendarView.setMinDate(cmin.getTimeInMillis());
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth)
            {
               if (WalletDatas!=null&&WalletDatas.getDataList().size()>0)
               {
                   boolean isFinddata=false;
                   for (WalletData data: WalletDatas.getDataList())
                   {
                       Date date=data.getTodayTime();
                       Calendar c=Calendar.getInstance();
                       c.setTime(date);
                       if(c.get(Calendar.YEAR)==year&&c.get(Calendar.MONTH)==month&&c.get(Calendar.DAY_OF_MONTH)==dayOfMonth)
                       {
                           isFinddata=true;
                           String shwoInfo=year+"年"+(month+1)+"月"+dayOfMonth+"日:"+data.getTodayRandomMoney()+"\r\n";
                        shwoInfo+=WalletDatas.getDataList().size()+"天"+"\r\n"+"合计"+WalletDatas.getTotal()+"元";
                           txtShowInfo.setText(shwoInfo);
                       }
                   }
                   if (!isFinddata)
                   {
                       txtShowInfo.setText("");
                   }
               }
            }
        });
    }
    private void  readList()
    {
        ReadUtil data=new ReadUtil(CanlderHistoryActivity.this);
        String historydata=data.ReadHistory(fileName);
        if (historydata!=null&&historydata.length()>0)
        {
            WalletDatas=(WalletDataList) JsonUtil.stringToObject(historydata,WalletDataList.class);
        }

    }
    private void  InitControl()
    {
        calendarView=findViewById(R.id.calendarView);
        txtShowInfo=findViewById(R.id.txt_showNowData);
    }
}
