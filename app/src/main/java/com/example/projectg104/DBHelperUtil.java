package com.example.projectg104;

import android.database.Cursor;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class DBHelperUtil {
    @NonNull
    public static ArrayList<ArrayList<String>> cursorToArray(@NonNull Cursor cursor){
        ArrayList<ArrayList<String>> list = new ArrayList<>();
        if(cursor.getCount() == 0){
            return list;
        }else {
            while (cursor.moveToNext()) {
                ArrayList<String> item = new ArrayList<>();
                for (int i = 0; i < cursor.getColumnCount(); i++) item.add(cursor.getString(i));
                list.add(item);
            }
        }
        return list;
    }
}
