package android.wallet.android.walletapplication.Coom;

/**
 * Created by Andre on 2018/2/7.
 */
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import java.util.List;
import java.util.ArrayList;
/**
 * Created by Andre on 2018-01-25.
 * Json解析辅助类
 */

public class JsonUtil {
    private static Gson mGson = new Gson();
    /**
     * 将json字符串转化成实体对象
     * @param json
     * @param classOfT
     * @return
     */
    public static Object stringToObject( String json , Class classOfT)
    {
        return  mGson.fromJson( json , classOfT ) ;
    }
    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }
    /**
     * 把json 字符串转化成list
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToList(String json , Class<T> cls  )
    {
        Log.d("JsonStr",json);
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list ;
    }
}
