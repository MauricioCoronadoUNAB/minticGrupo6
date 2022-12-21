package com.example.projectg104.Services;

import android.database.Cursor;

import androidx.annotation.NonNull;

import com.example.projectg104.DBHelperUtil;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.Util;

import java.util.ArrayList;

public class ProductUtil {
    public ArrayList<Product> toProductList(@NonNull Cursor cursor){
        return toProductList(DBHelperUtil.cursorToArray(cursor));
    }
    public ArrayList<Product> toProductList(@NonNull ArrayList<ArrayList<String>> data){
        ArrayList<Product> productList = new ArrayList<>();
        for (ArrayList<String> item : data) {
            Product product = new Product();
            product.setId(item.get(0));
            product.setName(item.get(1));
            product.setDescription(item.get(2));
            product.setPrice(Integer.parseInt(item.get(3)));
            product.setImage(item.get(4));
            product.setDeleted(Boolean.valueOf(item.get(5)));
            product.setUpdatedAt(Util.stringToDate(item.get(6)));
            product.setCreatedAt(Util.stringToDate(item.get(7)));
            product.setLatitud(Double.parseDouble(item.get(8)));
            product.setLongitud(Double.parseDouble(item.get(9)));
            productList.add(product);
        }

        return productList;
    }
}
