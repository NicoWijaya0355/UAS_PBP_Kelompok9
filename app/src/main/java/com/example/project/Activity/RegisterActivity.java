package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;

public class RegisterActivity extends AppCompatActivity {
    EditText mName,mEmail,mPhone,mPassword,mAge,mAddress;
    Button mbtnRegister;
    TextView mtxtLogin;
    FirebaseAuth fAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mName = findViewById(R.id.etName);
        mEmail = findViewById(R.id.etEmail);
        mPhone = findViewById(R.id.etPhone);
        mPassword = findViewById(R.id.etPassword);
        mAge = findViewById(R.id.etAge);
        mAddress = findViewById(R.id.etAlamat);
        mbtnRegister = findViewById(R.id.btnRegister);
        mtxtLogin = findViewById(R.id.txtLogin);

        fAuth = FirebaseAuth.getInstance();

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mbtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String email = mEmail.getText().toString().trim();
                String password =mPassword.getText().toString().trim();

                if(TextUtils.isEmpty(mName.getText().toString().trim())){
                    mName.setError("Name is Required");
                    return;
                }

                if(TextUtils.isEmpty(mAge.getText().toString().trim())){
                    mAge.setError("Age is Required");
                    return;
                }

                if(TextUtils.isEmpty(mPhone.getText().toString().trim())){
                    mPhone.setError("Phone Number is Required");
                    return;
                }

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(mAddress.getText().toString().trim())){
                    mAddress.setError("Address is Required");
                    return;
                }

                if(TextUtils.isEmpty((password))){
                    mPassword.setError("Password is Required");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password must be >= 6 characters");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //link verifikasi
                            FirebaseUser fuser =fAuth.getCurrentUser();
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(RegisterActivity.this, "Verification Email has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                            UserData.name=mName.getText().toString().trim();
                            UserData.email=mEmail.getText().toString().trim();
                            UserData.age=mAge.getText().toString().trim();
                            UserData.address=mAddress.getText().toString().trim();
                            UserData.phone=mPhone.getText().toString().trim();
                            UserData.password=mPassword.getText().toString().trim();

                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.noimage);
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte[] byteArray = stream .toByteArray();
                            String image  = Base64.encodeToString(byteArray, Base64.DEFAULT);
                            UserData.image = image;
                        }else{
                            Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        mtxtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}