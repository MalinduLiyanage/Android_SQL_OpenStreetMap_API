package com.example.map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapEventsReceiver;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.MapEventsOverlay;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configuration.getInstance().setUserAgentValue("com.example.map");

        Button map_btn = findViewById(R.id.mapBtn);

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DatabaseActivity.class);
                startActivity(intent);
            }
        });

        dbHandler = new DBHandler(MainActivity.this);

        MapView map = findViewById(R.id.map);
        map.setTileSource(TileSourceFactory.MAPNIK); // Set the tile source
        map.setMultiTouchControls(true); // Enable multi-touch zoom and pan

        // Set the default map center coordinates (Rajarata University of Sri Lanka)
        GeoPoint startPoint = new GeoPoint(8.3608034, 80.4988308);
        map.getController().setCenter(startPoint);
        map.getController().setZoom(15.0);

        // Create a custom implementation of MapEventsReceiver
        MapEventsReceiver mapEventsReceiver = new MapEventsReceiver() {
            @Override
            public boolean singleTapConfirmedHelper(GeoPoint p) {
                // Convert the tapped geo point to latitude and longitude
                double latitude = p.getLatitude();
                double longitude = p.getLongitude();

                // Display a toast message with the coordinates
                String message = "Tapped Location - Latitude: " + latitude + ", Longitude: " + longitude;
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean longPressHelper(GeoPoint p) {
                // Convert the tapped geo point to latitude and longitude
                double latitude = p.getLatitude();
                double longitude = p.getLongitude();

                String latString = Double.toString(latitude);
                String longString = Double.toString(longitude);

                boolean success = dbHandler.addNewLocation(latString, longString);

                if (success) {
                    // Show toast message indicating successful addition
                    String message = "Location added to DB";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    // Show toast message indicating failure
                    String message = "Failed to add to DB";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                }

                return true;

            }

        };

        // Add the MapEventsReceiver to the map
        MapEventsOverlay mapEventsOverlay = new MapEventsOverlay(mapEventsReceiver);
        map.getOverlays().add(0, mapEventsOverlay); // Add the overlay to the map
    }
}
