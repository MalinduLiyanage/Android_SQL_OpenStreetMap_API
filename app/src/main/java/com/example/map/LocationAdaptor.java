package com.example.map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LocationAdaptor extends RecyclerView.Adapter<LocationAdaptor.LocationViewHolder> {

    private Context context;
    private List<Location> locationList;

    public LocationAdaptor(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.textViewLocationID.setText(String.valueOf(location.getId()));
        holder.textViewLocationLatitude.setText(location.getLatitude());
        holder.textViewLocationLongitude.setText(location.getLongitude());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocationID, textViewLocationLatitude, textViewLocationLongitude;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocationID = itemView.findViewById(R.id.textViewLocationName);
            textViewLocationLatitude = itemView.findViewById(R.id.textViewLocationLatitude);
            textViewLocationLongitude = itemView.findViewById(R.id.textViewLocationLongitude);
        }
    }
}
