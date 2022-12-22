package com.example.projectg104.view;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectg104.DB.DBFirebase;
import com.example.projectg104.DB.DBHelper;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.ProductUtil;
import com.example.projectg104.R;
import com.example.projectg104.Util;
import com.example.projectg104.ViewUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.MapEventsOverlay;
import org.osmdroid.views.overlay.Marker;

public class ProductEditActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private DBFirebase dbFirebase;
    private Button btnFormProduct;
    private EditText editNameFormProduct, editDescriptionFormProduct, editPriceFormProduct, editIdFormProduct;
    private ImageView imgFormProduct;
    private TextView textLatitudFormProduct, textLongitudFormProduct;
    private ImageButton btnToggleMap;
    private ConstraintLayout mapContainer;
    private MapView map;
    private MapController mapController;
    private StorageReference storageReference;
    private String urlImage = "";
    ActivityResultLauncher<String> content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intentIN;
        Boolean edit;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_edit_activity);
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        intentIN = getIntent();
        edit = intentIN.getBooleanExtra("edit", false);
        bindFields();
        initRepository();
        initMap();
        initActivityResultImage();
        initEventOnclick(intentIN,edit);
        if(edit){
            btnFormProduct.setText("Actualizar");
            Product product = ProductUtil.getProduct(intentIN);
            setFields(product);
            setMap(product);
        }
    }
    public void clean(){
        editNameFormProduct.setText("");
        editDescriptionFormProduct.setText("");
        editPriceFormProduct.setText("");
        imgFormProduct.setImageResource(R.drawable.empty);
    }
    private void bindFields(){
        btnFormProduct = (Button) findViewById(R.id.btnFormProduct);
        editNameFormProduct = (EditText) findViewById(R.id.editNameFormProduct);
        editDescriptionFormProduct = (EditText) findViewById(R.id.editDescriptionFormProduct);
        editPriceFormProduct = (EditText) findViewById(R.id.editPriceFormProduct);
        imgFormProduct = (ImageView) findViewById(R.id.imgFormProduct);
        textLatitudFormProduct = (TextView) findViewById(R.id.textLatitudFormProduct);
        textLongitudFormProduct = (TextView) findViewById(R.id.textLongitudFormProduct);
        btnToggleMap = (ImageButton) findViewById(R.id.btnToggleMap);
        mapContainer = (ConstraintLayout) findViewById(R.id.mapContainer);
    }
    private void setFields(@NonNull Product data){
        int Col = data.getPrice() * 5000;
        int Usd = data.getPrice();
        String prices = /*"USD: "+ */ String.valueOf(Usd);
        editNameFormProduct.setText(data.getName());
        editDescriptionFormProduct.setText(data.getDescription());
        editPriceFormProduct.setText(prices);
        textLatitudFormProduct.setText(Util.getString(data.getLatitud(),""));
        textLongitudFormProduct.setText(Util.getString(data.getLongitud(),""));
        setImageFields(Uri.parse(data.getImage()));
    }
    @NonNull
    private Product getFields(){
        Product product = new Product();
        product.setName(editNameFormProduct.getText().toString());
        product.setDescription(editDescriptionFormProduct.getText().toString());
        product.setPrice(Integer.parseInt(editPriceFormProduct.getText().toString()));
        product.setImage(urlImage);
        product.setLatitud(Double.parseDouble(textLatitudFormProduct.getText().toString().trim()));
        product.setLongitud(Double.parseDouble(textLongitudFormProduct.getText().toString().trim()));
        return product;
    }
    private void setImageFields(Uri uri){
        if(uri == null || uri.toString() == null || uri.toString().isEmpty()) {
            imgFormProduct.setImageResource(R.mipmap.ic_img_not_available);
            return;
        }
        Uri downloadUrl = uri;
        urlImage = downloadUrl.toString();
        ViewUtil.insertUriToImageView(ProductEditActivity.this,imgFormProduct,urlImage);
    }
    private void initMap(){
        map = (MapView) findViewById(R.id.mapForm);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        mapController = (MapController) map.getController();
        GeoPoint colombia = new GeoPoint(4.570868, -74.297333);
        mapController.setCenter(colombia);
        mapController.setZoom(12);
        map.setMultiTouchControls(true);
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(this, getEventMap());
        map.getOverlays().add(mapEventsOverlay);
    }
    @NonNull
    private MapEventsReceiver getEventMap(){
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(@NonNull GeoPoint p) {
                textLatitudFormProduct.setText(String.valueOf(p.getLatitude()));
                textLongitudFormProduct.setText(String.valueOf(p.getLongitude()));
                return false;
            }
            @Override
            public boolean longPressHelper(GeoPoint p) {
                return false;
            }
        };
        return  mapEventsReceiver;
    }
    private void setMap(@NonNull Product data){
        GeoPoint geoPoint = new GeoPoint(data.getLatitud(), data.getLongitud());
        Marker marker = new Marker(map);
        marker.setPosition(geoPoint);
        map.getOverlays().add(marker);
    }
    private void initActivityResultImage(){
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri result) {saveImage(result);}
                }
        );
    }
    private void initEventOnclick(Intent intent,boolean edit){
        imgFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {content.launch("image/*");}
        });

        btnFormProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Product product = getFields();
                    saveData(product,edit,intent.getStringExtra("id"));
                }catch (Exception e){
                    Log.e("DB Insert", e.toString());
                }

                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                startActivity(intent);
            }
        });
        btnToggleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapContainer.setVisibility((mapContainer.getVisibility() == View.GONE)?View.VISIBLE:View.GONE);
            }
        });
    }
    private void initRepository(){
        storageReference = FirebaseStorage.getInstance().getReference();
        try {
            dbHelper = new DBHelper(this);
            dbFirebase = new DBFirebase();
        }catch (Exception e){
            Log.e("DB", e.toString());
        }
    }
    private void saveData(Product data,boolean edit,String id){
        if(edit){
            data.setId(id);
            dbFirebase.updateData(data);
        }else{
            //dbHelper.insertData(data);
            dbFirebase.insertData(data);
        }
    }
    private void saveImage(Uri uri){
        Uri _uri = uri;
        StorageReference filePath = storageReference.child("images").child(_uri.getLastPathSegment());
        filePath.putFile(_uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getApplicationContext(), "Imagen Cargada", Toast.LENGTH_SHORT).show();
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {setImageFields(uri);}
                });
            }
        });
    }
}