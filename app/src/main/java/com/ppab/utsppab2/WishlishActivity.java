package com.ppab.utsppab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class WishlishActivity extends AppCompatActivity {
    private ArrayList<destination> destinations;
    private RecyclerView rv;
    private destinationAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlish);

        rv = findViewById(R.id.rvDestination);
        rv.setLayoutManager(new LinearLayoutManager(WishlishActivity.this));
        rv.setItemAnimator(new DefaultItemAnimator());
        Bundle extras = getIntent().getExtras();
        destinations = extras.getParcelableArrayList("destinations");
        adapter = new destinationAdapter(destinations, WishlishActivity.this, WishlishActivity.this);
        rv.setAdapter(adapter);
    }
}