package com.example.map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

public class DatabaseActivity extends AppCompatActivity {

    RecyclerView recyclerViewLocations;
    LocationAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        recyclerViewLocations = findViewById(R.id.recyclerViewLocations);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(this));

        // Load locations from database
        loadLocations();
    }

    private void loadLocations() {
        // Create an instance of DBHandler
        DBHandler dbHandler = new DBHandler(this);

        // Get all locations from database using getAllLocations() method from DBHandler
        List<Location> locations = dbHandler.getAllLocations();

        // Create and set adapter
        adapter = new LocationAdaptor(this, locations);
        recyclerViewLocations.setAdapter(adapter);
    }
}
