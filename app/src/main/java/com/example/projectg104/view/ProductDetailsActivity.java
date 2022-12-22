package com.example.projectg104.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectg104.Entities.Product;
import com.example.projectg104.ProductUtil;
import com.example.projectg104.R;
import com.example.projectg104.ViewUtil;

public class ProductDetailsActivity extends AppCompatActivity {
    //private DBHelper dbHelper;
    private Button btnProductList;
    private TextView textProductName, textProductDescription, textProductPrice;
    private ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);
        bindFields();
        Intent intentIn = getIntent();
        Product product = ProductUtil.getProduct(intentIn);
        setFields(product);
        initEventOnClick();
    }
    private void bindFields(){
        btnProductList = (Button) findViewById(R.id.btnProductList);
        textProductName = (TextView) findViewById(R.id.textProductName);
        textProductPrice = (TextView) findViewById(R.id.textProductPrice);
        textProductDescription = (TextView) findViewById(R.id.textProductDescription);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
    }
    private void setFields(@NonNull Product data){
        int Col = data.getPrice() * 5000;
        int Usd = data.getPrice();
        String prices = "USD: "+Usd;
        textProductName.setText(data.getName());
        textProductDescription.setText(data.getDescription());
        textProductPrice.setText(prices);
        ViewUtil.insertUriToImageView(ProductDetailsActivity.this,imgProduct,data.getImage());
    }
    private void initEventOnClick(){
        btnProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);
            }
        });
    }
}