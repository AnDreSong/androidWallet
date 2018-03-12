package android.wallet.android.walletapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.wallet.android.walletapplication.Coom.JsonUtil;
import android.wallet.android.walletapplication.Coom.ReadUtil;
import android.wallet.android.walletapplication.Data.WalletAdapter;
import android.wallet.android.walletapplication.Data.WalletData;
import android.wallet.android.walletapplication.Data.WalletDataList;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    ListView listView;
    String fileName="WalletDataList";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        Calendar c=Calendar.getInstance();
        fileName=fileName+c.get(Calendar.YEAR);
        readList();
        InitMainFunctions();
    }
    WalletDataList dataList=null;

    private void  readList()
    {
        ReadUtil data=new ReadUtil(HistoryActivity.this);
        String historydata=data.ReadHistory(fileName);
        if (historydata!=null&&historydata.length()>0)
        {
            dataList=(WalletDataList) JsonUtil.stringToObject(historydata,WalletDataList.class);
        }

    }

    private void InitMainFunctions()
    {
        final List<WalletData> functionsList= dataList.getDataList();
         WalletAdapter adapter=new WalletAdapter(HistoryActivity.this,R.layout.maintitle,functionsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                int total=dataList.getTotal();
                Toast.makeText(HistoryActivity.this,dataList.getDataList().size()+"天"+"合计:"+total+"元",Toast.LENGTH_LONG).show();
            }
        });

    }
}
