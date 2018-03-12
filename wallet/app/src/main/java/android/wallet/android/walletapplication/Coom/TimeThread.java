package android.wallet.android.walletapplication.Coom;

import android.os.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Handler;

/**
 * @author Andre
 * @date 2018/3/5  9:20
 * @className TimeThread
 */

public class TimeThread extends Thread {
  private android.os.Handler handlermsg;
  public TimeThread(android.os.Handler handler)
  {
      handlermsg=handler;
  }
    @Override
    public void run() {
        while (true)
        {

            SimpleDateFormat formatter = new SimpleDateFormat ("yyyy年MM月dd日 HH:mm:ss ");
            Date nowdate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(nowdate);
            Message msg=handlermsg.obtainMessage();
            msg.obj=str;
            msg.what=000;
            handlermsg.sendMessage(msg);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }
    }
}
