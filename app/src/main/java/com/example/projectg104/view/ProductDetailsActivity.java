package com.example.projectg104.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectg104.R;
import com.example.projectg104.Services.ProductUtil;
import com.example.projectg104.ViewUtil;

public class ProductDetailsActivity extends AppCompatActivity {
    //private DBHelper dbHelper;
    private ProductUtil productService;
    private Button btnProductInfo;
    private TextView textProductName, textProductDescription, textProductPrice;
    private ImageView imgProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_details_activity);

        btnProductInfo = (Button) findViewById(R.id.btnProductInfo);
        textProductName = (TextView) findViewById(R.id.textProductName);
        textProductPrice = (TextView) findViewById(R.id.textProductPrice);
        textProductDescription = (TextView) findViewById(R.id.textProductDescription);
        imgProduct = (ImageView) findViewById(R.id.imgProduct);
        //dbHelper = new DBHelper(this);
        productService = new ProductUtil();

        Intent intentIn = getIntent();



        textProductName.setText(intentIn.getStringExtra("name"));
        textProductDescription.setText(intentIn.getStringExtra("description"));
        textProductPrice.setText(String.valueOf(intentIn.getIntExtra("price",0)));
        ViewUtil.insertUriToImageView(ProductDetailsActivity.this,imgProduct,intentIn.getStringExtra("image"));

        btnProductInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);
            }
        });
    }
}