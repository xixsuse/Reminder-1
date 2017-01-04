package com.xiachunle.reminder.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by xiachunle on 2016/12/11.
 */

public class FileUtil {

    public static String END_FLAG="@end@";

    //获取应用的安装路径
    public static String getDataPath(Context context) {
        return context.getApplicationContext().getFilesDir().getPath();
    }


    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        return sf.format(date);
    }


    public static String getSDPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }




    public static byte[] bitmapToByte(Bitmap bm) {
        if (bm != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            return out.toByteArray();
        }
        return null;
    }

    public static Bitmap byteToBitmap(byte[] data) {
        if (data.length > 1) {
            return BitmapFactory.decodeByteArray(data, 0, data.length);
        }
        return null;
    }


    public static byte[] serialize(HashMap<String, byte[]> hashMap) {
        if (hashMap == null)
            return null;
        try {
            ByteArrayOutputStream mem_out = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(mem_out);

            out.writeObject(hashMap);
            out.close();
            mem_out.close();
            byte[] bytes = mem_out.toByteArray();
            return bytes;
        } catch (Exception e) {
            return null;
        }
    }

    public static HashMap<String, byte[]> deserialize(byte[] bytes) {
        if (bytes == null)
            return null;
        try {
            ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(mem_in);
            HashMap<String, byte[]> hashMap = (HashMap<String, byte[]>) in.readObject();
            in.close();
            mem_in.close();
            return hashMap;
        } catch (Exception e) {
            return null;
        }
    }


    public static Bitmap smallBimap(int width, int height, String path) {
        Bitmap bm = BitmapFactory.decodeFile(path);
        double targetWidth;
        if (width * height <= (768 * 1000)) {//限制图片不超过768K
            targetWidth = Math.sqrt(width * height * 1.0f);

        } else {
            targetWidth = Math.sqrt(768 * 1000);
        }
        if (bm.getWidth() > targetWidth || bm.getHeight() > targetWidth) {
            Matrix matrix = new Matrix();
            double x = Math.max(targetWidth / bm.getWidth(), targetWidth / bm.getHeight());
            matrix.postScale((float) x, (float) x);
            bm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        }
        return bm;
    }



    public static int dp2px(Context context,float dp)
    {
        return (int ) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }


    public static int getScreenWidth(Context context)
    {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE );
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics( outMetrics);
        return outMetrics .widthPixels ;
    }


    public static String parseTime(String time){

        Date date = null;

        try {
            if (time != null) {
                date = new SimpleDateFormat("yyyy年MM月dd日 HH:mm").parse(time);
            } else {
                date = null;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new SimpleDateFormat(" MM-dd ").format(date);
    }
    public static String getSecondTime(){

        Date date =new Date();
        return new SimpleDateFormat("HH:mm:ss").format(date);
    }
}
