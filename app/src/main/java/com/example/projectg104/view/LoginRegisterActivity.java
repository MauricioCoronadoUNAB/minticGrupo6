package com.example.projectg104.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectg104.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRegisterActivity extends AppCompatActivity {
    private Button btnRegisterReg;
    private EditText editEmailReg, editPassReg, editConfirmReg;
    private FirebaseAuth mAuth;
    String email,pass,confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_register_activity);
        bindFields();
        initAuth();
        initEventOnClick();
    }
    public void bindFields(){
        btnRegisterReg = (Button) findViewById(R.id.btnRegisterReg);
        editEmailReg = (EditText) findViewById(R.id.editEmailReg);
        editPassReg = (EditText) findViewById(R.id.editPassReg);
        editConfirmReg = (EditText) findViewById(R.id.editConfirmReg);
    }
    public void getFields(){
        email = editEmailReg.getText().toString().trim();
        pass = editPassReg.getText().toString().trim();
        confirm = editConfirmReg.getText().toString().trim();
    }
    public void initAuth(){
        mAuth = FirebaseAuth.getInstance();
    }
    public void register(String email, String pass){
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Usuario creado",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Error en Registro", Toast.LENGTH_SHORT).show();
                        Log.e("UserCreate", e.toString());
                    }
                });
    }
    public void initEventOnClick(){
        btnRegisterReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFields();
                if(pass.compareTo(confirm) == 0){
                    register(email,pass);
                }else{
                    Toast.makeText(getApplicationContext(),"Contraseña no coincide",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}