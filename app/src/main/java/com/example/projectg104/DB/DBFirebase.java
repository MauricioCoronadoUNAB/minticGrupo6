package com.example.projectg104.DB;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.projectg104.Adapters.ProductAdapter;
import com.example.projectg104.Entities.Product;
import com.example.projectg104.ProductUtil;
import com.example.projectg104.Util;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DBFirebase {
    private FirebaseFirestore db;

    public DBFirebase(){
        this.db = FirebaseFirestore.getInstance();
    }
    public void insertData(@NonNull Product prod){
        // Create a new user with a first and last name
        Map<String, Object> product = ProductUtil.toProductMap(prod);
        // Add a new document with a generated ID
        db.collection("products")
            .add(product)
            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(TAG, "Error adding document", e);
                }
            });
        //db.terminate();
    }
    public void getData(ProductAdapter productAdapter, ArrayList<Product> list){
        db.collection("products")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Product product = null;
                            if(!Boolean.valueOf(document.getData().get("deleted").toString())){
                                product = ProductUtil.toProduct(document.getData());
                                list.add(product);
                            }
                        }
                        productAdapter.notifyDataSetChanged();
                    } else {
                        Log.w(TAG, "Error getting documents.", task.getException());
                    }
                }
            });
    }
    public void deleteData(String id){
        db.collection("products").whereEqualTo("id",id)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().delete();
                            }
                        }
                    }
                });
    }
    public void updateData(@NonNull Product producto){
        Map<String, Object> productMap = ProductUtil.toProductMapMini(producto);
        db.collection("products").whereEqualTo("id", producto.getId())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                documentSnapshot.getReference().update(productMap);
                            }
                        }
                    }
                });
    }
}
