package android.wallet.android.walletapplication.Data;

import java.util.ArrayList;

/**
 * @author Andre
 * @date 2018/3/5  8:45
 * @className WalletDataList
 */

public class WalletDataList {

    public ArrayList<WalletData> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<WalletData> dataList) {
        this.dataList = dataList;
    }

    public int getTotal() {
       if(dataList!=null)
       {
           total=0;
           for (WalletData data:getDataList())
           {
             total+=data.getTodayRandomMoney();
           }
       }
       return total;
    }


    private ArrayList<WalletData> dataList;

    private int total;
}
