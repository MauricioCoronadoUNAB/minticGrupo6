package com.example.projectg104.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.AlertDialog;

import androidx.annotation.NonNull;

import com.example.projectg104.DB.DBFirebase;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.ProductUtil;
import com.example.projectg104.ViewUtil;
import com.example.projectg104.view.ProductListActivity;
import com.example.projectg104.view.ProductDetailsActivity;
import com.example.projectg104.view.ProductEditActivity;
import com.example.projectg104.R;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> arrayProducts;

    //---
    private ImageView imgProduct;
    private TextView textNameProduct;
    private TextView textDescriptionProduct;
    private TextView textPriceProduct;
    private Button btnEditTemplate;
    private Button btnDeleteTemplate;
    //---

    public ProductAdapter(Context context, ArrayList<Product> arrayProducts) {
        this.context = context;
        this.arrayProducts = arrayProducts;
    }
    @Override
    public int getCount() {
        return arrayProducts.size();
    }
    @Override
    public Object getItem(int i) {
        return arrayProducts.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        view = layoutInflater.inflate(R.layout.product_list_item_layout, null);
        bindFields(view);
        Product product = arrayProducts.get(i);
        setFields(product);
        initEventOnClick(product);
        return view;
    }
    //---
    private void bindFields(@NonNull View view){
        imgProduct = (ImageView) view.findViewById(R.id.imgProduct);
        textNameProduct = (TextView) view.findViewById(R.id.textNameProduct);
        textDescriptionProduct = (TextView) view.findViewById(R.id.textDescriptionProduct);
        textPriceProduct = (TextView) view.findViewById(R.id.textPriceProduct);
        btnEditTemplate = (Button) view.findViewById(R.id.btnEditTemplate);
        btnDeleteTemplate = (Button) view.findViewById(R.id.btnDeleteTemplate);
    }
    private void setFields(@NonNull Product data){
        int Col = data.getPrice() * 5000;
        int Usd = data.getPrice();
        String prices = "USD: "+Usd;
        textNameProduct.setText(data.getName());
        textDescriptionProduct.setText(data.getDescription());
        textPriceProduct.setText(prices);
        ViewUtil.insertUriToImageView(context,imgProduct,data.getImage());
    }
    private void initEventOnClick(Product product){
        imgProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductDetailsActivity.class);
                ProductUtil.putProduct(intent,product);
                context.startActivity(intent);
            }
        });
        btnEditTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context.getApplicationContext(), ProductEditActivity.class);
                intent.putExtra("edit", true);
                ProductUtil.putProduct(intent,product);
                context.startActivity(intent);
            }
        });
        btnDeleteTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("¿Estas seguro que deseas eliminar el producto?")
                        .setTitle("Confirmación")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteProduct(product.getId());
                                Intent intent = new Intent(context.getApplicationContext(), ProductListActivity.class);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {}
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        });        
    }
    private void deleteProduct(String id){
        DBFirebase dbFirebase = new DBFirebase();
        dbFirebase.deleteData(id);
    }
}
