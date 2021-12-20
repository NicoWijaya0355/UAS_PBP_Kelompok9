package com.example.project.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Preferences.UserPreferences;
import com.example.project.R;
import com.example.project.Volley.AdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private TextView txtRegister;
    private MaterialButton btnLogin;
    private UserPreferences userPreferences;
    FirebaseAuth auth;
    private String CHANNEL_ID = "Channel 1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        txtRegister=findViewById(R.id.txtRegister);
        auth = FirebaseAuth.getInstance();
        btnLogin= findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);
        if(auth.getCurrentUser()!=null && auth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Email is Required", Toast.LENGTH_SHORT).show();
                }
                else if(etPassword.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password is Required", Toast.LENGTH_SHORT).show();


                }
                else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()) {
                    if(etEmail.getText().toString().equals("admin") && etPassword.getText().toString().equals("admin")){
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid Email address", Toast.LENGTH_SHORT).show();
                    }

                }
                 else {
                    auth.signInWithEmailAndPassword(etEmail.getText().toString(),etPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        if(auth.getCurrentUser().isEmailVerified()){
                                            Toast.makeText(LoginActivity.this,"Login Successfull",Toast.LENGTH_SHORT).show();
                                            createNotificationChannel();
                                            addNotification();
                                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(LoginActivity.this, "Silahkan Verifikasi Email untuk Login", Toast.LENGTH_SHORT).show();
                                        }

                                    }else
                                    {
                                        Toast.makeText(LoginActivity.this,"Login Error !" +task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

    }

    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel 1";
            String description = "This is Channel 1";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Education Center")
                .setContentText("Selamat Datang "+etEmail.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

