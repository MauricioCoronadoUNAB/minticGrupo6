package com.example.projectg104;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;

public class ViewUtil {

    public byte[] imageViewToByte(@NonNull ImageView imageView){
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
    public static void insertUriToImageView(Context context, ImageView imageView, String url){
        Glide.with(context)
                .load(url)
                .override(500,500)
                .into(imageView);
    }

}
