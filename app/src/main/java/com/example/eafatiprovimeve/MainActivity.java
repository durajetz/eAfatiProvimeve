package com.example.eafatiprovimeve;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    public static final int MY_PERMISSIONS_REQUEST_CALENDAR = 99;
    public static final int ADD_PROVIMI_REQUEST = 1;
    public static final int EDIT_PROVIMI_REQUEST = 2;
    private AfatiProvimeveModel afatiProvimeveModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        String email = user.getEmail();

        FloatingActionButton buttonAddProvimi = findViewById(R.id.button_add_provimi);
        buttonAddProvimi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                startActivityForResult(intent, ADD_PROVIMI_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        AfatiProvimeveAdapter adapter = new AfatiProvimeveAdapter(this);

        recyclerView.setAdapter(adapter);

//        if (Build.VERSION.SDK_INT >= 23 &&
//                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
//            checkLocationPermission();
//        }

        afatiProvimeveModel = new ViewModelProvider(this).get(AfatiProvimeveModel.class);
        afatiProvimeveModel.getAllProvimet().observe(this, new Observer<List<AfatiProvimeve>>() {
            @Override
            public void onChanged(List<AfatiProvimeve> afatiProvimeves) {
                // update RecyclerView
//                Toast.makeText(MainActivity.this, "onChanged", Toast.LENGTH_SHORT).show();
                adapter.setProvimet(afatiProvimeves);
            }
        });
    }
}