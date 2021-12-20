package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.Preferences.UserPreferences;
import com.example.project.Model.User;
import com.google.firebase.auth.FirebaseAuth;

import java.io.ByteArrayOutputStream;

public class ProfilActivity extends AppCompatActivity {
    private TextView pName,pEmail,pPhone,pAge,pAddress;
    private ImageView pImage;
    private UserPreferences userPreferences;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        pName = findViewById(R.id.pNama);
        pEmail = findViewById(R.id.pEmail);
        pPhone = findViewById(R.id.pPhone);
        pAge = findViewById(R.id.pAge);
        pAddress = findViewById(R.id.pAlamat);
        pImage = findViewById(R.id.ivImage);

        pName.setText(UserData.name);
        pEmail.setText(UserData.email);
        pPhone.setText(UserData.phone);
        pAddress.setText(UserData.address);
        pAge.setText(UserData.age);
        byte[] bytes = Base64.decode(UserData.image,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        pImage.setImageBitmap(bitmap);

        Button editBtn = findViewById(R.id.EditProfilBtn);
        Button logoutBtn = findViewById(R.id.logoutBtn);
        bottomNavigation();
        userPreferences = new UserPreferences(ProfilActivity.this);
        user = userPreferences.getUserLogin();
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPreferences.logout();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
                finish();
                Toast.makeText(ProfilActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
            }
        });
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this, EditProfileActivity.class));
            }
        });
    }

    private void checklogin() {
        if (!userPreferences.checkLogin()) {
            startActivity(new Intent(ProfilActivity.this, LoginActivity.class));
            finish();
        } else {
            Toast.makeText(ProfilActivity.this, "Heyy kamu sudah login!!", Toast.LENGTH_SHORT).show();
        }
    }


    private void bottomNavigation() {
        LinearLayout profilBtn = findViewById(R.id.profilBtn);
        LinearLayout card_btn = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        profilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, ProfilActivity.class));
            }
        });
        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfilActivity.this, MainActivity.class));
            }
        });
    }



}