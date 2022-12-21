package com.example.projectg104.Services;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.R;
import com.example.projectg104.Util;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProductService {
    public ArrayList<Product> cursorToArray(@NonNull Cursor cursor){
        ArrayList<Product> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else{
            while (cursor.moveToNext()){
                Product product = new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        Integer.parseInt(cursor.getString(3)),
                        cursor.getString(4),
                        Boolean.valueOf(cursor.getString(5)),
                        Util.stringToDate(cursor.getString(6)),
                        Util.stringToDate(cursor.getString(7)),
                        Double.parseDouble(cursor.getString(8)),
                        Double.parseDouble(cursor.getString(9))
                );
                list.add(product);
            }
        }
        return list;
    }
}
