package android.wallet.android.walletapplication.Coom;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * @author Andre
 * @date 2018/3/5  8:36
 * @className ReadUtil
 */

public class ReadUtil {

    private Context ctx;
    public ReadUtil(Context x)
    {
        ctx=x;
    }
    public   String  ReadHistory(String fileName)
    {
        FileInputStream in=null;
        BufferedReader reader=null;
        String context="";
        try {

            in=ctx.openFileInput(fileName);
            reader=new BufferedReader(new InputStreamReader(in));
            context=reader.readLine();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return context;
    }
    public void Save(String filename,String saveResult)
    {
        if (saveResult!=null)
        {
            String jsonConfig= saveResult;

            FileOutputStream out=null;
            BufferedWriter writer=null;
            try {
                out=ctx.openFileOutput(filename,Context.MODE_PRIVATE);
                writer=new BufferedWriter(new OutputStreamWriter(out));
                writer.write(jsonConfig);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            finally {
                try {
                    if (writer!=null)
                    {
                        writer.close();
                    }

                }
                catch (IOException er)
                {
                    er.printStackTrace();
                }
            }

        }
    }
}
