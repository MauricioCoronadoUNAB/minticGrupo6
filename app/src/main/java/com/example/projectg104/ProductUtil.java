package com.example.projectg104;

import android.database.Cursor;
import androidx.annotation.NonNull;
import com.example.projectg104.Entities.Product;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductUtil {

    @NonNull
    public static ArrayList<Product> toProductList(@NonNull Cursor cursor){
        return toProductList(DBHelperUtil.cursorToArray(cursor));
    }
    @NonNull
    public static ArrayList<Product> toProductList(@NonNull ArrayList<ArrayList<String>> data){
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
    @NonNull
    public static Map<String, Object> toProductMap(@NonNull Product data){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("id",data.getId() );
        dataMap.put("name", data.getName());
        dataMap.put("description", data.getDescription());
        dataMap.put("price", data.getPrice());
        dataMap.put("image", data.getImage());
        dataMap.put("deleted", data.isDeleted());
        dataMap.put("createdAt", data.getCreatedAt());
        dataMap.put("updatedAt", data.getUpdatedAt());
        dataMap.put("latitud", data.getLatitud());
        dataMap.put("longitud", data.getLongitud());
        return dataMap;
    }
    public static Map<String, Object> toProductMapMini(@NonNull Product data){
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", data.getName());
        dataMap.put("description", data.getDescription());
        dataMap.put("price", data.getPrice());
        dataMap.put("image", data.getImage());
        dataMap.put("latitud", data.getLatitud());
        dataMap.put("longitud", data.getLongitud());
        return dataMap;
    }
    @NonNull
    public static Product toProduct(@NonNull Map<String, Object> data){
        Product product = new Product();
        product.setId(data.get("id").toString());
        product.setName(data.get("name").toString());
        product.setDescription(data.get("description").toString());
        product.setPrice(Integer.parseInt(data.get("price").toString()));
        product.setImage(data.get("image").toString());
        product.setDeleted(Boolean.valueOf(data.get("deleted").toString()));
        product.setCreatedAt(Util.stringToDate(data.get("createdAt").toString()));
        product.setUpdatedAt(Util.stringToDate(data.get("updatedAt").toString()));
        product.setLatitud(Double.parseDouble(data.get("latitud").toString()));
        product.setLongitud(Double.parseDouble(data.get("longitud").toString()));
        return product;
    }
}
