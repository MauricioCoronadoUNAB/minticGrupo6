package com.example.projectg104.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.projectg104.Adapters.ProductAdapter;
import com.example.projectg104.DB.DBFirebase;
import com.example.projectg104.DB.DBHelper;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.R;
import com.example.projectg104.Services.ProductService;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private ProductService productService;
    private ListView listViewProducts;
    private ProductAdapter productAdapter;
    private ArrayList<Product> arrayProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_list_activity);
        arrayProducts = new ArrayList<>();
        try {
            //dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
            productService = new ProductService();
            Cursor cursor = dbHelper.getData();
            arrayProducts = productService.cursorToArray(cursor);
            //if(arrayProducts.size() == 0){
               // dbFirebase.syncData(dbHelper);
            //}
        }catch (Exception e){
            Log.e("Database", e.toString());
        }

        listViewProducts = (ListView) findViewById(R.id.listViewProducts);

        productAdapter = new ProductAdapter(this, arrayProducts);
        listViewProducts.setAdapter(productAdapter);

        dbFirebase.getData(productAdapter, arrayProducts);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.actionAdd:
                intent = new Intent(getApplicationContext(), ProductEditActivity.class);
                startActivity(intent);
                return true;
            case R.id.actioMap:
                intent = new Intent(getApplicationContext(), MapsActivity.class);
                ArrayList<String> latitudes = new ArrayList<>();
                ArrayList<String> longitudes = new ArrayList<>();

                for(int i=0; i<arrayProducts.size(); i++){
                    latitudes.add(String.valueOf(arrayProducts.get(i).getLatitud()));
                    longitudes.add(String.valueOf(arrayProducts.get(i).getLongitud()));
                }
                intent.putStringArrayListExtra("latitudes", latitudes);
                intent.putStringArrayListExtra("longitudes", longitudes);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}