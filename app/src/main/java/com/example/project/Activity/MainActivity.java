package com.example.project.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.project.Adapter.PopularAdapter;
import com.example.project.Domain.FoodDomain;
import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter2;
    private TextView judul;
    private RecyclerView recyclerViewPopularList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        judul = findViewById(R.id.textView4);
        judul.setText(UserData.name);

        recyclerViewPopular();
        bottomNavigation();
    }

    private void bottomNavigation() {
        LinearLayout profilBtn = findViewById(R.id.profilBtn);
        LinearLayout card_btn = findViewById(R.id.card_btn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);

        profilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfilActivity.class));
            }
        });

        card_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
    }

    private void recyclerViewPopular() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerView2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("California Roll", "california", "California Roll adalah sushi berisi Ketimun, Kepiting dan Alpukat", 10000));
        foodlist.add(new FoodDomain("Fish Cake Roll", "fishcake", "Fish Cake Roll adalah sushi berbahan dasar ikan dengan sedikit sayur", 11000));
        foodlist.add(new FoodDomain("Katsu Roll", "katsu", "Katsu Roll adalah sushi berbahan dasar ayam dengan sedikit sayur", 12000));
        foodlist.add(new FoodDomain("Tempura Roll", "tempura", "Tempura Roll adalah sushi berbahan dasar udang dengan sedikit sayur", 10500));

        adapter2 = new PopularAdapter(foodlist);
        recyclerViewPopularList.setAdapter(adapter2);

    }

    public void logout1(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        finish();
    }
}