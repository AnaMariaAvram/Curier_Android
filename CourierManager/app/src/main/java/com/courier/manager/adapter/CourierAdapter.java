package com.courier.manager.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.courier.manager.database.CourierEntity;
import com.courier.manager.databinding.ItemCourierBinding;

import java.util.ArrayList;
import java.util.List;

//clasa adaptor necesara pentru creareea recycler view-ului
public class CourierAdapter extends RecyclerView.Adapter<CourierViewHolder> {

    private final CourierListener listener;
    private List<CourierEntity> couriers = new ArrayList<>();

    public CourierAdapter(CourierListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CourierViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourierBinding binding = ItemCourierBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CourierViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull CourierViewHolder holder, int position) {
        CourierEntity courier = couriers.get(position);
        holder.bind(courier);
    }

    @Override
    public int getItemCount() {
        return couriers != null ? couriers.size() : 0;
    }

    public void submitCourierList(List<CourierEntity> couriers) {
        this.couriers = couriers;
    }


}
