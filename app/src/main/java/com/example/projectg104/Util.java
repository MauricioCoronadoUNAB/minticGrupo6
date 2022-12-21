package com.example.projectg104;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Bitmap byteToBitmap(byte[] image){
        Bitmap bitmap  = BitmapFactory.decodeByteArray(image, 0, image.length );
        return bitmap;
    }
    @Nullable
    public static Date stringToDate (String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    @NonNull
    public static String dateToString (Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("ErrorDate", dateFormat.format(date));
        return dateFormat.format(date);
    }
}
