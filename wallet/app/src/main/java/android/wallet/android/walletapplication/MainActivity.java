package android.wallet.android.walletapplication;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.wallet.android.walletapplication.Coom.JsonUtil;
import android.wallet.android.walletapplication.Coom.ReadUtil;
import android.wallet.android.walletapplication.Coom.TimeThread;
import android.wallet.android.walletapplication.Data.DateUtil;
import android.wallet.android.walletapplication.Data.WalletData;
import android.wallet.android.walletapplication.Data.WalletDataList;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //private TextView txttime;
    //private TextView txt_total;
    //private TextView txt_random;
    //private Handler handlerMsg;
    CalendarView calendarView;
    TextView txtShownow;
    private Date date;
      String fileName="WalletDataList";
    SimpleDateFormat formatterinfo = new SimpleDateFormat ("yyyy-MM-dd");
    Calendar cmin=Calendar.getInstance();
    Calendar c=Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      // getSupportActionBar().hide();
         InitControls();
       // TimeThread  timeThread=new TimeThread(handlerMsg);
        //timeThread.start();
        date=new Date(System.currentTimeMillis());//获取当前时间
        c.setTime(date);
        cmin.set(c.get(Calendar.YEAR),0,1);
        fileName=fileName+c.get(Calendar.YEAR);
        GetRandomInfo();
        InitHistory();
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                if (dataList!=null&&dataList.getDataList().size()>0)
                {
                    boolean isFinddata=false;
                    for (WalletData data: dataList.getDataList())
                    {
                        Date date=data.getTodayTime();
                        Calendar c=Calendar.getInstance();
                        c.setTime(date);
                        if(c.get(Calendar.YEAR)==year&&c.get(Calendar.MONTH)==month&&c.get(Calendar.DAY_OF_MONTH)==dayOfMonth)
                        {

                            isFinddata=true;
                            DateUtil util=new DateUtil(data.getTodayTime());
                            String shwoInfo ="第"+data.getDayInyear()+"天"+util.getYear() + "年" + (util.getMonth()) + "月" + util.getDay() + "日:"+ data.getTodayRandomMoney() + "元"+"\r\n";
                            shwoInfo += "共"+dataList.getDataList().size() + "天" + "\r\n" + "合计" + dataList.getTotal() + "元";
                            txtShownow.setText(shwoInfo);
                        }
                    }
                    if (!isFinddata)
                    {
                        txtShownow.setText("");
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.item_history:
                Intent intent=new Intent(MainActivity.this,CanlderHistoryActivity.class);
                //Intent intent=new Intent(MainActivity.this,HistoryActivity.class);
                startActivity(intent);
                break;
            default:
                break;

        }
        return true;
    }

    WalletDataList dataList=null;
    private void InitControls()
    {
          //txttime=findViewById(R.id.txt_Time);
          //txt_total=findViewById(R.id.txt_total);
          //txt_random=findViewById(R.id.txt_now);
          calendarView=findViewById(R.id.calendarView_main);
          txtShownow=findViewById(R.id.txt_showRandom);
          calendarView.setMaxDate(c.getTimeInMillis());
          Calendar cbegin=Calendar.getInstance();
          cbegin.set(c.get(Calendar.YEAR),0,1);
          calendarView.setMinDate(cbegin.getTimeInMillis());
          //calendarView.setMaxDate(c.getTimeInMillis());

          /*handlerMsg=new Handler()
          {
              @Override
              public void handleMessage(Message msg) {
                 if (msg.obj!=null)
                 {
                     txttime.setText(msg.obj.toString());
                 }

              }
          };*/


    }

    private void InitHistory() {
        String dataListStr = getRead().ReadHistory(fileName);
        if (dataListStr.length() > 0) {
            dataList = (WalletDataList) JsonUtil.stringToObject(dataListStr, WalletDataList.class);
            if (dataList != null) {
                DateUtil dateUtil = new DateUtil(date);
                WalletData datnow = null;
                for (WalletData data : dataList.getDataList()) {
                    DateUtil temputil = new DateUtil(data.getTodayTime());
                    if (dateUtil.getDay() == temputil.getDay() && dateUtil.getMonth() == temputil.getMonth() && dateUtil.getYear() == temputil.getYear())
                    {
                        datnow = data;
                    }

                }
                if (datnow!=null)
                {
                    String shwoInfo ="第"+datnow.getDayInyear()+"天"+dateUtil.getYear() + "年" + (dateUtil.getMonth()) + "月" + dateUtil.getDay() + "日:"+ datnow.getTodayRandomMoney() + "元"+"\r\n";
                    shwoInfo += "共"+dataList.getDataList().size() + "天" + "\r\n" + "合计" + dataList.getTotal() + "元";
                    txtShownow.setText(shwoInfo);

                }


            }
        }
    }


    private void GetRandomInfo()
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        WalletData dataNow=null;
        if (dataList==null)
        {
            InitHistory();
        }
        if (dataList==null||(dataList!=null&&dataList.getDataList().size()==0))
        {
            dataList=new WalletDataList();
            dataList.setDataList(new ArrayList<WalletData>());
            dataNow=new WalletData();
            dataNow.setTodayTime(date);
            dataNow.setTodayRandomMoney(GetRandomdata());
            ArrayList<WalletData> dataTemp=dataList.getDataList();
            dataTemp.add(dataNow);
            dataList.setDataList(dataTemp);
            String waleetjson=JsonUtil.objectToString(dataList);
            getRead().Save(fileName,waleetjson);
        }
        else
        {
            for (WalletData dattemp:dataList.getDataList())
            {
                String source=formatter.format(dattemp.getTodayTime());
                String resource=formatter.format(date);
                if (source.equals(resource))
                {
                    dataNow=dattemp;
                    break;

                }
            }
            while (dataNow==null)
            {

                int randomTemp=GetRandomdata();
                boolean isFinddata=false;
                for (WalletData dattemp:dataList.getDataList())
                {
                   if (dattemp.getTodayRandomMoney()==randomTemp)
                   {
                       isFinddata=true;
                       break;
                   }

                }
                if (!isFinddata)
                {

                    dataNow=new WalletData();
                    dataNow.setTodayTime(date);
                    dataNow.setTodayRandomMoney(randomTemp);
                    ArrayList<WalletData> dataListTemp=dataList.getDataList();
                    dataListTemp.add(dataNow);
                    dataList.setDataList(dataListTemp);
                    String waleetjson=JsonUtil.objectToString(dataList);
                    getRead().Save(fileName,waleetjson);
                }
            }
        }
        //Toast.makeText(MainActivity.this,String.valueOf(dataNow.getTodayRandomMoney()),Toast.LENGTH_LONG).show();
        //txtShownow.setText(String.valueOf(dataNow.getTodayRandomMoney()));
    }

    private void  Create()
    {
        Calendar c = Calendar.getInstance();
        c.set(2018,0,1);
        Date dtbegin=c.getTime();
        Calendar end = Calendar.getInstance();
        end.set(2018,2,3);
        Date dtEnd=end.getTime();
        while (dtbegin.getTime()<dtEnd.getTime())
        {
            CreateHistTory(dtbegin);
            dtbegin=AddTime(dtbegin);
        }
    }

    private Date  AddTime(Date dt)
    {

        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE,1);// 今天+1天

        Date tomorrow = c.getTime();
        return tomorrow;
    }

    private void  CreateHistTory(Date dt)
    {
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        WalletData dataNow=null;
        if (dataList==null||(dataList!=null&&dataList.getDataList().size()==0))
        {
            dataList=new WalletDataList();
            dataList.setDataList(new ArrayList<WalletData>());
            dataNow=new WalletData();
            dataNow.setTodayTime(dt);
            dataNow.setTodayRandomMoney(GetRandomdata());
            ArrayList<WalletData> dataTemp=dataList.getDataList();
            dataTemp.add(dataNow);
            dataList.setDataList(dataTemp);
            String waleetjson=JsonUtil.objectToString(dataList);
            getRead().Save(fileName,waleetjson);
        }
        else
        {
            for (WalletData dattemp:dataList.getDataList())
            {
                String source=formatter.format(dattemp.getTodayTime());
                String resource=formatter.format(dt);
                if (source.equals(resource))
                {
                    dataNow=dattemp;
                    break;

                }
            }
            while (dataNow==null)
            {

                int randomTemp=GetRandomdata();
                boolean isFinddata=false;
                for (WalletData dattemp:dataList.getDataList())
                {
                    if (dattemp.getTodayRandomMoney()==randomTemp)
                    {
                        isFinddata=true;
                        break;
                    }

                }
                if (!isFinddata)
                {

                    dataNow=new WalletData();
                    dataNow.setTodayTime(dt);
                    dataNow.setTodayRandomMoney(randomTemp);
                    ArrayList<WalletData> dataListTemp=dataList.getDataList();
                    dataListTemp.add(dataNow);
                    dataList.setDataList(dataListTemp);
                    String waleetjson=JsonUtil.objectToString(dataList);
                    getRead().Save(fileName,waleetjson);
                }
            }
        }
    }

    private ReadUtil read=null;
    private ReadUtil getRead()
    {
        if (read==null)
        {
            read=new ReadUtil(MainActivity.this);
        }
        return read;
    }
    private int GetRandomdata()
    {
        Random random=new Random();
        int data=0;
        while (data==0)
        {
          data=random.nextInt(366);
        }
        return data;

    }



}
