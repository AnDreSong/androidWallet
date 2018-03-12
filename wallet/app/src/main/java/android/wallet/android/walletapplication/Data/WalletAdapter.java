package android.wallet.android.walletapplication.Data;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.wallet.android.walletapplication.R;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Andre
 * @date 2018/3/5  15:24
 * @className WalletAdapter
 */

public class WalletAdapter extends ArrayAdapter<WalletData>
{
    private int resourceId;
    SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
    public WalletAdapter(@NonNull Context context, int resource, @NonNull List<WalletData> objects)
    {
        super(context, resource, objects);
        resourceId=resource;
        formatter = new SimpleDateFormat ("yyyy-MM-dd");
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        WalletData functions = getItem(position); // 获取当前项的Function实例
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.dayInyear=(TextView)view.findViewById(R.id.txt_dayInyear);
            viewHolder.dateFunction = (TextView) view.findViewById (R.id.txt_dateInfo);
            viewHolder.functionText = (TextView) view.findViewById (R.id.fuction_name);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.dateFunction.setText(formatter.format(functions.getTodayTime()));
        viewHolder.functionText.setText(String.valueOf(functions.getTodayRandomMoney()));
        viewHolder.dayInyear.setText("第"+functions.getDayInyear()+"天");
        return view;
    }
    class ViewHolder {

        TextView dateFunction;
        TextView functionText;
        TextView dayInyear;

    }
}
